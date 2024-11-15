package com.medialibrary.authentication;

import com.medialibrary.exception.UserNotFoundException;
import com.medialibrary.model.User;
import com.medialibrary.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

  private final UserRepository userRepository;

  public AuthenticationService(@Autowired UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public User getCurrentUser() {
    String username = SecurityContextHolder.getContext().getAuthentication().getName();
    return userRepository.findByUsername(username)
        .orElseThrow(() -> new UserNotFoundException("User not found!"));
  }
}
