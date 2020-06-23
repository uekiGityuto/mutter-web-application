package com.example.repository;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;

//import org.junit.jupiter.api.BeforeEach;
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
@SpringBootTest(properties = { "spring.datasource.url:jdbc:h2:mem:testdb;DB_CLOSE_ON_EXIT=FALSE;" })
class UserRepositoryTest {

	@Autowired
	User user;
	@Autowired
	UserRepository userRepository;

	/*
	 * //各テストの前にテーブルの初期化をしたいが上手く出来ないためコメントアウト
	 * 
	 * @Sql(scripts = "classpath:/test-data-init.sql", config = @SqlConfig(encoding
	 * = "utf-8"))
	 * 
	 * @BeforeEach public void initialize() { //userRepository.deleteAll();
	 * System.out.println("各テストの前に毎回呼ばれる。");
	 * 
	 * }
	 */

	@Test
	@Sql(scripts = "classpath:/test-data-init.sql", config = @SqlConfig(encoding = "utf-8"))
	@Sql(scripts = "classpath:/test-data-create-user.sql", config = @SqlConfig(encoding = "utf-8"))
	void testFindByUserName() {
		Optional<User> optionalUser = userRepository.findByName("ueki");
		user = optionalUser.orElseThrow(() -> new UsernameNotFoundException("該当のユーザは存在しません"));
		assertEquals(user.getName(), "ueki");
		assertEquals(user.getPass(), "1qaz2wsx");
		assertEquals(user.isAdmin(), false);
		assertEquals(user.isEnable(), true);

		log.info("testFindByUserNameの実行結果:{}", user.toString());

	}

	@Test
	@Sql(scripts = "classpath:/test-data-init.sql", config = @SqlConfig(encoding = "utf-8"))
	@Sql(scripts = "classpath:/test-data-create-user.sql", config = @SqlConfig(encoding = "utf-8"))
	void testFindByUserNameNg() {
		Optional<User> optionalUser = userRepository.findByName("ueko");
		try {
			user = optionalUser.orElseThrow(() -> new UsernameNotFoundException("該当のユーザは存在しません"));
			assertEquals(user.getName(), "ueki");
			assertEquals(user.getPass(), "1qaz2wsx");
			assertEquals(user.isAdmin(), false);
			assertEquals(user.isEnable(), true);

			log.info("testFindByUserNameNgの実行結果:{}", user.toString());
		} catch (UsernameNotFoundException e) {
			System.out.println(e.getMessage());
			return;
		}
		fail();
	}

	@Test
	@Sql(scripts = "classpath:/test-data-init.sql", config = @SqlConfig(encoding = "utf-8"))
	@Sql(scripts = "classpath:/test-data-create-admin.sql", config = @SqlConfig(encoding = "utf-8"))
	void testFindByAdminName() {
		Optional<User> optionalUser = userRepository.findByName("admin");
		user = optionalUser.orElseThrow(() -> new UsernameNotFoundException("該当のユーザは存在しません"));
		assertEquals(user.getName(), "admin");
		assertEquals(user.getPass(), "1qaz2wsx");
		assertEquals(user.isAdmin(), true);
		assertEquals(user.isEnable(), true);

		log.info("testFindByAdminNameの実行結果:{}", user.toString());

	}

	@Test
	@Sql(scripts = "classpath:/test-data-init.sql", config = @SqlConfig(encoding = "utf-8"))
	void testCreateUser() {

		user.setName("yamada");
		user.setPass("1qaz2wsx");
		user.setAdmin(false);
		user.setEnable(true);
		userRepository.save(user);

		Optional<User> optionalUser = userRepository.findByName("yamada");
		user = optionalUser.orElseThrow(() -> new UsernameNotFoundException("該当のユーザは存在しません"));
		assertEquals(user.getName(), "yamada");
		assertEquals(user.getPass(), "1qaz2wsx");
		assertEquals(user.isAdmin(), false);
		assertEquals(user.isEnable(), true);

		log.info("testCreateUserの実行結果:{}", user.toString());
	}

	@Test
	@Sql(scripts = "classpath:/test-data-init.sql", config = @SqlConfig(encoding = "utf-8"))
	@Sql(scripts = "classpath:/test-data-create-user.sql", config = @SqlConfig(encoding = "utf-8"))
	void testCreateUserNg() {

		user.setName("ueki");
		user.setPass("1qaz2wsx");
		user.setAdmin(false);
		user.setEnable(true);

		try {
			log.info("testCreateUserNgの実行");
			user = userRepository.save(user);
			log.info("testCreateUserNgの実行結果:{}", user.toString());

		} catch (DataAccessException e) {
			System.out.print("例外発生：");
			e.printStackTrace();
			return;
		}
		fail();
	}
	
	@Test
	@Sql(scripts = "classpath:/test-data-init.sql", config = @SqlConfig(encoding = "utf-8"))
	void testCountUser() {
		long userNum = userRepository.count();
		assertEquals(userNum, 0);
		
		user.setName("yamada");
		user.setPass("1qaz2wsx");
		user.setAdmin(false);
		user.setEnable(true);
		userRepository.save(user);
		userNum = userRepository.count();
		assertEquals(userNum, 1);

	}

}
