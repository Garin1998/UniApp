package uniapp.services;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uniapp.controllers.requests.StudentReq;
import uniapp.controllers.responses.GenericSuccessRes;
import uniapp.models.dto.StudentDto;
import uniapp.repositories.StudentRepository;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class StudentService {

    private final StudentRepository studentRepository;

    public ResponseEntity<StudentDto> getStudent(UUID id) {
        return null;
    }

    public ResponseEntity<GenericSuccessRes> addStudent(StudentReq request) {
        return null;
    }

    public ResponseEntity<GenericSuccessRes> editStudent(UUID id, StudentReq request) {
        return null;
    }

    public ResponseEntity<GenericSuccessRes> deleteStudent(UUID id) {
        return null;
    }
}
