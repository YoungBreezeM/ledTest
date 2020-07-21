package com.fw.domain;

import java.io.Serializable;

/**
 * @author yqf
 */
public class Request implements Serializable {

    private String method;

    private String url;

    private Object body;

    @Override
    public String toString() {
        return "Request{" +
                "method='" + method + '\'' +
                ", url='" + url + '\'' +
                ", body=" + body +
                '}';
    }

    public Request(String method, String url, Object body) {
        this.method = method;
        this.url = url;
        this.body = body;
    }

    public Request() {
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Object getBody() {
        return body;
    }

    public void setBody(Object body) {
        this.body = body;
    }
}
