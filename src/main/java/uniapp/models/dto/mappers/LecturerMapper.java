package uniapp.models.dto.mappers;

import uniapp.models.Student;
import uniapp.models.dto.StudentDto;

public interface LecturerMapper {
    Student dtoToEntity(StudentDto dto);
    StudentDto entityToDto(Student entity);
}
