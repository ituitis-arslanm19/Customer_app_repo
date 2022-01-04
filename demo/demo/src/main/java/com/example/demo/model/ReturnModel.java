package com.example.demo.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter

public class ReturnModel {
    private Boolean successful;
    private String code;
    private String message;
    private Object result;


}
