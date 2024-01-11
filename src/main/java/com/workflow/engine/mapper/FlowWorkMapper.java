package com.workflow.engine.mapper;

import com.workflow.engine.entity.FlowWork;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface FlowWorkMapper {
    @Update("update flow_work set  current_node_id = #{currentNodeId}, next_node_id = #{nextNodeId}, last_operator = #{lastOperator}, update_time = #{updateTime} where id = #{id}")
    void updateByPrimaryKeySelective(FlowWork flowWork);

    @Update("insert into flow_work values(#{id}, #{flowName}, #{flowDefineCode}, #{lastOperator}, #{currentNodeId}, #{nextNodeId}, #{flowDefine}, #{status}, #{flowParam}, #{createTime}, #{updateTime}, #{isDeleted})")
    void insertSelective(FlowWork flowWork);

    @Select("select * from flow_work where id = #{flowWorkId}")
    FlowWork selectByPrimaryKey(String flowWorkId);


}
