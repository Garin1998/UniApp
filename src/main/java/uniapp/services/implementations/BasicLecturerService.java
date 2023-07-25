package uniapp.services.implementations;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uniapp.controllers.requests.LecturerReq;
import uniapp.controllers.responses.GenericSuccessRes;
import uniapp.models.dto.LecturerDto;
import uniapp.models.dto.mappers.LecturerMapper;
import uniapp.models.exceptions.LecturerNotFound;
import uniapp.repositories.LecturerRepository;
import uniapp.services.LecturerService;

import java.util.UUID;

import static uniapp.constants.ResponseLecturerMessages.*;

@Service
@RequiredArgsConstructor
public class BasicLecturerService implements LecturerService {

    private final LecturerRepository lecturerRepository;
    private final LecturerMapper lecturerMapper;

    public LecturerDto getLecturer(UUID id) {

        return lecturerMapper.entityToDto(lecturerRepository.findById(id).orElseThrow(LecturerNotFound::new));

    }

    public GenericSuccessRes addLecturer(LecturerReq request) {

        LecturerDto lecturerDto = LecturerDto.builder()
                .firstName(request.firstName())
                .lastName(request.lastName())
                .build();

        lecturerRepository.save(lecturerMapper.dtoToEntity(lecturerDto));
        return new GenericSuccessRes(LECTURER_SUCCESS_ADD);

    }

    public GenericSuccessRes editLecturer(UUID id, LecturerReq request) {

        if(lecturerRepository.findById(id).isEmpty()) {
            throw new LecturerNotFound();
        }
        else {
            LecturerDto lecturerDto = LecturerDto.builder()
                    .id(id)
                    .firstName(request.firstName())
                    .lastName(request.lastName())
                    .build();

            lecturerRepository.save(lecturerMapper.dtoToEntity(lecturerDto));
        }

        return new GenericSuccessRes(LECTURER_SUCCESS_EDIT);

    }

    public GenericSuccessRes deleteLecturer(UUID id) {

        if(lecturerRepository.findById(id).isEmpty()) {
            throw new LecturerNotFound();
        }
        else {
            lecturerRepository.deleteById(id);
        }

        return new GenericSuccessRes(LECTURER_SUCCESS_DELETE);

    }

}
