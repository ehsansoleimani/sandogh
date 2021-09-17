package com.sandogh.sandogh.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;


@Configuration
@EnableWebSecurity
public class profileSecurity extends WebSecurityConfigurerAdapter {
    @Autowired
    DataSource dataSource;

    @Autowired
    protected void configure(AuthenticationManagerBuilder auth) throws
            Exception {

        auth.jdbcAuthentication().dataSource(dataSource).usersByUsernameQuery(
                        "select user_name,password,active from table_user where user_name=?"
                ).authoritiesByUsernameQuery("select r.user,r.role from role r JOIN table_user on r.user=table_user.id and table_user.user_name=?"
                )
                .passwordEncoder(new BCryptPasswordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
        http.authorizeRequests()
                .antMatchers("/profile/**")
                .access("hasRole('ROLE_USER')")
                .and().formLogin().loginPage("/login")
                .successHandler(new AuthenticationSuccessHandler() {

                    @Override
                    public void onAuthenticationSuccess(HttpServletRequest request
                            , HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
                        redirectStrategy.sendRedirect(
                                request, response, "/profile");
                    }
                }).and().logout().logoutSuccessUrl("/login?logout=true").deleteCookies("JSESSIONID");

    }
}
