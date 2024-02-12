package com.otp.project.OTPProject.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.otp.project.OTPProject.dto.UserLogInRequestDTO;
import com.otp.project.OTPProject.dto.UserLogInResponseDTO;
import com.otp.project.OTPProject.dto.UserSignInRequestDTO;
import com.otp.project.OTPProject.dto.UserSignInResponseDTO;
import com.otp.project.OTPProject.entities.User;

@Mapper
public interface UserMapper {

	UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

	User userSignInDTOtoEntity(UserSignInRequestDTO userSignInRequestDTO);

	User userLogInDTOtoEntity(UserLogInRequestDTO userLogInRequestDTO);

	UserSignInResponseDTO userSignInEntityToDTO(User user);

	UserLogInResponseDTO userLogInEntityToDTO(User user);

}
