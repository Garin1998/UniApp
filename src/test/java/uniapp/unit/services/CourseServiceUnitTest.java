package uniapp.unit.services;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import uniapp.controllers.requests.CourseReq;
import uniapp.models.dto.CourseDto;
import uniapp.models.dto.LecturerDto;
import uniapp.models.dto.mappers.CourseMapper;
import uniapp.models.dto.mappers.LecturerMapper;
import uniapp.models.entities.Course;
import uniapp.models.entities.Lecturer;
import uniapp.repositories.CourseRepository;
import uniapp.repositories.LecturerRepository;
import uniapp.services.CourseService;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
class CourseServiceUnitTest {

    @InjectMocks
    CourseService courseService;

    @Mock
    CourseRepository courseRepository;
    @Mock
    LecturerRepository lecturerRepository;
    @Mock
    CourseMapper courseMapper;
    @Mock
    LecturerMapper lecturerMapper;

    @Test
    void getCourse() {

        assertNotNull(courseRepository);
        assertNotNull(courseMapper);

        UUID courseId = UUID.randomUUID();
        UUID lecturerId = UUID.randomUUID();

        Lecturer lecturer = Lecturer.builder()
                .id(lecturerId)
                .firstName("Jan")
                .lastName("Nowak")
                .build();

        Course course = Course.builder()
                .id(courseId)
                .name("Java Programming")
                .lecturer(lecturer)
                .ects(5)
                .build();

        LecturerDto lecturerDto = LecturerDto.builder()
                .id(lecturerId)
                .firstName("Jan")
                .lastName("Nowak")
                .build();

        CourseDto courseDto = CourseDto.builder()
                .id(courseId)
                .name("Java Programming")
                .lecturer(lecturerDto)
                .ects(5)
                .build();

        when(courseMapper.entityToDto(course)).thenReturn(courseDto);
        when(courseRepository.findById(courseId)).thenReturn(Optional.of(course));

        courseService.getCourse(courseId);

        verify(courseMapper).entityToDto(course);
        verify(courseRepository).findById(courseId);

    }

    @Test
    void addCourse() {

        assertNotNull(courseRepository);
        assertNotNull(courseMapper);

        UUID courseId = UUID.randomUUID();
        UUID lecturerId = UUID.randomUUID();

        CourseReq request = CourseReq.builder()
                .name("Java Programming")
                .lecturerId(lecturerId)
                .ects(5)
                .build();

        Lecturer lecturer = Lecturer.builder()
                .id(lecturerId)
                .firstName("Jan")
                .lastName("Nowak")
                .build();

        Course course = Course.builder()
                .id(courseId)
                .name("Java Programming")
                .lecturer(lecturer)
                .ects(5)
                .build();

        LecturerDto lecturerDto = LecturerDto.builder()
                .id(lecturerId)
                .firstName("Jan")
                .lastName("Nowak")
                .build();

        CourseDto courseDto = CourseDto.builder()
                .name("Java Programming")
                .lecturer(lecturerDto)
                .ects(5)
                .build();

        when(lecturerRepository.findById(lecturerId)).thenReturn(Optional.of(lecturer));
        when(courseMapper.dtoToEntity(courseDto)).thenReturn(course);
        when(lecturerMapper.entityToDto(lecturer)).thenReturn(lecturerDto);
        when(courseRepository.save(course)).thenReturn(course);

        courseService.addCourse(request);

        verify(courseMapper).dtoToEntity(courseDto);
        verify(courseRepository).save(course);

    }

    @Test
    void editCourse() {

        assertNotNull(courseRepository);
        assertNotNull(courseMapper);

        UUID courseId = UUID.randomUUID();
        UUID lecturerId = UUID.randomUUID();

        CourseReq request = CourseReq.builder()
                .name("Python Programming")
                .lecturerId(lecturerId)
                .ects(5)
                .build();

        Lecturer lecturer = Lecturer.builder()
                .id(lecturerId)
                .firstName("Jan")
                .lastName("Nowak")
                .build();

        Course course = Course.builder()
                .id(courseId)
                .name("Java Programming")
                .lecturer(lecturer)
                .ects(5)
                .build();

        LecturerDto lecturerDto = LecturerDto.builder()
                .id(lecturerId)
                .firstName(lecturer.getFirstName())
                .lastName(lecturer.getLastName())
                .build();

        CourseDto newCourseDto = CourseDto.builder()
                .id(courseId)
                .name(request.name())
                .lecturer(lecturerDto)
                .ects(request.ects())
                .build();

        Course newCourse = Course.builder()
                .id(courseId)
                .name(newCourseDto.name())
                .lecturer(lecturer)
                .ects(newCourseDto.ects())
                .build();

        when(courseRepository.findById(courseId)).thenReturn(Optional.of(course));
        when(lecturerRepository.findById(lecturerId)).thenReturn(Optional.of(lecturer));
        when(lecturerMapper.entityToDto(lecturer)).thenReturn(lecturerDto);
        when(courseMapper.dtoToEntity(newCourseDto)).thenReturn(newCourse);
        when(courseRepository.save(newCourse)).thenReturn(newCourse);

        courseService.editCourse(courseId, request);

        verify(courseRepository).findById(courseId);
        verify(lecturerRepository).findById(lecturerId);
        verify(lecturerMapper).entityToDto(lecturer);
        verify(courseMapper).dtoToEntity(newCourseDto);
        verify(courseRepository).save(newCourse);

    }

    @Test
    void deleteCourse() {

        assertNotNull(courseRepository);
        assertNotNull(courseMapper);

        UUID courseId = UUID.randomUUID();
        UUID lecturerId = UUID.randomUUID();

        CourseReq request = CourseReq.builder()
                .name("Python Programming")
                .lecturerId(lecturerId)
                .ects(5)
                .build();

        Lecturer lecturer = Lecturer.builder()
                .id(lecturerId)
                .firstName("Jan")
                .lastName("Nowak")
                .build();

        Course course = Course.builder()
                .id(courseId)
                .name("Java Programming")
                .lecturer(lecturer)
                .ects(5)
                .build();

        when(courseRepository.findById(courseId)).thenReturn(Optional.of(course));
        doNothing().when(courseRepository).deleteById(courseId);

        courseService.deleteCourse(courseId);

        verify(courseRepository).findById(courseId);
        verify(courseRepository).deleteById(courseId);

    }

}