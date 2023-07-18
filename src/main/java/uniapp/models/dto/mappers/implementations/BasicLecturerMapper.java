package uniapp.models.dto.mappers.implementations;

import org.springframework.stereotype.Component;
import uniapp.models.entities.Lecturer;
import uniapp.models.dto.LecturerDto;
import uniapp.models.dto.mappers.LecturerMapper;

@Component
public class BasicLecturerMapper implements LecturerMapper {

    @Override
    public Lecturer dtoToEntity(LecturerDto dto) {

        return Lecturer.builder()
                .id(dto.id())
                .firstName(dto.firstName())
                .lastName(dto.lastName())
                .build();

    }

    @Override
    public LecturerDto entityToDto(Lecturer entity) {

        return LecturerDto.builder()
                .id(entity.getId())
                .firstName(entity.getFirstName())
                .lastName(entity.getLastName())
                .build();

    }

}
