package com.skullprogrammer.project.service;

import com.skullprogrammer.project.error.SkullProgrammerException;
import com.skullprogrammer.project.model.UserEntity;

public interface IUserService {

    public UserEntity findUserByUsername(String username) throws SkullProgrammerException;

    public UserEntity saveUser(UserEntity user) throws SkullProgrammerException;

//    public TokenDTO login(LoginDTO loginDTO);
}
