package com.liucan.boot.service.db;

import com.liucan.boot.persist.jooq.amazondata.tables.records.ShopInfoRecord;
import com.liucan.boot.persist.jooq.javalearn.tables.records.CommonUserRecord;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import static com.liucan.boot.persist.jooq.amazondata.Tables.SHOP_INFO;
import static com.liucan.boot.persist.jooq.javalearn.Tables.COMMON_USER;

/**
 * @author liucan
 * @version 19-5-29
 */
@Service
public class JooqService {

    private final DSLContext javaLearnDSL;
    private final DSLContext amazonDSL;

    public JooqService(@Qualifier("javaLearnDSL") DSLContext javaLearnDSL,
                       @Qualifier("amazonDSL") DSLContext amazonDSL) {
        this.javaLearnDSL = javaLearnDSL;
        this.amazonDSL = amazonDSL;
    }

    public String getUserName(Integer userId) {
        CommonUserRecord commonUserRecord = javaLearnDSL.selectFrom(COMMON_USER)
                .where(COMMON_USER.ID.equal(userId))
                .fetchOne();
        return commonUserRecord.getName();
    }

    public String getShopName(Integer shopId) {
        ShopInfoRecord shopInfoRecord = amazonDSL.selectFrom(SHOP_INFO)
                .where(SHOP_INFO.SHOP_ID.eq(shopId))
                .fetchOne();
        return shopInfoRecord.getShopName();
    }
}
