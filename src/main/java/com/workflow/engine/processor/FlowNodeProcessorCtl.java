package com.workflow.engine.processor;

import com.workflow.engine.entity.FlowWork;
import com.workflow.engine.mapper.FlowWorkMapper;
import com.workflow.engine.model.BaseModel;
import com.workflow.engine.model.EndModel;
import com.workflow.engine.model.FlowModel;
import com.workflow.engine.model.StartModel;
import com.workflow.engine.param.FlowParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class FlowNodeProcessorCtl {
    @Autowired(required=false)
    private List<FlowNodeProcessor> flowNodeProcessorList;
    @Resource
    private FlowWorkMapper flowWorkMapper;

    /**
     * 节点流程处理器控制
     * @param flowWork 当前流程实例
     * @param flowModel	当前流程实例模型
     * @param model 当前节点模型
     * @param param 流程参数
     */
    public void process(FlowWork flowWork, FlowModel flowModel, BaseModel model, FlowParam param){
        FlowWork upFlowWork = new FlowWork();
        if(model == null) {
            return;
        }
        // 执行开始节点
        for(FlowNodeProcessor processor:flowNodeProcessorList) {
            if(processor.getNodeType().equals(model.getNodeType())){
                BaseModel nextNodeModel = flowModel.getNodeModel(model.getNextNodeId());
                if(model instanceof StartModel) { //开始节点
                    process(flowWork,flowModel,nextNodeModel, param);
                } else if(model instanceof EndModel)  {
                    // 结束节点
                    upFlowWork.setStatus(30);
                    Date now = new Date();
                    upFlowWork.setId(flowWork.getId());
                    upFlowWork.setCurrentNodeId(model.getId());
                    upFlowWork.setNextNodeId("");
                    upFlowWork.setLastOperator(param.getOperator());
                    upFlowWork.setUpdateTime(now);
                    flowWorkMapper.updateByPrimaryKeySelective(upFlowWork);
                } else { // 非开始和结束节点
                    processor.process(flowWork, flowModel, model,param);
//                    System.out.println(model.getNextNodeId());
                    Date now = new Date();
                    upFlowWork.setId(flowWork.getId());
                    upFlowWork.setCurrentNodeId(model.getId());
                    upFlowWork.setNextNodeId(model.getNextNodeId());
                    upFlowWork.setLastOperator(param.getOperator());
                    upFlowWork.setUpdateTime(now);
//                    System.out.println("hahahahha");
                    flowWorkMapper.updateByPrimaryKeySelective(upFlowWork);
                }
                // 存在一个满足条件的，即可中断，其实设计上也仅会有一个相同类型的。
                break;
            }
        }
    }
}
