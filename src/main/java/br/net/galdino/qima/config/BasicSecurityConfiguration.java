package br.net.galdino.qima.config;

import static org.springframework.security.config.Customizer.withDefaults;
import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.jdbc.JdbcDaoImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class BasicSecurityConfiguration {
	
	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests(
			auth -> {
				auth
				.requestMatchers(antMatcher("/hello")).hasRole("REGULAR")
				.requestMatchers(antMatcher("/hello")).hasRole("SUPER")
				.requestMatchers(antMatcher("/h2/**")).hasRole("SUPER")
				.requestMatchers(antMatcher("/products/**")).hasRole("SUPER")
				.anyRequest().authenticated();
				}
		);
		
		http.sessionManagement(
			session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
		);
		
		//http.formLogin();
		http.httpBasic(withDefaults());
		
		http.csrf().disable();
		
		http.headers().frameOptions().sameOrigin();
		
		return http.build();
	}
	
	@Bean
	public UserDetailsService userDetailService(DataSource dataSource) {
		
		var user = User.withUsername("regular")
			.password("regular")
			.passwordEncoder(str -> passwordEncoder().encode(str))
			.roles("REGULAR")
			.build();
		
		var admin = User.withUsername("super")
				.password("super")
				.passwordEncoder(str -> passwordEncoder().encode(str))
				.roles("REGULAR", "SUPER")
				.build();
		
		var jdbcUserDetailsManager = new JdbcUserDetailsManager(dataSource);
		jdbcUserDetailsManager.createUser(user);
		jdbcUserDetailsManager.createUser(admin);

		return jdbcUserDetailsManager;
	}
	
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public DataSource datasource() {
		return new EmbeddedDatabaseBuilder()
				.setType(EmbeddedDatabaseType.H2)
				.addScript(JdbcDaoImpl.DEFAULT_USER_SCHEMA_DDL_LOCATION)
				.build();
	}

}
