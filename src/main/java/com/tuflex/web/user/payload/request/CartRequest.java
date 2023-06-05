package com.tuflex.web.user.payload.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartRequest {
    private Long rid;
    private Integer adult, child;
    private String startDate, endDate;
}
