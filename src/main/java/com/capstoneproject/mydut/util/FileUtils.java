package com.capstoneproject.mydut.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Paths;

/**
 * @author vndat00
 * @since 10/23/2024
 */
public class FileUtils {
    private static final String TEMPLATE_PATH = "src/main/resources/templates";
    public static String readMailTemplate(String fileName) throws IOException {
        String filePath = Paths.get(TEMPLATE_PATH, fileName).toString();

        StringBuilder content = new StringBuilder();
        try (FileInputStream fis  = new FileInputStream(filePath)){
            int byteData;
            while ((byteData = fis.read()) != -1) {
                content.append((char) byteData);
            }
        }
        return content.toString();
    }
}
