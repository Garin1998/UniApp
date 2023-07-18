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
import uniapp.controllers.requests.LecturerReq;
import uniapp.repositories.LecturerRepository;
import uniapp.services.LecturerService;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static uniapp.constants.ControllerPathConstants.LECTURER_REQ_URL;
import static uniapp.constants.ResponseLecturerMessages.*;
import static uniapp.constants.ResponseLecturerMessages.LECTURER_SUCCESS_DELETE;

@SpringBootTest(webEnvironment = RANDOM_PORT)
class LecturerControllerIntegrationTest {

    @LocalServerPort
    int port;

    @Autowired
    LecturerService lecturerService;

    @Autowired
    LecturerRepository lecturerRepository;

    @BeforeEach
    void setUp() {

        RestAssured.baseURI = "http://localhost:" + port;
        RestAssured.basePath = LECTURER_REQ_URL;
        RestAssured.port = port;

    }

    @AfterEach
    void cleanUpEach() {

        lecturerRepository.deleteAll();

    }

    @Test
    @Sql("/import_lecturer.sql")
    void whenProvidedIdIsValidThenReturnLecturerAndReturn200() {

        String id = "d62a2265-4f9b-41a0-8d3b-e574f57f0d3e";

        given()
                .param("id", id)
        .when()
                .get()
        .then()
                .statusCode(HttpStatus.OK.value())
                .body("id", equalTo(id))
                .body("firstName", equalTo("Jan"))
                .body("lastName", equalTo("Nowak"));

    }

    @Test
    void whenLecturerIsValidThenAddLecturerAndReturn200() {

        LecturerReq request =
                LecturerReq.builder()
                        .firstName("Jan")
                        .lastName("Kowalski")
                        .build();

        given()
                .contentType(ContentType.JSON)
                .body(request)
        .when()
                .post()
        .then()
                .statusCode(HttpStatus.OK.value())
                .body("message", equalTo(LECTURER_SUCCESS_ADD));

    }

    @Test
    @Sql("/import_lecturer.sql")
    void whenProvidedIdAndLecturerBodyIsValidThenEditLecturerAndReturn200() {

        String id = "d62a2265-4f9b-41a0-8d3b-e574f57f0d3e";

        LecturerReq request =
                LecturerReq.builder()
                        .firstName("Jan")
                        .lastName("Kowalski")
                        .build();

        given()
                .param("id", id)
                .contentType("application/json")
                .body(request)
        .when()
                .put()
        .then()
                .statusCode(HttpStatus.OK.value())
                .body("message", equalTo(LECTURER_SUCCESS_EDIT));

    }

    @Test
    @Sql("/import_lecturer.sql")
    void whenProvidedIdIsValidThenDeleteLecturerAndReturn200() {

        String id = "d62a2265-4f9b-41a0-8d3b-e574f57f0d3e";

        given()
                .param("id", id)
        .when()
                .delete()
        .then()
                .statusCode(HttpStatus.OK.value())
                .body("message", equalTo(LECTURER_SUCCESS_DELETE));

    }

}
