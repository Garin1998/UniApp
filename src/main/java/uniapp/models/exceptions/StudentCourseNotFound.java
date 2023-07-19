package uniapp.models.exceptions;

public class StudentCourseNotFound extends UserNotFound {

    public StudentCourseNotFound() {
        super("User or Course not found");
    }

    public StudentCourseNotFound(String message) {
        super(message);
    }
}
