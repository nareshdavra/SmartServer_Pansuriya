//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.spacecode.smartserver.command;

import com.spacecode.sdk.user.User;
import com.spacecode.smartserver.SmartServer;
import com.spacecode.smartserver.command.ClientCommand;
import com.spacecode.smartserver.command.CommandContract;
import com.spacecode.smartserver.database.DbManager;
import com.spacecode.smartserver.database.dao.DaoUser;
import com.spacecode.smartserver.database.entity.UserEntity;
import com.spacecode.smartserver.helper.DeviceHandler;
import io.netty.channel.ChannelHandlerContext;

@CommandContract(
        paramCount = 1,
        deviceRequired = true
)
public class CmdAddUser extends ClientCommand {
    public CmdAddUser() {
    }

    public synchronized void execute(ChannelHandlerContext ctx, String[] parameters) {
        User newUser = User.deserialize(parameters[0]);
        if(newUser == null) {
            SmartServer.sendMessage(ctx, new String[]{"adduser", "false"});
        } else {
            String username = newUser.getUsername();
            if(username != null && !username.trim().isEmpty()) {
                if(DeviceHandler.getDevice().getUsersService().getUserByName(username) != null) {
                    SmartServer.sendMessage(ctx, new String[]{"adduser", "false"});
                } else {
                    DaoUser daoUser = (DaoUser)DbManager.getDao(UserEntity.class);
                    UserEntity userEntity = daoUser.getByUsername(username);
                    if(userEntity != null) {
                        SmartServer.sendMessage(ctx, new String[]{"adduser", "false"});
                    } else if(!DeviceHandler.getDevice().getUsersService().addUser(newUser)) {
                        SmartServer.sendMessage(ctx, new String[]{"adduser", "false"});
                    } else if(!daoUser.persist(newUser)) {
                        DeviceHandler.getDevice().getUsersService().removeUser(username);
                        SmartServer.sendMessage(ctx, new String[]{"adduser", "false"});
                    } else {
                        SmartServer.sendMessage(ctx, new String[]{"adduser", "true"});
                    }
                }
            } else {
                SmartServer.sendMessage(ctx, new String[]{"adduser", "false"});
            }
        }
    }
}
