package com.triple.tripleMileageService.repository;

import com.triple.tripleMileageService.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
@RequiredArgsConstructor
public class MemberRepository {

    @PersistenceContext
    private EntityManager em;

    // 회원 등록
    public Long save(Member member) {
        System.out.println("=======MemberRepository.save()=======");
        em.persist(member);
        return member.getId();
    }

    // 회원 조회
    public Member findMember(Long id) {
        System.out.println("=======MemberRepository.findMember()=======");
        return em.find(Member.class, id);
    }


}
