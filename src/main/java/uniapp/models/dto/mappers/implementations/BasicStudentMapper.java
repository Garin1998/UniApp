package uniapp.models.dto.mappers.implementations;

import org.springframework.stereotype.Component;
import uniapp.models.entities.Student;
import uniapp.models.dto.StudentDto;
import uniapp.models.dto.mappers.StudentMapper;

@Component
public class BasicStudentMapper implements StudentMapper {

    @Override
    public Student dtoToEntity(StudentDto dto) {

        return Student.builder()
                .id(dto.id())
                .firstName(dto.firstName())
                .lastName(dto.lastName())
                .term(dto.term())
                .build();

    }

    @Override
    public StudentDto entityToDto(Student entity) {

        return StudentDto.builder()
                .id(entity.getId())
                .firstName(entity.getFirstName())
                .lastName(entity.getLastName())
                .term(entity.getTerm())
                .build();

    }

}
