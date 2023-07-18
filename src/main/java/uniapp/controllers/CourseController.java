package uniapp.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uniapp.controllers.requests.CourseReq;
import uniapp.controllers.responses.GenericSuccessRes;
import uniapp.models.dto.CourseDto;
import uniapp.services.CourseService;

import java.util.UUID;

import static uniapp.constants.ControllerPathConstants.COURSE_REQ_URL;

@RestController
@RequestMapping(COURSE_REQ_URL)
@RequiredArgsConstructor
public class CourseController {

    private final CourseService courseService;

    @GetMapping(produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<CourseDto> getCourseById(@RequestParam(name = "id") UUID id) {

        return ResponseEntity.ok(courseService.getCourse(id));

    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<GenericSuccessRes> addCourse(@RequestBody CourseReq request) {

        return ResponseEntity.ok(courseService.addCourse(request));

    }

    @PutMapping(consumes = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<GenericSuccessRes> editCourser(
            @RequestParam(name = "id") UUID id,
            @RequestBody CourseReq request
    ) {

        return ResponseEntity.ok(courseService.editCourse(id, request));

    }

    @DeleteMapping(produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<GenericSuccessRes> deleteCourse(@RequestParam(name = "id") UUID id) {

        return ResponseEntity.ok(courseService.deleteCourse(id));

    }

}
