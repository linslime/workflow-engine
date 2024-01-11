package com.workflow.engine.processor.impl;

import com.workflow.engine.entity.FlowTask;
import com.workflow.engine.entity.FlowWork;
import com.workflow.engine.event.FlowTaskCreatedEvent;
import com.workflow.engine.event.FlowTaskNodeEvent;
import com.workflow.engine.mapper.FlowTaskMapper;
import com.workflow.engine.mapper.FlowWorkMapper;
import com.workflow.engine.model.BaseModel;
import com.workflow.engine.model.FlowModel;
import com.workflow.engine.model.TaskModel;
import com.workflow.engine.param.FlowParam;
import com.workflow.engine.processor.FlowNodeProcessor;
import com.workflow.engine.service.FlowNodeUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.noggit.JSONUtil;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

@Component
public class TaskNodeProcessorImpl implements FlowNodeProcessor{
    @Resource
    private FlowTaskMapper flowTaskMapper;
    @Autowired(required=false)
    private FlowNodeUserService flowNodeUserService;
    @Autowired(required=false)
    private FlowTaskNodeEvent flowTaskStartEvent;
    @Autowired(required=false)
    private FlowTaskCreatedEvent flowTaskCreatedEvent;
    @Override
    public void process(FlowWork flowWork, FlowModel flowModel, BaseModel currentNddeModel, FlowParam flowParam) {
        // 下一个节点
        TaskModel taskModel = (TaskModel)currentNddeModel;
        // 1. 创建任务
        createTask(flowWork, flowParam, taskModel);
        // 2. 修改当前流程实例状态
        if(null != flowTaskStartEvent) {
            flowTaskStartEvent.OnEvent(flowWork, taskModel, flowParam);
        }
    }
    /**
     * 创建任务
     * @param flowWork
     * @param flowParam
     * @param taskModel
     */
    private int createTask(FlowWork flowWork, FlowParam flowParam, TaskModel taskModel){
        Date now = new Date();
        String flowWorkId = flowWork.getId();
        List<String> userIds = flowParam.getUserIds();
        if(null==userIds) {
            userIds = new ArrayList<>();
        }
        if(null!=flowNodeUserService) {
            List<String> list = flowNodeUserService.loadUser(taskModel, flowParam);
            if(null!=list) {
                for(String id:list) {
                    if(!userIds.contains(id)) {
                        userIds.add(id);
                    }
                }

            }
        }

        for(String actorUserId:userIds) {
            // 2. 创建一个任务
            Random r = new Random();
            FlowTask flowTask = new FlowTask();
            flowTask.setId("flowTaskId" + r.nextInt(10000000));
            flowTask.setFlowWorkId(flowWorkId);
            flowTask.setFlowNodeId(taskModel.getId());
            flowTask.setTaskName(taskModel.getName());
            flowTask.setOperator(flowParam.getOperator());
            flowTask.setActorUserId(actorUserId);
            flowTask.setStatus(10);
            flowTask.setServiceType(flowParam.getServiceType());
            flowTask.setServiceId(flowParam.getServiceId());
            flowTask.setFlowParam(JSONUtil.toJSON(flowParam));
            flowTask.setCreateTime(now);
            flowTask.setUpdateTime(now);
            flowTask.setIsDeleted(0);
            flowTaskMapper.insertSelective(flowTask);
            if(null != flowTaskCreatedEvent) {
                flowTaskCreatedEvent.onEvent(flowWork, flowTask, flowParam);
            }
        }
        return userIds.size();
    }

    @Override
    public String getNodeType() {
        return "TASK";
    }
}
