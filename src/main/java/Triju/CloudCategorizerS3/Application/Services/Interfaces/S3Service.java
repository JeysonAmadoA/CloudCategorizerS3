package Triju.CloudCategorizerS3.Application.Services.Interfaces;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface S3Service {

    String uploadFile(MultipartFile file) throws IOException;

    String downloadFile(String fileName) throws IOException;

    List<String> listFiles() throws IOException;

    String deleteFile(String fileName) throws IOException;

    String updateFileName(String oldFileName, String newFileName) throws IOException;
}
