package Triju.CloudCategorizerS3.Domain.Exceptions;

public class FolderNotFoundException extends Exception{

    private static final String MESSAGE  = "No se encontró carpeta ingresada";

    public FolderNotFoundException() {
        super(MESSAGE);
    }
}
