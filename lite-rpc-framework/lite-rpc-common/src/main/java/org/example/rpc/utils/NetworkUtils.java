package org.example.rpc.utils;

import lombok.extern.slf4j.Slf4j;
import org.example.rpc.exception.NetworkException;

import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

/**
 * @author yelihu
 */
@Slf4j
public class NetworkUtils {
    /**
     * get LAN ip address
     *
     * @return {@link String}
     */
    public static String getIpAddress() {
        try {
            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
            while (interfaces.hasMoreElements()) {
                NetworkInterface anInterface = interfaces.nextElement();
                //filter loopback and virtual network interface
                if (anInterface.isLoopback() || anInterface.isVirtual() || !anInterface.isUp()) {
                    continue;
                }
                Enumeration<InetAddress> addresses = anInterface.getInetAddresses();
                while (addresses.hasMoreElements()) {
                    InetAddress addr = addresses.nextElement();
                    //filter ipv6 and loopback address
                    if (addr instanceof Inet6Address || addr.isLoopbackAddress()) {
                        continue;
                    }
                    String ipResult = addr.getHostAddress();
                    if (log.isDebugEnabled()) {
                        log.debug("NetUtils getIpAddress ipResult: " + ipResult);
                    }
                    return ipResult;
                }
            }
            throw new NetworkException("can not get ip address");
        } catch (SocketException e) {
            log.error("NetUtils getIpAddress error={}", e.getMessage());
            throw new NetworkException(e.getMessage(), e);
        }
    }
}
