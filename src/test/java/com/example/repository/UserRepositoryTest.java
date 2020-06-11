package com.example.repository;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.example.domain.User;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Import(User.class)
@ExtendWith(SpringExtension.class)
@SpringBootTest(properties = {"spring.datasource.url:jdbc:log4jdbc:h2:mem:testdb"})
class UserRepositoryTest {

	@Autowired
	User user;
	@Autowired
	UserRepository userRepository;

	@Test
	@Sql(scripts = "classpath:/test-data-find.sql", config = @SqlConfig(encoding = "utf-8"))
	void testFindByName() {
		Optional<User> optionalUser = userRepository.findByName("ueki");
		user = optionalUser.orElseThrow(() -> new UsernameNotFoundException("The requested user is not found."));
		assertEquals(user.getName(), "ueki");
		assertEquals(user.getPass(), "1qaz2wsx");
		
		log.info("testFindByNameの実行結果:{}", user.toString());

	}

	@Test
	void testCreateUserOk() {

		user.setName("yamada");
		user.setPass("1qaz2wsx");
		userRepository.save(user);
		
		Optional<User> optionalUser = userRepository.findByName("yamada");
		user = optionalUser.orElseThrow(() -> new UsernameNotFoundException("The requested user is not found."));
		assertEquals(user.getName(), "yamada");
		assertEquals(user.getPass(), "1qaz2wsx");
		
		log.info("testCreateUserOkの実行結果:{}", user.toString());
	}
	
	@Test
	@Sql(scripts = "classpath:/test-data-create-ng.sql", config = @SqlConfig(encoding = "utf-8"))
	void testCreateUserNg() {

		user.setName("yuto");
		user.setPass("1qaz2wsx");
		try {
			log.info("testCreateUserNgの実行");
			user = userRepository.save(user);
			log.info("testCreateUserNgの実行結果:{}", user.toString());
			
		} catch(DataAccessException e) {
			System.out.print("例外発生：");
			e.printStackTrace();
			return;
			}
		fail();
	}
}
