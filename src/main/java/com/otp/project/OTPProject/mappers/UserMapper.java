package com.otp.project.OTPProject.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.otp.project.OTPProject.DTO.UserLogInRequestDTO;
import com.otp.project.OTPProject.DTO.UserLogInResponseDTO;
import com.otp.project.OTPProject.DTO.UserSignInRequestDTO;
import com.otp.project.OTPProject.DTO.UserSignInResponseDTO;
import com.otp.project.OTPProject.entities.User;

@Mapper
public interface UserMapper {

	UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

	User userSignInDTOtoEntity(UserSignInRequestDTO userSignInRequestDTO);

	User userLogInDTOtoEntity(UserLogInRequestDTO userLogInRequestDTO);

	UserSignInResponseDTO userSignInEntityToDTO(User user);

	UserLogInResponseDTO userLogInEntityToDTO(User user);

}
