package curso.springboot.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class WebConfigSecurity extends WebSecurityConfigurerAdapter {

    @Autowired
    private ImplementacaoUserDetailsService implementacaoUserDetailsService;

    @Override //Configura as solicitacoes de acesso por Http
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()// Desabilita as configuracoes padrao de memoria.
                .authorizeRequests()// Permitir restringir acessos
                .antMatchers(HttpMethod.GET, "/").permitAll()//Qualquer usuario acessa
               // .antMatchers("/materialize/**").permitAll()
               // .antMatchers("/", "/signup", "/login", "/*.css", "/*.jpg").permitAll()
                .antMatchers(HttpMethod.GET, "/cadastroPessoa").hasAnyRole("admin")
                .anyRequest().authenticated()
                .and().formLogin().permitAll()// permite qualquer usuario
                .loginPage("/login")
                .defaultSuccessUrl("/cadastroPessoa")
                .failureUrl("/login?error=true")
                .and().logout().logoutSuccessUrl("/login")//Mapeia URL de logout e invalida usuario autenticado
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"));
    }

    @Override //Cria autenticacao do usuario com banco de dados ou em memoria
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        auth.userDetailsService(implementacaoUserDetailsService)
                .passwordEncoder(new BCryptPasswordEncoder());

//        auth.inMemoryAuthentication().passwordEncoder(new BCryptPasswordEncoder())
//                .withUser("Lucas")
//                .password("$2a$10$yWrQLPYoJJSDRWmNFvpvLO0396bieli2cR3hDKFDgJeV9yE3U2uhu")
//                .roles("ADMIN");
    }

    @Override //Ignora URL especificas
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/materialize/**");
    }
}
