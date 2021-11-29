package com.unitech.ShoppingCart.admin.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.unitech.ShoppingCart.admin.security.ShoppingCartUserDetailsService;
import com.unitech.ShoppingCart.admin.user.UserRepository;
import com.unitech.commom.entities.User;



public class ShoppingCartUserDetailsService implements UserDetailsService {

	@Autowired
	private UserRepository userRepo;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		User user = userRepo.getUserByEmail(email);
		if (user != null) {
			return new ShoppingCartUserDetails(user);
		}
		
		throw new UsernameNotFoundException("Could not find user with email: " + email);
	}



}
