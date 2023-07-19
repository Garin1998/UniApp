package uniapp.models.dto.mappers;

import uniapp.models.dto.StudentCourseDto;
import uniapp.models.entities.StudentCourse;

public interface StudentCourseMapper {

    StudentCourse dtoToEntity(StudentCourseDto dto);
    StudentCourseDto entityToDto(StudentCourse entity);

}
