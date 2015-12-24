//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.spacecode.smartserver;

import com.spacecode.sdk.device.Device;
import com.spacecode.sdk.network.communication.MessageHandler;
import com.spacecode.smartserver.SmartServerHandler;
import com.spacecode.smartserver.WebSocketHandler;
import com.spacecode.smartserver.database.DbManager;
import com.spacecode.smartserver.helper.ConfManager;
import com.spacecode.smartserver.helper.DeviceHandler;
import com.spacecode.smartserver.helper.SmartLogger;
import com.spacecode.smartserver.helper.TemperatureCenter;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.ChannelGroupFuture;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.concurrent.GlobalEventExecutor;
import java.io.File;
import java.io.IOException;
import java.net.SocketAddress;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class SmartServer {
    private static final EventLoopGroup BOSS_GROUP = new NioEventLoopGroup();
    private static final EventLoopGroup WORKER_GROUP = new NioEventLoopGroup();
    private static final ChannelGroup TCP_IP_CHAN_GROUP;
    private static final ChannelGroup WS_CHAN_GROUP;
    private static final int TCP_IP_PORT = 8080;
    private static final ChannelHandler TCP_IP_HANDLER;
    private static final int WS_PORT = 8081;
    private static final ChannelHandler WS_HANDLER;
    private static final List<SocketAddress> ADMINISTRATORS;
    private static Channel _channel;
    private static Channel _wsChannel;
    public static final int MAX_FRAME_LENGTH = 4194304;

    private SmartServer() {
    }

    public static String getWorkingDirectory() {
        try {
            String se = SmartServer.class.getProtectionDomain().getCodeSource().getLocation().getPath();
            int lastSeparatorIndex = se.lastIndexOf(File.separator);
            if(se.endsWith(".jar") && lastSeparatorIndex != -1) {
                return se.substring(0, lastSeparatorIndex + 1);
            }
        } catch (SecurityException var2) {
            SmartLogger.getLogger().log(Level.SEVERE, "Permission to get SmartServer Directory not allowed.", var2);
        }

        return "." + File.separator;
    }

    public static void main(String[] args) throws IOException, SQLException {
        com.spacecode.sdk.SmartLogger.getLogger().setLevel(Level.SEVERE);
        SmartLogger.initialize();
        initializeShutdownHook();
        if(!DbManager.initializeDatabase()) {
            SmartLogger.getLogger().severe("Database could not be initialized. SmartServer will not start.");
        } else if(!DeviceHandler.connectDevice()) {
            SmartLogger.getLogger().severe("Unable to connect a device. SmartServer will not start");
        } else if(!init()) {
            SmartLogger.getLogger().severe("Unable to initialize SmartServer [init].");
        } else {
            SmartLogger.getLogger().info("SmartServer is Ready");
            SmartLogger.getLogger().setLevel(Level.INFO);
            startListening();
        }
    }

    private static void loadModuleGSerial() {
        try {
            (new ProcessBuilder(new String[]{"/bin/sh", "-c", "if ! lsmod | grep g_serial; then modprobe g_serial; fi;"})).start();
        } catch (IOException var1) {
            SmartLogger.getLogger().log(Level.SEVERE, "Unable to load module g_serial: I/O error.", var1);
        }

    }

    private static void initializeShutdownHook() {
        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            public void run() {
                TemperatureCenter.stop();
                DeviceHandler.disconnectDevice();
                DbManager.close();
                SmartServer.stop();
            }
        }));
    }

    private static boolean init() {
        Device currentDevice = DeviceHandler.getDevice();
        String devSerialNumber = currentDevice.getSerialNumber();
        SmartLogger.getLogger().info(currentDevice.getDeviceType() + ": " + devSerialNumber);
        if(!DbManager.createDeviceIfNotExists(devSerialNumber)) {
            SmartLogger.getLogger().severe("Unknown device could not be create in DB. SmartServer won\'t start.");
            return false;
        } else {
            return DeviceHandler.onConnected();
        }
    }

    private static void startListening() {
        int portTcp;
        try {
            portTcp = Integer.parseInt(ConfManager.getAppPortTcp());
        } catch (NumberFormatException var12) {
            portTcp = 8080;
            SmartLogger.getLogger().info("Using default TCP port for TCP/IP channel");
        }

        int portWs;
        try {
            portWs = Integer.parseInt(ConfManager.getAppPortWs());
        } catch (NumberFormatException var11) {
            portWs = 8081;
            SmartLogger.getLogger().info("Using default TCP port for WebSocket channel");
        }

        SmartLogger.getLogger().info(String.format("TCP Ports - TCP/IP: %d, WebSocket: %d", new Object[]{Integer.valueOf(portTcp), Integer.valueOf(portWs)}));

        try {
            ServerBootstrap ie = new ServerBootstrap();
            ((ServerBootstrap)((ServerBootstrap)ie.group(BOSS_GROUP, WORKER_GROUP).channel(NioServerSocketChannel.class)).childHandler(new ChannelInitializer() {
                @Override
                public void initChannel(Channel ch) {
                    ch.pipeline().addLast(new ChannelHandler[]{new DelimiterBasedFrameDecoder(4194304, Unpooled.wrappedBuffer(new byte[]{(byte)4}))});
                    ch.pipeline().addLast(new ChannelHandler[]{new StringDecoder(), new StringEncoder()});
                    ch.pipeline().addLast(new ChannelHandler[]{SmartServer.TCP_IP_HANDLER});
                }
            }).option(ChannelOption.SO_BACKLOG, Integer.valueOf(128))).childOption(ChannelOption.SO_KEEPALIVE, Boolean.valueOf(true));
            _channel = ie.bind(portTcp).sync().channel();
            ServerBootstrap wsBootStrap = new ServerBootstrap();
            ((ServerBootstrap)wsBootStrap.group(BOSS_GROUP, WORKER_GROUP).channel(NioServerSocketChannel.class)).childHandler(new ChannelInitializer() {
                public void initChannel(Channel ch) {
                    ch.pipeline().addLast(new ChannelHandler[]{new HttpServerCodec()});
                    ch.pipeline().addLast(new ChannelHandler[]{new HttpObjectAggregator(4194304)});
                    ch.pipeline().addLast(new ChannelHandler[]{SmartServer.WS_HANDLER});
                }
            });
            _wsChannel = wsBootStrap.bind(portWs).sync().channel();
            SmartLogger.getLogger().info("Loading module g_serial (if necessary)...");
            loadModuleGSerial();
            _channel.closeFuture().sync();
        } catch (InterruptedException var9) {
            Logger.getLogger(SmartServer.class.getName()).log(Level.SEVERE, "InterruptedException during execution of sync().", var9);
        } finally {
            WORKER_GROUP.shutdownGracefully();
            BOSS_GROUP.shutdownGracefully();
        }

    }

    private static void stop() {
        if(_channel != null) {
            _channel.close();
        }

        if(_wsChannel != null) {
            _wsChannel.close();
        }

        WORKER_GROUP.shutdownGracefully();
        BOSS_GROUP.shutdownGracefully();
    }

    public static void addClientChannel(Channel newChannel, ChannelHandler handler) {
        if(handler == WS_HANDLER) {
            WS_CHAN_GROUP.add(newChannel);
        } else if(handler == TCP_IP_HANDLER) {
            TCP_IP_CHAN_GROUP.add(newChannel);
        }

    }

    public static ChannelFuture sendMessage(ChannelHandlerContext ctx, String... packets) {
        if(ctx == null) {
            return null;
        } else {
            String message = MessageHandler.packetsToFullMessage(packets);
            return message == null?null:(ctx.handler() == WS_HANDLER?ctx.writeAndFlush(new TextWebSocketFrame(message)):ctx.writeAndFlush(message));
        }
    }

    public static ChannelGroupFuture sendAllClients(String... packets) {
        String message = MessageHandler.packetsToFullMessage(packets);
        if(message == null) {
            return null;
        } else {
            ChannelGroupFuture result = TCP_IP_CHAN_GROUP.write(message);
            WS_CHAN_GROUP.write(new TextWebSocketFrame(message));
            TCP_IP_CHAN_GROUP.flush();
            WS_CHAN_GROUP.flush();
            return result;
        }
    }

    public static boolean addAdministrator(SocketAddress socketAddress) {
        return socketAddress != null && ADMINISTRATORS.add(socketAddress);
    }

    public static boolean removeAdministrator(SocketAddress socketAddress) {
        return socketAddress != null && ADMINISTRATORS.remove(socketAddress);
    }

    public static boolean isAdministrator(SocketAddress socketAddress) {
        return socketAddress != null && ADMINISTRATORS.contains(socketAddress);
    }

    static {
        TCP_IP_CHAN_GROUP = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
        WS_CHAN_GROUP = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
        TCP_IP_HANDLER = new SmartServerHandler();
        WS_HANDLER = new WebSocketHandler();
        ADMINISTRATORS = new ArrayList();
    }
}
