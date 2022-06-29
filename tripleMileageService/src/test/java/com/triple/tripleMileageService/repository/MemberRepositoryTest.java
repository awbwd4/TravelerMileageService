package com.triple.tripleMileageService.repository;

import com.triple.tripleMileageService.domain.Member;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;


@RunWith(SpringRunner.class)
@SpringBootTest
public class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;

    @Test
    @Transactional
    @Rollback(value = false)
    public void testSaveMember() {
        //given
        Member member = new Member("userA");
        //when
        Long saveMember = memberRepository.save(member);
        Member findMember = memberRepository.findMember(saveMember);


        //then
        // 회원 아이디 검증
        Assertions.assertThat(findMember.getId()).isEqualTo(member.getId());
        // 회원 이름 검증
        Assertions.assertThat(findMember.getName()).isEqualTo(member.getName());


    }


}