package com.workflow.engine;

import com.workflow.engine.entity.FlowDefine;
import com.workflow.engine.entity.FlowTask;
import com.workflow.engine.entity.FlowWork;
import com.workflow.engine.mapper.FlowDefineMapper;
import com.workflow.engine.mapper.FlowTaskMapper;
import com.workflow.engine.mapper.FlowWorkMapper;
import com.workflow.engine.model.BaseModel;
import com.workflow.engine.model.FlowModel;
import com.workflow.engine.model.StartModel;
import com.workflow.engine.param.FlowParam;
import com.workflow.engine.parser.FlowNodeParser;
import com.workflow.engine.processor.FlowNodeProcessorCtl;
import org.noggit.JSONUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import javax.annotation.Resource;
import java.io.IOException;
import java.util.Date;
import java.util.Random;


@Service
public class FlowWorkCtl {
    /**
     * 通过流程定义编码启动流程
     * @param flowDefineCode 流程定义编码
     * @param param 流程启动入参
     */
    @Resource
    private FlowTaskMapper flowTaskMapper;
    @Resource
    private FlowDefineMapper flowDefineMapper;
    @Resource
    private FlowWorkMapper flowWorkMapper;
    @Resource
    private FlowNodeProcessorCtl flowNodeProcessorCtl;


    /**
     * 启动一个流程
     * @param flowDefineCode
     * @param operator 操作者用户id
     * @param param
     */
    public FlowWork startProcess(String flowDefineCode, String operator, FlowParam param){
        // TODO 加载流程配置
        FlowDefine q = new FlowDefine();
        q.setFlowDefineCode(flowDefineCode);
        q.setIsDeleted(0);
        FlowNodeParser flowNodeParser = new FlowNodeParser();
        FlowDefine flowDefine = flowDefineMapper.selectOne(q);
        if(null==param.getOperator()) {
            param.setOperator(operator);
        }
        FlowModel flowModel = new FlowModel();
        try {
            flowModel = flowNodeParser.parser(flowDefine.getFlowDefine());
        } catch (IOException e) {
            e.printStackTrace();
        }
        StartModel startModel = flowModel.getStartModel();
        Date now = new Date();
        // 创建一个新流程
        Random r = new Random();
        FlowWork flowWork = new FlowWork();
        flowWork.setId("myFlowWorkId" + r.nextInt(1000000000));
        flowWork.setFlowName(flowDefine.getFlowName());
        flowWork.setFlowDefineCode(flowDefineCode);
        flowWork.setLastOperator(operator);
        flowWork.setCurrentNodeId(startModel.getId());
        flowWork.setNextNodeId(startModel.getNextNodeId());
        flowWork.setFlowDefine(flowDefine.getFlowDefine());
        flowWork.setStatus(10);
        flowWork.setFlowParam(JSONUtil.toJSON(param));
        flowWork.setCreateTime(now);
        flowWork.setUpdateTime(now);
        flowWork.setIsDeleted(0);
        flowWorkMapper.insertSelective(flowWork);
        // 执行开始模型
        flowNodeProcessorCtl.process(flowWork, flowModel, startModel, param);
        return flowWork;
    }

