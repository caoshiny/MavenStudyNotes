package com.shiny.utils;

import ws.schild.jave.Encoder;
import ws.schild.jave.EncoderException;
import ws.schild.jave.MultimediaObject;
import ws.schild.jave.encode.AudioAttributes;
import ws.schild.jave.encode.EncodingAttributes;

import java.io.File;
import java.io.IOException;

public class FfmpegUtil {
    private static final int SAMPLING_RATE = 16000;
    private static final int SINGLE_CHANNEL = 1;

    /**
     * @author: shiny
     * @description: 音频统一转换为wav格式
     * @param audioPath 原文件音频路径
     * @param targetPath 目标文件夹存放路径
     * @return 转换后的wav文件路径
     */
    public static String audioToWav(String audioPath, String targetPath) throws EncoderException, IOException {
        File sourceAudioFile = new File(audioPath);
        String wavFileName = sourceAudioFile.getName().substring(0, sourceAudioFile.getName().lastIndexOf(".")) + ".wav";
        File wavFile = new File(targetPath + File.separator + wavFileName);
        if (!wavFile.exists()) {
            wavFile.createNewFile();
        }

        MultimediaObject multimediaObject = new MultimediaObject(sourceAudioFile);
        AudioAttributes audioAttr = new AudioAttributes();
        audioAttr.setSamplingRate(SAMPLING_RATE);
        audioAttr.setChannels(SINGLE_CHANNEL);
        EncodingAttributes encodingAttr = new EncodingAttributes();
        encodingAttr.setOutputFormat("wav");
        encodingAttr.setAudioAttributes(audioAttr);
        Encoder encoder = new Encoder();
        encoder.encode(multimediaObject, wavFile, encodingAttr);

        return wavFile.getAbsolutePath();
    }

    public static void main(String[] args) throws EncoderException, IOException {
        FfmpegUtil.audioToWav("C:\\Users\\1S-CAOSN\\Desktop\\source\\1月.MP3", "C:\\Users\\1S-CAOSN\\Desktop\\target");
    }
}
