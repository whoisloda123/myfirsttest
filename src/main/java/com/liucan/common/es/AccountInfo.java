package com.liucan.common.es;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import java.io.Serializable;

/**
 * @author liucan
 * @date 2018/9/9
 * @brief es索引实体类, 某个实体需要建立索引，只需要加上@Document、@Field注解即可
 * 1.参考资料：https://blog.csdn.net/hololens/article/details/78932628，
 * https://www.cnblogs.com/guozp/p/8686904.html
 * https://blog.csdn.net/laoyang360/article/details/52244917
 * 2.index：相当于mysql数据库，type：相当于表，document：相当于一行数据，filed：相当于一列
 * 3.shards：分区，replicas：分区复制
 * 4.@Document注解之后，默认情况下这个实体中所有的属性都会被建立索引、并且分词,
 * 通过@Field注解来进行详细的指定，如果没有特殊需求，那么只需要添加@Document即可。
 * 5.加上@Id注解后，在Elasticsearch里对应的该列就是主键了，在查询时就可以直接用主键查询。mysql非常类似，基本就是一个数据库。
 */
@Data
@Document(indexName = "bank", type = "account", shards = 3, replicas = 2)
public class AccountInfo implements Serializable {
    @Id
    private Integer account_number;
    private Integer balance;
    private String firstname;
    private String lastname;
    private Integer age;
    private String gender;
    private String address;
    private String employer;
    private String email;
    private String city;
    private String state;
}
