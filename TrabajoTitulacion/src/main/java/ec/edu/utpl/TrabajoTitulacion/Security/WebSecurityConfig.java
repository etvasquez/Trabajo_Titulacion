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
class mWebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        PasswordEncoder encoder =
                PasswordEncoderFactories.createDelegatingPasswordEncoder();
        auth
                .inMemoryAuthentication()
                .withUser("cpsarango")
                .password(encoder.encode("123"))
                .roles("USER")
                .and()
                .withUser("nopiedra")
                .password(encoder.encode("123"))
                .roles("USER")
                .and()
                .withUser("rlramirez")
                .password(encoder.encode("123"))
                .roles("USER")
                .and()
                .withUser("dijara")
                .password(encoder.encode("123"))
                .roles("USER")
                .and()
                .withUser("admin")
                .password(encoder.encode("admin"))
                .roles("ADMIN");
    }

    @Override
    public void configure(WebSecurity web){
        web.ignoring()
                .antMatchers("/static/**","/webjars/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/","/proyecto/{id}","/listaBusquedaPersona/{id}","/projectID/{id}","/files/{filename:.+}",
                        "/personID/{id}","/project/{id}","/person/{id}","/getNameByEmail/{email}","/comentarioresponse",
                        "/comentario","/personperson/{id}","/listaBusquedaProyecto/{id}","/listaBusquedaArea","/listaBusquedaTipoProyecto","/obtenerFoto/{id}",
                        "/projectArea/{id}","/projectTipo/{id}","/estadisticas",/*"/nosotros",*/"/repositorio","/usuario/{id}","/repositorio/{id}/{busqueda}")
                .permitAll()
                .antMatchers ("/proyectos","/editar_proyecto/{id}","/login").hasRole("USER")
                //.anyRequest().authenticated()
                .antMatchers ("/admin").hasRole("ADMIN")
                .anyRequest().authenticated()
                .and()
                .formLogin().loginPage("/login")
                .permitAll()
                .and()
                .logout().permitAll();
    }

}
