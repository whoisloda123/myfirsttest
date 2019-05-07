package com.liucan.boot.service.es;

import com.github.pagehelper.Page;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.awt.print.Pageable;
import java.util.List;

/**
 * @author liucan
 * @date 2018/9/9
 * @brief account数据查询
 *        1.我们只要使用特定的单词对方法名进行定义，那么Spring就会对我们写的方法名进行解析
 *        2.该机制条前缀find…By，read…By，query…By，count…By，和get…By从所述方法和开始分析它的其余部分。
 *          引入子句可以包含进一步的表达式，如Distinct在要创建的查询上设置不同的标志。然而，
 *          第一个By作为分隔符来指示实际标准的开始。在非常基础的层次上，您可以定义实体属性的条件并将它们与And和连接起来Or。
 *        3.类似于mybatis-generator生成的文件
 */
public interface AccountElasticsearchRepository extends ElasticsearchRepository<AccountInfo, Integer> {
    List<AccountInfo> findAccountInfosByAgeBetween(Integer first, Integer second);

    Page<AccountInfo> findAccountInfoByFirstname(String firstName, Pageable pageable);
}
