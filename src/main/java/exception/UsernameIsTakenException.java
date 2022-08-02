package exception;

public class UsernameIsTakenException extends Exception {
    public UsernameIsTakenException() {
    }

    public UsernameIsTakenException(String message) {
        super(message);
    }
}
