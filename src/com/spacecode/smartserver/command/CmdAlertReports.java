//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.spacecode.smartserver.command;

import com.spacecode.smartserver.SmartServer;
import com.spacecode.smartserver.command.ClientCommand;
import com.spacecode.smartserver.command.CommandContract;
import com.spacecode.smartserver.database.DbManager;
import com.spacecode.smartserver.database.dao.DaoAlertHistory;
import com.spacecode.smartserver.database.entity.AlertHistoryEntity;
import com.spacecode.smartserver.helper.SmartLogger;
import io.netty.channel.ChannelHandlerContext;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;

@CommandContract(
        paramCount = 2,
        strictCount = true,
        responseIfInvalid = ""
)
public class CmdAlertReports extends ClientCommand {
    public CmdAlertReports() {
    }

    public void execute(ChannelHandlerContext ctx, String[] parameters) {
        long timestampStart;
        long timestampEnd;
        try {
            timestampStart = Long.parseLong(parameters[0]);
            timestampEnd = Long.parseLong(parameters[1]);
        } catch (NumberFormatException var12) {
            SmartLogger.getLogger().log(Level.WARNING, "Invalid timestamp sent by client for AlertReports.", var12);
            SmartServer.sendMessage(ctx, new String[]{"alertreports"});
            return;
        }

        if(timestampEnd <= timestampStart) {
            SmartServer.sendMessage(ctx, new String[]{"alertreports"});
        } else {
            DaoAlertHistory daoAlertHistory = (DaoAlertHistory)DbManager.getDao(AlertHistoryEntity.class);
            List entities = daoAlertHistory.getAlertsHistory(new Date(timestampStart), new Date(timestampEnd));
            ArrayList responsePackets = new ArrayList();
            responsePackets.add("alertreports");
            Iterator i$ = entities.iterator();

            while(i$.hasNext()) {
                AlertHistoryEntity entity = (AlertHistoryEntity)i$.next();
                responsePackets.add(String.valueOf(entity.getAlert().getId()));
                responsePackets.add(String.valueOf(entity.getCreatedAt().getTime() / 1000L));
                responsePackets.add("".equals(entity.getExtraData())?" ":entity.getExtraData());
            }

            SmartServer.sendMessage(ctx, (String[])responsePackets.toArray(new String[responsePackets.size()]));
        }
    }
}
