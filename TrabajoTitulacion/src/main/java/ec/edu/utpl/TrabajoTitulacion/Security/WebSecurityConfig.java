package ec.edu.utpl.TrabajoTitulacion.Security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        PasswordEncoder encoder =
                PasswordEncoderFactories.createDelegatingPasswordEncoder();
        auth
                .inMemoryAuthentication()
                .withUser("user")
                .password(encoder.encode("password"))
                .roles("USER")
                .and()
                .withUser("admin")
                .password(encoder.encode("admin"))
                .roles("USER", "ADMIN");
    }

    @Override
    public void configure(WebSecurity web){
        web.ignoring()
                .antMatchers("/static/**","/webjars/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/","/comentario/{id}","/listaBusquedaPersona/{id}","/projectID/{id}",
                        "/personID/{id}","/project/{id}","/person/{id}","/getNameByEmail/{email}","/comentarioresponse",
                        "/comentario","/personperson/{id}","/listaBusquedaProyecto/{id}","/listaBusquedaArea","/listaBusquedaTipoProyecto",
                        "/projectArea/{id}","/projectTipo/{id}","/estadisticas","/nosotros","/repositorio")
                .permitAll()
                .antMatchers ("/proyectos").authenticated ()
                .anyRequest().authenticated()
                .and()
                .formLogin().loginPage("/login")
                .permitAll()
                .and()
                .logout().permitAll();
    }

}
