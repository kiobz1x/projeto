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
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    private List<Leitor> carregarLeitores() {
        try (Reader reader = new FileReader(ARQUIVO)) {
            Type listType = new TypeToken<ArrayList<Leitor>>() {}.getType();
            List<Leitor> leitores = gson.fromJson(reader, listType);
            return leitores != null ? leitores : new ArrayList<>();
        } catch (IOException e) {
            return new ArrayList<>();
        }
    }

    private void salvarLeitores(List<Leitor> leitores) {
        try (FileWriter writer = new FileWriter(ARQUIVO)) {
            gson.toJson(leitores, writer);
        } catch (IOException e) {
            System.out.println("Erro ao salvar leitores: " + e.getMessage());
        }
    }

    public List<Leitor> listar() {
        return carregarLeitores();
    }

    public boolean adicionar(Leitor leitor) {
    	List<Leitor> leitores = carregarLeitores();
    	for(Leitor l: leitores) {
    		if(l.getMatricula().equalsIgnoreCase(leitor.getMatricula())) {
    			return false;
    		}
    	}
    	
        leitores.add(leitor);
        salvarLeitores(leitores);
        return true;
    }
    
    public void atualizarLeitor(Leitor atualizado) {
    	List<Leitor> leitores = carregarLeitores();
    	for(int i=0; i<leitores.size(); i++) {
    		if (leitores.get(i).getMatricula().equalsIgnoreCase(atualizado.getMatricula())) {
                leitores.set(i, atualizado);
                salvarLeitores(leitores);
                return;
    		}
    	}
    }

    public Leitor buscarPorMatricula(String matricula) {
    	List<Leitor> leitores = carregarLeitores();
        for (Leitor l : leitores) {
            if (l.getMatricula().equalsIgnoreCase(matricula)) {
                return l;
            }
        }
        return null;
    }
    

    public boolean remover(String matricula) {
    	List<Leitor> leitores = carregarLeitores();
    	boolean removerLeitor = leitores.removeIf(lei -> lei.getMatricula().equalsIgnoreCase(matricula.trim()));
    	if(removerLeitor) {
    		salvarLeitores(leitores);
    	}
    	
    	return removerLeitor;
    }
    
}
