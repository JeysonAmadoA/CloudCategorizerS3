# CloudCategorizerS3
Is a cloud-based solution leveraging AWS S3 to store and categorize uploaded files based on their extensions. Seamlessly organize your data in the cloud, making it easy to locate and manage files efficiently. With CloudCategorizerS3, simplify your file management workflow and enhance productivity.

![Example](src/main/resources/static/images/Files%20categorized.png)

To use this project, you need to adjust the application.properties file as specified in the application.properties.example file by adding the access keys and the name of the bucket you will be interacting with.

```properties
aws.accessKeyId=<AWS-ACCESS-KEY>
aws.secretKey=<AWS-SECRET-KEY>
aws.bucketName=<AWS-S3-BUCKET-NAME>
```

For a detailed overview of the functionalities enabled in this REST API, you can review the documentation [here](https://documenter.getpostman.com/view/24997642/2sA3BoZWqe#74e09970-1204-4697-b768-1791769c2ff7).