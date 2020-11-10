package com.microservices.demo.services.product.config;

import com.microservices.demo.services.config.CommonSecurityConfig;
import com.microservices.demo.services.security.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;

@Configuration
@Import({CommonSecurityConfig.class})
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private DaoAuthenticationProvider authenticationProvider;

    @Autowired
    private DataSource dataSource;

    @Autowired
    public PasswordEncoder encoder;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        auth.userDetailsService(userDetailsService)
                .passwordEncoder(encoder)
                .and()
                .authenticationProvider(authenticationProvider)
                .jdbcAuthentication()
                .dataSource(dataSource);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
                .antMatchers("/resources/**");
    }

    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/product/allProducts").hasAnyRole("USER", "ADMIN")
                .antMatchers("/product/createProduct*", "/product/createProduct/*",
                        "/product/createProduct/**").hasRole("ADMIN")
                .antMatchers("/product/deleteById*", "/product/deleteById/*",
                        "/product/deleteById/**").hasRole("ADMIN")
                .antMatchers("/product/findByProductId*", "/product/findByProductId/*",
                        "/product/findByProductId/**").hasRole("ADMIN")
                .anyRequest()
                .authenticated()
                .and()
                .httpBasic();
    }

}
