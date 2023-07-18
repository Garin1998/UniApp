package uniapp.unit;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.parsing.Parser;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import uniapp.controllers.requests.CourseReq;
import uniapp.controllers.responses.GenericSuccessRes;
import uniapp.models.dto.CourseDto;
import uniapp.models.dto.LecturerDto;
import uniapp.services.CourseService;

import java.util.UUID;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static uniapp.constants.ControllerPathConstants.COURSE_REQ_URL;
import static uniapp.constants.ResponseCourseMessages.*;

@SpringBootTest(webEnvironment = RANDOM_PORT)
class CourseControllerUnitTest {

    @LocalServerPort
    int port;

    @MockBean
    CourseService courseService;

    @BeforeEach
    void setUp() {

        RestAssured.baseURI = "http://localhost:" + port;
        RestAssured.basePath = COURSE_REQ_URL;
        RestAssured.port = port;
        RestAssured.defaultParser = Parser.JSON;

    }

    @Test
    void whenProvidedIdIsValidThenReturnCourseAndReturn200() throws JSONException {

        UUID courseId = UUID.randomUUID();
        UUID lecturerId = UUID.randomUUID();

        LecturerDto lecturerDto = LecturerDto.builder()
                .id(lecturerId)
                .build();
        CourseDto courseDto = CourseDto.builder()
                .id(courseId)
                .name("Java Programming")
                .ects(5)
                .lecturer(lecturerDto)
                .build();

        JSONObject lecturerDtoAsExpectedJson = new JSONObject();
        lecturerDtoAsExpectedJson
                .put("id", lecturerDto.id().toString())
                .put("firstName", "Jan")
                .put("lastName", "Nowak");

        JSONObject expectedResponse = new JSONObject();
        expectedResponse
                .put("id", courseDto.id().toString())
                .put("name", courseDto.name())
                .put("ects", courseDto.ects())
                .put("lecturer", lecturerDtoAsExpectedJson);


        when(courseService.getCourse(courseId)).thenReturn(courseDto);

        String response =
        given()
                .param("id", courseId.toString())
        .when()
                .get()
        .then()
                .statusCode(HttpStatus.OK.value())
                .extract().response().asString();

        JSONObject actualResponse = new JSONObject(response);
        JSONAssert.assertEquals(expectedResponse, actualResponse, false);

        verify(courseService).getCourse(courseId);

    }

    @Test
    void whenCourseIsValidThenAddCourseAndReturn200() {

        UUID lecturerId = UUID.randomUUID();

        CourseReq request = CourseReq.builder()
                .name("Java Programming")
                .ects(5)
                .lecturerId(lecturerId)
                .build();

        GenericSuccessRes expectedResponse = new GenericSuccessRes(COURSE_SUCCESS_ADD);

        when(courseService.addCourse(request)).thenReturn(expectedResponse);

        given()
                .contentType(ContentType.JSON)
                .body(request)
        .when()
                .post()
        .then()
                .statusCode(HttpStatus.OK.value())
                .body("message", equalTo(COURSE_SUCCESS_ADD));

        verify(courseService).addCourse(request);

    }

    @Test
    void whenProvidedIdAndLecturerBodyIsValidThenEditLecturerAndReturn200() {

        UUID id = UUID.randomUUID();
        GenericSuccessRes expectedResponse = new GenericSuccessRes(COURSE_SUCCESS_EDIT);

        UUID lecturerId = UUID.randomUUID();

        CourseReq request = CourseReq.builder()
                .name("Java Programming")
                .ects(10)
                .lecturerId(lecturerId)
                .build();

        when(courseService.editCourse(id, request)).thenReturn(expectedResponse);

        given()
                .param("id", id.toString())
                .contentType("application/json")
                .body(request)
                .when()
                .put()
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("message", equalTo(COURSE_SUCCESS_EDIT));

        verify(courseService).editCourse(id, request);

    }

    @Test
    void whenProvidedIdIsValidThenDeleteLecturerAndReturn200() {

        UUID id = UUID.randomUUID();
        GenericSuccessRes successResponse = new GenericSuccessRes(COURSE_SUCCESS_DELETE);

        when(courseService.deleteCourse(id)).thenReturn(successResponse);

        given()
                .param("id", id.toString())
        .when()
                .delete()
        .then()
                .statusCode(HttpStatus.OK.value())
                .body("message", equalTo(COURSE_SUCCESS_DELETE));

        verify(courseService).deleteCourse(id);

    }

}
