package uniapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import uniapp.models.ERole;
import uniapp.models.entities.Role;

import java.util.Optional;
import java.util.UUID;

public interface RoleRepository extends JpaRepository<Role, UUID> {

    Optional<Role> findByName(ERole name);

}
