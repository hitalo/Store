package com.hit.store.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.hit.store.repositories.people.UserRepository;
import com.hit.store.security.config.SecurityUser;


@Service
public class MyUserDetailsService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;
	
	
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		final Optional<com.hit.store.models.people.User> result = userRepository.findByLogin(username);
		if(!result.isPresent()) return null;
		final com.hit.store.models.people.User resultUser = result.get();
		return new SecurityUser(resultUser);
	}

}
