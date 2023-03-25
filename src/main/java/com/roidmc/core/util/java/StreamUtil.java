package com.roidmc.core.util.java;

import java.io.*;

public class StreamUtil {

    public static InputStream[] copies(InputStream input, int amount) throws IOException {
        InputStream[] copies = new InputStream[amount];
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len;
        while ((len = input.read(buffer)) > -1 ) {
            baos.write(buffer, 0, len);
        }
        baos.flush();
        for(int i = 0; i < amount; i++){
            copies[i] = new ByteArrayInputStream(baos.toByteArray());
        }
        input.close();
        return copies;
    }
}
