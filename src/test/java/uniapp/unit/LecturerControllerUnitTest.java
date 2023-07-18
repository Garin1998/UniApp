package uniapp.unit;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import uniapp.controllers.requests.LecturerReq;
import uniapp.controllers.responses.GenericSuccessRes;
import uniapp.models.dto.LecturerDto;
import uniapp.services.LecturerService;

import java.util.UUID;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static uniapp.constants.ControllerPathConstants.LECTURER_REQ_URL;
import static uniapp.constants.ResponseLecturerMessages.*;

@SpringBootTest(webEnvironment = RANDOM_PORT)
class LecturerControllerUnitTest {

    @LocalServerPort
    int port;

    @MockBean
    LecturerService lecturerService;

    @BeforeEach
    void setUp() {

        RestAssured.baseURI = "http://localhost:" + port;
        RestAssured.basePath = LECTURER_REQ_URL;
        RestAssured.port = port;

    }

    @Test
    void whenProvidedIdIsValidThenReturnLecturerAndReturn200() {

        UUID id = UUID.randomUUID();
        LecturerDto lecturerDto = LecturerDto.builder()
                .id(id)
                .firstName("Jan")
                .lastName("Kowalski")
                .build();

        when(lecturerService.getLecturer(id)).thenReturn(lecturerDto);

        given()
                .param("id", id.toString())
        .when()
                .get().prettyPeek()
        .then()
                .statusCode(HttpStatus.OK.value())
                .body("id", equalTo(id.toString()))
                .body("firstName", equalTo("Jan"))
                .body("lastName", equalTo("Kowalski"));

        verify(lecturerService).getLecturer(id);

    }

    @Test
    void whenLecturerIsValidThenAddLecturerAndReturn200() {

        LecturerReq request =
                LecturerReq.builder()
                        .firstName("Jan")
                        .lastName("Kowalski")
                        .build();

        GenericSuccessRes expectedResponse = new GenericSuccessRes(LECTURER_SUCCESS_ADD);

        when(lecturerService.addLecturer(request)).thenReturn(expectedResponse);

        given()
                .contentType(ContentType.JSON)
                .body(request)
        .when()
                .post()
        .then()
                .statusCode(HttpStatus.OK.value())
                .body("message", equalTo(LECTURER_SUCCESS_ADD));

        verify(lecturerService).addLecturer(request);

    }

    @Test
    void whenProvidedIdAndLecturerBodyIsValidThenEditLecturerAndReturn200() {

        UUID id = UUID.randomUUID();
        GenericSuccessRes expectedResponse = new GenericSuccessRes(LECTURER_SUCCESS_EDIT);

        LecturerReq request =
                LecturerReq.builder()
                        .firstName("Jan")
                        .lastName("Nowak")
                        .build();

        when(lecturerService.editLecturer(id, request)).thenReturn(expectedResponse);

        given()
                .param("id", id.toString())
                .contentType("application/json")
                .body(request)
        .when()
                .put()
        .then()
                .statusCode(HttpStatus.OK.value())
                .body("message", equalTo(LECTURER_SUCCESS_EDIT));

        verify(lecturerService).editLecturer(id, request);

    }

    @Test
    void whenProvidedIdIsValidThenDeleteLecturerAndReturn200() {

        UUID id = UUID.randomUUID();
        GenericSuccessRes successResponse = new GenericSuccessRes(LECTURER_SUCCESS_DELETE);

        when(lecturerService.deleteLecturer(id)).thenReturn(successResponse);

        given()
                .param("id", id.toString())
        .when()
                .delete()
        .then()
                .statusCode(HttpStatus.OK.value())
                .body("message", equalTo(LECTURER_SUCCESS_DELETE));

        verify(lecturerService).deleteLecturer(id);

    }
}