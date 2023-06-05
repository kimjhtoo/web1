package com.tuflex.web.user.payload.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewRequest {
    private Long hid;
    private Integer rating;
    private String content;
}
