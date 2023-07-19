package uniapp.unit.controllers;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import uniapp.controllers.requests.StudentCourseReq;
import uniapp.controllers.requests.StudentReq;
import uniapp.controllers.responses.GenericSuccessRes;
import uniapp.controllers.responses.StudentCourseRes;
import uniapp.models.dto.CourseDto;
import uniapp.models.dto.LecturerDto;
import uniapp.models.dto.StudentDto;
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

@SpringBootTest(webEnvironment = RANDOM_PORT)
class StudentControllerUnitTest {

    @LocalServerPort
    int port;

    @MockBean
    StudentService studentService;

    @BeforeEach
    void setUp() {

        RestAssured.baseURI = "http://localhost:" + port;
        RestAssured.basePath = STUDENT_REQ_URL;
        RestAssured.port = port;

    }

    @Test
    void whenProvidedIdIsValidThenReturnStudentAndReturn200() {

        UUID id = UUID.randomUUID();
        StudentDto studentDto =
                StudentDto.builder()
                        .id(id)
                        .firstName("Jan")
                        .lastName("Kowalski")
                        .term(1)
                        .build();

        when(studentService.getStudent(id)).thenReturn(studentDto);

        given()
                .param("id", id.toString())
        .when()
                .get().prettyPeek()
        .then()
                .statusCode(HttpStatus.OK.value())
                .body("id", equalTo(id.toString()))
                .body("firstName", equalTo("Jan"))
                .body("lastName", equalTo("Kowalski"))
                .body("term", equalTo(1));

        verify(studentService).getStudent(id);

    }

    @Test
    void whenStudentIsValidThenAddStudentAndReturn200() {

        StudentReq request =
                StudentReq.builder()
                        .firstName("Jan")
                        .lastName("Kowalski")
                        .term(1)
                        .build();

        GenericSuccessRes expectedResponse = new GenericSuccessRes(STUDENT_SUCCESS_ADD);

        when(studentService.addStudent(request)).thenReturn(expectedResponse);

        given()
                .contentType(ContentType.JSON)
                .body(request)
        .when()
                .post()
        .then()
                .statusCode(HttpStatus.OK.value())
                .body("message", equalTo(STUDENT_SUCCESS_ADD));

        verify(studentService).addStudent(request);

    }

    @Test
    void whenProvidedIdAndStudentBodyIsValidThenEditStudentAndReturn200() {

        UUID id = UUID.randomUUID();
        GenericSuccessRes expectedResponse = new GenericSuccessRes(STUDENT_SUCCESS_EDIT);

        StudentReq request =
                StudentReq.builder()
                        .firstName("Jan")
                        .lastName("Nowak")
                        .term(1)
                        .build();

        when(studentService.editStudent(id, request)).thenReturn(expectedResponse);

        given()
                .param("id", id.toString())
                .contentType("application/json")
                .body(request)
        .when()
                .put()
        .then()
                .statusCode(HttpStatus.OK.value())
                .body("message", equalTo(STUDENT_SUCCESS_EDIT));

        verify(studentService).editStudent(id, request);

    }

    @Test
    void whenProvidedIdIsValidThenDeleteStudentAndReturn200() {

        UUID id = UUID.randomUUID();
        GenericSuccessRes successResponse = new GenericSuccessRes(STUDENT_SUCCESS_DELETE);

        when(studentService.deleteStudent(id)).thenReturn(successResponse);

        given()
                .param("id", id.toString())
        .when()
                .delete()
        .then()
                .statusCode(HttpStatus.OK.value())
                .body("message", equalTo(STUDENT_SUCCESS_DELETE));

        verify(studentService).deleteStudent(id);

    }

