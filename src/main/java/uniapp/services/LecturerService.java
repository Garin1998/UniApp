package uniapp.services;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uniapp.controllers.requests.LecturerReq;
import uniapp.controllers.responses.GenericSuccessRes;
import uniapp.models.dto.LecturerDto;
import uniapp.repositories.LecturerRepository;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LecturerService {

    private final LecturerRepository lecturerRepository;

    public ResponseEntity<LecturerDto> getLecturer(UUID id) {
        return null;
    }

    public ResponseEntity<GenericSuccessRes> addLecturer(LecturerReq request) {
        return null;
    }

    public ResponseEntity<GenericSuccessRes> editLecturer(UUID id, LecturerReq request) {
        return null;
    }

    public ResponseEntity<GenericSuccessRes> deleteLecturer(UUID id) {
        return null;
    }
}
