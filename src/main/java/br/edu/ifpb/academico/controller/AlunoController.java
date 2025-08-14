package br.edu.ifpb.academico.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import br.edu.ifpb.academico.entity.Aluno;
import br.edu.ifpb.academico.service.AlunoService;

/** 
 * 
 * 
 *  @ModelAttribute é utilizada para indicar que um parâmetro de método deve 
 * ser preenchido automaticamente com os dados enviados em uma requisição
 * 
 *   @PathVariable é usada para indicar que um parâmetro de um método deve ser 
 * preenchido com o valor de uma variável presente na URL.
 * 
 *   @see .addAttribute() Ele é utilizado para adicionar atributos (dados) ao modelo,
 * que serão enviados para a view (página HTML).
 * 
 * 
 */
@Controller
@RequestMapping("/aluno")
public class AlunoController {

	@Autowired
	protected AlunoService alunoService;
	

	@GetMapping("/form")
	public String home(Model model) {
		model.addAttribute("aluno", new Aluno());
		return "cadastrarAluno";
	}
	

	/**
	 * Salva um novo aluno no sistema.
	 * 
	 * @param aluno O objeto Aluno a ser salvo.
	 * @param model O modelo para adicionar atributos à view.
	 * @return A view de cadastro de aluno com mensagem de sucesso ou erro.
	 */
	@PostMapping("save")
	public String saveAluno(@ModelAttribute Aluno aluno, Model model) {	
		// Verifica se o aluno já existe pelo número de matrícula
		if (alunoService.existsByMatricula(aluno.getMatricula())) {
			model.addAttribute("mensagemErro", "Aluno com matrícula " + aluno.getMatricula() + " já cadastrado.");
			return "cadastrarAluno";
		} else {			
			alunoService.save(aluno);
		}
		model.addAttribute("mensagemSucesso", "Aluno com matrícula " + aluno.getMatricula() + " cadastrado com sucesso!");
		return "cadastrarAluno";
	}


	/** Exibe o formulário de edição de um aluno existente.
	 * 
	 * @param id    O ID do aluno a ser editado.
	 * @param model O modelo para adicionar atributos à view.
	 * @return A view de edição de aluno.
	 */
	@GetMapping("/edit/{id}")
	public String editAluno(@PathVariable Long id, Model model) {
		Aluno aluno = alunoService.findById(id);
		model.addAttribute("aluno", aluno);
		return "editarAluno";
	}
	
	/**
	 * Lista todos os alunos cadastrados no sistema.
	 * 
	 * @param model O modelo para adicionar atributos à view.
	 * @return a pagina de listagem de alunos.
	 */
	@GetMapping("list")
	public String listAlunos(Model model) {
		model.addAttribute("alunos", alunoService.findAll());
		return "listarAluno";
	}

	/**
	 * Atualiza os dados de um aluno existente.
	 * @param aluno O objeto Aluno com os dados atualizados.
	 * @param model O modelo para adicionar atributos à view.
	 * @return A view de listagem de alunos.
	 */
	@PostMapping("/edit")
	public String editAlunoPost(@ModelAttribute Aluno aluno, Model model) {
		//// Verifica se a matricula alterado já existe em outro aluno, exeto ele mesmo
		if((!alunoService.findById(aluno.getId()).getMatricula().equals(aluno.getMatricula())) && alunoService.existsByMatricula(aluno.getMatricula())) {
			model.addAttribute("mensagemErro", "Aluno com matrícula " + aluno.getMatricula() + " já existe.");
			return listAlunos(model);
		}else {
		alunoService.save(aluno);
		}
		
		model.addAttribute("mensagemSucesso", "Aluno com matrícula " + aluno.getMatricula() + " atualizado com sucesso!");
		return listAlunos(model);

	}
	
	
	/**
	 * Exclui um aluno pelo seu ID.
	 * 
	 * @param id    O ID do aluno a ser excluído.
	 * @param model O modelo para adicionar atributos à view.
	 * @return A view de listagem de alunos com mensagem de sucesso ou erro.
	 */
	@GetMapping("/delete/{id}")
	public String deleteAluno(@PathVariable Long id, Model model) {
		alunoService.deleteById(id);
		model.addAttribute("mensagemSucesso",  "deletado com sucesso!");
		return listAlunos(model);
	}
	

	
}

// http://localhost:8080/aluno/save
// http://localhost:8080/aluno/form