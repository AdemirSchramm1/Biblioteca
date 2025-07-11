package main;

import java.util.InputMismatchException;
import java.util.Scanner;

import dao.FuncionarioDAO;
import dao.LivroDAO;
import dao.UsuarioDAO;
import modelos.Funcionario;
import modelos.Livro;
import modelos.Usuario;

public class Biblioteca{
	static Scanner scanner = new Scanner(System.in);

	public static void main(String[] args) {
			 Funcionario funcionario = new Funcionario();
		        System.out.print("Nome do funcionário: ");
		        funcionario.setNomeFuncionario(scanner.nextLine());

		        System.out.print("Senha: ");
		        funcionario.setSenha(scanner.nextLine());

		
	        if (!FuncionarioDAO.autenticadorFuncionario(funcionario)) {
	            System.out.println("Encerrando o sistema.");
	            return;
	        }else {

		
		while (true) {
			System.out.println("Cadastrar usuario- 1");
			System.out.println("Listar livros - 2");
			System.out.println("Pesquisar livro - 3");
			System.out.println("Listar livros do usuario - 4");
			System.out.println("Adicionar novo livro - 5");
			System.out.println("Alterar livro - 6");
			System.out.println("Deletar livro - 7");
			System.out.println("Liberar livro para o usuario - 8");
			System.out.println("Devolver livro do usuario - 9");
			System.out.println("Desligar sistema biblioteca- 0");
			try {
			int opcao = scanner.nextInt();
			scanner.nextLine();
			switch (opcao) {
			case 0:
				System.out.println("Desligando...");
				return;
			case 1:
				cadastrarUsuario();
				break;
			case 2:
				listarLivro();
				break;
			case 3: 
				PesquisarLivro();
				break;
			case 4:
				listarLivroUsuario();
				break;
			case 5:
				adicionarLivro();
				break;
			case 6:
				alterarLivro();
				break;
			case 7:
				deletarLivro();
				break;
			case 8:
				liberarLivro();
				break;
			case 9:
				devolverLivro();
				break;
			}
		}catch(InputMismatchException e) {
			System.out.println("Você deve digitar um valor valido!");
			scanner.nextLine();
		}
		}
	   }
		
}

	// Usuario
	public static void cadastrarUsuario() {
		Usuario usuarioCadastrar = new Usuario();

		System.out.print("Digite o nome do Usuario: ");
		usuarioCadastrar.setNome(scanner.nextLine());
		
		System.out.print("Digite o cpf do Usuario: ");
		usuarioCadastrar.setCpf(scanner.next());
		
		UsuarioDAO.cadastrar(usuarioCadastrar);
	}

	public static void listarLivroUsuario() {
		Usuario usuarioListarAlugados = new Usuario();
		System.out.print("Digite o cpf do usuario: ");
		usuarioListarAlugados.setCpf(scanner.next());

		UsuarioDAO.selectLivroUsuario(usuarioListarAlugados);
	}

	// Livro
	public static void listarLivro() {
		Livro livroPesquisar = new Livro();
		LivroDAO.select(livroPesquisar);
	}

	public static void adicionarLivro() {
		Livro livroCadastrar = new Livro();

		System.out.print("Digite o autor: ");
		livroCadastrar.setAutor(scanner.nextLine());

		System.out.print("Digite o titulo: ");
		livroCadastrar.setTitulo(scanner.nextLine());

		System.out.print("Digite a data do seu lançamento: ");
		livroCadastrar.setData_lancamento(scanner.nextLine());

		LivroDAO.insert(livroCadastrar);
	}

	public static void alterarLivro() {
		Livro livroAlterar = new Livro();

		System.out.print("Digite a isbn do livro que deseja alterar: ");
		livroAlterar.setIsbn(scanner.nextInt());

		System.out.print("Digite o novo autor: ");
		livroAlterar.setAutor(scanner.nextLine());

		System.out.print("Digite o novo titulo: ");
		livroAlterar.setTitulo(scanner.nextLine());

		System.out.print("Digite a nova data do seu lançamento: ");
		livroAlterar.setData_lancamento(scanner.nextLine());

		LivroDAO.update(livroAlterar);
	}

	public static void deletarLivro() {
		Livro livroDeletar = new Livro();

		System.out.print("Digite a isbn do livro que deseja excluir: ");
		livroDeletar.setIsbn(scanner.nextInt());

		LivroDAO.delete(livroDeletar);
	}

	public static void devolverLivro() {
		Livro livroDevolver = new Livro();
		Usuario usuarioDevolver = new Usuario();
		System.out.print("Digite o cpf do usuario: ");
		usuarioDevolver.setCpf(scanner.next());
		
		System.out.print("Digite a isbn do livro que deseja devolver:");
		livroDevolver.setIsbn(scanner.nextInt());
		
		LivroDAO.devolverLivro(usuarioDevolver,livroDevolver);
		}

	public static void liberarLivro() {
		Usuario usuarioLiberar = new Usuario();
		Livro livroLiberar = new Livro();

		System.out.print("Digite o cpf do usuario: ");
		usuarioLiberar.setCpf(scanner.next());

		System.out.print("Digite a isbn do livro que deseja liberar: ");
		livroLiberar.setIsbn(scanner.nextInt());

		LivroDAO.liberarLivro(livroLiberar, usuarioLiberar);
	}
	
	public static void PesquisarLivro() {
		Livro livroLiberar = new Livro();
		System.out.print("Digite o Titulo do livro: ");
		livroLiberar.setTitulo(scanner.next());
		LivroDAO.selectLivroEspecifico(livroLiberar);
	}
}
