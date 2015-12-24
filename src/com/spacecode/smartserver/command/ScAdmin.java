//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.spacecode.smartserver.command;

import com.spacecode.sdk.device.data.DeviceStatus;
import com.spacecode.smartserver.SmartServer;
import com.spacecode.smartserver.command.ClientCommand;
import com.spacecode.smartserver.command.ClientCommandException;
import com.spacecode.smartserver.command.CommandContract;
import com.spacecode.smartserver.helper.ConfManager;
import com.spacecode.smartserver.helper.DeviceHandler;
import com.spacecode.smartserver.helper.SmartLogger;
import io.netty.channel.ChannelHandlerContext;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.regex.Pattern;

class ScAdmin {
    ScAdmin() {
    }

    @CommandContract(
            paramCount = 1,
            strictCount = true,
            noResponseWhenInvalid = true
    )
    static class CmdUpdateReport extends ClientCommand {
        private static boolean UPDATE_IN_PROGRESS = false;
        private static String PATCHES_COUNT = "0";
        static final String EVENT_CODE_STARTED = "event_update_started";
        static final String EVENT_CODE_PROGRESS = "event_update_progress";
        static final String EVENT_CODE_ENDED = "event_update_ended";

        CmdUpdateReport() {
        }

        public synchronized void execute(ChannelHandlerContext ctx, String[] parameters) {
            String parameter = parameters[0].trim();

            try {
                int nfe = Integer.parseInt(parameter);
            } catch (NumberFormatException var6) {
                SmartLogger.getLogger().log(Level.SEVERE, "Invalid parameter provided to UpdateReport command", var6);
                return;
            }

            byte var5 = -1;
            switch(parameter.hashCode()) {
                case 48:
                    if(parameter.equals("0")) {
                        var5 = 0;
                    }
                    break;
                case 1444:
                    if(parameter.equals("-1")) {
                        var5 = 1;
                    }
            }

            switch(var5) {
                case 0:
                    SmartLogger.getLogger().info("[Update] Success.");
                    SmartServer.sendAllClients(new String[]{"event_update_ended", "true"});
                    UPDATE_IN_PROGRESS = false;
                    break;
                case 1:
                    SmartLogger.getLogger().info("[Update] Failure.");
                    SmartServer.sendAllClients(new String[]{"event_update_ended", "false"});
                    UPDATE_IN_PROGRESS = false;
                    break;
                default:
                    if(!UPDATE_IN_PROGRESS) {
                        SmartLogger.getLogger().info("[Update] Started. " + parameter + " new patches.");
                        UPDATE_IN_PROGRESS = true;
                        SmartServer.sendAllClients(new String[]{"event_update_started"});
                        PATCHES_COUNT = parameter;
                    } else {
                        SmartLogger.getLogger().info("[Update] Progress: " + parameter + " patches left.");
                        SmartServer.sendAllClients(new String[]{"event_update_progress", parameter, PATCHES_COUNT});
                    }
            }

        }
    }

    static class CmdStartUpdate extends ClientCommand {
        private static Process _pythonProcess = null;

        CmdStartUpdate() {
        }

        public synchronized void execute(ChannelHandlerContext ctx, String[] parameters) {
            if(_pythonProcess != null) {
                try {
                    _pythonProcess.waitFor();
                } catch (InterruptedException var5) {
                    SmartLogger.getLogger().log(Level.SEVERE, "Interrupted while waiting for AutoUpdate to complete.", var5);
                }
            }

            try {
                _pythonProcess = (new ProcessBuilder(new String[]{"/bin/sh", "-c", "python /usr/local/bin/Spacecode/update.py"})).start();
                SmartLogger.getLogger().info("Running the auto-update process...");
            } catch (IOException var4) {
                SmartLogger.getLogger().log(Level.SEVERE, "Unable to execute the auto-update script.", var4);
            }

        }
    }

    @CommandContract(
            paramCount = 1
    )
    static class CmdSetNetworkSettings extends ClientCommand {
        private static final String VALID_IPV4_REGEX = "^(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?).(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?).(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?).(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$";
        private static final Pattern VALID_IPV4_PATTERN = Pattern.compile("^(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?).(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?).(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?).(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$");

        CmdSetNetworkSettings() {
        }

