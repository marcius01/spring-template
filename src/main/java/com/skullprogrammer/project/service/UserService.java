package com.skullprogrammer.project.service;

import com.skullprogrammer.project.error.SkullProgrammerException;
import com.skullprogrammer.project.error.enums.ErrorType;
import com.skullprogrammer.project.model.UserEntity;
import com.skullprogrammer.project.repository.UserRespository;
import com.skullprogrammer.project.utility.PasswordUtility;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService implements IUserService, UserDetailsService {

    @Autowired
    private UserRespository userRespository;
    @Autowired
    private PasswordEncoder passwordEncoder;



    @Override
    public UserEntity findUserByUsername(String username) throws SkullProgrammerException {
        Optional<UserEntity> user = userRespository.findByUsername(username);
        if (!user.isPresent()) {
            throw new SkullProgrammerException(ErrorType.USER_NOT_FOUND);
        }
        return user.get();
    }

    @Override
    public UserEntity saveUser(UserEntity user) throws SkullProgrammerException {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        log.debug("PASSWORD: " + user.getPassword());
        return userRespository.save(user);
    }

//    @Override
//    public TokenDTO login(LoginDTO loginDTO) {
//        Optional<UserEntity> user = userRespository.findByUsername(loginDTO.getUsername());
//        if (user.isEmpty() || !checkPassword(user.get(), loginDTO.getPassword())) {
//            throw new DigitalPantryException(ErrorType.USER_NOT_AUTHORIZED);
//        }
//        String token = getJWTToken(user.get().getUsername());
//        TokenDTO tokenDTO = new TokenDTO(token);
//        return tokenDTO;
//
//    }

//    private boolean checkPassword(UserEntity user, String password) {
//        String encryptedPass = PasswordUtility.getSHA512(password);
//        return encryptedPass.equals(user.getPassword());
//    }


//    private String getJWTToken(String username) {
//        String secretKey = "mySecretKey";
//        List<GrantedAuthority> grantedAuthorities = AuthorityUtils
//                .commaSeparatedStringToAuthorityList("ROLE_USER");
//
//        String token = Jwts
//                .builder()
//                .setId("skullProgrammerJWT")
//                .setSubject(username)
//                .claim("authorities",
//                        grantedAuthorities.stream()
//                                .map(GrantedAuthority::getAuthority)
//                                .collect(Collectors.toList()))
//                .setIssuedAt(new Date(System.currentTimeMillis()))
//                .setExpiration(new Date(System.currentTimeMillis() + 600000))
//                .signWith(SignatureAlgorithm.HS512,
//                        secretKey.getBytes()).compact();
//
//        return token;
//    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserEntity> user = userRespository.findByUsername(username);
        if (user.isEmpty()) {
            throw new UsernameNotFoundException("Not Found");
        }
        UserEntity userEntity = user.get();
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        userEntity.getRoles().forEach( role ->
                authorities.add(new SimpleGrantedAuthority(role))
        );
        return new User(userEntity.getUsername(), userEntity.getPassword(), authorities);
    }
}
