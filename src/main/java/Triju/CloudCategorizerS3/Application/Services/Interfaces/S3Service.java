package Triju.CloudCategorizerS3.Application.Services.Interfaces;

import Triju.CloudCategorizerS3.Domain.Exceptions.FolderNotFoundException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface S3Service {

    String uploadFile(MultipartFile file) throws Exception;

    byte[] downloadFile(String fileName) throws Exception;

    List<String> listFiles() throws IOException;

    String deleteFile(String fileName) throws Exception;

    String updateFileName(String oldFileName, String newFileName) throws Exception;

    byte[]  downloadFilesByFolder(String folderName) throws IOException, FolderNotFoundException;
}
