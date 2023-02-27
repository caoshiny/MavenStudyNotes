package com.shiny;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class FtpUtil {
    private static final String ip = "192.168.201.1";
    // private static final String ip = "localhost";
    private static final int port = 21;
    private static final String username = "pi";
    private static final String password = "raspberry4";
    private final FTPClient ftpClient = new FTPClient();

    // 连接ftp服务器
    public boolean connectFtpServer() {
        try{
            ftpClient.connect(ip, port);
            ftpClient.login(username, password);
            ftpClient.setConnectTimeout(30000);
            // 判断 ftp 是否登录成功
            int reply = ftpClient.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) {
                System.out.println("login ftp server error!");
                ftpClient.disconnect();
                return false;
            }
            ftpClient.setControlEncoding("UTF-8");
            ftpClient.enterLocalPassiveMode();
            ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
            return true;
        }catch (Exception e) {
            System.out.println("connect ftp server error -> " + e);
            return false;
        }
    }

    // 断开ftp服务器
    public void disconnectFtpServer() {
        try {
            ftpClient.logout();
            ftpClient.disconnect();
        }catch (Exception e) {
            System.out.println("disconnect ftp server error -> " + e);
        }
    }

    // 根据文件名读ftp上文件内容
    public String readFile(String fileName) {
        StringBuilder content = new StringBuilder();
        try {
            ftpClient.changeWorkingDirectory("/");
            InputStream in = ftpClient.retrieveFileStream(fileName);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String line;
            while ((line = br.readLine()) != null) {
                content.append(line);
            }
            br.close();
            in.close();
            ftpClient.completePendingCommand();
        }catch (Exception e) {
            System.out.println("readFile from ftp server error -> " + e);
        }
        return content.toString();
    }

    // 根据文件名删除文件
    public boolean deleteFile(String fileName) {
        boolean isDelFlag = false;
        try {
            ftpClient.changeWorkingDirectory("/");
            isDelFlag = ftpClient.deleteFile(fileName);
        }catch (Exception e) {
            System.out.println("deleteFile from ftp server error -> " + e);
        }
        return isDelFlag;
    }

    // 上传文件到ftp服务器
    public boolean uploadFile(String fileName, InputStream ins) {
        boolean isUploadFlag = false;
        try {
            ftpClient.changeWorkingDirectory("/");
            isUploadFlag = ftpClient.storeFile(fileName, ins);
            ins.close();
        }catch (Exception e) {
            System.out.println("uploadFile to ftp server error -> " + e);
        }
        return isUploadFlag;
    }

    // 判断 ftp 服务器上是否存在某个文件
    public boolean isExistFileByName(String fileName) {
        boolean isExist = false;
        try {
            ftpClient.changeWorkingDirectory("/");
            FTPFile[] ftpFilesList = ftpClient.listFiles();
            for(FTPFile ftpFile: ftpFilesList) {
                if (ftpFile.isFile()) {
                    if(ftpFile.getName().equals(fileName)) {
                        isExist = true;
                    }
                }
            }
        }catch (Exception e ) {
            e.printStackTrace();
        }
        return isExist;
    }

    // 字符串转字节流
    public InputStream convertStrToIns(String target) {
        return new ByteArrayInputStream(target.getBytes(StandardCharsets.UTF_8));
    }


    public static void main(String[] args) throws IOException {
        FtpUtil ftpUtil = new FtpUtil();
        ftpUtil.connectFtpServer();

        System.out.println(ftpUtil.readFile("cmd_gain_ack"));
        //ftpUtil.uploadFile("cmd_gain_ack", ftpUtil.convertStrToIns("78"));

        ftpUtil.disconnectFtpServer();
    }
}