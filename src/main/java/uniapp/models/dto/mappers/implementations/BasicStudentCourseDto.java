package uniapp.models.dto.mappers.implementations;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import uniapp.models.dto.StudentCourseDto;
import uniapp.models.dto.mappers.CourseMapper;
import uniapp.models.dto.mappers.StudentCourseMapper;
import uniapp.models.dto.mappers.StudentMapper;
import uniapp.models.entities.StudentCourse;

@Component
@RequiredArgsConstructor
public class BasicStudentCourseDto implements StudentCourseMapper {

    private final StudentMapper studentMapper;
    private final CourseMapper courseMapper;

    @Override
    public StudentCourse dtoToEntity(StudentCourseDto dto) {

        return StudentCourse.builder()
                .id(dto.id())
                .student(studentMapper.dtoToEntity(dto.studentDto()))
                .course(courseMapper.dtoToEntity(dto.courseDto()))
                .degree(dto.degree())
                .build();

    }

    @Override
    public StudentCourseDto entityToDto(StudentCourse entity) {

        return StudentCourseDto.builder()
                .id(entity.getId())
                .studentDto(studentMapper.entityToDto(entity.getStudent()))
                .courseDto(courseMapper.entityToDto(entity.getCourse()))
                .degree(entity.getDegree())
                .build();

    }

}
