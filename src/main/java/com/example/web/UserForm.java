package com.example.web;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class UserForm {

	@NotNull
	@Size(min = 1, max = 64)
	private String name;
	@NotNull
	@Size(min = 8, max = 64)
	@Pattern(regexp = "[a-zA-Z0-9]*")
	@Pattern(regexp = ".*[a-zA-Z].*")
	@Pattern(regexp = ".*[0-9].*")
	private String pass;
}
