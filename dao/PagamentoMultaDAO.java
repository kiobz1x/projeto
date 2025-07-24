package dao;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import model.PagamentoMulta;

public class PagamentoMultaDAO {
    private final String arquivo = "pagamentos.json";
    private final Gson gson = new GsonBuilder()
            .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
            .setPrettyPrinting()
            .create();

    public List<PagamentoMulta> carregar() {
        try (FileReader reader = new FileReader(arquivo)) {
            Type tipoLista = new TypeToken<List<PagamentoMulta>>() {}.getType();
            List<PagamentoMulta> lista = gson.fromJson(reader, tipoLista);
            return (lista != null) ? lista : new ArrayList<>();
        } catch (IOException e) {
            return new ArrayList<>();
        }
    }

    public void salvar(List<PagamentoMulta> lista) {
        try (FileWriter writer = new FileWriter(arquivo)) {
            gson.toJson(lista, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
