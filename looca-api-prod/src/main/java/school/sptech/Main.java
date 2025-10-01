package school.sptech;

import com.github.britooo.looca.api.core.Looca;
import com.github.britooo.looca.api.group.rede.RedeInterface;
import school.sptech.dto.LeituraDTO;
import school.sptech.entity.Leitura;
import school.sptech.entity.connectBD.DBConnectionProvider;
import school.sptech.mapper.LeituraMapper;
import school.sptech.repository.LeituraRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) throws InterruptedException {
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

        for (Leitura leitura : leituras) {
            System.out.println(leitura);
        }

        Looca looca = new Looca();
        List<RedeInterface> dados = looca.getRede().getGrupoDeInterfaces().getInterfaces();

        Long id = 3L;

        while (true) {

            Long bytesRecebidosAntes = dados.stream()
                    .map(RedeInterface::getBytesRecebidos)
                    .reduce(0L, Long::sum);

            Long bytesEnviadosAntes = dados.stream()
                    .map(RedeInterface::getBytesEnviados)
                    .reduce(0L, Long::sum);

            Thread.sleep(5000);

            Long bytesRecebidosDepois = dados.stream()
                    .map(RedeInterface::getBytesRecebidos)
                    .reduce(0L, Long::sum);

            Long bytesEnviadosDepois = dados.stream()
                    .map(RedeInterface::getBytesEnviados)
                    .reduce(0L, Long::sum);

            Long diferencaBytes = bytesRecebidosDepois - bytesRecebidosAntes;
            Long diferencaBytesEnviados = bytesEnviadosDepois - bytesEnviadosAntes;

            Double downloadMbps = (diferencaBytes * 8.0) / 1_000_000.0;
            Double uploadMbps = (diferencaBytesEnviados * 8.0) / 1_000_000.0;

            System.out.println("-".repeat(50));

            System.out.printf("Velocidade de download: %.2f Mbps%n", downloadMbps);
            System.out.printf("Velocidade de upload: %.2f Mbps%n", uploadMbps);

            repository.salvar(new Leitura(id++, downloadMbps, "Mbps", LocalDateTime.now()));
            repository.salvar(new Leitura(id++, uploadMbps, "Mbps", LocalDateTime.now()));
            System.out.println("Leitura de Download e Upload salva!");
        }
    }
}
