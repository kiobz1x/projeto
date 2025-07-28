import controller.EmprestimoController;
import controller.MultaController;
import exception.DevolucaoInvalidaException;
import exception.EmprestimoJaRealizadoException;
import exception.LeitorNaoEncontradoException;
import exception.ObraNaoDisponivelException;
import exception.ObraNaoEncontradaException;

import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        EmprestimoController emprestimoController = new EmprestimoController();
        MultaController multaController = new MultaController();

        try {
            // 1. Empréstimo válido feito há 4 dias
            emprestimoController.realizarEmprestimoComData("6666", "1111", LocalDate.now().minusDays(4));
            System.out.println("✅ Empréstimo realizado com sucesso.");

            // 2. Tentativa de duplicar empréstimo da mesma obra para o mesmo leitor
            emprestimoController.realizarEmprestimoComData("6666", "1111", LocalDate.now());
        } catch (EmprestimoJaRealizadoException e) {
            System.out.println("❌ Erro: Empréstimo já realizado - " + e.getMessage());
        } catch (ObraNaoDisponivelException e) {
            System.out.println("❌ Erro: Obra indisponível - " + e.getMessage());
        } catch (ObraNaoEncontradaException | LeitorNaoEncontradoException e) {
            System.out.println("❌ Erro: Leitor ou obra não encontrada - " + ((Throwable) e).getMessage());
        }

        try {
            // 3. Devolução feita hoje (considera atraso se for obra com prazo < 4 dias)
            emprestimoController.realizarDevolucaoComData("1111", LocalDate.now());
            System.out.println("✅ Devolução realizada.");
        } catch (DevolucaoInvalidaException e) {
            System.out.println("❌ Erro: Devolução inválida - " + e.getMessage());
        }

        // 4. Gerar multas automaticamente após devolução
        multaController.gerarMultasAutomaticamente();
        System.out.println("🔔 Multas atualizadas.");

        // 5. Teste com obra inexistente
        try {
            emprestimoController.realizarEmprestimoComData("9999", "9999", LocalDate.now());
        } catch (Exception e) {
            System.out.println("❌ Erro esperado com obra inexistente: " + e.getMessage());
        }
    }
}
