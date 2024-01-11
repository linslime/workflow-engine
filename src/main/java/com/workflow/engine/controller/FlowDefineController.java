package com.workflow.engine.controller;

import com.workflow.engine.FlowWorkCtl;
import com.workflow.engine.entity.FlowTask;
import com.workflow.engine.mapper.FlowDefineMapper;
import com.workflow.engine.mapper.FlowTaskMapper;
import com.workflow.engine.model.FlowModel;
import com.workflow.engine.param.FlowParam;
import com.workflow.engine.parser.FlowNodeParser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("")
public class FlowDefineController {

    @Resource
    FlowWorkCtl flowWorkCtl = new FlowWorkCtl();
    @Resource
    FlowTaskMapper flowTaskMapper;

    @ResponseBody
    @RequestMapping("/index")
    public String insert(Model model){

        String flowDefineCode = "First";
        FlowParam flowParam = new FlowParam();
        flowParam.setUserIds(new ArrayList<>());
        flowParam.getUserIds().add("张三");
        String operator = "张三";
        flowWorkCtl.startProcess(flowDefineCode,operator, flowParam);

//        String flowTaskId = "flowTaskId6319931";
//        FlowParam flowParam = new FlowParam();
//        flowParam.setUserIds(new ArrayList<>());
//        flowParam.getUserIds().add("王五");
//        String operator = "张三";
//        flowWorkCtl.completeTask(flowTaskId, operator, flowParam);

        List<FlowTask> flowTasks = flowTaskMapper.selectByActorUserId("张三");


        return flowTasks.toString();
    }

}
