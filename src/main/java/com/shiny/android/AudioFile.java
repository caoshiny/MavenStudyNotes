package com.shiny.android;

import java.util.ArrayList;
import java.util.List;

public class AudioFile {
    public String name;
    public String duration;
    public String size;

    public AudioFile() {
    }

    public AudioFile(String name, String duration, String size) {
        this.name = name;
        this.duration = duration;
        this.size = size;
    }

    public static final String[] nameArr ={"20230120081100-20230120081101", "20230220081100-20230220081101",
            "20230320081100-20230320081101", "20230121081100-20230121081101", "20230120081100-20230120081101",
            "20230420081100-20230420081101", "20230122081100-20230122081101", "20230120081100-20230120081101",
            "20230520081100-20230520081101", "20230123081100-20230123081101", "20230120081100-20230120081101"
    };

    public static final String[] durationArr = {"1小时34分", "34分", "2小时34分", "34分", "22分", "34分", "34分", "34分", "34分", "34分", "34分", };

    public static final String[] sizeArr = {"253M", "23M", "225M", "123M", "25M", "11M", "321M", "66M", "14M", "96M", "164M"};

    public static List<AudioFile> getDefaultList() {
        List<AudioFile> audioFiles = new ArrayList<>();
        for (int i = 0; i < nameArr.length; i++) {
            audioFiles.add(new AudioFile(nameArr[i], durationArr[i], sizeArr[i]));
        }
        return audioFiles;
    }

    @Override
    public String toString() {
        return "AudioFile{" +
                "name='" + name + '\'' +
                ", duration='" + duration + '\'' +
                ", size='" + size + '\'' +
                '}';
    }
}
