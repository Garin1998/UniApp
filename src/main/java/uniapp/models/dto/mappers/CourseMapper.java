package uniapp.models.dto.mappers;

import org.springframework.stereotype.Component;
import uniapp.models.dto.CourseDto;
import uniapp.models.entities.Course;

@Component
public interface CourseMapper {

    Course dtoToEntity(CourseDto dto);
    CourseDto entityToDto(Course entity);

}
