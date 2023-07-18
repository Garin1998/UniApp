package uniapp.models.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String name;
    private int ects;
    @OneToOne
    @JoinColumn(name = "lecturer", referencedColumnName = "id")
    private Lecturer lecturer;

}
