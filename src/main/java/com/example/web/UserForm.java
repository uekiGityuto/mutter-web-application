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
	@Pattern(regexp = "[a-zA-Z0-9]*", message = "英数字で入力してください")
	@Pattern(regexp = ".*[a-zA-Z].*", message = "英字を含めてください")
	@Pattern(regexp = ".*[0-9].*", message = "数字を含めてください")
	private String pass;
}
