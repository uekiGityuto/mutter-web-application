package com.example.service;

import java.util.Collection;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.domain.User;
import com.example.repository.UserRepository;

@Service
public class LoginUserDetailsService implements UserDetailsService {
	@Autowired
	UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<User> optionalUser = userRepository.findByName(username);
		User user = optionalUser.orElseThrow(() -> new UsernameNotFoundException("該当のユーザは存在しません"));
		if(!user.isEnable()) {
			throw new UsernameNotFoundException("該当のユーザは無効になっています");
		}
		return new LoginUserDetails(user, this.getAuthorities(user));
	}
	
	private Collection<GrantedAuthority> getAuthorities(User user){
		if(user.isAdmin()) {
			return AuthorityUtils.createAuthorityList("ROLE_ADMIN","ROLE_USER");
		} else {
			return AuthorityUtils.createAuthorityList("ROLE_USER");
		}
	}
}
