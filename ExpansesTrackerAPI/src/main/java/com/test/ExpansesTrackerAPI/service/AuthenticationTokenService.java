package com.test.ExpansesTrackerAPI.service;

import com.test.ExpansesTrackerAPI.model.AuthenticationToken;
import com.test.ExpansesTrackerAPI.model.User;
import com.test.ExpansesTrackerAPI.repository.IAuthTokenRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationTokenService {
    @Autowired
    IAuthTokenRepo authTokenRepo;

    public void saveAuthToken(AuthenticationToken authToken)
    {
        authTokenRepo.save(authToken);
    }

    public boolean authenticate(String email, String token) {
        AuthenticationToken authToken = authTokenRepo.findFirstByTokenValue(token);
        if(authToken == null){
            return false;
        }
        String tokenConnectedUserEmail = authToken.getUser().getEmail();
        return tokenConnectedUserEmail.equals(email);

    }

    public AuthenticationToken findFirstByUser(User user) {
        return authTokenRepo.findFirstByUser(user);
    }

    public void removeToken(AuthenticationToken token) {
        authTokenRepo.delete(token);
    }
}
