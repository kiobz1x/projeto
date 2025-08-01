package dao;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import model.Usuario;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO implements Persistivel<Usuario> {
    private final String arquivo = "usuarios.json";
    private final Gson gson = new Gson();

    @Override
    public List<Usuario> carregar() { // Agora ele faz List<Usuario>
        try (FileReader reader = new FileReader(arquivo)) {
            Type tipoLista = new TypeToken<List<Usuario>>() {}.getType();
            List<Usuario> lista = gson.fromJson(reader, tipoLista);
            return (lista != null) ? lista : new ArrayList<>();
        } catch (IOException e) {
            return new ArrayList<>();
        }
    }

    @Override
    public void salvar(List<Usuario> lista) { // E grava List<Usuario>
        try (FileWriter writer = new FileWriter(arquivo)) {
            gson.toJson(lista, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public Usuario buscarPorMatricula(String matricula) {
        List<Usuario> usuarios = carregar();
        for (Usuario u : usuarios) {
            if (u.getMatricula().equalsIgnoreCase(matricula)) {
                return u;
            }
        }
        return null;
    }
    
    public boolean adicionarUsuario(Usuario usuario) {
    	List<Usuario> usuarios = carregar();
    	for(Usuario u: usuarios) {
    		if(u.getMatricula().equalsIgnoreCase(usuario.getMatricula())) {
    			return false;
    		}
    	}
    	
        usuarios.add(usuario);
        salvar(usuarios);
        return true;
    }
    
    public boolean removerUsuario(String matricula) {
    	List<Usuario> usuarios = carregar();
    	boolean removerUsuario = usuarios.removeIf(u -> u.getMatricula().equalsIgnoreCase(matricula.trim()));
    	if(removerUsuario) {
    		salvar(usuarios);
    	}
    	
    	return removerUsuario;
    }
}

