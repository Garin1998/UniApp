package uniapp.unit.services;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import uniapp.controllers.requests.LecturerReq;
import uniapp.models.dto.LecturerDto;
import uniapp.models.dto.mappers.LecturerMapper;
import uniapp.models.entities.Lecturer;
import uniapp.repositories.LecturerRepository;
import uniapp.services.LecturerService;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
class LecturerServiceUnitTest {

    @InjectMocks
    LecturerService lecturerService;

    @Mock
    LecturerRepository lecturerRepository;

    @Mock
    LecturerMapper lecturerMapper;


    @Test
    void getLecturer() {

        assertNotNull(lecturerRepository);
        assertNotNull(lecturerMapper);

        UUID lecturerId = UUID.randomUUID();

        Lecturer lecturer = Lecturer.builder()
                .id(lecturerId)
                .firstName("Jan")
                .lastName("Kowalski")
                .build();

        LecturerDto lecturerDto = LecturerDto.builder()
                .id(lecturerId)
                .firstName("Jan")
                .lastName("Kowalski")
                .build();

        when(lecturerRepository.findById(lecturerId)).thenReturn(Optional.of(lecturer));
        when(lecturerMapper.entityToDto(lecturer)).thenReturn(lecturerDto);

        lecturerService.getLecturer(lecturerId);

        verify(lecturerRepository).findById(lecturerId);
        verify(lecturerMapper).entityToDto(lecturer);

    }

    @Test
    void addLecturer() {

        UUID lecturerId = UUID.randomUUID();

        LecturerReq request = LecturerReq.builder()
                .firstName("Jan")
                .lastName("Kowalski")
                .build();

        LecturerDto lecturerDto = LecturerDto.builder()
                .firstName(request.firstName())
                .lastName(request.lastName())
                .build();

        Lecturer lecturer = Lecturer.builder()
                .id(lecturerId)
                .firstName(lecturerDto.firstName())
                .lastName(lecturerDto.lastName())
                .build();

        when(lecturerMapper.dtoToEntity(lecturerDto)).thenReturn(lecturer);
        when(lecturerRepository.save(lecturer)).thenReturn(lecturer);

        lecturerService.addLecturer(request);

        verify(lecturerMapper).dtoToEntity(lecturerDto);
        verify(lecturerRepository).save(lecturer);

    }

    @Test
    void editLecturer() {

        UUID lecturerId = UUID.randomUUID();

        LecturerReq request = LecturerReq.builder()
                .firstName("Jan")
                .lastName("Nowak")
                .build();

        Lecturer lecturer = Lecturer.builder()
                .id(lecturerId)
                .firstName("Jan")
                .lastName("Kowalski")
                .build();

        LecturerDto newLecturerDto = LecturerDto.builder()
                .id(lecturerId)
                .firstName(request.firstName())
                .lastName(request.lastName())
                .build();

        Lecturer newLecturer = Lecturer.builder()
                .id(lecturerId)
                .firstName(newLecturerDto.firstName())
                .lastName(newLecturerDto.lastName())
                .build();

        when(lecturerRepository.findById(lecturerId)).thenReturn(Optional.of(lecturer));
        when(lecturerMapper.dtoToEntity(newLecturerDto)).thenReturn(lecturer);
        when(lecturerRepository.save(lecturer)).thenReturn(newLecturer);

        lecturerService.editLecturer(lecturerId, request);

        verify(lecturerRepository).findById(lecturerId);
        verify(lecturerMapper).dtoToEntity(newLecturerDto);
        verify(lecturerRepository).findById(lecturerId);

    }

    @Test
    void deleteLecturer() {

        UUID lecturerId = UUID.randomUUID();

        Lecturer lecturer = Lecturer.builder()
                .id(lecturerId)
                .firstName("Jan")
                .lastName("Kowalski")
                .build();

        when(lecturerRepository.findById(lecturerId)).thenReturn(Optional.of(lecturer));
        doNothing().when(lecturerRepository).deleteById(lecturerId);

        lecturerService.deleteLecturer(lecturerId);

        verify(lecturerRepository).findById(lecturerId);
        verify(lecturerRepository).deleteById(lecturerId);

    }
}