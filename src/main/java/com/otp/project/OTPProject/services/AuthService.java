package com.otp.project.OTPProject.services;

import java.util.Optional;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.otp.project.OTPProject.dto.UserLogInRequestDTO;
import com.otp.project.OTPProject.dto.UserLogInResponseDTO;
import com.otp.project.OTPProject.dto.UserSignInRequestDTO;
import com.otp.project.OTPProject.dto.UserSignInResponseDTO;
import com.otp.project.OTPProject.entities.User;
import com.otp.project.OTPProject.exceptions.EmailOrPasswordException;
import com.otp.project.OTPProject.exceptions.UserAlreadyExistsException;
import com.otp.project.OTPProject.mappers.UserMapper;
import com.otp.project.OTPProject.repositories.UserRepository;
import com.otp.project.OTPProject.utlis.Role;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

	private final UserRepository userRepository;

	private final JwtService jwtService;

	private final PasswordEncoder passwordEncoder;

	private final AuthenticationManager authenticationManager;

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
		userResponseDTO.setMessage("user created successfully");
		user.setRole(Role.USER);
		userRepository.save(user);

		return userResponseDTO;
	}

	public UserLogInResponseDTO logInUser(UserLogInRequestDTO userLogInRequestDTO) throws EmailOrPasswordException {
		authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userLogInRequestDTO.getUserEmail(),
				userLogInRequestDTO.getUserPassword()));
		Optional<User> optionalUser = userRepository.findByUserEmail(userLogInRequestDTO.getUserEmail());

		if (optionalUser.isEmpty()) {
			throw new EmailOrPasswordException("User not found");
		}

		User user = optionalUser.get();
		String password = userLogInRequestDTO.getUserPassword();
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
