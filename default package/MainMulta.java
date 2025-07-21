import controller.EmprestimoController;
import controller.MultaController;
import model.Livro;
import model.Obra;
import model.Usuario;
import model.TipoUsuario;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

public class MainMulta {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        EmprestimoController empController = new EmprestimoController();
        MultaController multaController = new MultaController();

        System.out.println("=== Sistema de Empréstimos ===");

        // Cadastrar usuário
        System.out.print("Digite nome do usuário: ");
        String nome = sc.nextLine();

        System.out.print("Digite matrícula do usuário: ");
        String matricula = sc.nextLine();

        System.out.println("Tipos: 1-ADMINISTRADOR, 2-BIBLIOTECARIO, 3-ESTAGIARIO");
        int tipoNum = Integer.parseInt(sc.nextLine());
        TipoUsuario tipo = switch (tipoNum) {
            case 1 -> TipoUsuario.ADMINISTRADOR;
            case 2 -> TipoUsuario.BIBLIOTECARIO;
            case 3 -> TipoUsuario.ESTAGIARIO;
            default -> TipoUsuario.ESTAGIARIO;
        };

        System.out.print("Digite telefone: ");
        String telefone = sc.nextLine();

        System.out.print("Digite email: ");
        String email = sc.nextLine();

        Usuario usuario = new Usuario(nome, matricula, tipo, telefone, email);

        // Cadastrar obra (livro simples)
        System.out.print("Digite código da obra: ");
        String codigo = sc.nextLine();

        System.out.print("Digite título da obra: ");
        String titulo = sc.nextLine();

        System.out.print("Digite autor da obra: ");
        String autor = sc.nextLine();

        System.out.print("Digite ano de publicação: ");
        int ano = Integer.parseInt(sc.nextLine());

        Obra livro = new Livro(codigo, titulo, autor, ano);

        // Fazer empréstimo
        boolean emprestado = empController.realizarEmprestimo(usuario, livro);
        if (!emprestado) {
            System.out.println("Não foi possível realizar o empréstimo.");
            return;
        }

        // Devolução com data digitada
        System.out.print("Digite a data de devolução (AAAA-MM-DD): ");
        String dataStr = sc.nextLine().replace(" ", "-");
        LocalDate dataDevolucao = LocalDate.parse(dataStr, DateTimeFormatter.ISO_LOCAL_DATE);

        // Atualizar devolução no empréstimo
        empController.realizarDevolucao(livro);

        // Encontrar o empréstimo para gerar multa
        var emprestimo = empController.encontrarEmprestimoPorCodigoObra(codigo);
        if (emprestimo != null) {
            emprestimo.devolver(dataDevolucao);
            if (!emprestimo.isAtrasado()) {
                System.out.println("Devolução no prazo, sem multa.");
            } else {
                multaController.gerarMultasAutomaticamente(List.of(emprestimo));
                System.out.println("Devolução atrasada. Multa gerada.");
            }
        } else {
            System.out.println("Empréstimo não encontrado.");
        }

        // Listar multas pendentes
        System.out.println("\nMultas pendentes:");
        List<model.Multa> multasPendentes = multaController.listarMultasPendentes();
        for (model.Multa multa : multasPendentes) {
            System.out.println(multa);
        }

        sc.close();
    }
}
