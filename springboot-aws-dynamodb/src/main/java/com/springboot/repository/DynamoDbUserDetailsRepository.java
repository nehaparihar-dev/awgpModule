package com.springboot.repository;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBSaveExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ComparisonOperator;
import com.amazonaws.services.dynamodbv2.model.ConditionalCheckFailedException;
import com.amazonaws.services.dynamodbv2.model.ExpectedAttributeValue;
import com.springboot.model.UserDetails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class DynamoDbUserDetailsRepository {

	private static final Logger LOGGER = LoggerFactory.getLogger(DynamoDbUserDetailsRepository.class);

	@Autowired
	private DynamoDBMapper mapper;

	public void insertIntoDynamoDB(UserDetails userDetails) {
		mapper.save(userDetails);
	}

	public void verifyUserDetails(){}

	public UserDetails getOneMemberDetails(String userId, String phoneNumber) {
		return mapper.load(UserDetails.class, userId, phoneNumber);
	}

	public void updateMemberDetails(UserDetails userDetails) {
		try {
			mapper.save(userDetails, buildDynamoDBSaveExpression(userDetails));
		} catch (ConditionalCheckFailedException exception) {
			LOGGER.error("invalid data - " + exception.getMessage());
		}
	}

	public void deleteMemberDetails(UserDetails userDetails) {
		mapper.delete(userDetails);
	}

	public DynamoDBSaveExpression buildDynamoDBSaveExpression(UserDetails userDetails) {
		DynamoDBSaveExpression saveExpression = new DynamoDBSaveExpression();
		Map<String, ExpectedAttributeValue> expected = new HashMap<>();
		expected.put("userId", new ExpectedAttributeValue(new AttributeValue(userDetails.getUserId()))
				.withComparisonOperator(ComparisonOperator.EQ));
		saveExpression.setExpected(expected);
		return saveExpression;
	}
}