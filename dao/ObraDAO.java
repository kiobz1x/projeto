package dao;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import model.*;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ObraDAO implements Persistivel<Obra> {
    private final String arquivo = "obras.json";
    private final Gson gson;

    public ObraDAO() {
        // Adapter para permitir serialização/deserialização correta de classes filhas
        RuntimeTypeAdapterFactory<Obra> adapter = RuntimeTypeAdapterFactory
                .of(Obra.class, "tipo")
                .registerSubtype(Livro.class, "Livro")
                .registerSubtype(Revista.class, "Revista")
                .registerSubtype(Artigo.class, "Artigo");

        // GsonBuilder com suporte para LocalDate e o adapter para tipos
        gson = new GsonBuilder()
                .registerTypeAdapterFactory(adapter)
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter()) // Adiciona suporte para datas
                .setPrettyPrinting()
                .create();
    }

    @Override
    public List<Obra> carregar() {
        try (FileReader reader = new FileReader(arquivo)) {
            Type tipoLista = new TypeToken<List<Obra>>() {}.getType();
            List<Obra> lista = gson.fromJson(reader, tipoLista);
            return (lista != null) ? lista : new ArrayList<>();
        } catch (IOException | com.google.gson.JsonSyntaxException e) {
            return new ArrayList<>();
        }
    }

    @Override
    public void salvar(List<Obra> lista) {
        try (FileWriter writer = new FileWriter(arquivo)) {
            gson.toJson(lista, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
