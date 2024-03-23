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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class TelefoneController {

    @Autowired
    private TelefoneRepository telefoneRep;

    @Autowired
    private PessoaRepository pessoaRep;

    @PostMapping("**/salvarTelefonePessoa/{idPessoa}")
    public ModelAndView salvarTelefonePessoa(Telefone telefone, @PathVariable("idPessoa") Long idPessoa) {
        ModelAndView view = new ModelAndView("cadastro/telefones");
        Pessoa pessoa = pessoaRep.findById(idPessoa).get();
        if(telefone != null && (telefone.getNumero() == null || telefone.getNumero().isEmpty())
                || (telefone.getDdd() == null || telefone.getDdd().isEmpty()
                || (telefone.getTipo() == null || telefone.getTipo().isEmpty()))){
            List<String> msg = new ArrayList<>();
            if(telefone.getTipo().isEmpty()) {
                msg.add("Tipo Telefone deve ser informado");
            }
            if(telefone.getDdd().isEmpty()) {
                msg.add("DDD deve ser informado");
            }
            if(telefone.getNumero().isEmpty()) {
                msg.add("Numero deve ser informado");
            }
            view.addObject("msg", msg);
            view.addObject("pessoaobj", pessoa);
            view.addObject("telefoneobj", telefone);
            view.addObject("telefones", telefoneRep.geTelefones(idPessoa));
            return view;
        }

        telefone.setPessoa(pessoa);
        telefoneRep.save(telefone);
        view.addObject("pessoaobj", pessoa);
        view.addObject("telefoneobj", new Telefone());
        view.addObject("telefones", telefoneRep.geTelefones(idPessoa));
        return view;
    }

    @GetMapping("/editarTelefone/{idTelefone}")
    public ModelAndView editar(@PathVariable("idTelefone") Long idTelefone) {
        ModelAndView view = new ModelAndView("cadastro/telefones");
        Optional<Telefone> telefone = telefoneRep.findById(idTelefone);
        Optional<Pessoa> pessoa = pessoaRep.findById(telefone.get().getPessoa().getId());
        view.addObject("pessoaobj", pessoa.get());
        view.addObject("telefones", telefoneRep.geTelefones(pessoa.get().getId()));
        view.addObject("telefoneobj", telefone.get());
        return view;
    }

    @GetMapping("/excluirTelefone/{idTelefone}")
    public ModelAndView excluir(@PathVariable("idTelefone") Long idTelefone) {
        Pessoa pessoa = telefoneRep.findById(idTelefone).get().getPessoa();
        telefoneRep.deleteById(idTelefone);
        ModelAndView view = new ModelAndView("cadastro/telefones");
        view.addObject("pessoaobj", pessoa);
        view.addObject("telefones", telefoneRep.geTelefones(pessoa.getId()));
        view.addObject("telefoneobj", new Telefone());
        return view;
    }
}
