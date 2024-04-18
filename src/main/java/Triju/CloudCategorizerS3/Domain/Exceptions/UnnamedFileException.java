package Triju.CloudCategorizerS3.Domain.Exceptions;

public class UnnamedFileException extends Exception {

    private static final String MESSAGE  = "El archivo no cuenta con nombre";

    public UnnamedFileException() {
        super(MESSAGE);
    }
}
