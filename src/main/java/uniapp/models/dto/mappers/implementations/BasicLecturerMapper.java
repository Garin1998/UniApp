package uniapp.models.dto.mappers.implementations;

import uniapp.models.Student;
import uniapp.models.dto.StudentDto;
import uniapp.models.dto.mappers.LecturerMapper;

public class BasicLecturerMapper implements LecturerMapper {

    @Override
    public Student dtoToEntity(StudentDto dto) {
        return null;
    }

    @Override
    public StudentDto entityToDto(Student entity) {
        return null;
    }
}
