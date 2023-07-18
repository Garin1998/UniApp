package uniapp.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uniapp.controllers.requests.StudentReq;
import uniapp.controllers.responses.GenericSuccessRes;
import uniapp.models.dto.StudentDto;
import uniapp.services.StudentService;

import java.util.UUID;

import static uniapp.constants.ControllerPathConstants.STUDENT_REQ_URL;

@RestController
@RequestMapping(STUDENT_REQ_URL)
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;

    @GetMapping(produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<StudentDto> getStudentById(@RequestParam(name = "id") UUID uuid) {

        return ResponseEntity.ok(studentService.getStudent(uuid));

    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<GenericSuccessRes> addStudent(@RequestBody StudentReq request) {

        return ResponseEntity.ok(studentService.addStudent(request));

    }

    @PutMapping(consumes = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<GenericSuccessRes> editStudent(
            @RequestParam(name = "id") UUID uuid,
            @RequestBody StudentReq request
    ) {

        return ResponseEntity.ok(studentService.editStudent(uuid, request));

    }

    @DeleteMapping(produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<GenericSuccessRes> deleteStudent(@RequestParam(name = "id") UUID uuid) {

        return ResponseEntity.ok(studentService.deleteStudent(uuid));

    }
}
