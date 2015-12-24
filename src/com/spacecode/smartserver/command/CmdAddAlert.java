//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.spacecode.smartserver.command;

import com.spacecode.sdk.network.alert.Alert;
import com.spacecode.sdk.network.alert.AlertTemperature;
import com.spacecode.sdk.network.alert.AlertType;
import com.spacecode.smartserver.SmartServer;
import com.spacecode.smartserver.command.ClientCommand;
import com.spacecode.smartserver.command.CommandContract;
import com.spacecode.smartserver.database.DbManager;
import com.spacecode.smartserver.database.dao.DaoAlert;
import com.spacecode.smartserver.database.entity.AlertEntity;
import io.netty.channel.ChannelHandlerContext;

@CommandContract(
        paramCount = 1,
        strictCount = true
)
public class CmdAddAlert extends ClientCommand {
    public CmdAddAlert() {
    }

    public synchronized void execute(ChannelHandlerContext ctx, String[] parameters) {
        Alert newAlert = Alert.deserialize(parameters[0]);
        if(newAlert != null && newAlert.getId() == 0) {
            if(newAlert.getType() == AlertType.TEMPERATURE && !(newAlert instanceof AlertTemperature)) {
                SmartServer.sendMessage(ctx, new String[]{"addalert", "false"});
            } else {
                DaoAlert daoAlert = (DaoAlert)DbManager.getDao(AlertEntity.class);
                if(!daoAlert.persist(newAlert)) {
                    SmartServer.sendMessage(ctx, new String[]{"addalert", "false"});
                } else {
                    SmartServer.sendMessage(ctx, new String[]{"addalert", "true"});
                }
            }
        } else {
            SmartServer.sendMessage(ctx, new String[]{"addalert", "false"});
        }
    }
}

