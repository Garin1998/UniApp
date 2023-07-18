package uniapp.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uniapp.controllers.requests.CourseReq;
import uniapp.controllers.responses.GenericSuccessRes;
import uniapp.models.dto.CourseDto;
import uniapp.models.dto.mappers.CourseMapper;
import uniapp.models.dto.mappers.LecturerMapper;
import uniapp.models.entities.Lecturer;
import uniapp.models.exceptions.CourseNotFound;
import uniapp.models.exceptions.LecturerNotFound;
import uniapp.repositories.CourseRepository;
import uniapp.repositories.LecturerRepository;

import java.util.UUID;

import static uniapp.constants.ResponseCourseMessages.*;

@Service
@RequiredArgsConstructor
public class CourseService {

    private final CourseRepository courseRepository;
    private final LecturerRepository lecturerRepository;
    private final CourseMapper courseMapper;
    private final LecturerMapper lecturerMapper;

    public CourseDto getCourse(UUID id) {

        return courseMapper.entityToDto(courseRepository.findById(id).orElseThrow(CourseNotFound::new));

    }

    public GenericSuccessRes addCourse(CourseReq request) {

        Lecturer lecturer = lecturerRepository.findById(request.lecturerId()).orElseThrow(LecturerNotFound::new);

        CourseDto courseDto = CourseDto.builder()
                .name(request.name())
                .ects(request.ects())
                .lecturer(lecturerMapper.entityToDto(lecturer))
                .build();

        courseRepository.save(courseMapper.dtoToEntity(courseDto));

        return new GenericSuccessRes(COURSE_SUCCESS_ADD);

    }

    public GenericSuccessRes editCourse(UUID id, CourseReq request) {

        if(courseRepository.findById(id).isEmpty()) {
            throw new CourseNotFound();
        }
        else {

            Lecturer lecturer = lecturerRepository.findById(request.lecturerId()).orElseThrow(LecturerNotFound::new);

            CourseDto courseDto = CourseDto.builder()
                    .name(request.name())
                    .ects(request.ects())
                    .lecturer(lecturerMapper.entityToDto(lecturer))
                    .build();

            courseRepository.save(courseMapper.dtoToEntity(courseDto));
        }

        return new GenericSuccessRes(COURSE_SUCCESS_EDIT);

    }

    public GenericSuccessRes deleteCourse(UUID id) {

        if(courseRepository.findById(id).isEmpty()) {
            throw new CourseNotFound();
        }
        else {
            courseRepository.deleteById(id);
        }

        return new GenericSuccessRes(COURSE_SUCCESS_DELETE);

    }

}
