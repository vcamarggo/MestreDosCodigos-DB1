package br.com.seguranca.jwtotp.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    //Configura a segurança da aplicação
    //POST liberado em urls de login e h2 (BD para depuração)
    // apenas ADMIN pode fazer operação com semente
    // caso haja requisição de login, esta é tratada com o filtro correto
    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf().disable().authorizeRequests()
                .antMatchers(HttpMethod.POST, "/banking/acessar", "/h2-console/*").permitAll()
                .antMatchers(HttpMethod.GET, "/h2-console/*").permitAll()
                .antMatchers("/banking/semente/*").hasAuthority("ROLE_ADMIN")
                .antMatchers("/banking/semente").hasAuthority("ROLE_ADMIN")
                .anyRequest().authenticated()
                .and()
                // filtra requisições de login
                .addFilterBefore(new JWTLoginFilter("/banking/acessar", authenticationManager()),
                        UsernamePasswordAuthenticationFilter.class)
                // filtra outras requisições para verificar a presença do JWT no header
                .addFilterBefore(new JWTAuthenticationFilter(),
                        UsernamePasswordAuthenticationFilter.class)
                .headers().frameOptions().disable();
    }

    // "Cadastra" usuários default
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("111222").password("{noop}123456").authorities("ADMIN")
                .and()
                .withUser("999000").password("{noop}098765").authorities("USER");
    }
}