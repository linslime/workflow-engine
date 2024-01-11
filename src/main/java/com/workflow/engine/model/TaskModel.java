package com.workflow.engine.model;

import lombok.Data;

@Data
public class TaskModel extends BaseModel{
    /**
     * 参与方式（
     * ANY->任何一个参与者处理完即可执行下一步,
     * ALL->所有参与者都完成，才可执行下一步
     * ）
     */
    private String performType;
    /**
     * 期望完成时间（产生的任务期望啥时间完成）
     */
    private String expireTime;
    /**
     * 提醒时间（如任务未处理，啥时候提醒）
     */
    private String reminderTime;
    /**
     * 提醒间隔(分钟)（如任务未处理，提醒规则是什么）
     */
    private Integer reminderRepeat;
    /**
     * 是否自动执行（如任务未处理且到期，是否自动执行）
     */
    private Boolean autoExecute;

}
