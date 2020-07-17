package br.com.abinbev.controller;

import br.com.abinbev.repository.entity.UserEntity;
import br.com.abinbev.util.Constants;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class SecurityController {

    @RequestMapping(value = "/user-auth", method = RequestMethod.GET)
    @ResponseBody
    @Secured({Constants.ROLE_CLIENT, Constants.ROLE_ADMIN})
    public UserEntity user() {
        return (UserEntity) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
    }
}
