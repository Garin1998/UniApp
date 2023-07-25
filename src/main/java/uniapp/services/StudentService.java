package uniapp.services;

import uniapp.controllers.requests.StudentCourseReq;
import uniapp.controllers.requests.StudentReq;
import uniapp.controllers.responses.GenericSuccessRes;
import uniapp.controllers.responses.StudentCourseRes;
import uniapp.models.dto.StudentDto;

import java.util.Set;
import java.util.UUID;

public interface StudentService {

    StudentDto getStudent(UUID id);
    GenericSuccessRes addStudent(StudentReq request);
    GenericSuccessRes editStudent(UUID id, StudentReq request);
    GenericSuccessRes deleteStudent(UUID id);
    Set<StudentCourseRes> getAllStudentCoursesById(UUID id);
    GenericSuccessRes addStudentCourseByStudentId(StudentCourseReq request);
    GenericSuccessRes editStudentCourseByStudentCourseId(UUID id, StudentCourseReq request);
    GenericSuccessRes deleteStudentCourseByStudentCourseId(UUID studentCourseId);

}
