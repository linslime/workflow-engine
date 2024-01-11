package com.workflow.engine.entity;

import lombok.Data;

import java.util.Date;

@Data
public class FlowTask {
    private String id;
    private String flowWorkId;
    private String flowNodeId;
    private String taskName;
    private String operator;
    private String actorUserId;
    private int status;
    private String serviceType;
    private String serviceId;
    private Date finishTime;
    private String flowParam;
    private Date createTime;
    private Date updateTime;
    private int isDeleted;
}
