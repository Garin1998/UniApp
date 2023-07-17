package uniapp.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "lecturer")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Lecturer {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID uuid;
    private String firstName;
    private String lastName;
}
