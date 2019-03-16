package com.heyx.hook.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "hook_task")
public class Task {

    @Id
    @GenericGenerator(name = "uuid", strategy = "uuid")
    @GeneratedValue(generator = "uuid")
    @Column(name = "id", length = 64)
    private String id;

    /**
     * 任务名称
     */
    @Column
    private String name;

    /**
     * 考试开始时间
     */
    @Column
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date dateLine;

    public Task() {
    }

    public Task(String name, Date dateLine) {
        this.name = name;
        this.dateLine = dateLine;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDateLine() {
        return dateLine;
    }

    public void setDateLine(Date dateLine) {
        this.dateLine = dateLine;
    }
}
