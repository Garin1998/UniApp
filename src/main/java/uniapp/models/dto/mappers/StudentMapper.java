package uniapp.models.dto.mappers;

import uniapp.models.entities.Student;
import uniapp.models.dto.StudentDto;

public interface StudentMapper {

    Student dtoToEntity(StudentDto dto);
    StudentDto entityToDto(Student entity);

}
