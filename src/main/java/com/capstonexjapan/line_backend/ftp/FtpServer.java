package com.capstonexjapan.line_backend.ftp;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@Component
@Slf4j
public class FtpServer {

    @Value("${ftp.server}")
    private String server;

    @Value("${ftp.username}")
    private String username;

    @Value("${ftp.password}")
    private String password;

    /**
     * FTP 서버 연결
     */
    public FTPClient connectFTP() throws IOException {
        FTPClient ftp = new FTPClient();
        ftp.setControlEncoding("utf-8");

        try {
            ftp.connect(server);
            int replyCode = ftp.getReplyCode();
            log.info("replyCode : {}", replyCode);

            if (!FTPReply.isPositiveCompletion(replyCode)) {
                log.error("FTP 연결 실패");
                ftp.disconnect();
                throw new IOException("FTP 서버에 연결할 수 없습니다.");
            }

            if (!ftp.login(username, password)) {
                log.error("FTP 로그인 실패");
                ftp.logout();
                throw new IOException("FTP 로그인에 실패했습니다.");
            }

            ftp.setFileType(FTP.BINARY_FILE_TYPE);
            return ftp;
        } catch (IOException e) {
            log.error("FTP 연결 중 오류 발생", e);
            throw new IOException("FTP 연결 중 오류가 발생했습니다.", e);
        }
    }

    public void disconnectFTP(FTPClient ftp) {
        try {
            if (ftp.isConnected()) {
                ftp.logout();
                ftp.disconnect();
            }
        } catch (IOException e) {
            log.error("FTPClient:: 서버 종료 실패.", e);
        }
    }

    @Async
    public void upload(MultipartFile file, UUID uuid) {
        FTPClient ftp = null;
        try {
            ftp = connectFTP();

            boolean directoryExists = ftp.changeWorkingDirectory("/A");
            if (!directoryExists) {
                log.error("Directory /A does not exist.");
                return;
            }

            try (InputStream inputStream = file.getInputStream()) {
                String remoteFileName = uuid.toString() + "_" + file.getOriginalFilename();
                boolean success = ftp.storeFile(remoteFileName, inputStream);
                if (!success) {
                    log.error("FTPClient:: file upload failed for file {}", remoteFileName);
                    throw new IOException("파일 업로드 실패: " + remoteFileName);
                }
            }
        } catch (IOException e) {
            log.error("FTPClient:: 파일 업로드 중 오류 발생.", e);
        } finally {
            if (ftp != null && ftp.isConnected()) {
                disconnectFTP(ftp);
            }
        }
    }
}
