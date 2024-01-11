package com.workflow.engine.processor.impl;

import com.workflow.engine.entity.FlowWork;
import com.workflow.engine.event.FlowStartNodeEvent;
import com.workflow.engine.model.BaseModel;
import com.workflow.engine.model.FlowModel;
import com.workflow.engine.model.StartModel;
import com.workflow.engine.param.FlowParam;
import com.workflow.engine.processor.FlowNodeProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class StartNodeProcessorImpl implements FlowNodeProcessor {
    @Autowired(required=false)
    private FlowStartNodeEvent flowStartEvent;
    @Override
    public void process(FlowWork flowWork, FlowModel flowModel, BaseModel currentNddeModel, FlowParam flowParam) {
        // 开始节点事件--
        if(null != flowStartEvent) {
            StartModel startModel = flowModel.getStartModel();
            flowStartEvent.onEvent(flowWork, startModel, flowParam);
        }
    }
    @Override
    public String getNodeType() {
        return "START";
    }


}
