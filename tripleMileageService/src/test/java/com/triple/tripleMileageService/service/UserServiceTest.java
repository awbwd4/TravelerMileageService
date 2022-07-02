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

import java.util.List;

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
        User user = User.createUser("userA", "pointUuIddA");

        //when
        String joinUserUuid = userService.join(user);
        System.out.println("joinUserId = " + joinUserUuid);
        User findUser = userRepository.findUser(joinUserUuid);
        System.out.println("findUser = " + findUser.getUuid());


        //then
        // 유저 정상 가입 검증
        Assertions.assertThat(joinUserUuid).isEqualTo(findUser.getUuid());


    }


    @Test
    @Transactional
    @Rollback(value = false)
    public void userFindAllTest() {
        //given

        //when
        List<User> allUser = userService.findAllUser();


        
        //then
        for (User user : allUser) {
            System.out.println("user.getName() = " + user.getName());
        }


    }



}