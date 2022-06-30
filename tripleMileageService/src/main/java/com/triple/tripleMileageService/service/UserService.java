package com.triple.tripleMileageService.service;

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

    @Transactional
    public String join(User user) {

        System.out.println("=======UserService.join=====");

        return userRepository.create(user);

    }

    public User findUser(String uuid) {
        return userRepository.findUser(uuid);
    }








}
