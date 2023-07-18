package uniapp.models.exceptions;

public class LecturerNotFound extends UserNotFound {

    public LecturerNotFound() {
        super("Lecturer not found");
    }

    public LecturerNotFound(String message) {
        super(message);
    }

}
