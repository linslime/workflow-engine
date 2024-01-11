package com.workflow.engine.mapper;

import com.workflow.engine.entity.FlowDefine;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.joda.time.DateTime;

public interface FlowDefineMapper {

    @Update("insert into flow_define(id,flow_define_code,flow_name,description,flow_define,is_deleted) values(#{id},#{flowDefineCode},#{flowName},#{description},#{flowDefine},#{isDeleted})")
    void addFlowDefine(String id, String flowDefineCode, String flowName, String description, String flowDefine,int isDeleted);

    @Select("select * from flow_define where flow_define_code = #{flowDefineCode}")
    FlowDefine selectOne(FlowDefine flowDefine);

}
