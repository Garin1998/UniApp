package uniapp.integration;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.jdbc.Sql;
import uniapp.controllers.requests.StudentCourseReq;
import uniapp.controllers.requests.StudentReq;
import uniapp.controllers.responses.GenericSuccessRes;
import uniapp.controllers.responses.StudentCourseRes;
import uniapp.models.dto.CourseDto;
import uniapp.models.dto.LecturerDto;
import uniapp.repositories.CourseRepository;
import uniapp.repositories.LecturerRepository;
import uniapp.repositories.StudentRepository;
import uniapp.services.StudentService;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static uniapp.constants.ControllerPathConstants.STUDENT_REQ_URL;
import static uniapp.constants.ResponseStudentMessages.*;
import static uniapp.constants.ResponseStudentMessages.STUDENT_SUCCESS_DELETE;

@SpringBootTest(webEnvironment = RANDOM_PORT)
class StudentControllerIntegrationTest {

    @LocalServerPort
    int port;

    @Autowired
    StudentService studentService;

    @Autowired
    StudentRepository studentRepository;

    @Autowired
    LecturerRepository lecturerRepository;

    @Autowired
    CourseRepository courseRepository;

    @BeforeEach
    void setUp() {

        RestAssured.baseURI = "http://localhost:" + port;
        RestAssured.basePath = STUDENT_REQ_URL;
        RestAssured.port = port;

    }

    @AfterEach
    void cleanUpEach() {

        studentRepository.deleteAll();
        courseRepository.deleteAll();
        lecturerRepository.deleteAll();

    }

    @Test
    @Sql({"/import_student.sql"})
    void whenProvidedIdIsValidThenReturnStudentAndReturn200() {

        String id = "a499037f-22b4-4d31-9fd1-e549e7c9ccb4";

        given()
                .param("id", id)
        .when()
                .get()
        .then()
                .statusCode(HttpStatus.OK.value())
                .body("id", equalTo(id))
                .body("firstName", equalTo("Jan"))
                .body("lastName", equalTo("Kowalski"))
                .body("term", equalTo(1));

    }

    @Test
    void whenStudentIsValidThenAddStudentAndReturn200() {

        StudentReq request =
                StudentReq.builder()
                        .firstName("Jan")
                        .lastName("Kowalski")
                        .term(1)
                        .build();

        given()
                .contentType(ContentType.JSON)
                .body(request)
        .when()
                .post()
        .then()
                .statusCode(HttpStatus.OK.value())
                .body("message", equalTo(STUDENT_SUCCESS_ADD));

    }

    @Test
    @Sql({"/import_student.sql"})
    void whenProvidedIdAndStudentBodyIsValidThenEditStudentAndReturn200() {

        String id = "a499037f-22b4-4d31-9fd1-e549e7c9ccb4";

        StudentReq request =
                StudentReq.builder()
                        .firstName("Jan")
                        .lastName("Nowak")
                        .term(1)
                        .build();

        given()
                .param("id", id)
                .contentType("application/json")
                .body(request)
        .when()
                .put()
        .then()
                .statusCode(HttpStatus.OK.value())
                .body("message", equalTo(STUDENT_SUCCESS_EDIT));

    }

    @Test
    @Sql({"/import_student.sql"})
    void whenProvidedIdIsValidThenDeleteStudentAndReturn200() {

        String id = "a499037f-22b4-4d31-9fd1-e549e7c9ccb4";

        given()
                .param("id", id)
        .when()
                .delete()
        .then()
                .statusCode(HttpStatus.OK.value())
                .body("message", equalTo(STUDENT_SUCCESS_DELETE));

    }

