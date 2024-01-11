package com.workflow.engine.event;

import com.workflow.engine.entity.FlowWork;
import com.workflow.engine.model.EndModel;
import com.workflow.engine.param.FlowParam;

public interface FlowEndNodeEvent {
    /**
     *
     * @param flowWork 当前流程实例
     * @param endModel 结束节点
     * @param flowParam 流程参数
     */
    public void onEvent(FlowWork flowWork, EndModel endModel, FlowParam flowParam);
}
