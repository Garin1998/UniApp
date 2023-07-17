package uniapp.unit;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import uniapp.controllers.requests.StudentReq;
import uniapp.controllers.responses.GenericSuccessRes;
import uniapp.models.dto.StudentDto;
import uniapp.services.StudentService;

import java.util.UUID;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static uniapp.constants.ControllerPathConstants.STUDENT_REQ_URL;
import static uniapp.constants.ResponseStudentMessages.*;

@SpringBootTest(webEnvironment = RANDOM_PORT)
class StudentControllerTest {

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

        when(studentService.getStudent(id)).thenReturn(ResponseEntity.ok(studentDto));

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

        when(studentService.addStudent(request)).thenReturn(ResponseEntity.ok(expectedResponse));

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

        when(studentService.editStudent(id, request)).thenReturn(ResponseEntity.ok(expectedResponse));

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

        when(studentService.deleteStudent(id)).thenReturn(ResponseEntity.ok(successResponse));

        given()
                .param("id", id.toString())
                .when()
                .delete()
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("message", equalTo(STUDENT_SUCCESS_DELETE));

        verify(studentService).deleteStudent(id);

    }
}