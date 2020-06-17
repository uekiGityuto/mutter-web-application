package com.example.service;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.session.SessionAuthenticationException;

public class CustomAuthenticationFailurehandler implements AuthenticationFailureHandler {

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException, ServletException {

		String errReason = null;
		if (exception instanceof BadCredentialsException) {
			errReason = "ユーザ名またはパスワードが正しくありません";
		} else if (exception instanceof AccountExpiredException) {
			errReason = "このユーザは有効期限が切れています";
		} else if (exception instanceof CredentialsExpiredException) {
			errReason = "パスワードの有効期限が切れています";
		} else if (exception instanceof DisabledException) {
			errReason = "このユーザは無効になっています";
		} else if (exception instanceof LockedException) {
			errReason = "このユーザはロックされています";
		} else if (exception instanceof SessionAuthenticationException) {
			errReason = "このユーザはログイン中です。同一ユーザでのログインは出来ません";
		} else {
			errReason = "不明なエラーが発生しました";
		}

		if (errReason != null && errReason.length() > 0) {
			HttpSession session = request.getSession();
			session.removeAttribute("errReason");
			session.setAttribute("errReason", errReason);
		}
		
		RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
        redirectStrategy.sendRedirect(request, response, "/index?error");

	}

}
