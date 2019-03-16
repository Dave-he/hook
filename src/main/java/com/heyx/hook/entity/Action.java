package com.heyx.hook.entity;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.ManyToAny;

import javax.persistence.*;

@Entity
@Table(name = "hook_action")
public class Action {

    @Id
    @GenericGenerator(name = "uuid", strategy = "uuid")
    @GeneratedValue(generator = "uuid")
    @Column(name = "id", length = 64)
    private String id;

    /**
     * 对应操作
     */
    @Column
    private String operation;

    /**
     * 对应任务
     */
    @ManyToOne
    @JoinColumn
    private Task task;

    public Action() {
    }

    public Action(String operation, Task task) {
        this.operation = operation;
        this.task = task;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }
}
