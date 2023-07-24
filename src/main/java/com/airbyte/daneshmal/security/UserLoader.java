package com.airbyte.daneshmal.security;

import com.airbyte.daneshmal.security.permission.Role;
import com.airbyte.daneshmal.security.user.UserInformationRepository;
import com.airbyte.daneshmal.security.user.UserInformationService;
import com.airbyte.daneshmal.security.user.model.UserDTO;
import com.airbyte.daneshmal.security.user.model.UserInformation;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class UserLoader implements ApplicationRunner {

    private final UserInformationService userInformationService;
    private final UserInformationRepository userInformationRepository;

    public UserLoader(UserInformationService userInformationService, UserInformationRepository userInformationRepository) {
        this.userInformationService = userInformationService;
        this.userInformationRepository = userInformationRepository;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        try {
            userInformationService.getByUsername("09383740404");
        } catch (IllegalArgumentException usernameNotFoundException) {
            UserInformation userDTO = new UserInformation();
            userDTO.setUsername("09383740404");
            userDTO.setPassword("airByte");
            userDTO.setRole(Role.MANAGER);
            userInformationRepository.save(userDTO);
        }
    }
}
