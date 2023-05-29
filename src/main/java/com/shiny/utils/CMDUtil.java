package com.shiny.utils;

import org.apache.commons.exec.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class CMDUtil {
    public static void exec() throws IOException {
        //无止境ping，但会再下面被ExecuteWatchdog设置的超时值终止进程而中断
        CommandLine commandLine = CommandLine.parse("ping 192.192.1.116");

        DefaultExecutor executor = new DefaultExecutor();
        executor.setExitValues(null);

        //如果进程运行时间超过timeOut毫秒，进程就自动结束（销毁）
        ExecuteWatchdog watchdog = new ExecuteWatchdog(5 * 1000);
        executor.setWatchdog(watchdog);

        final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        final ByteArrayOutputStream errorStream = new ByteArrayOutputStream();
        PumpStreamHandler streamHandler = new PumpStreamHandler(outputStream, errorStream);
        executor.setStreamHandler(streamHandler);

        ExecuteResultHandler erh = new ExecuteResultHandler() {
            @Override
            public void onProcessComplete(int exitValue) {
                try {
                    String os = outputStream.toString("GBK");
                    System.out.println(os);
                } catch(Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onProcessFailed(ExecuteException e) {
                e.printStackTrace();
                try {
                    String es = errorStream.toString("GBK");
                    System.out.println("onProcessFailed:" + es);
                } catch (Exception ee) {
                    ee.printStackTrace();
                }
            }
        };

        // 执行命令
        executor.execute(commandLine, erh);

    }

    public static void main(String[] args) throws IOException {
        CMDUtil.exec();
    }
}
