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
    public void testSaveMember() {
        //given
        User user = new User("userA");
        //when
        Long saveMember = userRepository.save(user);
        User findUser = userRepository.findUser(saveMember);


        //then
        // 회원 아이디 검증
        Assertions.assertThat(findUser.getId()).isEqualTo(user.getId());
        // 회원 이름 검증
        Assertions.assertThat(findUser.getName()).isEqualTo(user.getName());


    }


}