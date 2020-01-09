package com.excalibur.myBlog.utils.fileService;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Service
public class FTPService {

   public StoreResult storeFile(String dirName, String fileName, byte[] fileContent) {
       FTPClient ftpClient = new FTPClient();
       List<String> errors = new ArrayList<>();
       try {
           ftpClient.connect("localhost", 51234);
           if (ftpClient.isConnected()) {
               if (ftpClient.login("admin", "eckalibyr")) {
                   ftpClient.enterLocalPassiveMode();
                   if (ftpClient.changeWorkingDirectory(dirName)) {
                       InputStream stream = new ByteArrayInputStream(fileContent);
                       ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
                       ftpClient.storeFile(fileName, stream);
                       stream.close();
                       return new StoreResult(true, fileName, errors);
                   } else {
                       errors.add("CHANGE TO ADMIN DIR FAILED");
                       return new StoreResult(true, fileName, errors);
                   }
               } else {
                   errors.add("LOGIN FAILED");
                   return new StoreResult(true, fileName, errors);
               }
           } else {
               errors.add("CONNECT FAILED");
               return new StoreResult(true, fileName, errors);
           }
       } catch (Exception ex) {
           ex.printStackTrace();
           errors.add(ex.getMessage());
           return new StoreResult(true, fileName, errors);
       } finally {
           try {
               ftpClient.abor();
               ftpClient.quit();
           } catch (Exception e) {
               e.printStackTrace();
           }
       }
   }

}
