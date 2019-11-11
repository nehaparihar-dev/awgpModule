package com.springboot.controller;

import com.springboot.model.BaseResponse;
import com.springboot.model.Error;
import com.springboot.model.UserDetails;
import com.springboot.repository.DynamoDbUserDetailsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/userDb")
public class UserDetailsDbController {

	@Autowired
	private DynamoDbUserDetailsRepository repository;

    private static final Logger LOGGER= LoggerFactory.getLogger(UserDetailsDbController.class);


    @PostMapping("/createUser")
	public BaseResponse insertIntoDynamoDB(@RequestBody UserDetails userDetails) {
        LOGGER.info("Inside insertIntoDynamoDB start");
        BaseResponse baseResponse=new BaseResponse();
		try{
			repository.insertIntoDynamoDB(userDetails);
			baseResponse.setSuccess(true);

		}catch(Exception e){
			Error error = new Error();
			error.setErrorCode("DYNAMO_DB_ERROR");
			error.setErrorMessage(e.getMessage());
			baseResponse.setError(error);
		}
        LOGGER.info("Inside insertIntoDynamoDB end");

		return baseResponse;
	}


//	@PutMapping
//	public void updateUserDetails(@RequestBody UserDetails userDetails) {
//		repository.updateMemberDetails(userDetails);
//	}


//	@GetMapping
//	public ResponseEntity<UserDetails> getOneMemberDetails(@RequestParam String memberId, @RequestParam String lastName) {
//		UserDetails userDetails = repository.getOneMemberDetails(memberId, lastName);
//		return new ResponseEntity<UserDetails>(userDetails, HttpStatus.OK);
//	}
//
//
//	@PutMapping
//	public void updateUserDetails(@RequestBody UserDetails userDetails) {
//		repository.updateMemberDetails(userDetails);
//	}
//
//	@DeleteMapping(value = "{userId}/{phoneNumber}")
//	public void deleteMemberDetails(@PathVariable("userId") String userId,
//			@PathVariable("mobileNumber") String mobileNumber) {
//		UserDetails userDetails = new UserDetails();
//		userDetails.setMemberId(memberId);
//		userDetails.setPhoneNumber(mobileNumber);
//		repository.deleteMemberDetails(userDetails);
//	}
}