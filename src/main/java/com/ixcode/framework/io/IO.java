package com.ixcode.framework.io;

import java.io.*;

public class IO {
    public static void tryToClose(InputStream inputStream) {
        if (inputStream != null) {
            try {
                inputStream.close();
            } catch (IOException e) {
                throw new RuntimeException("Could not close input stream.", e);
            }
        }
    }
}
