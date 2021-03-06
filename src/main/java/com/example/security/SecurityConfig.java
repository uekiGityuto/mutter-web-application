package com.example.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
//import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.security.web.session.HttpSessionEventPublisher;

import com.example.service.CustomAuthenticationFailurehandler;
import com.example.service.LoginUserDetailsService;

//アプリ起動時に読み込まれるコンフィグファイル
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	LoginUserDetailsService userDetailsService;

	// アクセスフィルター（AuthenticationFilter）のカスタマイズ
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring()
				.antMatchers("/webjars/**", "/css/**");
	}

	// アクセスフィルター（AuthenticationFilter）のカスタマイズ
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// ログインページを指定
		// ログインページへのアクセスは全員許可する
		http.formLogin()
				.loginPage("/index")
				.loginProcessingUrl("/authenticate")
				.usernameParameter("name").passwordParameter("pass")
				.defaultSuccessUrl("/main", true)
				.failureHandler(new CustomAuthenticationFailurehandler())// 呼ばないと"/index?error"にリダイレクトする(デフォルト)
				.permitAll();// 全てのユーザにログインページへのアクセスを許す

		// ユーザ登録ページへのアクセスは全員許可する。
		// それ以外は認証が必要とする
		// "/management/"以下はROLE_ADMINのユーザのみ認可する
		http.authorizeRequests()
				.antMatchers("/index**").permitAll()
				.antMatchers("/user/registration").permitAll()
				.antMatchers("/user/registerResult").permitAll()
				.antMatchers("/user/gotoTop").permitAll()
				.antMatchers("/management/**").hasRole("ADMIN")// 'ROLE_'はつけない
				.anyRequest().authenticated();

		// ログアウト後に遷移するページを指定
		http.logout()
				.logoutSuccessUrl("/index");

		// 二重ログインを禁止
		http.sessionManagement()
				.maximumSessions(1)
				//.maxSessionsPreventsLogin(true)//コメントアウトを外すと後にログインしたほうがログイン出来なくなる
				.expiredUrl("/index?expired");
		
		// 例外発生時の処理を定義(ただFORM認証中の例外はCustomAuthenticationFailurehandlerの定義に従う)
		http.exceptionHandling()
				.authenticationEntryPoint(authenticationEntryPoint());
		
	}

	// パスワードのエンコード方式を指定
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	/*
	 * //パスワードのエンコード方式を指定
	 * 
	 * @Bean PasswordEncoder passwordEncoder2() { return new
	 * Pbkdf2PasswordEncoder(); }
	 */
	
	// http.sessionManagement().maximumSessions(int)とした場合に、
	// 期限切れのセッションをクリーンアップするため処理
	// 特に、maxSessionsPreventsLogin(true)の場合はこの処理がないとログアウトしてもセッションが破棄されない
	@Bean
	public static ServletListenerRegistrationBean<HttpSessionEventPublisher> httpSessionEventPublisher() {
		return new ServletListenerRegistrationBean<HttpSessionEventPublisher>(new HttpSessionEventPublisher());
	}

	// maxSessionsPreventsLogin(false)でセッションタイムアウトした時の遷移先を指定
	// maxSessionsPreventsLogin(true)の場合はFORM認証時に例外が発生するので、
	// CustomAuthenticationFailurehandlerで実装した処理に従って遷移する
	@Bean
	AuthenticationEntryPoint authenticationEntryPoint() {
		return new SessionExpiredDetectingLoginUrlAuthenticationEntryPoint("/index");
	    }

	// 認証処理（AuthenticationProvider）のカスタマイズ（今回は実装しなくても自動参照されるので問題なく動くが勉強のため実装）
	// （UserDetailsServiceやPasswordEncoderが2つ以上ある場合は自動参照できないので実装が必要となる）
	@Autowired
	void configureAuthenticationManager(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService)// DaoAuthenticationProviderが使用するUserDetailsServiceを指定
				.passwordEncoder(passwordEncoder());// DaoAuthenticationProviderが使用するPasswordEncoderを指定
	}

}
