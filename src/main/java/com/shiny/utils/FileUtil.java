package com.shiny.utils;

import com.alibaba.fastjson.JSONArray;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FileUtil {
    private final String[] formats = {".mp3", ".MP3", ".wav", ".WAV"};
    private List<File> audioFiles = new ArrayList<>();

    private void traverseFolder(String folderPath) {
        // 文件后缀名数组转list
        List<String> suffixList = new ArrayList<>(formats.length);
        Collections.addAll(suffixList, formats);

        File folder = new File(folderPath);
        File[] files = folder.listFiles();

        for (File file : files) {
            if (file.isFile()) {
                String fileSuffix = file.getName().substring(file.getName().lastIndexOf("."));
                if (suffixList.contains(fileSuffix)) {
                    audioFiles.add(file);
                }
            } else {
                traverseFolder(file.getAbsolutePath());
            }
        }
    }

    private void clearAudioFiles() {
        audioFiles.clear();
    }

    private List<File> getAudioFiles() {
        return audioFiles;
    }

    public List<File> traverse(String folderPath) {
        clearAudioFiles();
        traverseFolder(folderPath);
        return getAudioFiles();
    }

    public JSONArray getPathInfo(String path) {
        JSONArray directoryJsonArr = new JSONArray();
        if (StringUtils.isEmpty(path)) {
            File[] roots = File.listRoots();
            for (File root : roots) {
                directoryJsonArr.add(root.getAbsolutePath());
            }
        } else {
            File directory = new File(path);
            for (File file: directory.listFiles()) {
                if (file.isDirectory()) {
                    directoryJsonArr.add(file.getName());
                }
            }
        }
        return directoryJsonArr;
    }

    /**
     * 文件大小智能转换
     * 会将文件大小转换为最大满足单位
     * @param size（文件大小，单位为B）
     * @return
     */
    public static String readableFileSize(long size) {
        if (size <= 0) {
            return "0";
        }
        final String[] units = new String[]{"B", "KB", "MB", "GB", "TB"};
        int digitGroups = (int) (Math.log10(size) / Math.log10(1024));
        return new DecimalFormat("#,###.##").format(size / Math.pow(1024, digitGroups)) + " " + units[digitGroups];
    }

    public static void main(String[] args) {
        FileUtil fileUtil = new FileUtil();
//        List<File> files = fileUtil.traverse("C:\\Users\\1S-CAOSN\\Desktop\\source");
//        for (File file: files) {
//            System.out.println(file.getAbsolutePath());
//        }

        JSONArray dir = fileUtil.getPathInfo(null);
        System.out.println(dir);
        JSONArray dir1 = fileUtil.getPathInfo("D:/");
        System.out.println(dir1);

        System.out.println(readableFileSize(4101796336L));
    }
}
