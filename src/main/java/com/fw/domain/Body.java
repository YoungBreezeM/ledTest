package com.fw.domain;

import java.io.Serializable;

/**
 * @author yqf
 */
public class Body implements Serializable {
    private String type;
    private Object msg;
    private String url;
    private Integer status;

    public Body(String type, Object msg, String url, Integer status) {
        this.type = type;
        this.msg = msg;
        this.url = url;
        this.status = status;
    }

    public Body() {
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Object getMsg() {
        return msg;
    }

    public void setMsg(Object msg) {
        this.msg = msg;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Body{" +
                "type='" + type + '\'' +
                ", msg=" + msg +
                ", url='" + url + '\'' +
                ", status=" + status +
                '}';
    }
}