    /**
     * 完成一个任务
     * @param flowTaskId 流程任务id
     * @param operator 操作者用户id
     * @param param 流程参数
     */
    public void completeTask(String flowTaskId,String operator,FlowParam param) {
        if(null==param.getOperator()) {
            param.setOperator(operator);
        }
        FlowNodeParser flowNodeParser = new FlowNodeParser();
        FlowTask task = flowTaskMapper.selectByPrimaryKey(flowTaskId);
        FlowWork flowWork = flowWorkMapper.selectByPrimaryKey(task.getFlowWorkId());
        FlowModel flowModel = new FlowModel();
        try {
            flowModel = flowNodeParser.parser(flowWork.getFlowDefine());
        } catch (IOException e) {
            e.printStackTrace();
        }

        BaseModel currentNodeModel = flowModel.getNodeModel(task.getFlowNodeId());
        BaseModel nextNodeModel = flowModel.getNodeModel(currentNodeModel.getNextNodeId());
        Date now = new Date();
        FlowTask upTask = new FlowTask();
        upTask.setId(task.getId());
        upTask.setUpdateTime(now);
        upTask.setStatus(30);
        upTask.setFinishTime(now);
        // 修改当前任务为已完成
        flowTaskMapper.updateByPrimaryKeySelective(upTask);
        flowNodeProcessorCtl.process(flowWork, flowModel, nextNodeModel, param);
//        int count = 0;
//        if("ALL".equals(currentNodeModel.getNodeType())) {
//            // 所有人都完成，才能走下一步流程
//
//            count = flowTaskMapper.selectCountByCondition();
//        } else {
//            // ANY 任务一个节点完成都可以走下一步，别的任务要修改为已取消
//
//            FlowTask upTaskToCancel = new FlowTask();
//            upTaskToCancel.setUpdateTime(now);
//            upTaskToCancel.setStatus(40);
//            flowTaskMapper.updateByPrimaryKeySelective(upTaskToCancel);
//        }
//        if (count==0) {
//            // 3. 执行下一步流程
//            flowNodeProcessorCtl.process(flowWork, flowModel, nextNodeModel, param);
//        }
    }

//    /**
//     * 驳回一个任务
//     * @param flowTaskId 流程任务id
//     * @param operator 操作者用户id
//     * @param flowParam 流程参数
//     */
//    public void rejectTask(String flowTaskId,String operator,FlowParam flowParam) {
//        if(null==flowParam.getOperator()) {
//            flowParam.setOperator(operator);
//        }
//        FlowTask task = flowTaskMapper.selectByPrimaryKey(flowTaskId);
//        if(null == task || YesNoEnum.YES.equals(task.getIsDeleted())) {
//            // 流程任务不存在
//            throw new ServiceException(FlowErrEnum.FLOW86000007);
//        }
//        // 判断人是否在里面
//        if(!task.getActorUserId().equals(operator)) {
//            // 该用户无流程任务
//            throw new ServiceException(FlowErrEnum.FLOW86000008);
//        }
//        if(FlowTask.StatusEnum.FINISHED.equals(task.getStatus())) {
//            // 已完成，就不处理了
//            return;
//        }
//        FlowWork flowWork = flowWorkMapper.selectByPrimaryKey(task.getFlowWorkId());
//        if(null==flowWork) {
//            // 流程实例不存在
//            throw new ServiceException(FlowErrEnum.FLOW86000005);
//        }
//        FlowModel flowModel = null;
//        try {
//            flowModel = flowNodeParser.parser(flowWork.getFlowDefine());
//        } catch (IOException e) {
//            // 流程解析异常
//            throw new ServiceException(FlowErrEnum.FLOW86000002);
//        }
//        Date now = new Date();
//        FlowTask upTask = new FlowTask();
//        upTask.setId(task.getId());
//        upTask.setUpdateTime(now);
//        upTask.setStatus(FlowTask.StatusEnum.REJECT);
//        upTask.setFinishTime(now);
//        // 修改当前任务为驳回
//        flowTaskMapper.updateByPrimaryKeySelective(upTask);
//        // 中断当前节点任务
//        Condition upTaskCondition = new Condition(FlowTask.class);
//        upTaskCondition.createCriteria().andEqualTo("flowWorkId", task.getFlowWorkId())
//                .andEqualTo("flowNodeId", task.getFlowNodeId())
//                .andEqualTo("status", FlowTask.StatusEnum.CREATED)
//                .andEqualTo("isDeleted", YesNoEnum.NO)
//                .andNotEqualTo("actorUserId", operator);
//        FlowTask upTaskToCancel = new FlowTask();
//        upTaskToCancel.setUpdateTime(now);
//        upTaskToCancel.setStatus(FlowTask.StatusEnum.CANCEL);
//        flowTaskMapper.updateByPrimaryKeySelective(upTaskToCancel);
//
//        // 获取上一个节点
//        BaseModel preNodeModel = flowModel.getPreNodeModel(task.getFlowNodeId());
//        // 执行上一个节点处理器
//        flowNodeProcesstorCtl.process(flowWork, flowModel, preNodeModel, flowParam);
//    }

}
