package com.fw.server;

import com.fw.domain.Body;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import javafx.scene.control.RadioButton;

import java.util.List;

/**
 * @author yqf
 */
public class MyServer {
    private Integer port;
    private  EventLoopGroup bossGroup;
    private EventLoopGroup workerGroup;
    private Boolean flag = false;

    public MyServer(Integer port) {
        this.port = port;
    }

    public Channel start(List<RadioButton> radioButtons){

         bossGroup = new NioEventLoopGroup();
         workerGroup = new NioEventLoopGroup();
        ChannelFuture channelFuture= null;
        try{
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup,workerGroup).channel(NioServerSocketChannel.class)
                    .childHandler(new MyServerInitializer(radioButtons));

                channelFuture = serverBootstrap.bind(port).sync();

        }catch (Exception e){
            e.printStackTrace();
        }

        flag = true;
        return  channelFuture.channel();
    }

    public void close(){
        if(flag){
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
            flag = false;
        }
    }
}