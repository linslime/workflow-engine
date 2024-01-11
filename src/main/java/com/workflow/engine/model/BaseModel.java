package com.workflow.engine.model;

import java.util.Map;
import lombok.Data;

@Data
public class BaseModel {

    /**
     * 节点id
     */
    private String id;

    /**
     * 节点名称
     */
    private String name;

    /**
     * 节点类型
     */
    private String nodeType;

    /**
     * 下一个节点id
     */
    private String nextNodeId;

    /**
     * 扩展属性
     */
    private Map<String,Object> ext;

}
