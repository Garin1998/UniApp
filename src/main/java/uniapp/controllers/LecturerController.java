package uniapp.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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

    @GetMapping(produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<LecturerDto> getLecturerById(@RequestParam(name = "id") UUID id) {
        return ResponseEntity.ok(lecturerService.getLecturer(id));
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<GenericSuccessRes> addLecturer(@RequestBody LecturerReq request) {
        return ResponseEntity.ok(lecturerService.addLecturer(request));
    }

    @PutMapping(consumes = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<GenericSuccessRes> editLecturer(
            @RequestParam(name = "id") UUID id,
            @RequestBody LecturerReq request
    ) {
        return ResponseEntity.ok(lecturerService.editLecturer(id, request));
    }

    @DeleteMapping(produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<GenericSuccessRes> deleteLecturer(@RequestParam(name = "id") UUID id) {
        return ResponseEntity.ok(lecturerService.deleteLecturer(id));
    }
}
