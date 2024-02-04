package com.secuirty.demo.requests;

import com.secuirty.demo.service.dto.Parameter;

import lombok.Data;

@Data
public class VerifyCodeRequest  {
    private String username;
    private String code;
    private Parameter data;
}
