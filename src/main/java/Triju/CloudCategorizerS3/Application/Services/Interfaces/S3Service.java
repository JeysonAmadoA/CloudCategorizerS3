package Triju.CloudCategorizerS3.Application.Services.Interfaces;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface S3Service {

    String uploadFile(MultipartFile file) throws Exception;

    String downloadFile(String fileName) throws Exception;

    List<String> listFiles() throws IOException;

    String deleteFile(String fileName) throws Exception;

    String updateFileName(String oldFileName, String newFileName) throws Exception;
}
