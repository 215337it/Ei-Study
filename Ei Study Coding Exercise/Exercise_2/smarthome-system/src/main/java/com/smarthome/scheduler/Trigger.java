package com.smarthome.scheduler;

public class Trigger {
    private String condition;
    private String action;
    int id;

    public Trigger(String condition, String action,int id) {
        this.condition = condition;
        this.action = action;
        this.id=id;
    }

    public String getCondition() {
        return condition;
    }

    public String getAction() {
        return action;
    }
    public int getId() {
        return id;
    }
}
