package com.workflow.engine.processor.impl;

import com.workflow.engine.entity.FlowWork;
import com.workflow.engine.event.FlowEndNodeEvent;
import com.workflow.engine.model.BaseModel;
import com.workflow.engine.model.EndModel;
import com.workflow.engine.model.FlowModel;
import com.workflow.engine.param.FlowParam;
import com.workflow.engine.processor.FlowNodeProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EndNodeProcessorImpl implements FlowNodeProcessor {
    @Autowired(required=false)
    private FlowEndNodeEvent flowEndEvent;
    @Override
    public void process(FlowWork flowWork, FlowModel flowModel, BaseModel currentNddeModel, FlowParam flowParam) {
        if(null == flowEndEvent) {
            EndModel endModel = flowModel.getEndModel();
            flowEndEvent.onEvent(flowWork, endModel, flowParam);;
        }
    }

    @Override
    public String getNodeType() {
        return "END";
    }

}
