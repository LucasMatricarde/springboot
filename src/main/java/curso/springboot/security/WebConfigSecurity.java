package curso.springboot.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class WebConfigSecurity extends WebSecurityConfigurerAdapter {

    @Override //Configura as solicitacoes de acesso por Http
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()// Desabilita as configuracoes padrao de memoria.
                .authorizeRequests()// Permitir restringir acessos
                .antMatchers(HttpMethod.GET, "/").permitAll()//Qualquer usuario acessa
                .anyRequest().authenticated()
                .and().formLogin().permitAll()// permite qualquer usuario
                .and().logout()//Mapeia URL de logout e invalida usuario autenticado
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"));
    }

    @Override //Cria autenticacao do usuario com banco de dados ou em memoria
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication().passwordEncoder(NoOpPasswordEncoder.getInstance())
                .withUser("Lucas")
                .password("123")
                .roles("ADMIN");
    }

    @Override //Ignora URL especificas
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/materialize/**");
    }
}
