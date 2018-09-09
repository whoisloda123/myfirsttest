package com.liucan.controller;

import com.liucan.common.es.AccountElasticsearchRepository;
import com.liucan.common.es.AccountInfo;
import com.liucan.common.response.CommonResponse;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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

    @RequestMapping("account_info_by_id")
    public CommonResponse queryAccountInfoById(String id) {
        Optional<AccountInfo> optionalAccountInfo = accountElasticsearchRepository.findById(id);
        long count = accountElasticsearchRepository.count();
        List<AccountInfo> accountInfoByAgeBetween = accountElasticsearchRepository.findAccountInfosByAgeBetween(36, 37);

        //自定义查询，类似于mybatis的自定义xml
        QueryBuilder queryBuilder = QueryBuilders.boolQuery()
                .must(QueryBuilders.termQuery("age", 36))
                .mustNot(QueryBuilders.termQuery("firstname", "Vivian"));
        Page<AccountInfo> page = accountElasticsearchRepository.search(queryBuilder, PageRequest.of(1, 10));

        //字段权重值设置
//        FunctionScoreQueryBuilder functionScoreQueryBuilder = QueryBuilders.functionScoreQuery(
//                QueryBuilders.boolQuery().should(QueryBuilders.matchQuery("firstname", "Vivian")),
//                FunctionScoreQueryBuilder.FilterFunctionBuilder);


        return CommonResponse.ok(optionalAccountInfo.orElse(null));
    }
}
