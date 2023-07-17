package uniapp.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uniapp.controllers.requests.LecturerReq;
import uniapp.controllers.responses.GenericSuccessRes;
import uniapp.models.dto.LecturerDto;
import uniapp.services.LecturerService;

import java.util.UUID;

import static uniapp.constants.ControllerPathConstants.LECTURER_REQ_URL;

@RestController
@RequestMapping(LECTURER_REQ_URL)
@RequiredArgsConstructor
public class LecturerController {

    private final LecturerService lecturerService;

    @GetMapping
    public ResponseEntity<LecturerDto> getLecturerById(@RequestParam(name = "id") UUID id) {
        return lecturerService.getLecturer(id);
    }

    @PostMapping
    public ResponseEntity<GenericSuccessRes> addLecturer(@RequestBody LecturerReq request) {
        return lecturerService.addLecturer(request);
    }

    @PutMapping
    public ResponseEntity<GenericSuccessRes> editLecturer(
            @RequestParam(name = "id") UUID id,
            @RequestBody LecturerReq request
    ) {
        return lecturerService.editLecturer(id, request);
    }

    @DeleteMapping
    public ResponseEntity<GenericSuccessRes> deleteLecturer(@RequestParam(name = "id") UUID id) {
        return lecturerService.deleteLecturer(id);
    }
}
