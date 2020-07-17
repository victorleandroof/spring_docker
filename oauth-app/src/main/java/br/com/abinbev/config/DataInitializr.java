package br.com.abinbev.config;

import br.com.abinbev.repository.RoleRepository;
import br.com.abinbev.repository.UserRepository;
import br.com.abinbev.repository.entity.RoleEntity;
import br.com.abinbev.repository.entity.UserEntity;
import br.com.abinbev.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class DataInitializr implements ApplicationListener<ContextRefreshedEvent> {

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public DataInitializr(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent arg0) {
        List<UserEntity> userEntities = userRepository.findAll();
        if (userEntities.isEmpty()) {
            createUser("admin", passwordEncoder.encode("123456"), Constants.ROLE_ADMIN);
            createUser("client",passwordEncoder.encode("123456"), Constants.ROLE_CLIENT);
        }
    }

    public void createUser(String username, String password, String roleName) {
        RoleEntity role = new RoleEntity(roleName);
        this.roleRepository.save(role);
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(username);
        userEntity.setPassword(password);
        userEntity.setRoleEntityList(Arrays.asList(role));
        userRepository.save(userEntity);
    }

}