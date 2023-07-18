package uniapp.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uniapp.controllers.requests.StudentReq;
import uniapp.controllers.responses.GenericSuccessRes;
import uniapp.models.dto.StudentDto;
import uniapp.models.dto.mappers.StudentMapper;
import uniapp.models.exceptions.StudentNotFound;
import uniapp.models.exceptions.UserNotFound;
import uniapp.repositories.StudentRepository;

import java.util.UUID;

import static uniapp.constants.ResponseStudentMessages.*;

@Service
@RequiredArgsConstructor
public class StudentService {

    private final StudentRepository studentRepository;
    private final StudentMapper studentMapper;

    public StudentDto getStudent(UUID id) {

        return studentMapper.entityToDto(studentRepository.findById(id).orElseThrow(StudentNotFound::new));

    }

    public GenericSuccessRes addStudent(StudentReq request) {

        StudentDto studentDto = StudentDto.builder()
                .firstName(request.firstName())
                .lastName(request.lastName())
                .term(request.term())
                .build();

        studentRepository.save(studentMapper.dtoToEntity(studentDto));
        return new GenericSuccessRes(STUDENT_SUCCESS_ADD);

    }

    public GenericSuccessRes editStudent(UUID id, StudentReq request) {

        if(studentRepository.findById(id).isEmpty()) {
            throw new UserNotFound();
        }
        else {
            StudentDto studentDto = StudentDto.builder()
                    .id(id)
                    .firstName(request.firstName())
                    .lastName(request.lastName())
                    .term(request.term())
                    .build();

            studentRepository.save(studentMapper.dtoToEntity(studentDto));

        }

        return new GenericSuccessRes(STUDENT_SUCCESS_EDIT);

    }

    public GenericSuccessRes deleteStudent(UUID id) {

        if(studentRepository.findById(id).isEmpty()) {
            throw new StudentNotFound();
        }

        studentRepository.deleteById(id);

        return new GenericSuccessRes(STUDENT_SUCCESS_DELETE);

    }

}
