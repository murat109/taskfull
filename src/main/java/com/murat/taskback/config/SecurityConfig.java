package com.murat.taskback.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

	@Bean
	public InMemoryUserDetailsManager userDetailsService() {
		UserDetails admin = User.withUsername("admin")
				.password(encoder().encode("editor")).roles("ALLOW_EDIT") // to obtain ROLE_ALLOW_EDIT
				.build();
		UserDetails user = User.withUsername("user")
				.password(encoder().encode("viewer")).roles("VIEW_ONLY") // to obtain ROLE_VIEW_ONLY
				.build();
		return new InMemoryUserDetailsManager(admin, user);
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.csrf().disable().authorizeRequests()
				.requestMatchers("/api/cities").permitAll() // any user can fetch from API
				.requestMatchers("/api/search").permitAll() // any user can fetch from API
				.requestMatchers(HttpMethod.PUT, "/api/{id}").hasRole("ALLOW_EDIT") // Spring Security ROLE_ALLOW_EDIT restriction 
				.and().formLogin().and().httpBasic().and().logout();
		return http.build();
	}

	@Bean
	public PasswordEncoder encoder() {
		return new BCryptPasswordEncoder();
	}

}
