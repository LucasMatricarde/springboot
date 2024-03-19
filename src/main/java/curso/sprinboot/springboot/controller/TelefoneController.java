package curso.sprinboot.springboot.controller;

import curso.sprinboot.springboot.model.Pessoa;
import curso.sprinboot.springboot.model.Telefone;
import curso.sprinboot.springboot.repository.PessoaRepository;
import curso.sprinboot.springboot.repository.TelefoneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@Controller
public class TelefoneController {

    @Autowired
    private TelefoneRepository telefoneRep;

    @GetMapping("/editarTelefone/{idTelefone}")
    public ModelAndView editar(@PathVariable("idTelefone") Long idTelefone) {
        ModelAndView view = new ModelAndView("cadastro/telefones");
        Optional<Telefone> telefone = telefoneRep.findById(idTelefone);
        view.addObject("telefoneobj", telefone.get());
        return view;
    }

    @GetMapping("/excluirTelefone/{idTelefone}")
    public ModelAndView excluir(@PathVariable("idTelefone") Long idTelefone) {
        telefoneRep.deleteById(idTelefone);
        ModelAndView view = new ModelAndView("cadastro/telefones");
        view.addObject("telefones", telefoneRep.findAll());
        view.addObject("telefoneobj", new Telefone());
        return view;
    }
}
