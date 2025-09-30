package school.sptech;

import school.sptech.dto.LeituraDTO;
import school.sptech.entity.Leitura;
import school.sptech.entity.connectBD.DBConnectionProvider;
import school.sptech.mapper.LeituraMapper;
import school.sptech.repository.LeituraRepository;

import java.util.List;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        DBConnectionProvider db = new DBConnectionProvider();
        LeituraRepository repository = new LeituraRepository(db.getJdbcTemplate());

        repository.criarTabela();

       // repository.salvar(new Leitura());
       // repository.salvar(new Leitura());

        List<Leitura> musicas = repository.buscarTodas();

        List<LeituraDTO> dtos = musicas.stream()
                .map(LeituraMapper::leituraDTO)
                .collect(Collectors.toList());

        for (LeituraDTO dto : dtos) {
            System.out.println("ID: " + dto.getId());
        }
    }
}