    @Test
    @Sql({"/import_student.sql", "/import_lecturer.sql", "/import_course.sql", "/import_student_course.sql"})
    void whenProvidedStudentIdIsValidThenReturnAllStudentCoursesAndReturn200() throws JSONException {

        String studentId = "a499037f-22b4-4d31-9fd1-e549e7c9ccb4";
        String firstCourseId = "e98b9f76-62ff-42ab-b370-ded7e0a0b85f";
        String secondCourseId = "57c3cb42-e211-41dd-9650-603556b1bf4c";
        String lecturerId = "d62a2265-4f9b-41a0-8d3b-e574f57f0d3e";
        String firstStudentCourseId = "f3a2e8ba-fd9d-4808-b68b-9c0ede8f88f7";
        String secondStudentCourseId = "47a9cac0-6265-4f50-8e83-e6ecd73ae9e2";

        LecturerDto lecturerDto = LecturerDto.builder()
                .id(UUID.fromString(lecturerId))
                .firstName("Jan")
                .lastName("Nowak")
                .build();

        CourseDto firstCourseDto = CourseDto.builder()
                .id(UUID.fromString(firstCourseId))
                .name("Java Programming")
                .ects(5)
                .lecturer(lecturerDto)
                .build();

        CourseDto secondCourseDto = CourseDto.builder()
                .id(UUID.fromString(secondCourseId))
                .name("Python Programming")
                .ects(5)
                .lecturer(lecturerDto)
                .build();

        Set<StudentCourseRes> expectedResponse = new HashSet<>();

        StudentCourseRes firstRes = StudentCourseRes.builder()
                .id(UUID.fromString(firstStudentCourseId))
                .courseDto(firstCourseDto)
                .degree(5.0)
                .build();

        StudentCourseRes secondRes = StudentCourseRes.builder()
                .id(UUID.fromString(secondStudentCourseId))
                .courseDto(secondCourseDto)
                .degree(2.0)
                .build();

        expectedResponse.add(firstRes);
        expectedResponse.add(secondRes);

        String response =
        given()
                .param("id", studentId)
        .when()
                .get("/courses")
        .then()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .asString();

        JSONArray actualResponse = new JSONArray(response);
        JSONArray expectedResponseAsJsonArray = new JSONArray();

        for (StudentCourseRes studentCourse : expectedResponse) {

            JSONObject lecturerDtoAsJson = new JSONObject();
            lecturerDtoAsJson.put("id", studentCourse.courseDto().lecturer().id().toString());
            lecturerDtoAsJson.put("firstName", studentCourse.courseDto().lecturer().firstName());
            lecturerDtoAsJson.put("lastName", studentCourse.courseDto().lecturer().lastName());

            JSONObject courseDtoAsJson = new JSONObject();
            courseDtoAsJson.put("id", studentCourse.courseDto().id().toString());
            courseDtoAsJson.put("name", studentCourse.courseDto().name());
            courseDtoAsJson.put("ects", studentCourse.courseDto().ects());
            courseDtoAsJson.put("lecturer", lecturerDtoAsJson);

            expectedResponseAsJsonArray.put(new JSONObject()
                    .put("id", studentCourse.id().toString())
                    .put("course", courseDtoAsJson)
                    .put("degree", studentCourse.degree()));
        }

        JSONAssert.assertEquals(expectedResponseAsJsonArray, actualResponse, false);
    }

    @Test
    @Sql({"/import_student.sql", "/import_lecturer.sql", "/import_course.sql"})
    void whenStudentCourseIsValidThenAddStudentCourseAndReturn200() {

        String studentId = "a499037f-22b4-4d31-9fd1-e549e7c9ccb4";
        String courseId = "e98b9f76-62ff-42ab-b370-ded7e0a0b85f";

        StudentCourseReq request = StudentCourseReq.builder()
                .studentId(UUID.fromString(studentId))
                .courseId(UUID.fromString(courseId))
                .degree(5.0)
                .build();

        given()
                .contentType("application/json")
                .body(request)
        .when()
                .post("/courses")
        .then()
                .statusCode(HttpStatus.OK.value())
                .contentType(ContentType.JSON)
                .body("message", equalTo(STUDENT_COURSE_SUCCESS_ADD));

    }

    @Test
    @Sql({"/import_student.sql", "/import_lecturer.sql", "/import_course.sql", "/import_student_course.sql"})
    void whenProvidedIdAndStudentCourseBodyIsValidThenEditStudentCourseAndReturn200() {

        String studentCourseId = "f3a2e8ba-fd9d-4808-b68b-9c0ede8f88f7";
        String studentId = "a499037f-22b4-4d31-9fd1-e549e7c9ccb4";
        String courseId = "e98b9f76-62ff-42ab-b370-ded7e0a0b85f";

        StudentCourseReq request = StudentCourseReq.builder()
                .studentId(UUID.fromString(studentId))
                .courseId(UUID.fromString(courseId))
                .degree(2.0)
                .build();

        given()
                .param("id", studentCourseId)
                .contentType("application/json")
                .body(request)
        .when()
                .put("/courses")
        .then()
                .statusCode(HttpStatus.OK.value())
                .contentType(ContentType.JSON)
                .body("message", equalTo(STUDENT_COURSE_SUCCESS_EDIT));

    }

    @Test
    @Sql({"/import_student.sql", "/import_lecturer.sql", "/import_course.sql", "/import_student_course.sql"})
    void whenProvidedIdIsValidThenDeleteStudentCourseAndReturn200() {

        String studentCourseId = "f3a2e8ba-fd9d-4808-b68b-9c0ede8f88f7";

        given()
                .param("id", studentCourseId)
        .when()
                .delete("/courses")
        .then()
                .statusCode(HttpStatus.OK.value())
                .body("message", equalTo(STUDENT_COURSE_SUCCESS_DELETE));

    }

}