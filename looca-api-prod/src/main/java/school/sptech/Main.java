package school.sptech;

import com.github.britooo.looca.api.core.Looca;
import com.github.britooo.looca.api.group.rede.RedeInterface;
import school.sptech.entity.Leitura;
import school.sptech.entity.connectBD.DBConnectionProvider;
import school.sptech.repository.LeituraRepository;

import java.time.LocalDateTime;
import java.util.List;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        DBConnectionProvider db = new DBConnectionProvider();
        LeituraRepository repository = new LeituraRepository(db.getJdbcTemplate());

        repository.criarTabela();

        Looca looca = new Looca();
        List<RedeInterface> dados = looca.getRede().getGrupoDeInterfaces().getInterfaces();

        Long id = 1L;

        while (true) {

            Long bytesRecebidosAntes = dados.stream().map(RedeInterface::getBytesRecebidos).reduce(0L, Long::sum);
            Long bytesEnviadosAntes = dados.stream().map(RedeInterface::getBytesEnviados).reduce(0L, Long::sum);
            Long pacotesRecebidosAntes = dados.stream().map(RedeInterface::getPacotesRecebidos).reduce(0L, Long::sum);
            Long pacotesEnviadosAntes = dados.stream().map(RedeInterface::getPacotesEnviados).reduce(0L, Long::sum);

            Thread.sleep(5000);

            Long bytesRecebidosDepois = dados.stream().map(RedeInterface::getBytesRecebidos).reduce(0L, Long::sum);
            Long bytesEnviadosDepois = dados.stream().map(RedeInterface::getBytesEnviados).reduce(0L, Long::sum);
            Long pacotesRecebidosDepois = dados.stream().map(RedeInterface::getPacotesRecebidos).reduce(0L, Long::sum);
            Long pacotesEnviadosDepois = dados.stream().map(RedeInterface::getPacotesEnviados).reduce(0L, Long::sum);

            double diferencaPacotesRecebidos = pacotesRecebidosDepois.doubleValue() - pacotesRecebidosAntes.doubleValue();
            double diferencaPacotesEnviados = pacotesEnviadosDepois.doubleValue() - pacotesEnviadosAntes.doubleValue();
            long diferencaBytes = bytesRecebidosDepois - bytesRecebidosAntes;
            long diferencaBytesEnviados = bytesEnviadosDepois - bytesEnviadosAntes;

            // Packet Loss
            double packetLoss = 0.0;
            if (diferencaPacotesEnviados > 0) {
                packetLoss = (diferencaPacotesEnviados - diferencaPacotesRecebidos) / diferencaPacotesEnviados * 100.0;
                packetLoss = Math.max(0.0, Math.min(packetLoss, 100.0));
            }

            // Mbps
            double downloadMbps = (diferencaBytes * 8.0) / 1_000_000.0;
            double uploadMbps = (diferencaBytesEnviados * 8.0) / 1_000_000.0;

            // Salva no banco
            repository.salvar(new Leitura(id++, downloadMbps, "Mbps", LocalDateTime.now()));
            repository.salvar(new Leitura(id++, uploadMbps, "Mbps", LocalDateTime.now()));
            repository.salvar(new Leitura(id++, packetLoss, "%", LocalDateTime.now()));

            System.out.println("\n========== MONITORAMENTO DE REDE ==========");
            System.out.printf("Velocidade de download: %.2f Mbps%n", downloadMbps);
            System.out.printf("Velocidade de upload: %.2f Mbps%n", uploadMbps);
            System.out.printf("Packet Loss: %.2f %%\n", packetLoss);
            System.out.println("-------------------------------------------");

            printarLeituras(repository);
        }
    }

    private static void printarLeituras(LeituraRepository repository) {
        List<Leitura> leituras = repository.buscarTodas();
        System.out.printf("%-5s %-10s %-10s %-20s%n", "ID", "DADO", "UNIDADE", "DATA");
        for (Leitura leitura : leituras) {
            System.out.printf("%-5d %-10.2f %-10s %-20s%n",
                    leitura.getId(),
                    leitura.getDado(),
                    leitura.getUnidadeMedida(),
                    leitura.getDataHora());
        }
    }
}
