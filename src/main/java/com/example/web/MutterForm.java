package com.example.web;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class MutterForm {
	
	/*
	@NotNull
	@Size(min = 1, max = 64)
	private String userName;
	*/
	@NotNull
	@Size(min = 1, max = 140)
	private String text;
}
