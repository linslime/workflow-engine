package com.workflow.engine.event;

import com.workflow.engine.entity.FlowTask;
import com.workflow.engine.entity.FlowWork;
import com.workflow.engine.param.FlowParam;

public interface FlowTaskCreatedEvent {
    /**
     *
     * @param flowWork 当前流程实例
     * @param flowTask 当前任务实例
     * @param flowParam 流程参数
     */
    public void onEvent(FlowWork flowWork, FlowTask flowTask, FlowParam flowParam);


}
