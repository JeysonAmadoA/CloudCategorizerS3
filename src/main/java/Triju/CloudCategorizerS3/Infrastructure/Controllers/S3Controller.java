package Triju.CloudCategorizerS3.Infrastructure.Controllers;

import Triju.CloudCategorizerS3.Application.Services.Interfaces.S3Service;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
public class S3Controller {

    private final S3Service s3Service;

    public S3Controller(S3Service s3Service) {
        this.s3Service = s3Service;
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file")MultipartFile file) throws IOException {
        String response = s3Service.uploadFile(file);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/download/{fileName}")
    public ResponseEntity<String> downloadFile(@PathVariable("fileName")String fileName) throws IOException {
        String response = s3Service.downloadFile(fileName);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/list-files")
    public ResponseEntity<List<String>> listFiles() throws IOException {
        List<String> response = s3Service.listFiles();
        return ResponseEntity.ok(response);
    }

    @PutMapping("/update/{oldFileName}/{newFileName}")
    public ResponseEntity<String> updateFileName(@PathVariable("oldFileName")String oldFileName,
                                                 @PathVariable("newFileName")String newFileName) throws IOException {
        String response = s3Service.updateFileName(oldFileName, newFileName);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/delete/{fileName}")
    public ResponseEntity<String> delete(@PathVariable("fileName")String fileName) throws IOException {
        String response = s3Service.deleteFile(fileName);
        return ResponseEntity.ok(response);
    }
}
