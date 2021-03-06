/*
 * This file is generated by jOOQ.
 */
package com.liucan.boot.persist.jooq.amazondata.tables.records;


import com.liucan.boot.persist.jooq.amazondata.tables.ShopInfo;
import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record3;
import org.jooq.Row3;
import org.jooq.impl.UpdatableRecordImpl;

import javax.annotation.Generated;


/**
 * 店铺表
 */
@Generated(
        value = {
                "http://www.jooq.org",
                "jOOQ version:3.10.7"
        },
        comments = "This class is generated by jOOQ"
)
@SuppressWarnings({"all", "unchecked", "rawtypes"})
public class ShopInfoRecord extends UpdatableRecordImpl<ShopInfoRecord> implements Record3<Integer, Integer, String> {

    private static final long serialVersionUID = 237270944;

    /**
     * Setter for <code>amazon_data.shop_info.id</code>.
     */
    public void setId(Integer value) {
        set(0, value);
    }

    /**
     * Getter for <code>amazon_data.shop_info.id</code>.
     */
    public Integer getId() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>amazon_data.shop_info.shop_id</code>. 店铺id
     */
    public void setShopId(Integer value) {
        set(1, value);
    }

    /**
     * Getter for <code>amazon_data.shop_info.shop_id</code>. 店铺id
     */
    public Integer getShopId() {
        return (Integer) get(1);
    }

    /**
     * Setter for <code>amazon_data.shop_info.shop_name</code>. 店铺名
     */
    public void setShopName(String value) {
        set(2, value);
    }

    /**
     * Getter for <code>amazon_data.shop_info.shop_name</code>. 店铺名
     */
    public String getShopName() {
        return (String) get(2);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Record1<Integer> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Record3 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row3<Integer, Integer, String> fieldsRow() {
        return (Row3) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row3<Integer, Integer, String> valuesRow() {
        return (Row3) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field1() {
        return ShopInfo.SHOP_INFO.ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field2() {
        return ShopInfo.SHOP_INFO.SHOP_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field3() {
        return ShopInfo.SHOP_INFO.SHOP_NAME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer component1() {
        return getId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer component2() {
        return getShopId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component3() {
        return getShopName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value1() {
        return getId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value2() {
        return getShopId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value3() {
        return getShopName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ShopInfoRecord value1(Integer value) {
        setId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ShopInfoRecord value2(Integer value) {
        setShopId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ShopInfoRecord value3(String value) {
        setShopName(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ShopInfoRecord values(Integer value1, Integer value2, String value3) {
        value1(value1);
        value2(value2);
        value3(value3);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached ShopInfoRecord
     */
    public ShopInfoRecord() {
        super(ShopInfo.SHOP_INFO);
    }

    /**
     * Create a detached, initialised ShopInfoRecord
     */
    public ShopInfoRecord(Integer id, Integer shopId, String shopName) {
        super(ShopInfo.SHOP_INFO);

        set(0, id);
        set(1, shopId);
        set(2, shopName);
    }
}
