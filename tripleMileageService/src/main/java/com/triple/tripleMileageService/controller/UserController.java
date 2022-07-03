package com.triple.tripleMileageService.controller;

import com.triple.tripleMileageService.domain.Place;
import com.triple.tripleMileageService.domain.User;
import com.triple.tripleMileageService.dto.EventRequest;
import com.triple.tripleMileageService.form.UserForm;
import com.triple.tripleMileageService.repository.UserRepository;
import com.triple.tripleMileageService.service.PointOuterService;
import com.triple.tripleMileageService.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final PointOuterService pointOuterService;

    /**
     * 사용자 등록
     **/
    @GetMapping("/users/new")
    public String createForm(Model model) {
        model.addAttribute("userForm", new UserForm());
        return "users/createUserForm";
    }

    @PostMapping("/users/new")
    public String user(@Valid UserForm form, BindingResult result) {

        if (result.hasErrors()) {
            return "users/createUserForm";
        }

        //User객체 생성
        User createdUser = User.createUser(form.getName());
        String newUserPoint = pointOuterService.createNewUserPoint(createdUser.getUuid());

        userService.join(createdUser);

        return "redirect:/users";
    }



    /**
     * 사용자 조회
     **/
    @GetMapping("/users")
    public String list(Model model) {
       List<User> users = userService.findAllUser();
       model.addAttribute("users", users);
       return "users/userList";
    }


}
