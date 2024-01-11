package com.workflow.engine.event;

import com.workflow.engine.entity.FlowWork;
import com.workflow.engine.model.TaskModel;
import com.workflow.engine.param.FlowParam;

public interface FlowTaskNodeEvent {
    /**
     * 任务开始事件
     * @param flowWork 当前流程实例
     * @param taskModel 任务节点模型
     * @param flowParam 流程参数
     */
    public void OnEvent(FlowWork flowWork, TaskModel taskModel, FlowParam flowParam);

}
