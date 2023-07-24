package com.test.ExpansesTrackerAPI.repository;

import com.test.ExpansesTrackerAPI.model.User;
import org.springframework.boot.web.embedded.undertow.UndertowServletWebServer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IUserRepo extends JpaRepository<User,Integer> {
    User findByEmail(String newEmail);



    User findFirstByEmail(String email);
}
