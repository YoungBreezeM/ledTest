package com.fw.server;

import com.alibaba.fastjson.JSONObject;
import com.fw.domain.Body;
import com.fw.domain.Method;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import javafx.scene.control.RadioButton;
import javafx.scene.paint.Paint;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

/**
 * @author yqf
 */
public class MyServerHandler extends SimpleChannelInboundHandler<String> {

    private List<RadioButton> radioButtons;

    public MyServerHandler(List<RadioButton> radioButtons) {
        this.radioButtons = radioButtons;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {

        Body body = JSONObject.parseObject(msg, Body.class);

        System.out.println(body);
        switch (body.getUrl()){
            case "/getSize":
                HashMap<Object, Object> objectObjectHashMap = new HashMap<>(10);
                objectObjectHashMap.put("num",radioButtons.size());
                body.setMsg(objectObjectHashMap);
                ctx.channel().writeAndFlush(JSONObject.toJSONString(body));
                return;
            default:

        }


    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("Client active");
        super.channelActive(ctx);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
        System.out.println("服务结束");
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("连接中断");
    }
}