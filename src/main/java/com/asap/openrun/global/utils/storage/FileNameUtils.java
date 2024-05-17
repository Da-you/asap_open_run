package com.asap.openrun.global.utils.storage;

import java.util.UUID;

public class FileNameUtils {

    public static void checkImageMimeType(String mimeType) {
        if (!(mimeType.equals("image/jpg") || mimeType.equals("image/jpeg")
                || mimeType.equals("image/png") || mimeType.equals("image/gif"))) {
            throw new IllegalArgumentException();
        }
    }

    public static String fileNameConvert(String fileName) {
        StringBuilder builder = new StringBuilder();
        UUID uuid = UUID.randomUUID();
        String extension = getExtension(fileName);

        builder.append(uuid).append(".").append(extension);
        return builder.toString();
    }

    private static String getExtension(String fileName) {
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }

    public static String getFileName(String path) {
        return path.substring(path.lastIndexOf("/") + 1);
    }
    public static String toThumbnail(String src) {
        return src.replaceFirst("origin", "thumbnail");
    }

}