    @Test
    void whenProvidedIdIsValidThenReturnAllStudentCoursesAndReturn200() throws JSONException {

        UUID studentId = UUID.randomUUID();
        UUID courseId = UUID.randomUUID();
        UUID lecturerId = UUID.randomUUID();

        LecturerDto lecturerDto = LecturerDto.builder()
                .id(lecturerId)
                .firstName("Jan")
                .lastName("Nowak")
                .build();

        CourseDto firstCourseDto = CourseDto.builder()
                .id(courseId)
                .name("Java Programming")
                .ects(5)
                .lecturer(lecturerDto)
                .build();

        CourseDto secondCourseDto = CourseDto.builder()
                .id(courseId)
                .name("Python Programming")
                .ects(5)
                .lecturer(lecturerDto)
                .build();

        Set<StudentCourseRes> expectedResponse = new HashSet<>();

        StudentCourseRes firstRes = StudentCourseRes.builder()
                .id(UUID.randomUUID())
                .courseDto(firstCourseDto)
                .degree(5.0)
                .build();

        StudentCourseRes secondRes = StudentCourseRes.builder()
                .id(UUID.randomUUID())
                .courseDto(secondCourseDto)
                .degree(4.0)
                .build();

        expectedResponse.add(firstRes);
        expectedResponse.add(secondRes);

        when(studentService.getAllStudentCoursesById(studentId)).thenReturn(expectedResponse);

        String response =
        given()
                .param("id", studentId.toString())
        .when()
                .get("/courses")
        .then()
                .statusCode(HttpStatus.OK.value())
                .extract().response().asString();

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

        verify(studentService).getAllStudentCoursesById(studentId);

    }

    @Test
    void whenStudentCourseIsValidThenAddStudentCourseAndReturn200() {

        UUID studentId = UUID.randomUUID();
        UUID courseId = UUID.randomUUID();

        StudentCourseReq request = StudentCourseReq.builder()
                .studentId(studentId)
                .courseId(courseId)
                .degree(5.0)
                .build();

        when(studentService.addStudentCourseByStudentId(request))
                .thenReturn(new GenericSuccessRes(STUDENT_COURSE_SUCCESS_ADD));

        given()
                .contentType("application/json")
                .body(request)
        .when()
                .post("/courses")
        .then()
                .statusCode(HttpStatus.OK.value())
                .body("message", equalTo(STUDENT_COURSE_SUCCESS_ADD));

        verify(studentService).addStudentCourseByStudentId(request);

    }

    @Test
    void whenProvidedIdAndStudentBodyIsValidThenEditStudentCourseAndReturn200() {

        UUID studentId = UUID.randomUUID();
        UUID courseId = UUID.randomUUID();
        UUID studentCourseId = UUID.randomUUID();

        StudentCourseReq request = StudentCourseReq.builder()
                .studentId(studentId)
                .courseId(courseId)
                .degree(2.0)
                .build();

        when(studentService.editStudentCourseByStudentCourseId(studentCourseId, request))
                .thenReturn(new GenericSuccessRes(STUDENT_COURSE_SUCCESS_EDIT));

        given()
                .param("id", studentCourseId)
                .contentType("application/json")
                .body(request)
        .when()
                .put("/courses")
        .then()
                .statusCode(HttpStatus.OK.value())
                .body("message", equalTo(STUDENT_COURSE_SUCCESS_EDIT));

        verify(studentService).editStudentCourseByStudentCourseId(studentCourseId, request);

    }

    @Test
    void whenProvidedStudentCourseIdIsValidThenDeleteStudentCourseAndReturn200() {

        UUID studentCourseId = UUID.randomUUID();

        GenericSuccessRes successResponse = new GenericSuccessRes(STUDENT_COURSE_SUCCESS_DELETE);

        when(studentService.deleteStudentCourseByStudentCourseId(studentCourseId)).thenReturn(successResponse);

        given()
                .param("id", studentCourseId.toString())
        .when()
                .delete("/courses")
        .then()
                .statusCode(HttpStatus.OK.value())
                .body("message", equalTo(STUDENT_COURSE_SUCCESS_DELETE));

        verify(studentService).deleteStudentCourseByStudentCourseId(studentCourseId);

    }

}