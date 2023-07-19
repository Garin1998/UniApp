package uniapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uniapp.models.entities.Student;

import java.util.Optional;
import java.util.UUID;

public interface StudentRepository extends JpaRepository<Student, UUID> {

    @Query("SELECT s FROM Student AS s INNER JOIN StudentCourse as sc ON s.id = sc.student.id WHERE s.id = ?1 AND sc.id = ?2")
    Optional<Student> findByIdAndStudentCourseId(UUID studentId, UUID studentCourseId);

}
