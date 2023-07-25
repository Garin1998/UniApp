package uniapp.models.exceptions;

public class RoleNotFound extends CustomRuntimeException {

    public RoleNotFound() {

        super("Role not found");

    }

    public RoleNotFound(String message) {

        super(message);

    }

}
