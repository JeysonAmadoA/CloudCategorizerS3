package Triju.CloudCategorizerS3.Infrastructure.Controllers.S3;

import Triju.CloudCategorizerS3.Application.Services.Interfaces.S3Service;
import Triju.CloudCategorizerS3.Domain.Exceptions.FileNotFoundException;
import Triju.CloudCategorizerS3.Domain.Exceptions.UnnamedFileException;
import Triju.CloudCategorizerS3.Infrastructure.Controllers.BaseController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@RestController
public class S3Controller extends BaseController {

    private final S3Service s3Service;

    public S3Controller(S3Service s3Service) {
        this.s3Service = s3Service;
    }

    @PostMapping("/upload")
    public ResponseEntity<Map<String, Object>> uploadFile(@RequestParam("file")MultipartFile file) throws Exception {
        Map <String, Object> response = getJsonResponse(s3Service.uploadFile(file), HttpStatus.CREATED.value());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/download/{fileName}")
    public ResponseEntity<Map<String, Object>> downloadFile(@PathVariable("fileName")String fileName) throws Exception {
        Map <String, Object> response = getJsonResponse(s3Service.downloadFile(fileName));
        return ResponseEntity.ok(response);
    }

    @GetMapping("/list-files")
    public ResponseEntity<Map<String, Object>> listFiles() throws IOException {
        Map <String, Object> response = getJsonResponse(s3Service.listFiles());
        return ResponseEntity.ok(response);
    }

    @PutMapping("/update/{oldFileName}/{newFileName}")
    public ResponseEntity<Map<String, Object>> updateFileName(@PathVariable("oldFileName")String oldFileName,
                                                 @PathVariable("newFileName")String newFileName) throws Exception {
        Map <String, Object> response = getJsonResponse(s3Service.updateFileName(oldFileName, newFileName));
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/delete/{fileName}")
    public ResponseEntity<Map<String, Object>> delete(@PathVariable("fileName")String fileName) throws Exception {
        Map <String, Object> response = getJsonResponse(s3Service.deleteFile(fileName));
        return ResponseEntity.ok(response);
    }

    @ExceptionHandler({FileNotFoundException.class})
    public ResponseEntity<Map<String, Object>> handleNotFoundFile(FileNotFoundException exception){
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(getJsonResponse(exception, HttpStatus.NOT_FOUND.value()));
    }

    @ExceptionHandler({UnnamedFileException.class})
    public ResponseEntity<Map<String, Object>> handleUnnamedFile(UnnamedFileException exception){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(getJsonResponse(exception, HttpStatus.BAD_REQUEST.value()));
    }
}
