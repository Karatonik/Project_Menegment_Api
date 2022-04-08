package pl.project.ProjectManagement.model.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class SmartResponseEntity {

    static public ResponseEntity<?> fromBoolean(boolean success) {
        if (success) {
            return new ResponseEntity<>(
                    new ErrorObject(HttpStatus.OK.toString(), "OK"), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(
                    new ErrorObject(HttpStatus.BAD_REQUEST.toString(), "BAD_REQUEST"),
                    HttpStatus.BAD_REQUEST);
        }
    }

    static public ResponseEntity<?> fromString(String value) {
        if (value.equals("")) {
            return new ResponseEntity<>(value, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(
                    new ErrorObject(HttpStatus.NOT_FOUND.toString(),
                            "NOT FOUND"), HttpStatus.NOT_FOUND);
        }
    }

    static public ResponseEntity<?> fromJWTResponse(JwtResponse jwtResponse) {
        System.out.println(jwtResponse);
        if (!jwtResponse.getJwToken().equals("")) {
            return new ResponseEntity<>(jwtResponse, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(
                    new ErrorObject(HttpStatus.NOT_FOUND.toString(),
                            "NOT FOUND"), HttpStatus.NOT_FOUND);
        }
    }


}
