package uniapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import uniapp.models.entities.Course;

import java.util.UUID;

public interface CourseRepository extends JpaRepository<Course, UUID> {}
