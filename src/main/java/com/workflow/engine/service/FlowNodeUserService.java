package com.workflow.engine.service;

import com.workflow.engine.model.BaseModel;
import com.workflow.engine.param.FlowParam;

import java.util.List;

public interface FlowNodeUserService{
    /**
     * 加载节点参与人
     * @param nodeModel 节点模型
     * @param param 流程参数
     * @return
     */
    public List<String> loadUser(BaseModel nodeModel, FlowParam param);

}
