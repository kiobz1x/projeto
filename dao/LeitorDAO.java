package dao;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.GsonBuilder;
import model.Leitor;

import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class LeitorDAO {

    private static final String ARQUIVO = "leitores.json";
    private List<Leitor> leitores;

    public LeitorDAO() {
        this.leitores = carregarLeitores();
    }

    private List<Leitor> carregarLeitores() {
        try (Reader reader = new FileReader(ARQUIVO)) {
            Type listType = new TypeToken<ArrayList<Leitor>>() {}.getType();
            return new Gson().fromJson(reader, listType);
        } catch (IOException e) {
            return new ArrayList<>();
        }
    }

    private void salvarLeitores() {
        try (FileWriter writer = new FileWriter(ARQUIVO)) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(leitores, writer);
        } catch (IOException e) {
            System.out.println("Erro ao salvar leitores: " + e.getMessage());
        }
    }

    public List<Leitor> listar() {
        return leitores;
    }

    public boolean adicionar(Leitor leitor) {
        if (buscarPorMatricula(leitor.getMatricula()) == null) {
            leitores.add(leitor);
            salvarLeitores();
            return true;
        }
        return false; // matrícula duplicada
    }

    public Leitor buscarPorMatricula(String matricula) {
        for (Leitor l : leitores) {
            if (l.getMatricula().equalsIgnoreCase(matricula)) {
                return l;
            }
        }
        return null;
    }

    public boolean remover(String matricula) {
        Leitor leitor = buscarPorMatricula(matricula);
        if (leitor != null) {
            leitores.remove(leitor);
            salvarLeitores();
            return true;
        }
        return false;
    }
}
