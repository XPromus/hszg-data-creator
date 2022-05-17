package com.hszg.backend.util;

import java.nio.file.Path;
import java.nio.file.Paths;

public class FileUtil {

    private FileUtil() {

    }

    public static final String FOLDER_PATH = "images//";
    public static final Path FILE_PATH = Paths.get(FOLDER_PATH);

}
