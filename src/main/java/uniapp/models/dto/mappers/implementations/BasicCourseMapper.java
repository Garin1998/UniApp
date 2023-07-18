package uniapp.models.dto.mappers.implementations;

import org.springframework.stereotype.Component;
import uniapp.models.dto.CourseDto;
import uniapp.models.dto.mappers.CourseMapper;
import uniapp.models.entities.Course;

@Component
public class BasicCourseMapper implements CourseMapper {

    @Override
    public Course dtoToEntity(CourseDto dto) {
        return null;
    }

    @Override
    public CourseDto entityToDto(Course entity) {
        return null;
    }

}
