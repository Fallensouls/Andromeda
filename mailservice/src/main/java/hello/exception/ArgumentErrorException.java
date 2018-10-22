package hello.exception;

public class ArgumentErrorException extends Exception {
    private int number;
    private String message;

    public ArgumentErrorException(String message, int number){
        super(message);
        this.number = number;
    }

    public int getNumber(){
        return this.number;
    }
}
