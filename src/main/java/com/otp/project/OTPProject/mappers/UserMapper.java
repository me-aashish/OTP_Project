package com.otp.project.OTPProject.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.otp.project.OTPProject.DTO.UserRequestDTO;
import com.otp.project.OTPProject.DTO.UserResponseDTO;
import com.otp.project.OTPProject.entities.User;

@Mapper
public interface UserMapper {

	UserMapper iNSTANCE = Mappers.getMapper(UserMapper.class);

	User userDTOtoEntity(UserRequestDTO userRequestDTO);

	UserResponseDTO userEntityToDTO(UserMapper user);
}
