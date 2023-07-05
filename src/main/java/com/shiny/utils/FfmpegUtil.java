package com.shiny.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.io.FileUtils;
import ws.schild.jave.Encoder;
import ws.schild.jave.EncoderException;
import ws.schild.jave.MultimediaObject;
import ws.schild.jave.encode.AudioAttributes;
import ws.schild.jave.encode.EncodingAttributes;
import ws.schild.jave.info.MultimediaInfo;
import ws.schild.jave.process.ProcessWrapper;
import ws.schild.jave.process.ffmpeg.DefaultFFMPEGLocator;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

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

    public static void cut(String inputPath, String outputPath, String start, String end) throws IOException {
        ProcessWrapper ffmpeg = new DefaultFFMPEGLocator().createExecutor();
        ffmpeg.addArgument("-y");
        ffmpeg.addArgument("-i");
        ffmpeg.addArgument(inputPath);
        ffmpeg.addArgument("-ss");
        ffmpeg.addArgument(start);
        ffmpeg.addArgument("-to");
        ffmpeg.addArgument(end);
        ffmpeg.addArgument("-c");
        ffmpeg.addArgument("copy");
        ffmpeg.addArgument(outputPath);
        ffmpeg.execute();
        System.out.println(ffmpeg.getProcessExitCode());
    }

    public static void concat() throws IOException {
        ProcessWrapper ffmpeg = new DefaultFFMPEGLocator().createExecutor();
        ffmpeg.addArgument("-y");
        ffmpeg.addArgument("-i");
        ffmpeg.addArgument("\"concat:" + "C:\\Users\\1S-CAOSN\\Desktop\\target\\shiny\\temp\\6.wav" + "|" + "C:\\Users\\1S-CAOSN\\Desktop\\target\\shiny\\temp\\7.wav" + "|" + "C:\\Users\\1S-CAOSN\\Desktop\\target\\shiny\\temp\\07.wav" +"\"");
//        ffmpeg.addArgument("C:\\Users\\1S-CAOSN\\Desktop\\target\\shiny\\temp\\6.wav");
//        ffmpeg.addArgument("|");
//        ffmpeg.addArgument("C:\\Users\\1S-CAOSN\\Desktop\\target\\shiny\\temp\\7.wav");
//        ffmpeg.addArgument("\"");
        ffmpeg.addArgument("-acodec");
        ffmpeg.addArgument("copy");
        ffmpeg.addArgument("C:\\Users\\1S-CAOSN\\Desktop\\target\\shiny\\temp\\result.wav");
        ffmpeg.execute();
        // System.out.println(ffmpeg.getProcessExitCode());
        try (BufferedReader br = new BufferedReader(new InputStreamReader(ffmpeg.getErrorStream()))) {
            blockFfmpeg(br);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void cutAndConcat(String inputPath, String tempPath, String outputPath, JSONArray timePeriods) throws IOException {
        File tempDir = new File(tempPath);
        if (!tempDir.exists()) {
            tempDir.mkdirs();
        } else {
            // 删除临时文件夹所有文件
            FileUtils.cleanDirectory(tempDir);
        }
        File needConcatTxt = new File(tempPath + File.separator + "list.txt");
        if (needConcatTxt.exists()) {
            needConcatTxt.delete();
            needConcatTxt.createNewFile();
        } else {
            needConcatTxt.createNewFile();
        }

        // 截取
        for (int i = 0; i < timePeriods.size(); i ++) {
            JSONObject period = timePeriods.getJSONObject(i);
            ProcessWrapper ffmpeg = new DefaultFFMPEGLocator().createExecutor();
            ffmpeg.addArgument("-y");
            ffmpeg.addArgument("-i");
            ffmpeg.addArgument(inputPath);
            ffmpeg.addArgument("-ss");
            ffmpeg.addArgument(period.getString("start"));
            ffmpeg.addArgument("-to");
            ffmpeg.addArgument(period.getString("end"));
            ffmpeg.addArgument("-acodec");
            ffmpeg.addArgument("copy");
            ffmpeg.addArgument(tempPath + File.separator + i + ".wav");
            ffmpeg.execute();
            System.out.println(ffmpeg.getProcessExitCode());

            // 写入文件
            String needWrite = "file " + "'" + tempPath + File.separator + i + ".wav" + "'\n";
            FileUtils.writeStringToFile(needConcatTxt, needWrite, StandardCharsets.UTF_8, true);
        }

        // 拼接
        ProcessWrapper ffmpeg = new DefaultFFMPEGLocator().createExecutor();
        ffmpeg.addArgument("-y");
        ffmpeg.addArgument("-f");
        ffmpeg.addArgument("concat");
        ffmpeg.addArgument("-safe");
        ffmpeg.addArgument("0");
        ffmpeg.addArgument("-i");
        ffmpeg.addArgument(needConcatTxt.getAbsolutePath());
        ffmpeg.addArgument("-acodec");
        ffmpeg.addArgument("copy");
        ffmpeg.addArgument(outputPath + File.separator + "result.wav");
        ffmpeg.execute();
        ffmpeg.getProcessExitCode();

        // 删除临时文件夹所有文件
        FileUtils.cleanDirectory(tempDir);

    }

    public static long getMultimediaInfo(String localPath) {
        MultimediaInfo multimediaInfo = null;

        try {
            multimediaInfo = new MultimediaObject(new File(localPath)).getInfo();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return multimediaInfo.getDuration();
    }

    private static void blockFfmpeg(BufferedReader br) throws IOException {
        String line;
        // 该方法阻塞线程，直至合成成功
        while ((line = br.readLine()) != null) {
            doNothing(line);
        }
    }

    private static void doNothing(String line) {
        System.out.println(line);
    }


    public static void main(String[] args) throws EncoderException, IOException {
        // FfmpegUtil.audioToWav("C:\\Users\\1S-CAOSN\\Desktop\\source\\1月.MP3", "C:\\Users\\1S-CAOSN\\Desktop\\target");

        // FfmpegUtil.cut("C:\\Users\\1S-CAOSN\\Desktop\\211112_loke.wav", "C:\\Users\\1S-CAOSN\\Desktop\\1.wav", "0.704", "45.4646875");

        String timePeriodsStr = "[\n" +
                "        {\n" +
                "            \"start\": 90.24,\n" +
                "            \"end\": 90.616\n" +
                "        },\n" +
                "        {\n" +
                "            \"start\": 143.232,\n" +
                "            \"end\": 143.416\n" +
                "        },\n" +
                "        {\n" +
                "            \"start\": 197.888,\n" +
                "            \"end\": 198.264\n" +
                "        },\n" +
                "        {\n" +
                "            \"start\": 285.376,\n" +
                "            \"end\": 285.944\n" +
                "        },\n" +
                "        {\n" +
                "            \"start\": 322.176,\n" +
                "            \"end\": 322.36\n" +
                "        },\n" +
                "        {\n" +
                "            \"start\": 747.968,\n" +
                "            \"end\": 748.152\n" +
                "        },\n" +
                "        {\n" +
                "            \"start\": 820.032,\n" +
                "            \"end\": 823.928\n" +
                "        },\n" +
                "        {\n" +
                "            \"start\": 874.368,\n" +
                "            \"end\": 875.448\n" +
                "        }\n" +
                "    ]";
        JSONArray timePeriods = JSON.parseArray(timePeriodsStr);
//        FfmpegUtil.cutAndConcat("C:\\Users\\1S-CAOSN\\Desktop\\target\\shiny\\tmpWav\\1月.wav",
//                "C:\\Users\\1S-CAOSN\\Desktop\\target\\shiny\\temp",
//                "C:\\Users\\1S-CAOSN\\Desktop\\target\\shiny\\result",
//                timePeriods
//        );

        // concat();

        long dur = FfmpegUtil.getMultimediaInfo("C:\\Users\\1S-CAOSN\\Desktop\\target\\shiny\\final\\shiny_2023_06_08_10_31_06.wav");
        System.out.println(dur);
    }
}
