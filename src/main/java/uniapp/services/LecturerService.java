package uniapp.services;

import uniapp.controllers.requests.LecturerReq;
import uniapp.controllers.responses.GenericSuccessRes;
import uniapp.models.dto.LecturerDto;

import java.util.UUID;

public interface LecturerService {

    LecturerDto getLecturer(UUID id);
    GenericSuccessRes addLecturer(LecturerReq request);
    GenericSuccessRes editLecturer(UUID id, LecturerReq request);
    GenericSuccessRes deleteLecturer(UUID id);


}
