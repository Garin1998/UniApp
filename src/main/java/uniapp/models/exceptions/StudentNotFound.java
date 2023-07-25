package uniapp.models.exceptions;

public class StudentNotFound extends CustomRuntimeException {
    public StudentNotFound() {

        super("Student not found");

    }

    public StudentNotFound(String message) {

        super(message);

    }

}
