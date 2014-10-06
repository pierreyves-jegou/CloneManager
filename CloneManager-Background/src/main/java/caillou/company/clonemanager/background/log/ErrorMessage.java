/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package caillou.company.clonemanager.background.log;

/**
 *
 * @author pierre
 */
public class ErrorMessage {
    
    public static String IOEXCEPTION_WHILE_MIMETYPE = "IOException while detecting the mimeType for the file :";    
    public static String IOEXCEPTION_WHILE_CLOSING = "Problem encountered while closing the file : ";
    public static String IOEXCEPTION_WHILE_READING = "Problem encountered while reading the file : ";
    
    public static String FILENOTFOUNDEXCEPTION = "The following file hasn't been found :";
    
    public static String NOSUCHALGORITHMEXCEPTION = "The following algorithm needed hasn't been found : ";
    
    public static String MALFORMEDURLEXCEPTION_WHILE_CREATINGIMAGE = "MalformedURLException while creating the image view from the file :";
    public static String VISIT_FILE_FAILED = "Visit of the file failed : ";
    public static String VISIT_DIRECTORY_FAILED = "Visit of the directory failed : ";
    public static String FILE_MATCHING_PATH_DOESNT_EXIST = "The file denoted by the following path doesn't exist : ";
}
