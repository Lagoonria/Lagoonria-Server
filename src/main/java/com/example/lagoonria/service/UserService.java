package com.example.lagoonria.service;

import com.example.lagoonria.ObjectValidator;
import com.example.lagoonria.config.SecurityConfig;
import com.example.lagoonria.dto.UserDTO;
import com.example.lagoonria.entity.User;
import com.example.lagoonria.repository.UserRepository;
import com.example.lagoonria.util.VarList;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    private final PasswordEncoder passwordEncoder;

    public UserService(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public String saveUser(UserDTO userDTO) {

        String role = "USER";

        User user = userRepository.existByEmail(userDTO.getEmail());

        if (user != null) {
            return VarList.RSP_DUPLICATED;
        }
        else {
            User user_reg = new User();
            user_reg.setFirstName(userDTO.getFirstName());
            user_reg.setLastName(userDTO.getLastName());
            user_reg.setAddress(userDTO.getAddress());
            user_reg.setEmail(userDTO.getEmail());
            user_reg.setPassword(passwordEncoder.encode(userDTO.getCreatedPassword()));
            user_reg.setRole(role);

            userRepository.save(user_reg);
            return VarList.RSP_SUCCESS;
        }

    }

}
