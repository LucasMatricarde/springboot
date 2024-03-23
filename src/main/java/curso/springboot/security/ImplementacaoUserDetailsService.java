package curso.springboot.security;

import curso.springboot.model.Usuario;
import curso.springboot.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class ImplementacaoUserDetailsService implements UserDetailsService {

    @Autowired
    private UsuarioRepository userRep;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        Usuario usuario = userRep.findUserByLogin(userName);
        if(usuario == null) {
            throw new UsernameNotFoundException("Usuário não foi encontrado");
        }
        return usuario;
    }
}
