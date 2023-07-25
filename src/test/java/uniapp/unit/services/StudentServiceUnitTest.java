package uniapp.unit.services;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import uniapp.controllers.requests.StudentCourseReq;
import uniapp.controllers.requests.StudentReq;
import uniapp.controllers.responses.GenericSuccessRes;
import uniapp.controllers.responses.StudentCourseRes;
import uniapp.models.dto.CourseDto;
import uniapp.models.dto.LecturerDto;
import uniapp.models.dto.StudentCourseDto;
import uniapp.models.dto.StudentDto;
import uniapp.models.dto.mappers.CourseMapper;
import uniapp.models.dto.mappers.StudentCourseMapper;
import uniapp.models.dto.mappers.StudentMapper;
import uniapp.models.entities.Course;
import uniapp.models.entities.Lecturer;
import uniapp.models.entities.Student;
import uniapp.models.entities.StudentCourse;
import uniapp.repositories.CourseRepository;
import uniapp.repositories.StudentCourseRepository;
import uniapp.repositories.StudentRepository;
import uniapp.services.implementations.BasicStudentService;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static uniapp.constants.ResponseStudentMessages.*;

@SpringBootTest(webEnvironment = RANDOM_PORT)
class StudentServiceUnitTest {

    @InjectMocks
    BasicStudentService studentService;
    @Mock
    StudentRepository studentRepository;
    @Mock
    CourseRepository courseRepository;
    @Mock
    StudentMapper studentMapper;
    @Mock
    CourseMapper courseMapper;
    @Mock
    StudentCourseMapper studentCourseMapper;
    @Mock
    StudentCourseRepository studentCourseRepository;

    @Test
    void getStudent() {

        UUID studentId = UUID.randomUUID();

        Student student = Student.builder()
                .id(studentId)
                .firstName("Jan")
                .lastName("Nowak")
                .term(1)
                .build();

        StudentDto studentDto = StudentDto.builder()
                .id(studentId)
                .firstName(student.getFirstName())
                .lastName(student.getLastName())
                .term(student.getTerm())
                .build();

        when(studentRepository.findById(studentId)).thenReturn(Optional.of(student));
        when(studentMapper.entityToDto(student)).thenReturn(studentDto);

        StudentDto response = studentService.getStudent(studentId);

        verify(studentRepository).findById(studentId);
        verify(studentMapper).entityToDto(student);
        assertEquals(studentDto, response);

    }

    @Test
    void addStudent() {

        UUID studentId = UUID.randomUUID();

        StudentReq request = StudentReq.builder()
                .firstName("Jan")
                .lastName("Nowak")
                .term(1)
                .build();

        StudentDto studentDto = StudentDto.builder()
                .firstName(request.firstName())
                .lastName(request.lastName())
                .term(request.term())
                .build();

        Student student = Student.builder()
                .id(studentId)
                .firstName(studentDto.firstName())
                .lastName(studentDto.lastName())
                .term(studentDto.term())
                .build();

        when(studentMapper.dtoToEntity(studentDto)).thenReturn(student);
        when(studentRepository.save(student)).thenReturn(student);

        GenericSuccessRes response = studentService.addStudent(request);

        verify(studentMapper).dtoToEntity(studentDto);
        verify(studentRepository).save(student);
        assertEquals(STUDENT_SUCCESS_ADD, response.message());

    }

    @Test
    void editStudent() {

        UUID studentId = UUID.randomUUID();

        StudentReq request = StudentReq.builder()
                .firstName("Jan")
                .lastName("Nowak")
                .term(1)
                .build();

        Student student = Student.builder()
                .id(studentId)
                .firstName("Jan")
                .lastName("Kowalski")
                .term(1)
                .build();

        StudentDto newStudentDto = StudentDto.builder()
                .id(studentId)
                .firstName(request.firstName())
                .lastName(request.lastName())
                .term(request.term())
                .build();

        Student newStudent = Student.builder()
                .id(newStudentDto.id())
                .firstName(newStudentDto.firstName())
                .lastName(newStudentDto.lastName())
                .term(newStudentDto.term())
                .build();

        when(studentRepository.findById(studentId)).thenReturn(Optional.of(student));
        when(studentMapper.dtoToEntity(newStudentDto)).thenReturn(newStudent);

        GenericSuccessRes response = studentService.editStudent(studentId, request);

        verify(studentRepository).findById(studentId);
        verify(studentMapper).dtoToEntity(newStudentDto);
        assertEquals(STUDENT_SUCCESS_EDIT, response.message());

    }

