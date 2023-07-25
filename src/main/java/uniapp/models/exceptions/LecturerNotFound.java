package uniapp.models.exceptions;

public class LecturerNotFound extends CustomRuntimeException {

    public LecturerNotFound() {

        super("Lecturer not found");

    }

    public LecturerNotFound(String message) {

        super(message);

    }

}
