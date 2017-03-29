package com.github.demo.nio.mapped;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by error on 2017/3/16.
 */
public class MappedFileQueue {

    private final List<MappedFile> mappedFileList;
    private final String storePath;

    public MappedFileQueue(String storePath) {
        mappedFileList = new LinkedList<MappedFile>();
        this.storePath = storePath;
    }

    public boolean load() {
        Path path = Paths.get(this.storePath);

        try {
            DirectoryStream<Path> ds = Files.newDirectoryStream(path);
            for(Path file : ds) {
                MappedFile mappedFile = new MappedFile(file.getFileName().toAbsolutePath().toString(), 1024);
                mappedFile.initialize();
                mappedFileList.add(mappedFile);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    

}
