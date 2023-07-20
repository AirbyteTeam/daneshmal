package com.airbyte.daneshmal.security;

import com.airbyte.daneshmal.security.permission.Role;
import com.airbyte.daneshmal.security.user.UserInformationService;
import com.airbyte.daneshmal.security.user.model.UserDTO;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class UserLoader implements ApplicationRunner {

    private final UserInformationService userInformationService;

    public UserLoader(UserInformationService userInformationService) {
        this.userInformationService = userInformationService;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        try {
            userInformationService.getByUsername("09924664362");
        } catch (IllegalArgumentException usernameNotFoundException) {
            UserDTO userDTO = new UserDTO();
            userDTO.setUsername("09383740404");
            userDTO.setPassword("airByte");
            userDTO.setRole(Role.MANAGER.name());
            userInformationService.save(userDTO);
        }
    }
}
