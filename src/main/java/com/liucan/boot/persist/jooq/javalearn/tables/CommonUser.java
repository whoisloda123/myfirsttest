/*
 * This file is generated by jOOQ.
 */
package com.liucan.boot.persist.jooq.javalearn.tables;


import com.liucan.boot.persist.jooq.javalearn.Indexes;
import com.liucan.boot.persist.jooq.javalearn.JavaLearn;
import com.liucan.boot.persist.jooq.javalearn.Keys;
import com.liucan.boot.persist.jooq.javalearn.tables.records.CommonUserRecord;
import org.jooq.*;
import org.jooq.impl.DSL;
import org.jooq.impl.TableImpl;

import javax.annotation.Generated;
import java.util.Arrays;
import java.util.List;


/**
 * This class is generated by jOOQ.
 */
@Generated(
        value = {
                "http://www.jooq.org",
                "jOOQ version:3.10.7"
        },
        comments = "This class is generated by jOOQ"
)
@SuppressWarnings({"all", "unchecked", "rawtypes"})
public class CommonUser extends TableImpl<CommonUserRecord> {

    private static final long serialVersionUID = -464941250;

    /**
     * The reference instance of <code>java_learn.common_user</code>
     */
    public static final CommonUser COMMON_USER = new CommonUser();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<CommonUserRecord> getRecordType() {
        return CommonUserRecord.class;
    }

    /**
     * The column <code>java_learn.common_user.id</code>. 用户id
     */
    public final TableField<CommonUserRecord, Integer> ID = createField("id", org.jooq.impl.SQLDataType.INTEGER.nullable(false).identity(true), this, "用户id");

    /**
     * The column <code>java_learn.common_user.name</code>. 用户名
     */
    public final TableField<CommonUserRecord, String> NAME = createField("name", org.jooq.impl.SQLDataType.VARCHAR(100).nullable(false).defaultValue(org.jooq.impl.DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)), this, "用户名");

    /**
     * Create a <code>java_learn.common_user</code> table reference
     */
    public CommonUser() {
        this(DSL.name("common_user"), null);
    }

    /**
     * Create an aliased <code>java_learn.common_user</code> table reference
     */
    public CommonUser(String alias) {
        this(DSL.name(alias), COMMON_USER);
    }

    /**
     * Create an aliased <code>java_learn.common_user</code> table reference
     */
    public CommonUser(Name alias) {
        this(alias, COMMON_USER);
    }

    private CommonUser(Name alias, Table<CommonUserRecord> aliased) {
        this(alias, aliased, null);
    }

    private CommonUser(Name alias, Table<CommonUserRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, "");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Schema getSchema() {
        return JavaLearn.JAVA_LEARN;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Index> getIndexes() {
        return Arrays.<Index>asList(Indexes.COMMON_USER_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Identity<CommonUserRecord, Integer> getIdentity() {
        return Keys.IDENTITY_COMMON_USER;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<CommonUserRecord> getPrimaryKey() {
        return Keys.KEY_COMMON_USER_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<CommonUserRecord>> getKeys() {
        return Arrays.<UniqueKey<CommonUserRecord>>asList(Keys.KEY_COMMON_USER_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CommonUser as(String alias) {
        return new CommonUser(DSL.name(alias), this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CommonUser as(Name alias) {
        return new CommonUser(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public CommonUser rename(String name) {
        return new CommonUser(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public CommonUser rename(Name name) {
        return new CommonUser(name, null);
    }
}
