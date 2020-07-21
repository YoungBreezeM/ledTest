package com.fw.domain;

import java.io.Serializable;

/**
 * @author yqf
 */
public class Response implements Serializable {

    private Integer status;

    private Object body;

    public Response(Integer status, Object body) {
        this.status = status;
        this.body = body;
    }

    public Response() {
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Object getBody() {
        return body;
    }

    public void setBody(Object body) {
        this.body = body;
    }

    @Override
    public String toString() {
        return "Response{" +
                "status=" + status +
                ", body=" + body +
                '}';
    }
}