        public synchronized void execute(ChannelHandlerContext ctx, String[] parameters) throws ClientCommandException {
            if(parameters.length >= 1 && parameters.length <= 3 && (parameters.length != 1 || parameters[0].toLowerCase().equals("dhcp"))) {
                String osName = System.getProperty("os.name");
                if(osName == null) {
                    SmartLogger.getLogger().severe("Property os.name is null. Is the JVM fine?");
                    SmartServer.sendMessage(ctx, new String[]{"setnetworksettings", "false"});
                } else {
                    osName = osName.toLowerCase();
                    String ipDevice = "";
                    String ipSubnet = "";
                    String ipGateway = "";
                    if(parameters.length > 1) {
                        ipDevice = parameters[0].trim();
                        ipSubnet = parameters[1].trim();
                        if(!VALID_IPV4_PATTERN.matcher(ipDevice).matches()) {
                            SmartServer.sendMessage(ctx, new String[]{"setnetworksettings", "false"});
                            throw new ClientCommandException("Invalid IP Address provided [SetNetworkSettings].");
                        }

                        if(!VALID_IPV4_PATTERN.matcher(ipSubnet).matches()) {
                            SmartServer.sendMessage(ctx, new String[]{"setnetworksettings", "false"});
                            throw new ClientCommandException("Invalid Subnet Mask provided [SetNetworkSettings].");
                        }

                        if(parameters.length == 3) {
                            ipGateway = parameters[2].trim();
                            if(!ipGateway.isEmpty() && !VALID_IPV4_PATTERN.matcher(ipGateway).matches()) {
                                SmartServer.sendMessage(ctx, new String[]{"setnetworksettings", "false"});
                                throw new ClientCommandException("Invalid Gateway provided [SetNetworkSettings].");
                            }
                        }
                    }

                    try {
                        if(osName.contains("windows")) {
                            ArrayList e = new ArrayList(Arrays.asList(new String[]{"netsh.exe", "-c", "interface", "ip", "set", "address", "name=\"Local Area Connection\""}));
                            if(parameters.length > 1) {
                                e.add("source=static");
                                e.add("addr=");
                                e.add(ipDevice);
                                e.add("mask=");
                                e.add(ipSubnet);
                                if(!ipGateway.isEmpty()) {
                                    e.add("gateway=");
                                    e.add(ipGateway);
                                    e.add("gwmetric=1");
                                }
                            } else {
                                e.add("source=dhcp");
                            }

                            String[] out = (String[])e.toArray(new String[e.size()]);
                            (new ProcessBuilder(out)).start();
                        } else if(osName.contains("linux")) {
                            StringBuilder e1 = new StringBuilder("auto lo");
                            e1.append(System.lineSeparator());
                            e1.append("iface lo inet loopback");
                            e1.append(System.lineSeparator());
                            e1.append(System.lineSeparator());
                            e1.append("auto eth0");
                            e1.append(System.lineSeparator());
                            e1.append("iface eth0 inet ");
                            if(parameters.length > 1) {
                                e1.append("static");
                                e1.append(System.lineSeparator());
                                e1.append("address ");
                                e1.append(ipDevice);
                                e1.append(System.lineSeparator());
                                e1.append("netmask ");
                                e1.append(ipSubnet);
                                if(!ipGateway.isEmpty()) {
                                    e1.append(System.lineSeparator());
                                    e1.append("gateway ");
                                    e1.append(ipGateway);
                                }

                                e1.append(System.lineSeparator());
                            } else {
                                e1.append("dhcp");
                                e1.append(System.lineSeparator());
                            }

                            PrintWriter out1 = new PrintWriter("/etc/network/interfaces", "UTF-8");
                            out1.println(e1.toString());
                            out1.close();
                            Process ifdown = (new ProcessBuilder(new String[]{"/sbin/ifdown", "eth0"})).start();
                            SmartLogger.getLogger().info("Ifdown result: " + ifdown.waitFor());
                            Process ifup = (new ProcessBuilder(new String[]{"/sbin/ifup", "eth0"})).start();
                            SmartLogger.getLogger().info("Ifup result: " + ifup.waitFor());
                        } else {
                            SmartLogger.getLogger().severe("Property os.name contains an unhandled value. IP cannot be changed.");
                            SmartServer.sendMessage(ctx, new String[]{"setnetworksettings", "false"});
                        }
                    } catch (InterruptedException | IOException var11) {
                        SmartLogger.getLogger().log(Level.SEVERE, "Unable to apply the provided network settings.", var11);
                        SmartServer.sendMessage(ctx, new String[]{"setnetworksettings", "false"});
                    }

                }
            } else {
                SmartServer.sendMessage(ctx, new String[]{"setnetworksettings", "false"});
                throw new ClientCommandException("Invalid number of parameters [SetNetworkSettings].");
            }
        }
    }

