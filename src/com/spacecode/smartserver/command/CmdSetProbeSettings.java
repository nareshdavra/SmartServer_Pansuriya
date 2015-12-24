//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.spacecode.smartserver.command;

import com.spacecode.sdk.device.module.TemperatureProbe.Settings;
import com.spacecode.smartserver.SmartServer;
import com.spacecode.smartserver.command.ClientCommand;
import com.spacecode.smartserver.command.CommandContract;
import com.spacecode.smartserver.helper.ConfManager;
import com.spacecode.smartserver.helper.DeviceHandler;
import com.spacecode.smartserver.helper.SmartLogger;
import io.netty.channel.ChannelHandlerContext;
import java.util.logging.Level;

@CommandContract(
        paramCount = 3,
        strictCount = true,
        deviceRequired = true
)
public class CmdSetProbeSettings extends ClientCommand {
    public CmdSetProbeSettings() {
    }

    public synchronized void execute(ChannelHandlerContext ctx, String[] parameters) {
        try {
            int nfe = Integer.parseInt(parameters[0]);
            double delta = Double.parseDouble(parameters[1]);
            boolean state = Boolean.parseBoolean(parameters[2]);
            Settings settings = new Settings(nfe, delta, state);
            if(!ConfManager.setProbeConfiguration(settings)) {
                SmartServer.sendMessage(ctx, new String[]{"setprobesettings", "false"});
            } else {
                SmartServer.sendMessage(ctx, new String[]{"setprobesettings", "true"});
                SmartLogger.getLogger().info("Probe Settings have changed... Applying changes...");
                DeviceHandler.reloadTemperatureProbe();
            }
        } catch (NumberFormatException var8) {
            SmartLogger.getLogger().log(Level.SEVERE, "Delay or Delta invalid, unable to set Probe Settings.", var8);
            SmartServer.sendMessage(ctx, new String[]{"setprobesettings", "false"});
        }
    }
}
