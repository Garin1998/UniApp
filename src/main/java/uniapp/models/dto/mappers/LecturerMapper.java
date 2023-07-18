package uniapp.models.dto.mappers;

import uniapp.models.entities.Lecturer;
import uniapp.models.dto.LecturerDto;

public interface LecturerMapper {

    Lecturer dtoToEntity(LecturerDto dto);
    LecturerDto entityToDto(Lecturer entity);

}
