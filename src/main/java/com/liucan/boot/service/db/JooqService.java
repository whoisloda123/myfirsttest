package com.liucan.boot.service.db;

import com.liucan.boot.persist.jooq.javalearn.tables.records.CommonUserRecord;
import lombok.AllArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.stereotype.Service;

import static com.liucan.boot.persist.jooq.javalearn.Tables.COMMON_USER;

/**
 * @author liucan
 * @version 19-5-29
 */
@AllArgsConstructor
@Service
public class JooqService {
    private final DSLContext dslContext;

    public String getUserName(Integer userId) {
        CommonUserRecord commonUserRecord = dslContext.selectFrom(COMMON_USER)
                .where(COMMON_USER.ID.equal(userId))
                .fetchOne();
        return commonUserRecord.getName();
    }
}