    @CommandContract(
            paramCount = 2,
            strictCount = true
    )
    static class CmdSetFprSerial extends ClientCommand {
        CmdSetFprSerial() {
        }

        public synchronized void execute(ChannelHandlerContext ctx, String[] parameters) {
            String serial = parameters[0] == null?"":parameters[0].trim();
            boolean isMaster = Boolean.parseBoolean(parameters[1]);
            boolean result = isMaster?ConfManager.setDevFprMaster(serial):ConfManager.setDevFprSlave(serial);
            SmartServer.sendMessage(ctx, new String[]{"setfprserial", result?"true":"false"});
        }
    }

    @CommandContract(
            paramCount = 2,
            strictCount = true
    )
    static class CmdSetBrSerial extends ClientCommand {
        CmdSetBrSerial() {
        }

        public synchronized void execute(ChannelHandlerContext ctx, String[] parameters) {
            String serial = parameters[0] == null?"":parameters[0].trim();
            boolean isMaster = Boolean.parseBoolean(parameters[1]);
            boolean result = isMaster?ConfManager.setDevBrMaster(serial):ConfManager.setDevBrSlave(serial);
            SmartServer.sendMessage(ctx, new String[]{"setbrserial", result?"true":"false"});
        }
    }

    @CommandContract(
            paramCount = 1,
            strictCount = true,
            noResponseWhenInvalid = true
    )
    static class CmdSerialBridge extends ClientCommand {
        private static Process _portForwardingProcess = null;

        CmdSerialBridge() {
        }

        public synchronized void execute(ChannelHandlerContext ctx, String[] parameters) {
            if("ON".equals(parameters[0]) || "OFF".equals(parameters[0])) {
                boolean openBridge = "ON".equals(parameters[0]);
                if(openBridge) {
                    if(_portForwardingProcess != null) {
                        return;
                    }

                    try {
                        DeviceHandler.setForwardingSerialPort(true);
                        DeviceHandler.disconnectDevice();
                        String e = "socat /dev/ttyGS0,raw,echo=0,crnl /dev/ttyUSB0,raw,echo=0,crnl";
                        _portForwardingProcess = (new ProcessBuilder(new String[]{"/bin/sh", "-c", e})).start();
                        SmartLogger.getLogger().severe("Running Port Forwarding command.");
                    } catch (IOException var6) {
                        SmartLogger.getLogger().log(Level.SEVERE, "Unable to run Port Forwarding command.", var6);
                        DeviceHandler.reconnectDevice();
                    }
                } else {
                    if(_portForwardingProcess == null) {
                        return;
                    }

                    try {
                        Process e1 = (new ProcessBuilder(new String[]{"/bin/sh", "-c", "pkill -f socat"})).start();
                        e1.waitFor();
                        _portForwardingProcess = null;
                        SmartLogger.getLogger().severe("Stopped Port Forwarding command. Reconnecting Device...");
                        DeviceHandler.setForwardingSerialPort(false);
                        DeviceHandler.reconnectDevice();
                    } catch (InterruptedException | IOException var5) {
                        SmartLogger.getLogger().log(Level.SEVERE, "Unable to stop Port Forwarding command.", var5);
                    }
                }

            }
        }
    }

    @CommandContract(
            paramCount = 2,
            strictCount = true
    )
    static class CmdSignInAdmin extends ClientCommand {
        private static final String ADMIN_USERNAME = "testrfid";
        private static final String ADMIN_HASH = "f4b98ca124c933f0b5c2736efd1b931f3a4d27c621db7390d35ec964ee8b210a";

        CmdSignInAdmin() {
        }

