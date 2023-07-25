package uniapp.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uniapp.controllers.requests.AuthReq;
import uniapp.controllers.requests.RegisterReq;
import uniapp.controllers.responses.AuthResponse;
import uniapp.services.AuthService;

import static uniapp.constants.ControllerPathConstants.AUTH_REQ_URL;

@RestController
@RequestMapping(AUTH_REQ_URL)
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterReq request) {
        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthResponse> authenticate(@Valid @RequestBody AuthReq request) {
        return ResponseEntity.ok(authService.authenticate(request));
    }
}
