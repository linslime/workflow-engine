package com.workflow.engine.mapper;

import com.workflow.engine.entity.FlowTask;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface FlowTaskMapper {
    @Select("select * from flow_task where id = #{flowTaskId}")
    FlowTask selectByPrimaryKey(String flowTaskId);

    @Update("update flow_task set id = #{id}, update_time = #{updateTime}, status = #{status}, finish_time = #{finishTime} where id = #{id}")
    void updateByPrimaryKeySelective(FlowTask flowTask);

    @Update("insert into flow_task values(#{id},#{flowWorkId},#{flowNodeId},#{taskName},#{operator},#{actorUserId},#{status},#{serviceType},#{serviceId},#{finishTime},#{flowParam}, #{createTime},#{updateTime},#{isDeleted})")
    void insertSelective(FlowTask flowTask);

    @Select("select * from flow_task where actor_user_id = #{actorUserId}")
    List<FlowTask> selectByActorUserId(String actorUserId);
}
