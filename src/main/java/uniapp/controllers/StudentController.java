package uniapp.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uniapp.controllers.requests.StudentCourseReq;
import uniapp.controllers.requests.StudentReq;
import uniapp.controllers.responses.GenericSuccessRes;
import uniapp.controllers.responses.StudentCourseRes;
import uniapp.models.dto.StudentDto;
import uniapp.services.StudentService;

import java.util.Set;
import java.util.UUID;

import static uniapp.constants.ControllerPathConstants.STUDENT_REQ_URL;

@RestController
@RequestMapping(STUDENT_REQ_URL)
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;

    @GetMapping(produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<StudentDto> getStudentById(@RequestParam(name = "id") UUID id) {

        return ResponseEntity.ok(studentService.getStudent(id));

    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<GenericSuccessRes> addStudent(@RequestBody StudentReq request) {

        return ResponseEntity.ok(studentService.addStudent(request));

    }

    @PutMapping(consumes = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<GenericSuccessRes> editStudent(
            @RequestParam(name = "id") UUID id,
            @RequestBody StudentReq request
    ) {

        return ResponseEntity.ok(studentService.editStudent(id, request));

    }

    @DeleteMapping(produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<GenericSuccessRes> deleteStudent(@RequestParam(name = "id") UUID id) {

        return ResponseEntity.ok(studentService.deleteStudent(id));

    }

    @GetMapping(value = "/courses", produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Set<StudentCourseRes>> getAllStudentCourse(@RequestParam(name = "id") UUID id) {

        return ResponseEntity.ok(studentService.getAllStudentCoursesById(id));

    }

    @PostMapping(value = "/courses", consumes = "application/json", produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<GenericSuccessRes> addStudentCourseByStudentId(
            @RequestBody StudentCourseReq studentCourseReq
    ) {

        return ResponseEntity.ok(studentService.addStudentCourseByStudentId(studentCourseReq));

    }

    @PutMapping(value = "/courses", consumes = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<GenericSuccessRes> editStudentCourseByStudentCourseId(
            @RequestParam(name = "id") UUID id,
            @RequestBody StudentCourseReq studentCourseReq
    ) {

        return ResponseEntity.ok(studentService.editStudentCourseByStudentCourseId(id, studentCourseReq));

    }

    @DeleteMapping(value = "/courses", produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<GenericSuccessRes> deleteStudentCourseByStudentCourseId(@RequestParam(name = "id") UUID id) {

        return ResponseEntity.ok(studentService.deleteStudentCourseByStudentCourseId(id));

    }


}
