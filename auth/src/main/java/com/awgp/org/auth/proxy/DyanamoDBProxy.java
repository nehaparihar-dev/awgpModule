package com.awgp.org.auth.proxy;
import com.awgp.org.auth.pojo.BaseResponse;
import com.awgp.org.auth.pojo.UserDetails;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

//@FeignClient(name="user-auth-service")
@FeignClient(name="APIGateway")
@RibbonClient(name="awgp-dynamodb-service")
public interface DyanamoDBProxy {

//        @PostMapping("/userDb/createUser")
       @PostMapping("awgp-dynamodb-service/userDb/createUser")
       public BaseResponse createUser(@RequestBody UserDetails userDetails);
}
