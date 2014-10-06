package caillou.company.clonemanager.background.service.impl;

import caillou.company.clonemanager.background.bean.applicationFile.contract.ApplicationFile;
import caillou.company.clonemanager.background.exception.CloneManagerException;
import caillou.company.clonemanager.background.exception.CloneManagerIOException;
import caillou.company.clonemanager.background.log.ErrorMessage;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import org.apache.log4j.Logger;

public class HashProcessor {
    
    private static final Logger log = Logger.getLogger(HashProcessor.class.getName());
    
    private static int bufferSize = 8192;

    public static int getBufferSize() {
        return bufferSize;
    }

    public static void setBufferSize(int bufferSize) {
        HashProcessor.bufferSize = bufferSize;
    }

    public static String process(ApplicationFile applicationFile, Integer byteToRead) throws CloneManagerException{
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
            log.fatal(ErrorMessage.NOSUCHALGORITHMEXCEPTION + "MD5");
            throw new CloneManagerException(ErrorMessage.NOSUCHALGORITHMEXCEPTION + "MD5");
        }
        InputStream is = null;
        try {
            is = new FileInputStream(applicationFile.getEnclosingFile());
        } catch (FileNotFoundException ex) {
            log.fatal(ErrorMessage.FILENOTFOUNDEXCEPTION + applicationFile.getAbsolutePath());
            throw new CloneManagerIOException(ErrorMessage.IOEXCEPTION_WHILE_READING + applicationFile.getAbsolutePath(), ex.getMessage());
        }
                
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
            log.error(ErrorMessage.IOEXCEPTION_WHILE_READING + applicationFile.getAbsolutePath());
            throw new CloneManagerIOException(ErrorMessage.IOEXCEPTION_WHILE_READING + applicationFile.getAbsolutePath(), applicationFile.getAbsolutePath());
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                log.error(ErrorMessage.IOEXCEPTION_WHILE_CLOSING + applicationFile.getAbsolutePath());
                throw new CloneManagerIOException(ErrorMessage.IOEXCEPTION_WHILE_CLOSING + applicationFile.getAbsolutePath(), applicationFile.getAbsolutePath());
            }
        }
        return output;
    }
}
