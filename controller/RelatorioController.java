package controller;

import java.io.FileOutputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.itextpdf.text.Document;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import dao.EmprestimoDAO;
import dao.LeitorDAO;
import model.Emprestimo;
import model.Leitor;

public class RelatorioController {
    private final EmprestimoDAO dao = new EmprestimoDAO();
    private final LeitorDAO leitorDAO = new LeitorDAO();

    // 📄 Relatório de empréstimos do mês atual
    public void gerarRelatorioEmprestimosDoMes() {
        List<Emprestimo> todos = dao.carregar();
        int mesAtual = LocalDate.now().getMonthValue();
        int anoAtual = LocalDate.now().getYear();

        List<Emprestimo> filtrados = todos.stream().filter(e -> e.getDataEmprestimo().getMonthValue() == mesAtual && e.getDataEmprestimo().getYear() == anoAtual).collect(Collectors.toList());

        if (filtrados.isEmpty()) {
            System.out.println("📭 Nenhum empréstimo registrado este mês.");
            return;
        }

        String nomeArquivo = "relatorio_emprestimos_" + mesAtual + "_" + anoAtual + ".pdf";

        try {
            Document doc = new Document();
            PdfWriter.getInstance(doc, new FileOutputStream(nomeArquivo));
            doc.open();

            doc.add(new Paragraph("📄 Relatório de Empréstimos - " + mesAtual + "/" + anoAtual,
                    FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16)));
            doc.add(new Paragraph(" "));

            PdfPTable tabela = new PdfPTable(4);
            tabela.addCell("Usuário");
            tabela.addCell("Obra");
            tabela.addCell("Data Empréstimo");
            tabela.addCell("Prev. Devolução");

            for (Emprestimo emp : filtrados) {
                Leitor leitor = leitorDAO.buscarPorMatricula(emp.getLeitorId());
                String nomeLeitor = (leitor != null) ? leitor.getNome() : "Desconhecido";

                tabela.addCell(nomeLeitor);
                tabela.addCell(emp.getObra().getTitulo());
                tabela.addCell(emp.getDataEmprestimo().toString());
                tabela.addCell(emp.getDataPrevistaDevolucao().toString());
            }

            doc.add(tabela);
            doc.close();

            System.out.println("✅ Relatório gerado: " + nomeArquivo);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("❌ Erro ao gerar relatório.");
        }
    }

    // 📄 Relatório de obras mais emprestadas
    public void gerarRelatorioObrasMaisEmprestadas() {
        List<Emprestimo> emprestimos = dao.carregar();

        if (emprestimos.isEmpty()) {
            System.out.println("📭 Nenhum empréstimo encontrado.");
            return;
        }

        // Contar empréstimos por título
        Map<String, Integer> contagem = new HashMap<>();
        for (Emprestimo e : emprestimos) {
            String titulo = e.getObra().getTitulo();
            contagem.put(titulo, contagem.getOrDefault(titulo, 0) + 1);
        }

        // Ordenar do mais emprestado ao menos
        List<Map.Entry<String, Integer>> ordenado = new ArrayList<>(contagem.entrySet());
        ordenado.sort(Map.Entry.comparingByValue(Comparator.reverseOrder()));

        String nomeArquivo = "relatorio_obras_mais_emprestadas.pdf";

        try {
            Document doc = new Document();
            PdfWriter.getInstance(doc, new FileOutputStream(nomeArquivo));
            doc.open();

            doc.add(new Paragraph("📚 Obras Mais Emprestadas",
                    FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16)));
            doc.add(new Paragraph(" ")); // espaço

            PdfPTable tabela = new PdfPTable(2);
            tabela.addCell("Título da Obra");
            tabela.addCell("Qtd. Empréstimos");

            for (Map.Entry<String, Integer> entry : ordenado) {
                tabela.addCell(entry.getKey());
                tabela.addCell(String.valueOf(entry.getValue()));
            }

            doc.add(tabela);
            doc.close();

            System.out.println("✅ Relatório gerado: " + nomeArquivo);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("❌ Erro ao gerar relatório.");
        }
    }
    public void gerarRelatorioUsuariosComMaisAtrasos() {
        List<Emprestimo> emprestimos = dao.carregar();

        // Mapa para contar atrasos por usuário
        Map<String, Integer> contagemAtrasos = new HashMap<>();

        for (Emprestimo e : emprestimos) {
            if (e.getDataDevolucao() != null && e.isAtrasado()) {
            	Leitor leitor = leitorDAO.buscarPorMatricula(e.getLeitorId());
            	String nome = (leitor != null) ? leitor.getNome() : "Desconhecido";
            	contagemAtrasos.put(nome, contagemAtrasos.getOrDefault(nome, 0) + 1);
            }
        }

        if (contagemAtrasos.isEmpty()) {
            System.out.println("📭 Nenhum atraso registrado.");
            return;
        }

        // Ordenar do maior número de atrasos ao menor
        List<Map.Entry<String, Integer>> ordenado = new ArrayList<>(contagemAtrasos.entrySet());
        ordenado.sort(Map.Entry.comparingByValue(Comparator.reverseOrder()));

        String nomeArquivo = "relatorio_usuarios_com_mais_atrasos.pdf";

        try {
            Document doc = new Document();
            PdfWriter.getInstance(doc, new FileOutputStream(nomeArquivo));
            doc.open();

            doc.add(new Paragraph("⏰ Usuários com Mais Atrasos",
                    FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16)));
            doc.add(new Paragraph(" ")); // espaço

            PdfPTable tabela = new PdfPTable(2);
            tabela.addCell("Usuário");
            tabela.addCell("Qtd. Atrasos");

            for (Map.Entry<String, Integer> entry : ordenado) {
                tabela.addCell(entry.getKey());
                tabela.addCell(String.valueOf(entry.getValue()));
            }

            doc.add(tabela);
            doc.close();

            System.out.println("✅ Relatório gerado: " + nomeArquivo);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("❌ Erro ao gerar relatório.");
        }
    }

}
