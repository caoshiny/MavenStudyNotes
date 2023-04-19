package com.shiny.utils;

import com.coremedia.iso.boxes.Container;
import com.googlecode.mp4parser.DataSource;
import com.googlecode.mp4parser.FileDataSourceImpl;
import com.googlecode.mp4parser.authoring.Movie;
import com.googlecode.mp4parser.authoring.builder.DefaultMp4Builder;
import com.googlecode.mp4parser.authoring.tracks.h264.H264TrackImpl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

public class Mp4ParserUtil {
    public void parser() throws IOException {
        H264TrackImpl h264Track = new H264TrackImpl(new FileDataSourceImpl("D:/test/1.h264"));
        Movie movie = new Movie();
        movie.addTrack(h264Track);
        Container mp4file = new DefaultMp4Builder().build(movie);
        FileChannel fc = new FileOutputStream(new File("D:\\test\\1.mp4")).getChannel();
        mp4file.writeContainer(fc);
        fc.close();
    }

    public void parser1(String h264Path, String mp4Path) throws IOException {
        DataSource h264File = new FileDataSourceImpl(h264Path);
        H264TrackImpl h264Track = new H264TrackImpl(h264File);
        Movie movie = new Movie();
        movie.addTrack(h264Track);
        Container out = new DefaultMp4Builder().build(movie);
        FileOutputStream fos = new FileOutputStream(new File(mp4Path));
        out.writeContainer(fos.getChannel());
        fos.close();
    }

    public static void main(String[] args) throws IOException {
        Mp4ParserUtil mp4ParserUtil = new Mp4ParserUtil();
        mp4ParserUtil.parser1("D:/test/2.h264", "D:\\test\\1.mp4");
    }
}
