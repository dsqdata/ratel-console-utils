package org.ratel.console.utils.process.system;

import lombok.extern.slf4j.Slf4j;
import org.ratel.console.utils.base.RatelBaseSubProcess;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

@Slf4j
public class IpRatelProcess extends RatelBaseSubProcess {

    @Override
    public void processOperation() throws Exception {
        log.info("\n----------------------------------------------------------\n\t" +
                        "本机 IP 列表! \n\t" +
                        "{}\n" +
                        "----------------------------------------------------------",
                getLocalIPList()
        );
    }

    /**
     * 获取本地IP列表（针对多网卡情况）
     *
     * @return
     */
    public static List<String> getLocalIPList() {
        List<String> ipList = new ArrayList<String>();
        try {
            Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
            NetworkInterface networkInterface;
            Enumeration<InetAddress> inetAddresses;
            InetAddress inetAddress;
            String ip;
            while (networkInterfaces.hasMoreElements()) {
                networkInterface = networkInterfaces.nextElement();
                inetAddresses = networkInterface.getInetAddresses();
                while (inetAddresses.hasMoreElements()) {
                    inetAddress = inetAddresses.nextElement();
                    if (inetAddress != null && inetAddress instanceof Inet4Address) { // IPV4
                        ip = inetAddress.getHostAddress();
                        ipList.add(ip);
                    }
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }
        return ipList;
    }
}
