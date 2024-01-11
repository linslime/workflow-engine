package com.workflow.engine.entity;

import lombok.Data;
import org.joda.time.DateTime;

import java.util.Date;

@Data
public class FlowDefine {

    private String id;
    private String flowDefineCode;
    private String flowName;
    private String description;
    private String flowDefine;
    private Date createTime;
    private Date updateTime;
    private int isDeleted;
}
