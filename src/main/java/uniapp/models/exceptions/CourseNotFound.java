package uniapp.models.exceptions;

public class CourseNotFound extends UserNotFound {

    public CourseNotFound() {
        super("Course not found");
    }

    public CourseNotFound(String message) {
        super(message);
    }

}
