package uniapp.integration;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.jdbc.Sql;
import uniapp.controllers.requests.StudentReq;
import uniapp.repositories.StudentRepository;
import uniapp.services.StudentService;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
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

    @BeforeEach
    void setUp() {

        RestAssured.baseURI = "http://localhost:" + port;
        RestAssured.basePath = STUDENT_REQ_URL;
        RestAssured.port = port;

    }

    @AfterEach
    void cleanUpEach() {

        studentRepository.deleteAll();

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

}
