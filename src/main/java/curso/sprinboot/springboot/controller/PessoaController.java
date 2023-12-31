package curso.sprinboot.springboot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import curso.sprinboot.springboot.model.Pessoa;
import curso.sprinboot.springboot.repository.PessoaRepository;

@Controller
public class PessoaController {
	
	@Autowired
	private PessoaRepository pessoaRep;
	
	@RequestMapping(method = RequestMethod.GET, value = "/cadastroPessoa")
	public String inicio() {
		return "cadastro/cadastroPessoa";
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/salvarPessoa")
	public ModelAndView salvar(Pessoa pessoa) {
		pessoaRep.save(pessoa);
		
		ModelAndView view = new ModelAndView("cadastro/cadastroPessoa");
		Iterable<Pessoa> pessoasIt = pessoaRep.findAll();
		view.addObject("pessoas", pessoasIt);
		return view;
	}
	
	@RequestMapping(method = RequestMethod.GET, value="/listaPessoas")
	public ModelAndView pessoas() {
		ModelAndView view = new ModelAndView("cadastro/cadastroPessoa");
		Iterable<Pessoa> pessoasIt = pessoaRep.findAll();
		view.addObject("pessoas", pessoasIt);
		return view;
	}
}
