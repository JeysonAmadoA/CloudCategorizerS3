package Triju.CloudCategorizerS3.Domain.Exceptions;

public class FileNotFoundException extends Exception{

    private static final String MESSAGE  = "No fue posible acceder al archivo";

    public FileNotFoundException() {
        super(MESSAGE);
    }
}
