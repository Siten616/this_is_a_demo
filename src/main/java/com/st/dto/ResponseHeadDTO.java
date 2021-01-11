package com.st.dto;

import lombok.Data;

@Data
public class ResponseHeadDTO {
    //流水号
    private String seqNo;
    //响应状态
    private int status;
    //ESB响应代码
    private String esbCode;
    //ESB响应信息
    private String esbMessage;
    //应用响应代码
    private String appCode;
    //应用响应信息
    private String appMessage;
    //消费方流水号
    private String consumerSeqNo;
    //提供方流水号
    private String providerSeqNo;
}
