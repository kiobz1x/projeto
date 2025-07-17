import controller.LeitorController;
import model.Leitor;
import model.TipoLeitor;

import java.util.List;
import java.util.Scanner;

public class MainLeitor {
	 public static void main(String[] args) {
	        Scanner scanner = new Scanner(System.in);
	        LeitorController controller = new LeitorController();

	        int opcao;

	        do {
	            System.out.println("\n==== MENU LEITOR ====");
	            System.out.println("1 - Registrar novo leitor");
	            System.out.println("2 - Buscar leitor por matrícula");
	            System.out.println("0 - Sair");
	            System.out.print("Escolha: ");
	            opcao = scanner.nextInt();
	            scanner.nextLine(); // limpar buffer

	            switch (opcao) {
	                case 1 -> {
	                    System.out.print("Nome: ");
	                    String nome = scanner.nextLine();

	                    System.out.print("Matrícula: ");
	                    String matricula = scanner.nextLine();

	                    System.out.print("Telefone: ");
	                    String telefone = scanner.nextLine();

	                    System.out.print("Email: ");
	                    String email = scanner.nextLine();

	                    System.out.println("Tipo (1 - ALUNO | 2 - PROFESSOR | 3 - SERVIDOR): ");
	                    int tipo = scanner.nextInt();
	                    scanner.nextLine(); // limpar buffer
	                    TipoLeitor tipoLeitor = switch (tipo) {
	                    case 1 -> TipoLeitor.ALUNO;
	                    case 2 -> TipoLeitor.PROFESSOR;
	                    case 3 -> TipoLeitor.SERVIDOR;
	                    default -> {
	                        System.out.println("Tipo inválido. Leitor não cadastrado.");
	                        yield null;  // retorna null para tipo inválido
	                    }
	                };

	                if (tipoLeitor == null) {
	                    // Ignora cadastro e volta para o menu
	                    continue;  // esse continue funciona aqui, fora do switch
	                }

	                    Leitor leitor = new Leitor(nome, matricula, tipoLeitor, telefone, email);
	                    boolean sucesso = controller.cadastrarLeitor(leitor);
	                    System.out.println(sucesso ? "Leitor cadastrado com sucesso!" : "Erro: matrícula já existente.");
	                }

	                case 2 -> {
	                    System.out.print("Digite a matrícula para buscar: ");
	                    String busca = scanner.nextLine();
	                    Leitor l = controller.buscarLeitorPorMatricula(busca);
	                    if (l != null) {
	                        System.out.println("Leitor encontrado:");
	                        System.out.println(l);
	                    } else {
	                        System.out.println("Leitor não encontrado.");
	                    }
	                }

	                case 0 -> System.out.println("Encerrando...");
	                default -> System.out.println("Opção inválida.");
	            }

	        } while (opcao != 0);

	        scanner.close();
	    }
	}


