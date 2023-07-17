package uniapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import uniapp.models.Student;

import java.util.UUID;

public interface StudentRepository extends JpaRepository<Student, UUID> {
}