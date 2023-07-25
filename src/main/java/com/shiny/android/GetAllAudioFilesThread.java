package com.shiny.android;

public class GetAllAudioFilesThread extends Thread {
    @Override
    public void run() {
        System.out.println(111);
    }


    public static void main(String[] args) {
        GetAllAudioFilesThread thread = new GetAllAudioFilesThread();
        thread.start();
    }
}
