package com.skullprogrammer.project.control;

import com.skullprogrammer.project.dto.response.UserDTO;
import com.skullprogrammer.project.model.UserEntity;
import com.skullprogrammer.project.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@Slf4j
public class UserController {

    @Autowired
    private IUserService userService;

    @GetMapping("/user/{username}")
    public UserDTO fetchUserByUsername (@PathVariable String username) {
        log.info("fetchUserByUsername");
        UserEntity user = userService.findUserByUsername(username);
        Mapper mapper = new DozerBeanMapper();
        UserDTO userDTO = mapper.map(user, UserDTO.class);
        return userDTO;
    }

    @PostMapping("/user/save")
    public UserEntity saveUser(@RequestBody @Valid UserEntity user) {
        UserEntity userEntity = userService.saveUser(user);
        return userEntity;
    }

//    @PostMapping("/user/login")
//    public TokenDTO login(@RequestBody @Valid LoginDTO loginDTO) {
//        TokenDTO tokenDTO = userService.login(loginDTO);
//        return tokenDTO;
//    }
}
