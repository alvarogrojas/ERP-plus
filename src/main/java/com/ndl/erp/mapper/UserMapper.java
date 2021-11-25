package com.ndl.erp.mapper;

import com.ndl.erp.domain.User;
import com.ndl.erp.presentation.AuthorizationRequest;
import com.ndl.erp.presentation.UserResponse;

public class UserMapper {

	private UserMapper() {
	}

//	public static UserResponse toResponse(User user) {
//		return UserResponse.builder().name(user.getName()).id(user.getId()).build();
//	}
//
//	public static User toDomain(AuthorizationRequest authorizationRequest) {
//		return User.builder().name(authorizationRequest.getUserName()).password(authorizationRequest.getPassword())
//				.build();
//	}
}
