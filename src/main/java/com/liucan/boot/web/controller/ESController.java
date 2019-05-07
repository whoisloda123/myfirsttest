package com.liucan.boot.web.controller;

import com.liucan.boot.service.es.AccountElasticsearchRepository;
import com.liucan.boot.service.es.AccountInfo;
import com.liucan.boot.web.common.CommonResponse;
import org.elasticsearch.common.lucene.search.function.CombineFunction;
import org.elasticsearch.common.lucene.search.function.FieldValueFactorFunction;
import org.elasticsearch.common.lucene.search.function.FiltersFunctionScoreQuery;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.functionscore.FunctionScoreQueryBuilder;
import org.elasticsearch.index.query.functionscore.ScoreFunctionBuilder;
import org.elasticsearch.index.query.functionscore.ScoreFunctionBuilders;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

/**
 * @author liucan
 * @date 2018/9/9
 * @brief
 */
@RestController
@RequestMapping("es")
public class ESController {
    private final AccountElasticsearchRepository accountElasticsearchRepository;

    public ESController(AccountElasticsearchRepository accountElasticsearchRepository) {
        this.accountElasticsearchRepository = accountElasticsearchRepository;
    }

    @GetMapping("account_info_by_id")
    public CommonResponse queryAccountInfoById(Integer id) {
        Optional<AccountInfo> optionalAccountInfo = accountElasticsearchRepository.findById(id);
        long count = accountElasticsearchRepository.count();
        List<AccountInfo> accountInfoByAgeBetween = accountElasticsearchRepository.findAccountInfosByAgeBetween(36, 37);

        //自定义查询，类似于mybatis的自定义xml
        QueryBuilder queryBuilder = QueryBuilders.boolQuery()
                .must(QueryBuilders.termQuery("age", 36))
                .mustNot(QueryBuilders.termQuery("firstname", "Vivian"));
        Page<AccountInfo> page = accountElasticsearchRepository.search(queryBuilder, PageRequest.of(1, 10));

        return CommonResponse.ok(optionalAccountInfo.orElse(null));
    }

    @GetMapping("query_account")
    public CommonResponse queryAccount(Integer pageSize, Integer pageNum, String searchContext) {
        //查询
        QueryBuilder queryBuilder = QueryBuilders
                .boolQuery()
                .filter(QueryBuilders.rangeQuery("age").from(35).to(36));

        //从新打分
        //fieldValueFactor
        ScoreFunctionBuilder scoreFunctionBuilder = ScoreFunctionBuilders
                .fieldValueFactorFunction("balance")
                .modifier(FieldValueFactorFunction.Modifier.LN1P)
                .factor(0.1f);

        QueryBuilder functionScoreQueryBuilder = QueryBuilders
                .functionScoreQuery(queryBuilder, scoreFunctionBuilder)
                .boostMode(CombineFunction.SUM);

        //多score-function
        //查找account：个性推荐，年龄35最好，和35相差5岁也能接收，收入越高越好
        Integer userId = 1233;
        FunctionScoreQueryBuilder.FilterFunctionBuilder[] filterFunctionBuilders = new FunctionScoreQueryBuilder.FilterFunctionBuilder[]{
                //个性推荐，不同用户最好展示不同的结果
                new FunctionScoreQueryBuilder.FilterFunctionBuilder(ScoreFunctionBuilders.randomFunction(userId)),
                //权重
                new FunctionScoreQueryBuilder.FilterFunctionBuilder(ScoreFunctionBuilders.weightFactorFunction(0.1f)),
                //fieldValueFactor，收入越高越好
                new FunctionScoreQueryBuilder.FilterFunctionBuilder(ScoreFunctionBuilders
                        .fieldValueFactorFunction("balance")
                        .modifier(FieldValueFactorFunction.Modifier.LN1P)
                        .factor(0.1f)),
                //衰减函数，35岁左右，相差5岁是满意岁数，相差超出5岁开始衰减
                new FunctionScoreQueryBuilder.FilterFunctionBuilder(ScoreFunctionBuilders
                        .gaussDecayFunction("age", 35, 10, 5, 0.4))
        };

        QueryBuilder queryBuilder1 = QueryBuilders
                .boolQuery()
                .filter(QueryBuilders.matchQuery("firstname", searchContext));

        QueryBuilder functionScoreQueryBuilder1 = QueryBuilders
                .functionScoreQuery(queryBuilder1, filterFunctionBuilders)
                .scoreMode(FiltersFunctionScoreQuery.ScoreMode.SUM)
                .boostMode(CombineFunction.REPLACE);

        Page<AccountInfo> page1 = accountElasticsearchRepository.search(functionScoreQueryBuilder1,
                PageRequest.of(1, 10));

        Page<AccountInfo> page = accountElasticsearchRepository.search(functionScoreQueryBuilder,
                PageRequest.of(pageSize, pageNum));
        return CommonResponse.ok(page.getContent());
    }

    @PostMapping("add_document")
    public CommonResponse addDocument() {
        AccountInfo accountInfo = new AccountInfo();
        accountInfo.setAccount_number(1000);
        accountInfo.setAddress("中国");
        accountInfo.setAge(32);
        accountInfo.setBalance(32131);
        accountInfo.setCity("成都");
        accountInfo.setEmail("553670590@qq.com");
        accountInfo.setEmployer("Blanet");
        accountInfo.setFirstname("刘");
        accountInfo.setLastname("灿");
        accountInfo.setGender("F");
        accountInfo.setState("NJ");
        accountElasticsearchRepository.save(accountInfo);
        return CommonResponse.ok();
    }
}