        public void execute(ChannelHandlerContext ctx, String[] parameters) {
            String username = parameters[0];
            String password = sha256(parameters[1]);
            if("testrfid".equals(username) && "f4b98ca124c933f0b5c2736efd1b931f3a4d27c621db7390d35ec964ee8b210a".equals(password)) {
                SmartServer.addAdministrator(ctx.channel().remoteAddress());
                SmartServer.sendMessage(ctx, new String[]{"signinadmin", "true"});
            } else {
                SmartLogger.getLogger().info(String.format("Authentication Failure! (%s/%s)", new Object[]{username, password}));
                SmartServer.sendMessage(ctx, new String[]{"signinadmin", "false"});
            }
        }

        private static String sha256(String base) {
            try {
                MessageDigest e = MessageDigest.getInstance("SHA-256");
                byte[] hash = e.digest(base.getBytes("UTF-8"));
                StringBuilder hexString = new StringBuilder();
                byte[] arr$ = hash;
                int len$ = hash.length;

                for(int i$ = 0; i$ < len$; ++i$) {
                    byte aHash = arr$[i$];
                    String hex = Integer.toHexString(255 & aHash);
                    if(hex.length() == 1) {
                        hexString.append('0');
                    }

                    hexString.append(hex);
                }

                return hexString.toString();
            } catch (UnsupportedEncodingException | NoSuchAlgorithmException var9) {
                SmartLogger.getLogger().log(Level.SEVERE, "Unable to get a SHA256 hash of the given password", var9);
                return "";
            }
        }
    }

    static class CmdNetworkSettings extends ClientCommand {
        CmdNetworkSettings() {
        }

        public void execute(ChannelHandlerContext ctx, String[] parameters) {
            String osName = System.getProperty("os.name");
            if(osName == null) {
                SmartLogger.getLogger().severe("Property os.name is null. Is the JVM fine?");
                SmartServer.sendMessage(ctx, new String[]{"networksettings", "null"});
            } else {
                osName = osName.toLowerCase();
                String line;
                if(osName.contains("linux")) {
                    try {
                        List processBuilder = Files.readAllLines(Paths.get("/etc/network/interfaces", new String[0]), Charset.defaultCharset());
                        HashMap ioe = new HashMap();
                        ioe.put("address", "");
                        ioe.put("netmask", "");
                        ioe.put("gateway", "");
                        Iterator is = processBuilder.iterator();

                        while(is.hasNext()) {
                            String bufferedReader = (String)is.next();
                            bufferedReader = bufferedReader.toLowerCase();
                            if(bufferedReader.contains("inet dhcp")) {
                                SmartServer.sendMessage(ctx, new String[]{"networksettings", "dhcp"});
                                return;
                            }

                            Iterator netConf = ioe.keySet().iterator();

                            while(netConf.hasNext()) {
                                line = (String)netConf.next();
                                if(bufferedReader.contains(line)) {
                                    String[] isEth0 = bufferedReader.split(" ");
                                    if(isEth0.length >= 2) {
                                        ioe.put(line, isEth0[1]);
                                    }
                                    break;
                                }
                            }
                        }

                        SmartServer.sendMessage(ctx, new String[]{"networksettings", (String)ioe.get("address"), (String)ioe.get("netmask"), (String)ioe.get("gateway")});
                        return;
                    } catch (IOException var15) {
                        SmartLogger.getLogger().log(Level.SEVERE, "An I/O error occurred when getting Network Settings", var15);
                    }
                } else if(osName.contains("windows")) {
                    ProcessBuilder processBuilder1 = new ProcessBuilder(new String[]{"cmd.exe", "/c", "ipconfig"});
                    processBuilder1.redirectErrorStream(true);

                    try {
                        Process ioe1 = processBuilder1.start();
                        InputStream is1 = ioe1.getInputStream();
                        BufferedReader bufferedReader1 = new BufferedReader(new InputStreamReader(is1, "UTF-8"));
                        HashMap netConf1 = new HashMap();
                        netConf1.put("ip address", "");
                        netConf1.put("subnet", "");
                        netConf1.put("gateway", "");
                        line = bufferedReader1.readLine();

                        for(boolean isEth01 = false; line != null; line = bufferedReader1.readLine()) {
                            line = line.toLowerCase();
                            if(line.contains("adapter")) {
                                isEth01 = line.contains("local area connection");
                            }

                            if(isEth01) {
                                Iterator i$ = netConf1.keySet().iterator();

                                while(i$.hasNext()) {
                                    String keyConf = (String)i$.next();
                                    if(line.contains(keyConf)) {
                                        String[] fragments = line.split(": ");
                                        if(fragments.length >= 2) {
                                            netConf1.put(keyConf, fragments[1].trim());
                                        }
                                    }
                                }
                            }
                        }

                        bufferedReader1.close();
                        SmartServer.sendMessage(ctx, new String[]{"networksettings", (String)netConf1.get("ip address"), (String)netConf1.get("subnet"), (String)netConf1.get("gateway")});
                        return;
                    } catch (IOException var14) {
                        SmartLogger.getLogger().log(Level.SEVERE, "An I/O error occurred when getting Network Settings", var14);
                    }
                } else {
                    SmartLogger.getLogger().severe("Unknown OS: Unable to get the network settings.");
                }

                SmartServer.sendMessage(ctx, new String[]{"networksettings", "null"});
            }
        }
    }

