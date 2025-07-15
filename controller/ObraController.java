package controller;

import dao.ObraDAO;
import model.*;

import java.util.ArrayList;
import java.util.List;

public class ObraController {
    private final ObraDAO dao;
    private List<Obra> obras;

    public ObraController() {
        this.dao = new ObraDAO();
        this.obras = dao.carregar();
    }

    public List<Obra> buscarPorTitulo(String titulo) {
        List<Obra> resultado = new ArrayList<>();
        for (Obra o : obras) {
            if (o.getTitulo().toLowerCase().contains(titulo.toLowerCase())) {
                resultado.add(o);
            }
        }
        return resultado;
    }

    public List<Obra> buscarPorAutor(String autor) {
        List<Obra> resultado = new ArrayList<>();
        for (Obra o : obras) {
            if (o.getAutor().toLowerCase().contains(autor.toLowerCase())) {
                resultado.add(o);
            }
        }
        return resultado;
    }

    public Obra buscarPorCodigo(String codigo) {
        for (Obra o : obras) {
            if (o.getCodigo().equalsIgnoreCase(codigo)) {
                return o;
            }
        }
        return null;
    }

    public List<Obra> filtrarPorTipo(String tipo) {
        List<Obra> resultado = new ArrayList<>();
        for (Obra o : obras) {
            if (o.getClass().getSimpleName().equalsIgnoreCase(tipo)) {
                resultado.add(o);
            }
        }
        return resultado;
    }

    public List<Obra> filtrarPorStatus(boolean emprestado) {
        List<Obra> resultado = new ArrayList<>();
        for (Obra o : obras) {
            if (o.isEmprestado() == emprestado) {
                resultado.add(o);
            }
        }
        return resultado;
    }

    public void exibirLista(List<Obra> lista) {
        if (lista.isEmpty()) {
            System.out.println("📭 Nenhuma obra encontrada.");
        } else {
            for (Obra o : lista) {
                System.out.println(o);
            }
        }
    }

    public void listarTodasObras() {
        exibirLista(obras);
    }

    public boolean adicionarObra(String codigo, String titulo, String autor, int ano, String tipo) {
        for (Obra o : obras) {
            if (o.getCodigo().equalsIgnoreCase(codigo)) {
                return false; // código duplicado
            }
        }

        Obra nova = switch (tipo.toLowerCase()) {
            case "livro" -> new Livro(codigo, titulo, autor, ano);
            case "revista" -> new Revista(codigo, titulo, autor, ano);
            case "artigo" -> new Artigo(codigo, titulo, autor, ano);
            default -> null;
        };

        if (nova == null) return false;

        obras.add(nova);
        dao.salvar(obras);
        return true;
    }
    public void salvarLista() {
        dao.salvar(obras); // Salva a lista atual de obras com os novos estados (emprestado ou não)
    }


    // ✅ Método para fornecer a lista completa de obras (cópia)
    public List<Obra> getObras() {
        return new ArrayList<>(obras);
    }
}
