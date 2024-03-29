package com.apap.tugasakhir.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	@Override
	protected void configure(HttpSecurity http) throws Exception{
		http
			.csrf().disable()
			.authorizeRequests()
			.antMatchers("/css/**").permitAll()
			.antMatchers("/js/**").permitAll()
			.antMatchers("/rawat-jalan/obat/**").permitAll()
			.antMatchers("/rawat-jalan/poli/jadwal/dokter-available").permitAll()
			.antMatchers("/rawat-jalan/poli/tambah").hasAnyAuthority("ADMINRAWATJALAN")
			.antMatchers("/rawat-jalan/poli").hasAnyAuthority("ADMINRAWATJALAN", "STAFRAWATJALAN")
			.antMatchers("/rawat-jalan/poli/ubah").hasAnyAuthority("ADMINRAWATJALAN")
			.antMatchers("/rawat-jalan/poli/jadwal/tambah").hasAnyAuthority("ADMINRAWATJALAN")
			.antMatchers("/rawat-jalan/poli/jadwal").hasAnyAuthority("ADMINRAWATJALAN", "STAFRAWATJALAN")
			.antMatchers("/rawat-jalan/poli/jadwal/ubah/**").hasAnyAuthority("ADMINRAWATJALAN")
			.antMatchers("/rawat-jalan/pasien/**").hasAnyAuthority("ADMINRAWATJALAN", "STAFRAWATJALAN")
			.anyRequest().authenticated()
			.and()
			.formLogin()
			.loginPage("/login")
			.permitAll()
			.and()
			.logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutSuccessUrl("/login")
			.permitAll();
	}
//	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception{
		auth.inMemoryAuthentication()
		.passwordEncoder(encoder())
		.withUser("cokicoki").password(encoder().encode("enaksekali"))
		.roles("ADMINRAWATJALAN");
	}
	@Bean
	public BCryptPasswordEncoder encoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception{
		auth.userDetailsService(userDetailsService).passwordEncoder(encoder());
	}
}
