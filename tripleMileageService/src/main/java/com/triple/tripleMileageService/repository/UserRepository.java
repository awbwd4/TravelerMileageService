package com.triple.tripleMileageService.repository;

import com.triple.tripleMileageService.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
@RequiredArgsConstructor
public class UserRepository {

    @PersistenceContext
    private EntityManager em;

    // 회원 등록
    public String create(User user) {
        System.out.println("=======UserRepository.save()=======");
        em.persist(user);
        return user.getUuid();
    }

    // 회원 조회
    public User findUser(String uuid) {
        System.out.println("=======UserRepository.findUser()=======");
        return em.find(User.class, uuid);
    }


}
