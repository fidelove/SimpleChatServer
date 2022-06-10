package com.smart.simplechat.security.config;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
@SuppressWarnings("deprecation")
public class ChatRoomWebSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {

	@Bean
	public PasswordEncoder delegatingPasswordEncoder() {
		Map<String, PasswordEncoder> encoders = new HashMap<String, PasswordEncoder>();
		encoders.put("noop", NoOpPasswordEncoder.getInstance());
		encoders.put("bcrypt", new BCryptPasswordEncoder());
		encoders.put("scrypt", new SCryptPasswordEncoder());

		DelegatingPasswordEncoder passworEncoder = new DelegatingPasswordEncoder("bcrypt", encoders);
		passworEncoder.setDefaultPasswordEncoderForMatches(NoOpPasswordEncoder.getInstance());

		return passworEncoder;
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.cors().and().csrf().disable().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.ALWAYS).and()
				.authorizeRequests().anyRequest().authenticated().and().httpBasic().and().logout()
				.logoutUrl("/api/v1/logout").deleteCookies("JSESSIONID").invalidateHttpSession(true);
	}

	@Bean
	CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();
		configuration.setAllowedOrigins(Arrays.asList("*"));
		configuration.setAllowedMethods(Arrays.asList("*"));
		configuration.setAllowedHeaders(Arrays.asList("*"));
		configuration.setAllowCredentials(true);
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}
}