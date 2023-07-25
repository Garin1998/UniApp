package uniapp.models.entities;

import jakarta.persistence.*;
import lombok.*;
import uniapp.models.ERole;

import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Enumerated(EnumType.STRING)
    private ERole name;

}
