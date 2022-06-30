package com.triple.tripleMileageService.service;

import com.triple.tripleMileageService.domain.Point;
import com.triple.tripleMileageService.domain.User;
import com.triple.tripleMileageService.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional(readOnly = false)
    public Long join(User user) {

        System.out.println("=======UserService.join=====");

        //사용자 생성
        User createdUser = User.createUser(user.getName(), new Point());

        userRepository.save(createdUser);

        return createdUser.getId();
    }










}
