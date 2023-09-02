package com.example.lagoonria.controller;

import com.example.lagoonria.ObjectValidator;
import com.example.lagoonria.dto.ResponseDTO;
import com.example.lagoonria.dto.UserDTO;
import com.example.lagoonria.service.UserService;
import com.example.lagoonria.util.VarList;
import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private ResponseDTO responseDTO;

    @Autowired
    private ObjectValidator<UserDTO> objectValidator;

    @PostMapping(value = "/register")
    public ResponseEntity<?> registerUser(@RequestBody UserDTO userDTO) {

        var violations = objectValidator.validate(userDTO);

        if (violations.size() != 0) {
            System.out.println(violations);
            responseDTO.setCode(VarList.RSP_ERROR);
            responseDTO.setMessage("validation error");
            responseDTO.setContent(violations);
            return new ResponseEntity<>(responseDTO, HttpStatus.BAD_REQUEST);
        }

        if (!userDTO.getCreatedPassword().equals(userDTO.getConfirmPassword())) {
            responseDTO.setCode(VarList.RSP_FAIL);
            responseDTO.setMessage("passwords are not equal");
            responseDTO.setContent(null);
            return new ResponseEntity<>(responseDTO, HttpStatus.BAD_REQUEST);
        }

        try {

            String response = userService.saveUser(userDTO);

            if (response.equals("06")) {
                responseDTO.setCode(VarList.RSP_ERROR);
                responseDTO.setMessage("Email exist");
                responseDTO.setContent(userDTO);
                return new ResponseEntity<>(responseDTO, HttpStatus.BAD_REQUEST);
            }
            else {
                responseDTO.setCode(VarList.RSP_SUCCESS);
                responseDTO.setMessage("Successfully added user");
                responseDTO.setContent(null);
                return new ResponseEntity<>(responseDTO, HttpStatus.ACCEPTED);
            }

        }
        catch (Exception e) {
            responseDTO.setCode(VarList.RSP_ERROR);
            responseDTO.setMessage(e.getMessage());
            responseDTO.setContent(null);
            return new ResponseEntity(responseDTO, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

}
