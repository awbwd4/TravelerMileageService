package com.triple.tripleMileageService.service;

import com.triple.tripleMileageService.domain.User;
import com.triple.tripleMileageService.repository.PlaceRepository;
import com.triple.tripleMileageService.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Repository;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest {

    @Autowired
    UserService userService;
    @Autowired
    UserRepository userRepository;

    @Test
    @Transactional
    @Rollback(value = false)
    public void userJoinTest() {
        //given
        User user = new User("joinUserA");

        //when
        Long joinUserId = userService.join(user);
        System.out.println("joinUserId = " + joinUserId);
        User findUser = userRepository.findUser(joinUserId);
        System.out.println("findUser = " + findUser.getId());


        //then
        // 유저 정상 가입 검증
        Assertions.assertThat(joinUserId).isEqualTo(findUser.getId());


    }



}