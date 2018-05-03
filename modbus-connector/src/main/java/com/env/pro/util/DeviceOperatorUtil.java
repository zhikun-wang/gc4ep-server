package com.env.pro.util;

import org.springframework.util.Assert;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import static com.env.pro.util.CRC16Util.calcCrc16;

/**
 Created by Zhikun on 09/01/2018. */
public class DeviceOperatorUtil {

    public static void bagOut(String ip) throws IOException {
        String command = "01050008FF000DF8";
        String result = send(ip, command, 8);
        Assert.isTrue(command.equalsIgnoreCase(result), "bagOut command failure");
    }

    public static void bagIn(String ip) throws IOException {
        String command = "01050009FF005C38";
        String result = send(ip, command, 8);
        Assert.isTrue(command.equalsIgnoreCase(result), "bagIn command failure");
    }

    public static void query(String ip) throws IOException {
        String result = send(ip, "012000000005400E", 10);
        Assert.isTrue("0120050000000000a050".equalsIgnoreCase(result), "command failure");
    }

    public static int getPaperTime(String ip) throws IOException {
        String result = send(ip, "01180002000141C8", 8);
        String hex = result.substring(8, 12);
        Long second = Long.parseLong(hex, 16);
        return second.intValue() * 5 / 1000;
    }

    public static void setPaperTime(String ip, int second) throws IOException {
        send(ip, paperTimeTran(second), 8);
    }

    public static String paperTimeTran(int second) {
        StringBuilder sendInfo = new StringBuilder("01170002");
        String hex = String.format("%04X", second * 1000 / 5);
        sendInfo.append(hex);
        sendInfo.append(crc(sendInfo.toString()));
        return sendInfo.toString();
    }


    public static String send(String ip, String sendInfo, int length) throws IOException {
        System.out.println("send hex " + sendInfo);
        String result = send(ip, hexStringToBytes(sendInfo), length);
        System.out.println("result == " + result);
        return result;

    }

    public static String send(String ip, byte[] sendInfo, int length) throws IOException {

        Socket socket = SocketServer.getSocket(ip);
        InputStream is = socket.getInputStream();
        OutputStream os = socket.getOutputStream();
        os.write(sendInfo);
        os.flush();
        byte[] bs = new byte[length];
        is.read(bs);
        return bytesToHexString(bs);
    }


    public static String crc(String hex) {

        int crc = calcCrc16(hexStringToBytes(hex));
        String hexCrc = String.format("%X", crc);
        return hexCrc.substring(2, 4) + hexCrc.substring(0, 2);
    }


    private static String bytesToHexString(byte[] src) {
        StringBuilder stringBuilder = new StringBuilder("");
        if (src == null || src.length <= 0) {
            return null;
        }
        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }

    private static byte[] hexStringToBytes(String hexString) {
        if (hexString == null || hexString.equals("")) {
            return null;
        }
        hexString = hexString.toUpperCase();
        int length = hexString.length() / 2;
        char[] hexChars = hexString.toCharArray();
        byte[] d = new byte[length];
        for (int i = 0; i < length; i++) {
            int pos = i * 2;
            d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
        }
        return d;
    }

    private static byte charToByte(char c) {
        return (byte) "0123456789ABCDEF".indexOf(c);
    }
}
