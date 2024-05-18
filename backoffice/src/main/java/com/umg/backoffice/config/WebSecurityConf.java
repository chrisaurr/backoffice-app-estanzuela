package com.umg.backoffice.config;

import com.umg.backoffice.modelo.Autenticacion;
import com.umg.backoffice.modelo.entity.Constants;
import com.umg.backoffice.modelo.entity.Usuario;
import com.umg.backoffice.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class WebSecurityConf {

    @Autowired
    UsuarioRepository usuarioRepository;

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .authenticationProvider(authenticationProvider())
                .authorizeRequests((authorize) -> authorize
                        .requestMatchers("/login", "/register", "/signup","/css/**", "/js/**", "/images/**", "/webjars/**", "/favicon" +
                                ".ico").permitAll()
                        .anyRequest().authenticated()
                )
                .httpBasic(withDefaults())
                .formLogin(form -> form
                        .loginPage("/login")
                        .loginProcessingUrl("/loguearse")
                        .permitAll()
                )
                .logout((logout) -> logout.logoutSuccessUrl("/login?logout").permitAll());

        return http.build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().requestMatchers("/static/**", "/favicon.ico", "/assets/**", "/css/**", "/img" +
                "/**", "/js**", "/admin/**", "/webjars/**", "/templates/**");
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider authenticationStrategy = new DaoAuthenticationProvider();
        authenticationStrategy.setPasswordEncoder(passwordEncoder());
        authenticationStrategy.setUserDetailsService(userDetailsService());

        return authenticationStrategy;
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return (username -> {
            Usuario usuario = usuarioRepository.findByUsernameAndEstadoNot(username, Constants.ESTADO_ELIMINADO)
                    .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));
            Autenticacion autenticacion = new Autenticacion();
            autenticacion.setUsername(usuario.getUsername());
            autenticacion.setPassword(usuario.getPassword());
            GrantedAuthority authority = new SimpleGrantedAuthority("ADMIN");
            List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
            grantedAuthorities.add(authority);
            autenticacion.setGrantedAuthorities(grantedAuthorities);
            return autenticacion;
        });
    }
}
