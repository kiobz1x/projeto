package dao;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import model.Emprestimo;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class EmprestimoDAO implements Persistivel<Emprestimo> {
    private final String arquivo = "emprestimos.json";
    private final Gson gson = new Gson();

    @Override
    public List<Emprestimo> carregar() { // Agora ele faz List<Emprestimo>
        try (FileReader reader = new FileReader(arquivo)) {
            Type tipoLista = new TypeToken<List<Emprestimo>>() {}.getType();
            List<Emprestimo> lista = gson.fromJson(reader, tipoLista);
            return (lista != null) ? lista : new ArrayList<>();
        } catch (IOException e) {
            return new ArrayList<>();
        }
    }

    @Override
    public void salvar(List<Emprestimo> lista) { // E grava List<Emprestimo>
        try (FileWriter writer = new FileWriter(arquivo)) {
            gson.toJson(lista, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
