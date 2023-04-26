package com.shiny.utils;

import ws.schild.jave.*;

import java.io.File;
import java.io.IOException;

public class FfmpegUtil {
    public static void h264ToMp4(String h264Path) throws IOException, EncoderException {
        File file = new File(h264Path);
        String mp4FilePath = h264Path.replace("h264", "mp4");
        File mpFile = new File(mp4FilePath);
        if (!mpFile.exists()) {
            mpFile.createNewFile();
        }
        //获取文件多媒体类
        MultimediaObject sourceFile = new MultimediaObject(file);

        VideoAttributes video = new VideoAttributes();
        AudioAttributes audio = new AudioAttributes();
        //音频编码器
        audio.setCodec("libmp3lame");
        //位速率又叫比特率，是指在单位时间内可以传输多少数据
        audio.setBitRate(64000);
        //音频的通道数，一般来说 都是单通道和双通道（立体音）
        audio.setChannels(1);
        //是指在数码音频和视频技术应用中，当进行模拟/数码转换时，每秒钟对模拟信号进行取样时的快慢次数
        audio.setSamplingRate(22050);
        //视频编码器
        video.setCodec("libx264");
        //位速率又叫比特率，是指在单位时间内可以传输多少数据
        video.setBitRate(800000);
        //画面桢速率
        video.setFrameRate(20);
        video.setSize(new VideoSize(1920, 1080));
        EncodingAttributes attr = new EncodingAttributes();
        attr.setFormat("mp4");
        attr.setAudioAttributes(audio);
        attr.setVideoAttributes(video);
        Encoder encoder = new Encoder();
        encoder.encode(sourceFile, mpFile, attr);
    }

    public static void main(String[] args) throws IOException, EncoderException {
        long startTime = System.currentTimeMillis();
        FfmpegUtil.h264ToMp4("D:\\test\\1.h264");
        System.out.println(System.currentTimeMillis() - startTime);
    }
}