    @Test
    void deleteStudent() {

        UUID studentId = UUID.randomUUID();

        Student student = Student.builder()
                .id(studentId)
                .firstName("Jan")
                .lastName("Kowalski")
                .term(1)
                .build();

        when(studentRepository.findById(studentId)).thenReturn(Optional.of(student));
        doNothing().when(studentRepository).deleteById(studentId);

        GenericSuccessRes response = studentService.deleteStudent(studentId);

        verify(studentRepository).findById(studentId);
        assertEquals(STUDENT_SUCCESS_DELETE, response.message());

    }

    @Test
    void getAllStudentCoursesById() {

        UUID studentId = UUID.randomUUID();
        UUID lecturerId = UUID.randomUUID();
        UUID courseId = UUID.randomUUID();
        UUID firstStudentCourseId = UUID.randomUUID();
        UUID secondStudentCourseId = UUID.randomUUID();

        Student student = Student.builder()
                .id(studentId)
                .firstName("Jan")
                .lastName("Kowalski")
                .term(1)
                .build();

        Lecturer lecturer = Lecturer.builder()
                .id(lecturerId)
                .firstName("Jan")
                .lastName("Nowak")
                .build();

        Course firstCourse = Course.builder()
                .id(courseId)
                .name("Java Programming")
                .lecturer(lecturer)
                .ects(5)
                .build();

        Course secondCourse = Course.builder()
                .id(courseId)
                .name("Python Programming")
                .lecturer(lecturer)
                .ects(5)
                .build();

        Set<StudentCourse> studentCourses = new HashSet<>();

        StudentCourse firstStudentCourse = StudentCourse.builder()
                .id(firstStudentCourseId)
                .student(student)
                .course(firstCourse)
                .degree(5.0)
                .build();

        StudentCourse secondStudentCourse = StudentCourse.builder()
                .id(secondStudentCourseId)
                .student(student)
                .course(secondCourse)
                .degree(2.0)
                .build();

        studentCourses.add(firstStudentCourse);
        studentCourses.add(secondStudentCourse);

        student.setCourses(studentCourses);

        LecturerDto lecturerDto = LecturerDto.builder()
                .id(lecturerId)
                .firstName("Jan")
                .lastName("Nowak")
                .build();

        CourseDto firstCourseDto = CourseDto.builder()
                .id(firstCourse.getId())
                .name(firstCourse.getName())
                .lecturer(lecturerDto)
                .ects(firstCourse.getEcts())
                .build();

        CourseDto secondCourseDto = CourseDto.builder()
                .id(secondCourse.getId())
                .name(secondCourse.getName())
                .lecturer(lecturerDto)
                .ects(secondCourse.getEcts())
                .build();

        Set<StudentCourseRes> expectedResponse = new HashSet<>();

        StudentCourseRes firstCourseRes = StudentCourseRes.builder()
                .id(firstStudentCourseId)
                .courseDto(firstCourseDto)
                .degree(5.0)
                .build();

        StudentCourseRes secondCourseRes = StudentCourseRes.builder()
                .id(secondStudentCourseId)
                .courseDto(secondCourseDto)
                .degree(2.0)
                .build();

        expectedResponse.add(firstCourseRes);
        expectedResponse.add(secondCourseRes);

        when(studentRepository.findById(studentId)).thenReturn(Optional.of(student));
        when(courseMapper.entityToDto(firstStudentCourse.getCourse())).thenReturn(firstCourseDto);
        when(courseMapper.entityToDto(secondStudentCourse.getCourse())).thenReturn(secondCourseDto);

        Set<StudentCourseRes> actualResponse = studentService.getAllStudentCoursesById(studentId);

        verify(studentRepository).findById(studentId);
        verify(courseMapper).entityToDto(firstStudentCourse.getCourse());
        verify(courseMapper).entityToDto(secondStudentCourse.getCourse());
        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    void addStudentCourseByStudentId() {

        UUID studentId = UUID.randomUUID();
        UUID courseId = UUID.randomUUID();
        UUID lecturerId = UUID.randomUUID();
        UUID studentCourseId = UUID.randomUUID();

        Set<StudentCourse> studentCourses = new HashSet<>();

        Student student = Student.builder()
                .id(studentId)
                .firstName("Jan")
                .lastName("Kowalski")
                .term(1)
                .courses(studentCourses)
                .build();

        StudentDto studentDto = StudentDto.builder()
                .id(student.getId())
                .firstName(student.getFirstName())
                .lastName(student.getLastName())
                .term(student.getTerm())
                .build();

        Lecturer lecturer = Lecturer.builder()
                .id(lecturerId)
                .firstName("Jan")
                .lastName("Nowak")
                .build();

        LecturerDto lecturerDto = LecturerDto.builder()
                .id(lecturer.getId())
                .firstName(lecturer.getFirstName())
                .lastName(lecturer.getLastName())
                .build();

        Course course = Course.builder()
                .id(courseId)
                .name("Java Programming")
                .lecturer(lecturer)
                .ects(5)
                .build();

        CourseDto courseDto = CourseDto.builder()
                .id(course.getId())
                .name(course.getName())
                .lecturer(lecturerDto)
                .ects(course.getEcts())
                .build();

        StudentCourseReq request = StudentCourseReq.builder()
                .studentId(studentId)
                .courseId(courseId)
                .degree(5.0)
                .build();

        StudentCourseDto studentCourseDto = StudentCourseDto.builder()
                .courseDto(courseDto)
                .studentDto(studentDto)
                .degree(request.degree())
                .build();

        StudentCourse studentCourse = StudentCourse.builder()
                .id(studentCourseId)
                .course(course)
                .student(student)
                .degree(studentCourseDto.degree())
                .build();

        studentCourses.add(studentCourse);
        student.setCourses(studentCourses);

        when(studentRepository.findById(studentId)).thenReturn(Optional.of(student));
        when(courseRepository.findById(courseId)).thenReturn(Optional.of(course));
        when(courseMapper.entityToDto(course)).thenReturn(courseDto);
        when(studentMapper.entityToDto(student)).thenReturn(studentDto);
        when(studentCourseMapper.dtoToEntity(studentCourseDto)).thenReturn(studentCourse);
        when(studentRepository.save(student)).thenReturn(student);

        GenericSuccessRes actualResponse = studentService.addStudentCourseByStudentId(request);


        verify(studentRepository).findById(studentId);
        verify(courseRepository).findById(courseId);
        verify(courseMapper).entityToDto(course);
        verify(studentMapper).entityToDto(student);
        verify(studentCourseMapper).dtoToEntity(studentCourseDto);
        verify(studentRepository).save(student);
        assertEquals(STUDENT_COURSE_SUCCESS_ADD, actualResponse.message());

    }

    @Test
    void editStudentCourseByStudentCourseId() {

        UUID studentId = UUID.randomUUID();
        UUID courseId = UUID.randomUUID();
        UUID lecturerId = UUID.randomUUID();
        UUID studentCourseId = UUID.randomUUID();

        StudentCourseReq request = StudentCourseReq.builder()
                .studentId(studentId)
                .courseId(courseId)
                .degree(2.0)
                .build();

        Set<StudentCourse> studentCourses = new HashSet<>();

        Student student = Student.builder()
                .id(studentId)
                .firstName("Jan")
                .lastName("Kowalski")
                .term(1)
                .courses(studentCourses)
                .build();

        StudentDto studentDto = StudentDto.builder()
                .id(student.getId())
                .firstName(student.getFirstName())
                .lastName(student.getLastName())
                .term(student.getTerm())
                .build();

        Lecturer lecturer = Lecturer.builder()
                .id(lecturerId)
                .firstName("Jan")
                .lastName("Nowak")
                .build();

        LecturerDto lecturerDto = LecturerDto.builder()
                .id(lecturer.getId())
                .firstName(lecturer.getFirstName())
                .lastName(lecturer.getLastName())
                .build();

        Course course = Course.builder()
                .id(courseId)
                .name("Java Programming")
                .lecturer(lecturer)
                .ects(5)
                .build();

        CourseDto courseDto = CourseDto.builder()
                .id(course.getId())
                .name(course.getName())
                .lecturer(lecturerDto)
                .ects(course.getEcts())
                .build();

        StudentCourse studentCourse = StudentCourse.builder()
                .id(studentCourseId)
                .course(course)
                .student(student)
                .degree(5.0)
                .build();

        studentCourses.add(studentCourse);
        student.setCourses(studentCourses);

        StudentCourseDto newStudentCourseDto = StudentCourseDto.builder()
                .courseDto(courseDto)
                .studentDto(studentDto)
                .degree(2.0)
                .build();

        StudentCourse newStudentCourse = StudentCourse.builder()
                .id(newStudentCourseDto.id())
                .course(course)
                .student(student)
                .degree(2.0)
                .build();

        when(studentRepository.findByIdAndStudentCourseId(studentId, studentCourseId)).thenReturn(Optional.of(student));
        when(courseRepository.findById(courseId)).thenReturn(Optional.of(course));
        when(courseMapper.entityToDto(course)).thenReturn(courseDto);
        when(studentMapper.entityToDto(student)).thenReturn(studentDto);
        when(studentCourseMapper.dtoToEntity(newStudentCourseDto)).thenReturn(newStudentCourse);
        when(studentRepository.save(student)).thenReturn(student);

        GenericSuccessRes actualResponse = studentService.editStudentCourseByStudentCourseId(studentCourseId, request);

        verify(studentRepository).findByIdAndStudentCourseId(studentId, studentCourseId);
        verify(courseRepository).findById(courseId);
        verify(courseMapper).entityToDto(course);
        verify(studentMapper).entityToDto(student);
        verify(studentCourseMapper).dtoToEntity(newStudentCourseDto);
        verify(studentRepository).save(student);
        assertEquals(STUDENT_COURSE_SUCCESS_EDIT, actualResponse.message());

    }

    @Test
    void deleteStudentCourseByStudentCourseId() {

        UUID studentId = UUID.randomUUID();
        UUID courseId = UUID.randomUUID();
        UUID lecturerId = UUID.randomUUID();
        UUID studentCourseId = UUID.randomUUID();


        Lecturer lecturer = Lecturer.builder()
                .id(lecturerId)
                .firstName("Jan")
                .lastName("Nowak")
                .build();

        LecturerDto lecturerDto = LecturerDto.builder()
                .id(lecturer.getId())
                .firstName(lecturer.getFirstName())
                .lastName(lecturer.getLastName())
                .build();

        Course course = Course.builder()
                .id(courseId)
                .name("Java Programming")
                .lecturer(lecturer)
                .ects(5)
                .build();

        CourseDto courseDto = CourseDto.builder()
                .id(course.getId())
                .name(course.getName())
                .lecturer(lecturerDto)
                .ects(course.getEcts())
                .build();

        Set<StudentCourse> studentCourses = new HashSet<>();

        Student student = Student.builder()
                .id(studentId)
                .firstName("Jan")
                .lastName("Kowalski")
                .term(1)
                .courses(studentCourses)
                .build();

        StudentDto studentDto = StudentDto.builder()
                .id(student.getId())
                .firstName(student.getFirstName())
                .lastName(student.getLastName())
                .term(student.getTerm())
                .build();

        StudentCourse studentCourse = StudentCourse.builder()
                .id(studentCourseId)
                .course(course)
                .student(student)
                .degree(5.0)
                .build();

        StudentCourseDto studentCourseDto = StudentCourseDto.builder()
                .id(studentCourse.getId())
                .courseDto(courseDto)
                .studentDto(studentDto)
                .degree(studentCourse.getDegree())
                .build();

        studentCourses.add(studentCourse);
        student.setCourses(studentCourses);

        when(studentCourseRepository.findById(studentCourseId)).thenReturn(Optional.of(studentCourse));
        when(studentRepository.findById(studentId)).thenReturn(Optional.of(student));
        when(studentMapper.entityToDto(student)).thenReturn(studentDto);
        when(courseMapper.entityToDto(course)).thenReturn(courseDto);
        when(studentCourseMapper.dtoToEntity(studentCourseDto)).thenReturn(studentCourse);
        when(studentRepository.save(student)).thenReturn(student);

        GenericSuccessRes actualResponse = studentService.deleteStudentCourseByStudentCourseId(studentCourseId);

        assertEquals(STUDENT_COURSE_SUCCESS_DELETE, actualResponse.message());

    }
}