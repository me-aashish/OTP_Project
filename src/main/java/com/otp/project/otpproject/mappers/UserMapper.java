package com.otp.project.otpproject.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.otp.project.otpproject.dto.UserLogInRequestDTO;
import com.otp.project.otpproject.dto.UserLogInResponseDTO;
import com.otp.project.otpproject.dto.UserSignInRequestDTO;
import com.otp.project.otpproject.dto.UserSignInResponseDTO;
import com.otp.project.otpproject.entities.User;

@Mapper
public interface UserMapper {

	UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

	User userSignInDTOtoEntity(UserSignInRequestDTO userSignInRequestDTO);

	User userLogInDTOtoEntity(UserLogInRequestDTO userLogInRequestDTO);

	UserSignInResponseDTO userSignInEntityToDTO(User user);

	UserLogInResponseDTO userLogInEntityToDTO(User user);

}
