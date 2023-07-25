package uniapp.services;

import uniapp.controllers.requests.CourseReq;
import uniapp.controllers.responses.GenericSuccessRes;
import uniapp.models.dto.CourseDto;

import java.util.UUID;

public interface CourseService {

    CourseDto getCourse(UUID id);
    GenericSuccessRes addCourse(CourseReq request);
    GenericSuccessRes editCourse(UUID id, CourseReq request);
    GenericSuccessRes deleteCourse(UUID id);

}
