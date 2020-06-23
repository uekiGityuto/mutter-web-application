package com.example.web;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest(properties = { "spring.datasource.url:jdbc:h2:mem:testdb;DB_CLOSE_ON_EXIT=FALSE;" })
@AutoConfigureMockMvc
class UserControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@Test
	public void testMoveRegisterView() throws Exception {
		mockMvc.perform(get("/user/registration"))
			.andExpect(status().isOk())
			.andExpect(view().name("registration"));
	}

	//POST,PUT,DELETE,PATCHの場合はcsrfトークンを付与しないと403エラーになる
	//csrfトークンを付与するためにはwith(csrf())メソッドを使用する
	//with(csrf())メソッドを使用するためには、pom.xmlにorg.springframework.security:spring-security-testを記載する
	@Test
	public void testRegisterUserNg() throws Exception {
		mockMvc.perform(post("/user/registerResult").with(csrf()).param("name", "admin").param("pass", ""))
		 	.andExpect(model().hasErrors())
			.andExpect(view().name("registration"));
	}
	
	@Test
	@Sql(scripts = "classpath:/test-data-init.sql", config = @SqlConfig(encoding = "utf-8"))
	public void testRegisterUser() throws Exception {
		mockMvc.perform(post("/user/registerResult").with(csrf()).param("name", "admin").param("pass", "1qaz2wsx"))
			.andExpect(model().hasNoErrors())
			.andExpect(view().name("registerResult"));
	}
	
	@Test
	@Sql(scripts = "classpath:/test-data-init.sql", config = @SqlConfig(encoding = "utf-8"))
	@Sql(scripts = "classpath:/test-data-create-admin.sql", config = @SqlConfig(encoding = "utf-8"))
	public void testRegisterUserDuplicationNg() throws Exception {
		mockMvc.perform(post("/user/registerResult").with(csrf()).param("name", "admin").param("pass", "1qaz2wsx"))
			.andExpect(model().hasErrors())
			.andExpect(view().name("registration"));
	}

}
