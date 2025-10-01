package school.sptech;

import school.sptech.dto.LeituraDTO;
import school.sptech.entity.Leitura;
import school.sptech.entity.connectBD.DBConnectionProvider;
import school.sptech.mapper.LeituraMapper;
import school.sptech.repository.LeituraRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        DBConnectionProvider db = new DBConnectionProvider();
        LeituraRepository repository = new LeituraRepository(db.getJdbcTemplate());

        repository.criarTabela();

        repository.salvar(new Leitura(1L, 14.5, "Mbps", LocalDateTime.now()));
        repository.salvar(new Leitura(2L, 18.5, "Mbps", LocalDateTime.now()));

        List<Leitura> leituras = repository.buscarTodas();

        List<LeituraDTO> dtos = leituras.stream()
                .map(LeituraMapper::leituraDTO)
                .collect(Collectors.toList());

        for (LeituraDTO dto : dtos) {
            System.out.println("ID: " + dto.getId());
        }

        repository.criarTabela();
        for (Leitura leitura : leituras) {
            System.out.println(leitura);
        }
    }
}
