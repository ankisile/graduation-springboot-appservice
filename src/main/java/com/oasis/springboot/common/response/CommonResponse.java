package com.oasis.springboot.common.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommonResponse {
    boolean success;
    int code;
    String message;
}
