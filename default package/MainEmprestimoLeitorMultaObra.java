import controller.EmprestimoController;
import controller.MultaController;
import model.Emprestimo;
import model.Leitor;
import model.Obra;

import java.util.List;
import java.util.Scanner;

public class MainEmprestimoLeitorMultaObra {
    public static void main(String[] args) {
        EmprestimoController emprestimoController = new EmprestimoController();
        MultaController multaController = new MultaController();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n--- Sistema de Empréstimos ---");
            System.out.println("1. Realizar empréstimo");
            System.out.println("2. Registrar devolução");
            System.out.println("3. Listar todos os empréstimos");
            System.out.println("4. Gerar multas automaticamente");
            System.out.println("0. Sair");
            System.out.print("Escolha uma opção: ");
            String opcao = scanner.nextLine();

            switch (opcao) {
                case "1":
                    System.out.print("📚 Código da obra: ");
                    String obraCodigo = scanner.nextLine();
                    System.out.print("🧾 Matrícula do leitor: ");
                    String leitorId = scanner.nextLine();
                    emprestimoController.realizarEmprestimo(leitorId, obraCodigo);
                    break;

                case "2":
                    System.out.print("🔁 Código da obra para devolução: ");
                    String codigo = scanner.nextLine();
                    emprestimoController.realizarDevolucao(codigo);
                    break;

                case "3":
                    List<Emprestimo> lista = emprestimoController.listarEmprestimos();
                    if (lista.isEmpty()) {
                        System.out.println("📭 Nenhum empréstimo registrado.");
                    } else {
                        System.out.println("\n📋 Lista de empréstimos:");
                        for (Emprestimo e : lista) {
                            Leitor leitor = emprestimoController.getLeitorDoEmprestimo(e);
                            Obra obra = emprestimoController.getObraDoEmprestimo(e);
                            System.out.printf("ID: %s | Leitor: %s | Obra: %s | Empréstimo: %s | Prev. Devolução: %s | Devolução: %s\n",
                                    e.getId(),
                                    (leitor != null ? leitor.getNome() : "Desconhecido"),
                                    (obra != null ? obra.getTitulo() : "Desconhecida"),
                                    e.getDataEmprestimo(),
                                    e.getDataPrevistaDevolucao(),
                                    (e.getDataDevolucao() != null ? e.getDataDevolucao() : "Pendente")
                            );
                        }
                    }
                    break;

                case "4":
                    multaController.gerarMultasAutomaticamente();
                    break;

                case "0":
                    System.out.println("Encerrando o sistema...");
                    return;

                default:
                    System.out.println("❌ Opção inválida.");
            }
        }
    }
}
