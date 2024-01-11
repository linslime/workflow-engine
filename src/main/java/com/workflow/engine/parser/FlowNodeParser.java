package com.workflow.engine.parser;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.workflow.engine.model.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

public class FlowNodeParser {
    private ObjectMapper objectMapper = new ObjectMapper();
    private final static String FLOW_ID="flowId";
    private final static String FLOW_NAME="flowName";
    private final static String NODE_LIST="nodeList";
    private final static String NODE_TYPE="nodeType";
    /**
     * 解析流程定义
     * @param flowDefine
     * @return
     * @throws IOException
     */
    public FlowModel parser(String flowDefine) throws IOException {
        JsonNode root = objectMapper.readTree(flowDefine);
        if(!root.has(FLOW_ID)||!root.has(FLOW_NAME)||!root.has(NODE_LIST)) {

        }
        String flowId = root.get(FLOW_ID).asText();
        String flowName = root.get(FLOW_NAME).asText();
        JsonNode nodeList = root.get("nodeList");
        if(!nodeList.isArray()) {
            return null;
        }
        FlowModel flowModel = new FlowModel();
        flowModel.setFlowId(flowId);
        flowModel.setFlowName(flowName);
        flowModel.setNodeList(new ArrayList<BaseModel>());
        Iterator<JsonNode> nodes = nodeList.elements();
        while (nodes.hasNext()) {
            JsonNode node = nodes.next();
            if(node.has(NODE_TYPE)) {
                String nodeType = node.get(NODE_TYPE).asText();
                if("START".equals(nodeType)) {
                    // 开始节点
                    StartModel model = objectMapper.readValue(node.toString(), StartModel.class);
                    flowModel.getNodeList().add(model);
                } else if("TASK".equals(nodeType)) {
                    // 任务节点
                    TaskModel model = objectMapper.readValue(node.toString(), TaskModel.class);
                    flowModel.getNodeList().add(model);
                }	else if("END".equals(nodeType)) {
                    // 结束节点
                    EndModel model = objectMapper.readValue(node.toString(), EndModel.class);
                    flowModel.getNodeList().add(model);
                }
            }
        }
        return flowModel;
    }
}
