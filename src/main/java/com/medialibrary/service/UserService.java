package com.medialibrary.service;

import com.medialibrary.exception.UserAlreadyExistsException;
import com.medialibrary.model.User;
import com.medialibrary.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  @Autowired
  public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
  }

  public String saveUser(String username, String password) {
    if (userRepository.findByUsername(username).isPresent()) {
      throw new UserAlreadyExistsException("User already exists!");
    }

    User user = new User();
    user.setUsername(username);
    user.setPassword(passwordEncoder.encode(password));
    userRepository.save(user);
    return "User saved successfully.";
  }
}
