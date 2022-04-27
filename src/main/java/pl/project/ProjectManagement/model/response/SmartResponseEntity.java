package pl.project.ProjectManagement.model.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

public class SmartResponseEntity {

    public static ResponseEntity<?> fromBoolean(boolean success) {
        if (success) {
            return new ResponseEntity<>(
                    new ResponseMsg(HttpStatus.OK.toString(), "OK"), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(
                    new ResponseMsg(HttpStatus.BAD_REQUEST.toString(), "BAD_REQUEST"),
                    HttpStatus.BAD_REQUEST);
        }
    }


  public static ResponseEntity<?> fromOptional(Optional<?> optional){
        if(optional.isPresent()){
            return new ResponseEntity<>(optional.get(),HttpStatus.OK);
        }
      return new ResponseEntity<>(
              new ResponseMsg(HttpStatus.NOT_FOUND.toString(),
                      "NOT FOUND"), HttpStatus.NOT_FOUND);
  }



}
