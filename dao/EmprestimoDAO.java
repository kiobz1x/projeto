package dao;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import model.*;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class EmprestimoDAO implements Persistivel<Emprestimo> {
    private final String arquivo = "emprestimos.json";
    private final Gson gson;

    public EmprestimoDAO() {
        RuntimeTypeAdapterFactory<Obra> obraAdapterFactory = RuntimeTypeAdapterFactory
            .of(Obra.class, "tipo")
            .registerSubtype(Livro.class, "livro")
            .registerSubtype(Revista.class, "revista")
            .registerSubtype(Artigo.class, "artigo");

        this.gson = new GsonBuilder()
            .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
            .registerTypeAdapterFactory(obraAdapterFactory)
            .setPrettyPrinting()
            .create();
    }

    @Override
    public List<Emprestimo> carregar() {
        try (FileReader reader = new FileReader(arquivo)) {
            Type tipoLista = new TypeToken<List<Emprestimo>>() {}.getType();
            List<Emprestimo> lista = gson.fromJson(reader, tipoLista);
            return (lista != null) ? lista : new ArrayList<>();
        } catch (IOException | JsonSyntaxException e) {
            System.out.println("Arquivo de empréstimos não encontrado ou inválido. Criando lista vazia.");
            return new ArrayList<>();
        }
    }

    @Override
    public void salvar(List<Emprestimo> lista) {
        try (FileWriter writer = new FileWriter(arquivo)) {
            gson.toJson(lista, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // mesmo sistema, mas busca por id Buscar empréstimo por ID
    public Emprestimo buscarPorId(String id) {
        List<Emprestimo> emprestimos = carregar();
        for (Emprestimo e : emprestimos) {
            if (e.getId().equalsIgnoreCase(id)) {
                return e;
            }
        }
        return null;
    }

    //  Sistema para remover o empréstimo por ID (facilita o trabalho e não fica tudo em uma classe/json só)
    public void removerPorId(String id) {
        List<Emprestimo> emprestimos = carregar();
        emprestimos.removeIf(e -> e.getId().equalsIgnoreCase(id));
        salvar(emprestimos);
    }
}
