package uniapp.integration;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
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
import uniapp.controllers.requests.CourseReq;
import uniapp.repositories.CourseRepository;
import uniapp.repositories.LecturerRepository;
import uniapp.services.CourseService;

import java.util.UUID;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static uniapp.constants.ControllerPathConstants.COURSE_REQ_URL;
import static uniapp.constants.ResponseCourseMessages.*;

@SpringBootTest(webEnvironment = RANDOM_PORT)
class CourseControllerIntegrationTest {

    @LocalServerPort
    int port;

    @Autowired
    CourseService courseService;

    @Autowired
    CourseRepository courseRepository;
    @Autowired
    private LecturerRepository lecturerRepository;

    @BeforeEach
    void setUp() {

        RestAssured.baseURI = "http://localhost:" + port;
        RestAssured.basePath = COURSE_REQ_URL;
        RestAssured.port = port;

    }

    @AfterEach
    void cleanUpEach() {

        courseRepository.deleteAll();
        lecturerRepository.deleteAll();

    }

    @Test
    @Sql({"/import_lecturer.sql", "/import_course.sql"})
    void whenProvidedIdIsValidThenReturnCourseAndReturn200() throws JSONException {

        String lecturerId = "d62a2265-4f9b-41a0-8d3b-e574f57f0d3e";
        String courseId = "e98b9f76-62ff-42ab-b370-ded7e0a0b85f";

        JSONObject lecturerDtoAsExpectedJson = new JSONObject();
        lecturerDtoAsExpectedJson
                .put("id", lecturerId)
                .put("firstName", "Jan")
                .put("lastName", "Nowak");

        JSONObject expectedResponse = new JSONObject();
        expectedResponse
                .put("id", courseId)
                .put("name", "Java Programming")
                .put("ects", 5)
                .put("lecturer", lecturerDtoAsExpectedJson);

        String response =
        given()
                .param("id", courseId)
        .when()
                .get()
        .then()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .response()
                .asString();

        JSONObject actualResponse = new JSONObject(response);
        JSONAssert.assertEquals(expectedResponse, actualResponse, false);
    }

    @Test
    @Sql("/import_lecturer.sql")
    void whenCourseIsValidThenAddCourseAndReturn200() {

        String lecturerId = "d62a2265-4f9b-41a0-8d3b-e574f57f0d3e";

        CourseReq request = CourseReq.builder()
                .name("Java Programming")
                .ects(5)
                .lecturerId(UUID.fromString(lecturerId))
                .build();

        given()
                .contentType(ContentType.JSON)
                .body(request)
        .when()
                .post()
        .then()
                .statusCode(HttpStatus.OK.value())
                .body("message", equalTo(COURSE_SUCCESS_ADD));

    }

    @Test
    @Sql({"/import_lecturer.sql", "/import_course.sql"})
    void whenProvidedIdAndLecturerBodyIsValidThenEditLecturerAndReturn200() {

        String courseId = "e98b9f76-62ff-42ab-b370-ded7e0a0b85f";
        String lecturerId = "d62a2265-4f9b-41a0-8d3b-e574f57f0d3e";

        CourseReq request = CourseReq.builder()
                .name("Java Programming")
                .ects(10)
                .lecturerId(UUID.fromString(lecturerId))
                .build();

        given()
                .param("id", courseId)
                .contentType("application/json")
                .body(request)
        .when()
                .put()
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("message", equalTo(COURSE_SUCCESS_EDIT));

    }

    @Test
    @Sql({"/import_lecturer.sql", "/import_course.sql"})
    void whenProvidedIdIsValidThenDeleteLecturerAndReturn200() {

        String courseId = "e98b9f76-62ff-42ab-b370-ded7e0a0b85f";

        given()
                .param("id", courseId)
        .when()
                .delete()
        .then()
                .statusCode(HttpStatus.OK.value())
                .body("message", equalTo(COURSE_SUCCESS_DELETE));

    }

}
