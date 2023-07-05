package com.shiny.utils;

import java.net.*;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.List;

public class NetUtil {
    public static String getIp() throws UnknownHostException {
        return InetAddress.getLocalHost().getHostAddress();
    }

    public static List<String> getIpAddress() throws SocketException {
        List<String> list = new LinkedList<>();
        Enumeration enumeration = NetworkInterface.getNetworkInterfaces();
        while (enumeration.hasMoreElements()) {
            NetworkInterface network = (NetworkInterface) enumeration.nextElement();
            Enumeration addresses = network.getInetAddresses();
            while (addresses.hasMoreElements()) {
                InetAddress address = (InetAddress) addresses.nextElement();
                if (address != null && (address instanceof Inet4Address || address instanceof Inet6Address)) {
                    list.add(address.getHostAddress());
                }
            }
        }
        return list;
    }

    public static void main(String[] args) throws UnknownHostException, SocketException {
        System.out.println(getIp());
        System.out.println(getIpAddress());
    }
}
