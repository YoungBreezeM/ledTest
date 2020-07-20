package com.fw.domain;

import java.io.Serializable;

/**
 * @author yqf
 */
public class Led implements Serializable {

    private Integer index;
    private boolean status;

    public Led(Integer index, boolean status) {
        this.index = index;
        this.status = status;
    }

    public Led() {
    }

    @Override
    public String toString() {
        return "Led{" +
                "index=" + index +
                ", status=" + status +
                '}';
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
