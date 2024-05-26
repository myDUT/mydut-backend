package com.capstoneproject.mydut.payload.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @author vndat00
 * @since 5/26/2024
 */

@Getter
@Setter
@SuperBuilder
public class ListDTO<T> implements Serializable {
    private Long totalElements;
    private List<T> items;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Map<Object, Object> metadata;
}
