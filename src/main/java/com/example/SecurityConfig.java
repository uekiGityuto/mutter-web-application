package com.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;

import com.example.service.LoginUserDetailsService;

//アプリ起動時に読み込まれるコンフィグファイル
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired 
    LoginUserDetailsService userDetailsService;
	
	//アクセスフィルター（AuthenticationFilter）のカスタマイズ
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/webjars/**", "/css/**");
	}
	
	//アクセスフィルター（AuthenticationFilter）のカスタマイズ
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //ログインページを指定。
        //ログインページへのアクセスは全員許可する。
        http.formLogin()
            .loginPage("/index")
            .loginProcessingUrl("/authenticate")
            .usernameParameter("name")
            .passwordParameter("pass")
            .defaultSuccessUrl("/main", true)
            .failureUrl("/index?error")
            .permitAll();
		
        //ユーザ登録ページへのアクセスは全員許可する。それ以外は認証が必要とする。
        http.authorizeRequests()
        	//.antMatchers("/index").permitAll()
            .antMatchers("/user/register").permitAll()
            .antMatchers("/user/registerResult").permitAll()
            .antMatchers("/user/gotoTop").permitAll()
            .anyRequest().authenticated();
        
        //ログアウト後に遷移するページを指定
        http.logout().logoutSuccessUrl("/index");
    }

    //パスワードのエンコード方式を指定
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();//Pbkdf2PasswordEncoder()が推奨されているらしい
    }
/*
    //パスワードのエンコード方式を指定
    @Bean
    PasswordEncoder passwordEncoder2() {
        return new Pbkdf2PasswordEncoder();
    }
*/
 
    //認証処理（AuthenticationProvider）のカスタマイズ（今回は実装しなくても自動参照されるので問題なく動くが勉強のため実装）
    //（UserDetailsServiceやPasswordEncoderが2つ以上ある場合は自動参照できないので実装が必要となる）
    @Autowired
    void configureAuthenticationManager(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService)//DaoAuthenticationProviderが使用するUserDetailsServiceを指定
        	.passwordEncoder(passwordEncoder());//DaoAuthenticationProviderが使用するPasswordEncoderを指定
    }

}