    @CommandContract(
            paramCount = 1,
            strictCount = true
    )
    static class CmdFprSerial extends ClientCommand {
        CmdFprSerial() {
        }

        public void execute(ChannelHandlerContext ctx, String[] parameters) {
            boolean isMaster = Boolean.parseBoolean(parameters[0]);
            String serialPortNumber = isMaster?ConfManager.getDevFprMaster():ConfManager.getDevFprSlave();
            SmartServer.sendMessage(ctx, new String[]{"fprserial", serialPortNumber});
        }
    }

    static class CmdHostname extends ClientCommand {
        CmdHostname() {
        }

        public void execute(ChannelHandlerContext ctx, String[] parameters) {
            String osName = System.getProperty("os.name");
            if(osName == null) {
                SmartLogger.getLogger().severe("Property os.name is null. Is the JVM fine?");
                SmartServer.sendMessage(ctx, new String[]{"hostname", "[Null]"});
            } else {
                osName = osName.toLowerCase();
                if(osName.contains("linux")) {
                    ProcessBuilder uhe = new ProcessBuilder(new String[]{"/bin/hostname"});
                    uhe.redirectErrorStream(true);

                    try {
                        Process ioe = uhe.start();
                        InputStream is = ioe.getInputStream();
                        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
                        String hostname = bufferedReader.readLine();
                        bufferedReader.close();
                        if(hostname != null) {
                            SmartServer.sendMessage(ctx, new String[]{"hostname", hostname});
                            return;
                        }
                    } catch (IOException var9) {
                        SmartLogger.getLogger().log(Level.SEVERE, "An I/O error occurred when getting Hostname", var9);
                    }
                } else if(osName.contains("windows")) {
                    try {
                        SmartServer.sendMessage(ctx, new String[]{"hostname", InetAddress.getLocalHost().getHostName()});
                        return;
                    } catch (UnknownHostException var10) {
                        SmartLogger.getLogger().log(Level.SEVERE, "Unable to get Hostname", var10);
                    }
                } else {
                    SmartLogger.getLogger().severe("Unknown OS: could not get Hostname");
                }

                SmartServer.sendMessage(ctx, new String[]{"hostname", "[Null]"});
            }
        }
    }

    @CommandContract(
            paramCount = 1,
            strictCount = true,
            deviceRequired = true
    )
    static class CmdFlashFirmware extends ClientCommand {
        CmdFlashFirmware() {
        }

        public void execute(ChannelHandlerContext ctx, String[] parameters) {
            if(DeviceHandler.getDevice().getStatus() == DeviceStatus.FLASHING_FIRMWARE) {
                SmartServer.sendMessage(ctx, new String[]{"flashfirmware", "false"});
            } else {
                List firmwareLines = Arrays.asList(parameters[0].split("[\\r\\n]+"));
                boolean result = DeviceHandler.getDevice().flashFirmware(firmwareLines);
                SmartServer.sendMessage(ctx, new String[]{"flashfirmware", result?"true":"false"});
            }
        }
    }

    @CommandContract(
            paramCount = 1,
            strictCount = true
    )
    static class CmdBrSerial extends ClientCommand {
        CmdBrSerial() {
        }

        public void execute(ChannelHandlerContext ctx, String[] parameters) {
            boolean isMaster = Boolean.parseBoolean(parameters[0]);
            String serialPortName = isMaster?ConfManager.getDevBrMaster():ConfManager.getDevBrSlave();
            SmartServer.sendMessage(ctx, new String[]{"brserial", serialPortName});
        }
    }
}
