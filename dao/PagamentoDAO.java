package dao;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import model.PagamentoMulta;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PagamentoDAO implements Persistivel<PagamentoMulta> {
    private final String arquivo = "pagamentos.json";
    private final Gson gson;

    public PagamentoDAO() {
        this.gson = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter()) // Adapter para datas
                .setPrettyPrinting()
                .create();
    }

    @Override
    public List<PagamentoMulta> carregar() {
        try (FileReader reader = new FileReader(arquivo)) {
            Type tipoLista = new TypeToken<List<PagamentoMulta>>() {}.getType();
            List<PagamentoMulta> lista = gson.fromJson(reader, tipoLista);
            return (lista != null) ? lista : new ArrayList<>();
        } catch (IOException | com.google.gson.JsonSyntaxException e) {
            // Se o arquivo estiver vazio, corrompido ou não existir, retorna lista vazia
            return new ArrayList<>();
        }
    }

    @Override
    public void salvar(List<PagamentoMulta> lista) {
        try (FileWriter writer = new FileWriter(arquivo)) {
            gson.toJson(lista, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
