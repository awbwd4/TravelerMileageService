package com.triple.tripleMileageService.repository;

import com.triple.tripleMileageService.domain.User;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;


@RunWith(SpringRunner.class)
@SpringBootTest
public class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @Test
    @Transactional
    @Rollback(value = false)
    public void testCreateUser() {
        //given
        User user = User.createUser("userA", "pointUuid");

        //when
        String createdUser = userRepository.create(user);
        User findUser = userRepository.findUser(createdUser);


        //then
        // 회원 아이디 검증
        Assertions.assertThat(findUser.getUuid()).isEqualTo(user.getUuid());
        // 회원 이름 검증
        Assertions.assertThat(findUser.getName()).isEqualTo(user.getName());


    }


}