package uniapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import uniapp.models.entities.StudentCourse;

import java.util.UUID;

public interface StudentCourseRepository extends JpaRepository<StudentCourse, UUID> {
}
