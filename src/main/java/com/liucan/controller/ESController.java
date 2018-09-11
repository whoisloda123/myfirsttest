package com.liucan.controller;

import com.liucan.common.es.AccountElasticsearchRepository;
import com.liucan.common.es.AccountInfo;
import com.liucan.common.response.CommonResponse;
import org.elasticsearch.common.lucene.search.function.CombineFunction;
import org.elasticsearch.common.lucene.search.function.FieldValueFactorFunction;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.functionscore.ScoreFunctionBuilder;
import org.elasticsearch.index.query.functionscore.ScoreFunctionBuilders;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.GetMapping;
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
    public CommonResponse queryAccountInfoById(String id) {
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

        //衰减函数,用于位置或者时间
        ScoreFunctionBuilders.gaussDecayFunction("age", 35, 5, 10, 0.4);
        //可用于个性推荐
        ScoreFunctionBuilders.randomFunction(325);

        QueryBuilder functionScoreQueryBuilder = QueryBuilders
                .functionScoreQuery(queryBuilder, scoreFunctionBuilder)
                .boostMode(CombineFunction.SUM);

        Page<AccountInfo> page = accountElasticsearchRepository.search(functionScoreQueryBuilder,
                PageRequest.of(pageSize, pageNum));
        return CommonResponse.ok(page.getContent());
    }
}
