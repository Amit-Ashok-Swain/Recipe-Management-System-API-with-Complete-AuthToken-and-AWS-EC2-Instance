package com.geekster.moduleclearancetest.RecipeManagementSystemAPIwithAWS.service;


import com.geekster.moduleclearancetest.RecipeManagementSystemAPIwithAWS.model.AuthenticationToken;
import com.geekster.moduleclearancetest.RecipeManagementSystemAPIwithAWS.model.User;
import com.geekster.moduleclearancetest.RecipeManagementSystemAPIwithAWS.repository.AuthenticationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {
    @Autowired
    private AuthenticationRepository authenticationRepo;

    public String authenticate(String authTokenValue) {
        AuthenticationToken authToken = authenticationRepo.findFirstByTokenValue(authTokenValue);

        if (authToken == null) {
            return null; // Token is not valid
        }

        // You can add additional checks, such as token expiration, if necessary

        return authToken.getUser().getEmail(); // Return the user's email
    }

    public void saveAuthToken(AuthenticationToken authToken)
    {
        authenticationRepo.save(authToken);
    }

    public AuthenticationToken findFirstByUser(User user) {
        return authenticationRepo.findFirstByUser(user);
    }

    public void removeToken(AuthenticationToken token) {
        authenticationRepo.delete(token);
    }
}

