package com.example.service;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.example.domain.User;
import com.example.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Import(User.class)
@ExtendWith(SpringExtension.class)
@SpringBootTest(properties = 
	{ "spring.datasource.url:jdbc:log4jdbc:h2:mem:testdb;DB_CLOSE_ON_EXIT=FALSE" })
class UserServiceTest {
	
	@Autowired
	User user;
	@Autowired
	UserRepository userRepository;
	@Autowired
	UserService userService;
	@Autowired
	PasswordEncoder passwordEncoder;

	@Test
	@Sql(scripts = "classpath:/test-data-init.sql", config = @SqlConfig(encoding = "utf-8"))
	void testCreateAdmin() {
		user.setName("admin");
		user.setPass("1qaz2wsx");
		userService.create(user);
		
		Optional<User> optionalUser = userRepository.findByName("admin");
		user = optionalUser.orElseThrow(() -> new UsernameNotFoundException("該当のユーザは存在しません"));
		assertEquals(user.getName(), "admin");
		//assertEquals(user.getPass(), passwordEncoder.encode("1qaz2wsx"));
		assertEquals(user.isAdmin(), true);
		assertEquals(user.isEnable(), true);

		log.info("testCreateの実行結果:{}", user.toString());

	}

	@Test
	@Sql(scripts = "classpath:/test-data-init.sql", config = @SqlConfig(encoding = "utf-8"))
	@Sql(scripts = "classpath:/test-data-create-admin.sql", config = @SqlConfig(encoding = "utf-8"))
	void testCreateUser() {
		user.setName("ueki");
		user.setPass("1qaz2wsx");
		userService.create(user);
		
		Optional<User> optionalUser = userRepository.findByName("ueki");
		user = optionalUser.orElseThrow(() -> new UsernameNotFoundException("該当のユーザは存在しません"));
		assertEquals(user.getName(), "ueki");
		//assertEquals(user.getPass(), passwordEncoder.encode("1qaz2wsx"));
		assertEquals(user.isAdmin(), false);
		assertEquals(user.isEnable(), true);

		log.info("testCreateの実行結果:{}", user.toString());

	}
	
	@Test
	@Sql(scripts = "classpath:/test-data-init.sql", config = @SqlConfig(encoding = "utf-8"))
	@Sql(scripts = "classpath:/test-data-create-user.sql", config = @SqlConfig(encoding = "utf-8"))
	void testDelete() {
		Optional<User> optionalUser = userRepository.findByName("ueki");
		user = optionalUser.orElseThrow(() -> new UsernameNotFoundException("該当のユーザは存在しません"));
		userService.delete(user);
		optionalUser = userRepository.findByName("ueki");
		try {
			user = optionalUser.orElseThrow(() -> new UsernameNotFoundException("該当のユーザは存在しません"));
			log.info("testDeleteの実行結果:{}", user.toString());
		} catch(UsernameNotFoundException e) {
			System.out.println(e.getMessage());
			return;
		}
		fail();
	}

}
