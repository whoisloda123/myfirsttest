package com.liucan.boot.persist.mybatis.mode;

public class LoggingEventProperty {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column logging_event_property.event_id
     *
     * @mbg.generated Sat Apr 25 11:46:27 CST 2020
     */
    private Long eventId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column logging_event_property.mapped_key
     *
     * @mbg.generated Sat Apr 25 11:46:27 CST 2020
     */
    private String mappedKey;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column logging_event_property.mapped_value
     *
     * @mbg.generated Sat Apr 25 11:46:27 CST 2020
     */
    private String mappedValue;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column logging_event_property.event_id
     *
     * @return the value of logging_event_property.event_id
     *
     * @mbg.generated Sat Apr 25 11:46:27 CST 2020
     */
    public Long getEventId() {
        return eventId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column logging_event_property.event_id
     *
     * @param eventId the value for logging_event_property.event_id
     *
     * @mbg.generated Sat Apr 25 11:46:27 CST 2020
     */
    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column logging_event_property.mapped_key
     *
     * @return the value of logging_event_property.mapped_key
     *
     * @mbg.generated Sat Apr 25 11:46:27 CST 2020
     */
    public String getMappedKey() {
        return mappedKey;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column logging_event_property.mapped_key
     *
     * @param mappedKey the value for logging_event_property.mapped_key
     *
     * @mbg.generated Sat Apr 25 11:46:27 CST 2020
     */
    public void setMappedKey(String mappedKey) {
        this.mappedKey = mappedKey == null ? null : mappedKey.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column logging_event_property.mapped_value
     *
     * @return the value of logging_event_property.mapped_value
     *
     * @mbg.generated Sat Apr 25 11:46:27 CST 2020
     */
    public String getMappedValue() {
        return mappedValue;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column logging_event_property.mapped_value
     *
     * @param mappedValue the value for logging_event_property.mapped_value
     *
     * @mbg.generated Sat Apr 25 11:46:27 CST 2020
     */
    public void setMappedValue(String mappedValue) {
        this.mappedValue = mappedValue == null ? null : mappedValue.trim();
    }
}