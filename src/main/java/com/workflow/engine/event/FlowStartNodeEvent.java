package com.workflow.engine.event;

import com.workflow.engine.entity.FlowWork;
import com.workflow.engine.model.StartModel;
import com.workflow.engine.param.FlowParam;

public interface FlowStartNodeEvent {
    /**
     * @param flowWork 当前流程实例
     * @param startModel 开始节点模型
     * @param flowParam 流程参数
     */
    public void onEvent(FlowWork flowWork, StartModel startModel, FlowParam flowParam);

}
