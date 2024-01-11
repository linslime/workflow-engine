package com.workflow.engine.entity;

import lombok.Data;
import java.util.Date;

@Data
public class FlowWork {
    private String id;
    private String flowName;
    private String flowDefineCode;
    private String lastOperator;
    private String currentNodeId;
    private String nextNodeId;
    private String flowDefine;
    private int  status;
    private String flowParam;
    private Date createTime;
    private Date updateTime;
    private int isDeleted;
}
