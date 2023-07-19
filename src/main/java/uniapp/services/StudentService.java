package uniapp.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uniapp.controllers.requests.StudentCourseReq;
import uniapp.controllers.requests.StudentReq;
import uniapp.controllers.responses.GenericSuccessRes;
import uniapp.controllers.responses.StudentCourseRes;
import uniapp.models.dto.StudentCourseDto;
import uniapp.models.dto.StudentDto;
import uniapp.models.dto.mappers.CourseMapper;
import uniapp.models.dto.mappers.StudentCourseMapper;
import uniapp.models.dto.mappers.StudentMapper;
import uniapp.models.entities.Course;
import uniapp.models.entities.Student;
import uniapp.models.entities.StudentCourse;
import uniapp.models.exceptions.CourseNotFound;
import uniapp.models.exceptions.StudentCourseNotFound;
import uniapp.models.exceptions.StudentNotFound;
import uniapp.models.exceptions.UserNotFound;
import uniapp.repositories.CourseRepository;
import uniapp.repositories.StudentCourseRepository;
import uniapp.repositories.StudentRepository;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import static uniapp.constants.ResponseStudentMessages.*;

@Service
@RequiredArgsConstructor
public class StudentService {

    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;
    private final StudentCourseRepository studentCourseRepository;
    private final StudentMapper studentMapper;
    private final CourseMapper courseMapper;
    private final StudentCourseMapper studentCourseMapper;

    public StudentDto getStudent(UUID id) {

        return studentMapper.entityToDto(studentRepository.findById(id).orElseThrow(StudentNotFound::new));

    }

    public GenericSuccessRes addStudent(StudentReq request) {

        StudentDto studentDto = StudentDto.builder()
                .firstName(request.firstName())
                .lastName(request.lastName())
                .term(request.term())
                .build();

        studentRepository.save(studentMapper.dtoToEntity(studentDto));
        return new GenericSuccessRes(STUDENT_SUCCESS_ADD);

    }

    public GenericSuccessRes editStudent(UUID id, StudentReq request) {

        if(studentRepository.findById(id).isEmpty()) {
            throw new UserNotFound();
        }
        else {
            StudentDto studentDto = StudentDto.builder()
                    .id(id)
                    .firstName(request.firstName())
                    .lastName(request.lastName())
                    .term(request.term())
                    .build();

            studentRepository.save(studentMapper.dtoToEntity(studentDto));

        }

        return new GenericSuccessRes(STUDENT_SUCCESS_EDIT);

    }

    public GenericSuccessRes deleteStudent(UUID id) {

        if(studentRepository.findById(id).isEmpty()) {
            throw new StudentNotFound();
        }

        studentRepository.deleteById(id);

        return new GenericSuccessRes(STUDENT_SUCCESS_DELETE);

    }

    public Set<StudentCourseRes> getAllStudentCoursesById(UUID id) {

        Student student = studentRepository.findById(id).orElseThrow(StudentNotFound::new);
        Set<StudentCourse> coursesFromEntity = student.getCourses();

        Set<StudentCourseRes> courses = new HashSet<>();

        for(StudentCourse studentCourse : coursesFromEntity) {

            StudentCourseRes studentCourseRes = StudentCourseRes.builder()
                    .id(studentCourse.getId())
                    .courseDto(courseMapper.entityToDto(studentCourse.getCourse()))
                    .degree(studentCourse.getDegree())
                    .build();

            courses.add(studentCourseRes);
        }


        return courses;

    }

    public GenericSuccessRes addStudentCourseByStudentId(StudentCourseReq request) {

        Student student = studentRepository.findById(request.studentId()).orElseThrow(StudentNotFound::new);
        Course course = courseRepository.findById(request.courseId()).orElseThrow(CourseNotFound::new);

        Set<StudentCourse> studentCourses = student.getCourses();

        StudentCourseDto studentCourseDto = StudentCourseDto.builder()
                .courseDto(courseMapper.entityToDto(course))
                .studentDto(studentMapper.entityToDto(student))
                .degree(request.degree())
                .build();

        studentCourses.add(studentCourseMapper.dtoToEntity(studentCourseDto));

        student.setCourses(studentCourses);
        studentRepository.save(student);

        return new GenericSuccessRes(STUDENT_COURSE_SUCCESS_ADD);

    }

    public GenericSuccessRes editStudentCourseByStudentCourseId(UUID id, StudentCourseReq request) {

        Student student = studentRepository.findByIdAndStudentCourseId(request.studentId(), id).orElseThrow(StudentCourseNotFound::new);
        Course course = courseRepository.findById(request.courseId()).orElseThrow(CourseNotFound::new);

        Set<StudentCourse> studentCourses = student.getCourses();

        StudentCourseDto studentCourseDto = StudentCourseDto.builder()
                .courseDto(courseMapper.entityToDto(course))
                .studentDto(studentMapper.entityToDto(student))
                .degree(request.degree())
                .build();

        studentCourses.add(studentCourseMapper.dtoToEntity(studentCourseDto));

        student.setCourses(studentCourses);
        studentRepository.save(student);

        return new GenericSuccessRes(STUDENT_COURSE_SUCCESS_EDIT);

    }


    public GenericSuccessRes deleteStudentCourseByStudentCourseId(UUID studentCourseId) {

        StudentCourse studentCourse = studentCourseRepository.findById(studentCourseId).orElseThrow(StudentCourseNotFound::new);
        Student student = studentRepository.findById(studentCourse.getStudent().getId()).orElseThrow(StudentNotFound::new);

        StudentCourseDto studentCourseDto = StudentCourseDto.builder()
                .id(studentCourseId)
                .studentDto(studentMapper.entityToDto(student))
                .courseDto(courseMapper.entityToDto(studentCourse.getCourse()))
                .build();

        Set<StudentCourse> studentCourses = student.getCourses();
        studentCourses.remove(studentCourseMapper.dtoToEntity(studentCourseDto));

        student.setCourses(studentCourses);

        studentRepository.save(student);

        return new GenericSuccessRes(STUDENT_COURSE_SUCCESS_DELETE);

    }

}
