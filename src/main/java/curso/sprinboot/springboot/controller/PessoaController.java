package curso.sprinboot.springboot.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import curso.sprinboot.springboot.model.Pessoa;
import curso.sprinboot.springboot.repository.PessoaRepository;

@Controller
public class PessoaController {
	
	@Autowired
	private PessoaRepository pessoaRep;

	@GetMapping("/cadastroPessoa")
	public ModelAndView inicio() {
		ModelAndView view = new ModelAndView("cadastro/cadastroPessoa");
		view.addObject("pessoaobj", new Pessoa());
		return view;
	}

	@GetMapping("**/salvarPessoa")
	public ModelAndView salvar(Pessoa pessoa) {
		pessoaRep.save(pessoa);
		ModelAndView view = new ModelAndView("cadastro/cadastroPessoa");
		Iterable<Pessoa> pessoasIt = pessoaRep.findAll();
		view.addObject("pessoas", pessoasIt);
		view.addObject("pessoaobj", new Pessoa());
		return view;
	}

	@GetMapping("/listaPessoas")
	public ModelAndView pessoas() {
		ModelAndView view = new ModelAndView("cadastro/cadastroPessoa");
		Iterable<Pessoa> pessoasIt = pessoaRep.findAll();
		view.addObject("pessoas", pessoasIt);
		view.addObject("pessoaobj", new Pessoa());
		return view;
	}
	
	@GetMapping("/editarPessoa/{idPessoa}")
	public ModelAndView editar(@PathVariable("idPessoa") Long idPessoa) {
		ModelAndView view = new ModelAndView("cadastro/cadastroPessoa");
		Optional<Pessoa> pessoa = pessoaRep.findById(idPessoa);
		view.addObject("pessoaobj", pessoa.get());
		return view;
	}

	@GetMapping("/excluirPessoa/{idPessoa}")
	public ModelAndView excluir(@PathVariable("idPessoa") Long idPessoa) {
		pessoaRep.deleteById(idPessoa);
		ModelAndView view = new ModelAndView("cadastro/cadastroPessoa");
		view.addObject("pessoas", pessoaRep.findAll());
		view.addObject("pessoaobj", new Pessoa());
		return view;
	}
}
