package com.capstonexjapan.line_backend.ftp;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@Component
@Slf4j
public class FtpServer {
    @Value("${ftp.server}")
    private String server;


    @Value("${ftp.username}")
    private String username;

    @Value("${ftp.password}")
    private String password;

    private FTPClient ftp;

    /**
     * FTP 서버 연결
     *
     */
    public FTPClient connectFTP(){

        ftp = new FTPClient();
        ftp.setControlEncoding("utf-8");

        try {
            ftp.connect(server);

            int replyCode = ftp.getReplyCode();
            log.info("replyCode : {}",replyCode);

            if(!FTPReply.isPositiveCompletion(replyCode)){
                System.out.println("FTP 연결 실패");
            }

            if(!ftp.login(username, password)){
                System.out.println("FTP 로그인 실패");
            }

            return ftp;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void disconnectFTP() {
        try {
            ftp.logout();
            ftp.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
            log.error("FTPClient:: server close failed.");
        }
    }

    public void upload(MultipartFile file) throws IOException {
        connectFTP();
        InputStream inputStream = null;
        boolean directoryExists = ftp.changeWorkingDirectory("/A");
        if (!directoryExists) {
            log.error("Directory /a does not exist.");
            return;
        }
        try {
            inputStream = file.getInputStream();

            ftp.storeFile(file.getOriginalFilename(), inputStream);
        } catch (IOException e) {
            e.printStackTrace();
            log.error("FTPClient:: file upload failed.");
        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
                log.error("FTPClient:: file upload failed.");
            }
            disconnectFTP();
        }
    }

}

