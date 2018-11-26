package com.intdict.interactivedictionary.security;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private DataSource dataSource;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

      auth.jdbcAuthentication().dataSource(dataSource)
          .usersByUsernameQuery("select username, password, enabled"
              + " from user where username=?")
          .authoritiesByUsernameQuery("select username, authority "
              + "from role where username=?")
          .passwordEncoder(new BCryptPasswordEncoder());
    }
	
//	@Autowired
//	public void configureGlobalSecurity(AuthenticationManagerBuilder auth)
//			throws Exception {
//		auth.inMemoryAuthentication()
//			.passwordEncoder(NoOpPasswordEncoder.getInstance())
//			.withUser("in28Minutes")
//			.password("dummy")
//			.roles("USER", "ADMIN");
//	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
			.antMatchers("/login")
			.permitAll()
			.antMatchers("/registration")
			.permitAll()
			.antMatchers("/**")
			.access("hasRole('ROLE_USER')")
			.and()
			.formLogin()
			.loginPage("/login")
			.defaultSuccessUrl("/index")
			.usernameParameter("username")
			.passwordParameter("password");
//			.and().csrf().disable();
	}

}
