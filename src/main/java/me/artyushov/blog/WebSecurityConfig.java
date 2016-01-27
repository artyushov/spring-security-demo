package me.artyushov.blog;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.jdbc.JdbcDaoImpl;

/**
 * Author: artyushov
 * Date: 2016-01-13 00:26
 */
@Configuration
@EnableWebSecurity
@ComponentScan("me.artyushov.blog")
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        JdbcDaoImpl userDetailsService = new JdbcDaoImpl();
        userDetailsService.setJdbcTemplate(jdbcTemplate);
        userDetailsService.setUsersByUsernameQuery(
                "select name, password, 1 from User where name = ?");
        userDetailsService.setAuthoritiesByUsernameQuery(
                "select name, authority FROM UserAuthority where name = ?");

        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);

        auth.authenticationProvider(authenticationProvider);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        super.configure(http);
        http.authorizeRequests()
                .antMatchers("/admin/**").hasRole("ADMIN")
                .antMatchers("/onlyForUsers").authenticated()
                .antMatchers("/**").permitAll()
                .and()
                .formLogin();
    }
}
