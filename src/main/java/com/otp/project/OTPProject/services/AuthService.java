package com.otp.project.OTPProject.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.otp.project.OTPProject.DTO.UserLogInRequestDTO;
import com.otp.project.OTPProject.DTO.UserLogInResponseDTO;
import com.otp.project.OTPProject.DTO.UserSignInRequestDTO;
import com.otp.project.OTPProject.DTO.UserSignInResponseDTO;
import com.otp.project.OTPProject.entities.User;
import com.otp.project.OTPProject.mappers.UserMapper;
import com.otp.project.OTPProject.repositories.UserRepository;
import com.otp.project.OTPProject.utlis.Role;

@Service
public class AuthService {

	@Autowired
	UserRepository userRepository;

	@Autowired
	JwtService jwtService;

	@Autowired
	PasswordEncoder passwordEncoder;

	@Autowired
	AuthenticationManager authenticationManager;

	public UserSignInResponseDTO singUpUser(UserSignInRequestDTO userRequestDTO) {
		User user = UserMapper.INSTANCE.userSignInDTOtoEntity(userRequestDTO);

		String token = jwtService.generateToken(user);
		String password = passwordEncoder.encode(user.getPassword());
		user.setUserPassword(password);

		UserSignInResponseDTO userResponseDTO = UserMapper.INSTANCE.userSignInEntityToDTO(user);
		userResponseDTO.setToken(token);
		userResponseDTO.setCreated(true);
		user.setRole(Role.USER);
		userRepository.save(user);

		return userResponseDTO;
	}

	public UserLogInResponseDTO logInUser(UserLogInRequestDTO userLogInRequestDTO) {
		authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userLogInRequestDTO.getUserEmail(),
				userLogInRequestDTO.getUserPassword()));
		Optional<User> optionalUser = userRepository.findByUserEmail(userLogInRequestDTO.getUserEmail());

		if (optionalUser.isEmpty())
			throw new UsernameNotFoundException("User not found");

		User user = optionalUser.get();

		UserLogInResponseDTO userLogInResponseDTO = UserMapper.INSTANCE.userLogInEntityToDTO(user);

		String token = jwtService.generateToken(user);

		userLogInResponseDTO.setToken(token);

		return userLogInResponseDTO;
	}

}
