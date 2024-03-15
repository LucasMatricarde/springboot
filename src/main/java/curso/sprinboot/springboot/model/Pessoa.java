package curso.sprinboot.springboot.model;

import java.io.Serializable;
import java.util.List;

import jakarta.persistence.*;

@Entity
public class Pessoa implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pessoa_generator")
	@SequenceGenerator(name = "pessoa_generator", sequenceName = "pessoa_seq", allocationSize = 1)
	private Long id;
	
	private String nome;
	
	private String sobrenome;

	@OneToMany(mappedBy = "pessoa")
	private List<Telefone> telefones;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getSobrenome() {
		return sobrenome;
	}

	public void setSobrenome(String sobrenome) {
		this.sobrenome = sobrenome;
	}

	public List<Telefone> getTelefones() {
		return telefones;
	}

	public void setTelefones(List<Telefone> telefones) {
		this.telefones = telefones;
	}
}
