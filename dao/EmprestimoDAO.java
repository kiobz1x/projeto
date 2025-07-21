package dao;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import model.Emprestimo;
import model.Obra;
import model.Livro;
import model.Revista;
import model.Artigo;

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
        // Configura o RuntimeTypeAdapterFactory para a classe abstrata Obra
        RuntimeTypeAdapterFactory<Obra> obraAdapterFactory = RuntimeTypeAdapterFactory
            .of(Obra.class, "tipo")
            .registerSubtype(Livro.class, "Livro")
            .registerSubtype(Revista.class, "Revista")
            .registerSubtype(Artigo.class, "Artigo");

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
            System.out.println("Arquivo não encontrado ou inválido. Iniciando lista vazia.");
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
}
