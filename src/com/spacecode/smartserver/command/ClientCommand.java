//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.spacecode.smartserver.command;

import com.spacecode.smartserver.command.ClientCommandException;
import com.spacecode.smartserver.command.CommandContract;
import io.netty.channel.ChannelHandlerContext;
import java.util.concurrent.Executors;

@CommandContract
abstract class ClientCommand {
    static final String TRUE = "true";
    static final String FALSE = "false";

    ClientCommand() {
    }

    public abstract void execute(ChannelHandlerContext var1, String[] var2) throws ClientCommandException;

    static void parallelize(Runnable runnable) {
        Executors.newSingleThreadExecutor().submit(runnable);
    }
}
