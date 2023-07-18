package uniapp.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uniapp.controllers.requests.CourseReq;
import uniapp.controllers.requests.LecturerReq;
import uniapp.controllers.responses.GenericSuccessRes;
import uniapp.models.dto.CourseDto;
import uniapp.repositories.CourseRepository;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CourseService {

    private final CourseRepository courseRepository;

    public CourseDto getCourse(UUID id) {

        return null;

    }

    public GenericSuccessRes addCourse(CourseReq request) {

        return null;

    }

    public GenericSuccessRes editCourse(UUID id, CourseReq request) {

        return null;

    }

    public GenericSuccessRes deleteCourse(UUID id) {

        return null;

    }

}
