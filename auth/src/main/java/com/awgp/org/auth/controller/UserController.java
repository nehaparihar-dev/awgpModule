package com.awgp.org.auth.controller;

import com.awgp.org.auth.cognito.CognitoHelper;
import com.awgp.org.auth.pojo.BaseResponse;
import com.awgp.org.auth.pojo.UserDetails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
public class UserController{

    @Autowired
    CognitoHelper helper;

    @Autowired
    RestTemplate restTemplate;

    private static final Logger LOGGER= LoggerFactory.getLogger(UserController.class);

@PostMapping("/createUser")
public boolean createUser(@RequestBody UserDetails usr){
    LOGGER.info("Before Calling SignUp user");
    LOGGER.info("Name" +usr.getName()+"Password :"+usr.getPassword()+"Email :"+usr.getEmail()+"Phone No :"+usr.getPhoneNumber());
    boolean success=helper.signUpUser(usr.getName(),usr.getPassword(),usr.getEmail(),usr.getPhoneNumber());
    LOGGER.info("End Calling Sign user");
    restTemplate.postForObject("http://awgp-dynamodb-service/userDb/createUser",usr, BaseResponse.class);
    LOGGER.info("End Calling DynamoDb user");

    return success;
}

@PostMapping("/verifyUser")
public void verifyUser(String code,String userName){
    boolean success=helper.verifyAccessCode(userName,code);
    System.out.println("Success ::"+success);
}

}

