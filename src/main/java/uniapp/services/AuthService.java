package uniapp.services;

import uniapp.controllers.requests.AuthReq;
import uniapp.controllers.requests.RegisterReq;
import uniapp.controllers.responses.AuthResponse;

public interface AuthService {

    AuthResponse register(RegisterReq request);
    AuthResponse authenticate(AuthReq request);

}
