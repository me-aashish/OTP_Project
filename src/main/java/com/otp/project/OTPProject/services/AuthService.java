package com.otp.project.OTPProject.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.otp.project.OTPProject.DTO.UserLogInRequestDTO;
import com.otp.project.OTPProject.DTO.UserLogInResponseDTO;
import com.otp.project.OTPProject.DTO.UserSignInRequestDTO;
import com.otp.project.OTPProject.DTO.UserSignInResponseDTO;
import com.otp.project.OTPProject.entities.User;
import com.otp.project.OTPProject.exceptions.EmailOrPasswordException;
import com.otp.project.OTPProject.exceptions.UserAlreadyExistsException;
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

	public UserSignInResponseDTO singUpUser(UserSignInRequestDTO userRequestDTO) throws UserAlreadyExistsException {

		Optional<User> optionalUser = userRepository.findByUserEmail(userRequestDTO.getUserEmail());

		if (optionalUser.isPresent()) {

			throw new UserAlreadyExistsException(
					"User with email " + userRequestDTO.getUserEmail() + " already exists");

		}

		User user = UserMapper.INSTANCE.userSignInDTOtoEntity(userRequestDTO);

		String token = jwtService.generateToken(user);
		String password = passwordEncoder.encode(user.getPassword());
		user.setUserPassword(password);

		UserSignInResponseDTO userResponseDTO = UserMapper.INSTANCE.userSignInEntityToDTO(user);
		userResponseDTO.setToken(token);
		user.setRole(Role.USER);
		userRepository.save(user);

		return userResponseDTO;
	}

	public UserLogInResponseDTO logInUser(UserLogInRequestDTO userLogInRequestDTO) throws EmailOrPasswordException {
		authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userLogInRequestDTO.getUserEmail(),
				userLogInRequestDTO.getUserPassword()));
		Optional<User> optionalUser = userRepository.findByUserEmail(userLogInRequestDTO.getUserEmail());

		if (optionalUser.isEmpty()) {
			throw new EmailOrPasswordException("Wrong email or password entered");
		}

		User user = optionalUser.get();
		String password = user.getPassword();
		//		if(isPasswordWrong(password)) {
		//			throw new EmailOrPasswordException("Wrong email or password entered");
		//		}
		String hashedPassword = userRepository.getHashedPasswordByEmail(user.getUserEmail());

		if (!passwordEncoder.matches(password, hashedPassword)) {
			throw new EmailOrPasswordException("Wrong email or password entered");
		}

		UserLogInResponseDTO userLogInResponseDTO = UserMapper.INSTANCE.userLogInEntityToDTO(user);

		String token = jwtService.generateToken(user);

		userLogInResponseDTO.setToken(token);

		return userLogInResponseDTO;
	}

}
