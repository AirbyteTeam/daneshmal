package com.airbyte.daneshmal.security.user;

import com.airbyte.daneshmal.security.user.model.UserInformation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserInformationRepository extends JpaRepository<UserInformation, String> {
}
