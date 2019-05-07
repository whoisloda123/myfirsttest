package com.liucan.boot.web.common;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Collections;
import java.util.List;

/**
 * @author liucan
 * @version 18-11-25
 */
@Data
@AllArgsConstructor(staticName = "of")
public class ListResponse {
    public static final ListResponse EMPTY_LIST = ListResponse.of(false, Collections.emptyList());
    @JsonProperty("hsa_next")
    private Boolean hasNext;
    private List<?> list;
}
