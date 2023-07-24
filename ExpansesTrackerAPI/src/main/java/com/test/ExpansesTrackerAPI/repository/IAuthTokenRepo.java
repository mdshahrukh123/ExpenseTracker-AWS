package com.test.ExpansesTrackerAPI.repository;

import com.test.ExpansesTrackerAPI.model.AuthenticationToken;
import com.test.ExpansesTrackerAPI.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IAuthTokenRepo extends JpaRepository<AuthenticationToken,Integer> {
    AuthenticationToken findFirstByTokenValue(String token);

    AuthenticationToken findFirstByUser(User user);
}
