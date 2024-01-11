package com.workflow.engine.param;

import lombok.Data;

import java.util.List;

@Data
public class FlowParam {
    private String serviceId;
    private String serviceType;
    private List<String> userIds;
    private String operator;

}
