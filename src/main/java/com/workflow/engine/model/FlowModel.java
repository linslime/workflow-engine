package com.workflow.engine.model;

import lombok.Data;

import java.util.List;

@Data
public class FlowModel{

    private static final long serialVersionUID = -3978388377128544781L;
    //流程ID
    private String flowId;
    //流程名称
    private String flowName;
    //流程节点
    private List<BaseModel> nodeList;

    public BaseModel getNodeModel(String nodeId) {
        if(null == nodeList || nodeList.isEmpty()) {
            return null;
        }
        for(BaseModel node : nodeList) {
            if(node.getId().equals(nodeId)){
                return node;
            }
        }
        return null;
    }
    /**
     * 获取开始节点模型
     * @return
     */
    public StartModel getStartModel() {
        if(null == nodeList || nodeList.isEmpty()) {
            return null;
        }
        for(BaseModel node : nodeList) {
            if(node instanceof StartModel){
                return (StartModel)node;
            }
        }
        return null;
    }
    /**
     * 通过当前节点的上一个节点
     * @param nodeId
     * @return
     */
    public BaseModel getPreNodeModel(String nodeId) {
        if(null == nodeList || nodeList.isEmpty()) {
            return null;
        }
        for(BaseModel node : nodeList) {
            if(nodeId.equals(node.getNextNodeId())){
                return node;
            }
        }
        return null;
    }
    /**
     * 获取结束节点模型
     * @return
     */
    public EndModel getEndModel() {
        if(null == nodeList || nodeList.isEmpty()) {
            return null;
        }
        for(BaseModel node : nodeList) {
            if(node instanceof EndModel){
                return (EndModel)node;
            }
        }
        return null;
    }
}
