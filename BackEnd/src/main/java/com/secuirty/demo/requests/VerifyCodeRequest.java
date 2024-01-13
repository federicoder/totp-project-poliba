package com.secuirty.demo.requests;

import lombok.Data;

@Data
public class VerifyCodeRequest {
    private String username;
    private String code;
}
