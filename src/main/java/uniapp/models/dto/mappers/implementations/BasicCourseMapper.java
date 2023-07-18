package uniapp.models.dto.mappers.implementations;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import uniapp.models.dto.CourseDto;
import uniapp.models.dto.mappers.CourseMapper;
import uniapp.models.dto.mappers.LecturerMapper;
import uniapp.models.entities.Course;

@Component
@RequiredArgsConstructor
public class BasicCourseMapper implements CourseMapper {

    private final LecturerMapper lecturerMapper;

    @Override
    public Course dtoToEntity(CourseDto dto) {
        return Course.builder()
                .id(dto.id())
                .name(dto.name())
                .ects(dto.ects())
                .lecturer(lecturerMapper.dtoToEntity(dto.lecturer()))
                .build();
    }

    @Override
    public CourseDto entityToDto(Course entity) {
        return CourseDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .ects(entity.getEcts())
                .lecturer(lecturerMapper.entityToDto(entity.getLecturer()))
                .build();
    }

}
