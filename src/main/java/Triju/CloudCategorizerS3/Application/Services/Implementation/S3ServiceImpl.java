package Triju.CloudCategorizerS3.Application.Services.Implementation;

import Triju.CloudCategorizerS3.Application.Services.Interfaces.S3Service;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class S3ServiceImpl implements S3Service {

    private final S3Client s3Client;

    @Value("${aws.bucketName}")
    private String bucketName;

    @Value("${localPath}")
    private String localPath;

    public S3ServiceImpl(S3Client s3Client) {
        this.s3Client = s3Client;
    }

    @Override
    public String uploadFile(MultipartFile file) throws IOException {
        try {
            String fileName = file.getOriginalFilename();
            System.out.println(bucketName);
            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(fileName)
                    .build();

            s3Client.putObject(putObjectRequest, RequestBody.fromBytes(file.getBytes()));
            return "Archivo cargado";
        } catch (IOException exception) {
            throw new IOException(exception.getMessage());
        }
    }

    @Override
    public String downloadFile(String fileName) throws IOException {
        if (!doesObjectExist(fileName)){
            return "Archivo no existe";
        }
        GetObjectRequest request = GetObjectRequest.builder()
                .bucket(bucketName)
                .key(fileName)
                .build();

        ResponseInputStream<GetObjectResponse> result = s3Client.getObject(request);
        try(FileOutputStream fileOutput = new FileOutputStream(localPath+fileName)){
            byte[] readBuffer = new byte[1024];
            int readLen;
            while ((readLen = result.read(readBuffer)) > 0){
                fileOutput.write(readBuffer, 0, readLen);
            }
        } catch (IOException exception){
            throw new IOException(exception.getMessage());
        }
        return "Archivo descargado";
    }

    @Override
    public List<String> listFiles() throws IOException {
        try {
            ListObjectsRequest listObjectsRequest = ListObjectsRequest.builder()
                    .bucket(bucketName)
                    .build();

            List<S3Object> objects = s3Client
                    .listObjects(listObjectsRequest)
                    .contents();

            List<String> fileNames = new ArrayList<>();
            for (S3Object object: objects){
                fileNames.add(object.key());
            }
            return fileNames;
        } catch (S3Exception exception){
            throw new IOException(exception.getMessage());
        }
    }

    @Override
    public String deleteFile(String fileName) throws IOException {
        if (!doesObjectExist(fileName)){
            return "Archivo no existe";
        }
        try {
            DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder()
                    .bucket(bucketName)
                    .key(fileName)
                    .build();

            s3Client.deleteObject(deleteObjectRequest);
            return "Archivo eliminado";

        } catch (S3Exception exception){
            throw new IOException(exception.getMessage());
        }

    }

    @Override
    public String updateFileName(String oldFileName, String newFileName) throws IOException {
        if (!doesObjectExist(oldFileName)){
            return "Archivo no existe";
        }
        try {

            CopyObjectRequest copyObjectRequest = CopyObjectRequest.builder()
                    .copySource(bucketName + "/" + oldFileName)
                    .destinationBucket(bucketName)
                    .destinationKey(newFileName)
                    .build();

            s3Client.copyObject(copyObjectRequest);
            deleteFile(oldFileName);
            return "Archivo actualizado";
        } catch (S3Exception exception) {
            throw new IOException(exception.getMessage());
        }
    }

    private boolean doesObjectExist(String objectKey){
        try {
            HeadObjectRequest headObjectRequest = HeadObjectRequest
                    .builder()
                    .bucket(bucketName)
                    .key(objectKey)
                    .build();

            s3Client.headObject(headObjectRequest);
            return true;
        } catch (S3Exception exception){
            if (exception.statusCode() == 404){
                return false;
            }
        }
        return true;
    }
}


