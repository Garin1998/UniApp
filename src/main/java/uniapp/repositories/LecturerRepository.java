package uniapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import uniapp.models.entities.Lecturer;

import java.util.UUID;

public interface LecturerRepository extends JpaRepository<Lecturer, UUID> {}
