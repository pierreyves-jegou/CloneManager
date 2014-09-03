package caillou.company.clonemanager.background.service.impl;

import caillou.company.clonemanager.background.bean.applicationFile.contract.ApplicationFile;
import caillou.company.clonemanager.background.bean.impl.MyFile;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashHandler {

    private static int bufferSize = 8192;

    public static int getBufferSize() {
        return bufferSize;
    }

    public static void setBufferSize(int bufferSize) {
        HashHandler.bufferSize = bufferSize;
    }

    public static String getHash(ApplicationFile myFile, Integer byteToRead) throws FileNotFoundException{
        MessageDigest digest = null;
        String output = null;
        int byteReaded = 0;
        int localBufferSize = bufferSize;

        if (byteToRead != null && byteToRead < localBufferSize) {
            localBufferSize = byteToRead;
        }

        try {
            digest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e2) {
            // TODO : log4j
            System.out
                    .println("Algorithm needed for the operation not found for MD5 calculation");
            e2.printStackTrace();
        }
        InputStream is = null;
        is = new FileInputStream((MyFile)myFile);
        byte[] buffer = new byte[localBufferSize];
        int read = 0;
        try {

            do {
                read = is.read(buffer);
                if (!(read > 0)) {
                    break;
                }

                digest.update(buffer, 0, read);
                byteReaded += read;

				// If the buffer size is larger than the amount of data to be
                // read : reduce the buffer size
                if (byteToRead != null
                        && localBufferSize > (byteToRead - byteReaded)) {
                    localBufferSize = byteToRead - byteReaded;
                    buffer = new byte[localBufferSize];
                }

            } while (true);

            byte[] md5sum = digest.digest();
            BigInteger bigInt = new BigInteger(1, md5sum);
            output = bigInt.toString(16);
        } catch (IOException e) {
            throw new RuntimeException(
                    "Unable to process file for MD5 calculation", e);
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                throw new RuntimeException(
                        "Unable to close input stream for MD5 calculation", e);
            }
        }
        return output;
    }
}
