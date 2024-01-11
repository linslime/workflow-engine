package com.workflow.engine.processor;

import com.workflow.engine.entity.FlowWork;
import com.workflow.engine.model.BaseModel;
import com.workflow.engine.model.FlowModel;
import com.workflow.engine.param.FlowParam;

public interface FlowNodeProcessor {
    /**
     * 要处理的节点类型
     * @return
     */
    public String getNodeType();
    /**
     * 流程节点处理方法
     * @param flowWork 流程实例
     * @param flowModel 当前流程模型
     * @param currentNodeModel 当前节点模型
     * @param flowParam 流程参数
     */
    public void process(FlowWork flowWork, FlowModel flowModel, BaseModel currentNodeModel, FlowParam flowParam);

}
