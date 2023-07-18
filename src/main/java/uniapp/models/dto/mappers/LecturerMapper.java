package uniapp.models.dto.mappers;

import uniapp.models.Lecturer;
import uniapp.models.Student;
import uniapp.models.dto.LecturerDto;
import uniapp.models.dto.StudentDto;

public interface LecturerMapper {
    Lecturer dtoToEntity(LecturerDto dto);
    LecturerDto entityToDto(Lecturer entity);
}
