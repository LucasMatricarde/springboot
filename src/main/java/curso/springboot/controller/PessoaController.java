package curso.springboot.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import curso.springboot.model.Telefone;
import curso.springboot.repository.TelefoneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import curso.springboot.model.Pessoa;
import curso.springboot.repository.PessoaRepository;

import javax.validation.Valid;

@Controller
public class PessoaController {
	
	@Autowired
	private PessoaRepository pessoaRep;
	@Autowired
	private TelefoneRepository telefoneRep;

	@GetMapping("/index")
	public ModelAndView index() {
		ModelAndView view = new ModelAndView("index");
		return view;
	}

	@GetMapping("/cadastroPessoa")
	public ModelAndView cadastroPessoa() {
		ModelAndView view = new ModelAndView("cadastro/cadastroPessoa");
		Iterable<Pessoa> pessoasIt = pessoaRep.findAll();
		view.addObject("pessoas", pessoasIt);
		view.addObject("pessoaobj", new Pessoa());
		return view;
	}

	@PostMapping ("**/salvarPessoa")
	public ModelAndView salvar(@Valid Pessoa pessoa, BindingResult bindingResult) {
		ModelAndView view = new ModelAndView("cadastro/cadastroPessoa");
		Iterable<Pessoa> pessoasIt = pessoaRep.findAll();
		view.addObject("pessoas", pessoasIt);
		view.addObject("pessoaobj", new Pessoa());
		if(bindingResult.hasErrors()) {
			List<String> msg = new ArrayList<>();
			for (ObjectError objectError : bindingResult.getAllErrors()){
				msg.add(objectError.getDefaultMessage());// vem das anotações do objeto Pessoa
			}
			view.addObject("msg", msg);
			return view;
		}
		pessoaRep.save(pessoa);
		pessoasIt = pessoaRep.findAll();
		view.addObject("pessoas", pessoasIt);
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
	@PostMapping("**/pesquisarPessoa")
	public ModelAndView pesquisar(@RequestParam("nomePesquisa") String nomePesquisa){
		ModelAndView view = new ModelAndView("cadastro/cadastroPessoa");
		view.addObject("pessoas", pessoaRep.findPessoaByName(nomePesquisa));
		view.addObject("pessoaobj", new Pessoa());
		return view;
	}

	@GetMapping ("/telefones/{idPessoa}")
	public ModelAndView telefones(@PathVariable("idPessoa") Long idPessoa){
		ModelAndView view = new ModelAndView("cadastro/telefones");
		Optional<Pessoa> pessoa = pessoaRep.findById(idPessoa);
		view.addObject("pessoaobj", pessoa.get());
		view.addObject("telefoneobj", new Telefone());
		view.addObject("telefones", telefoneRep.geTelefones(idPessoa));
		return view;
	}
}
