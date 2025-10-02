package school.sptech.service;

import com.github.britooo.looca.api.core.Looca;
import com.github.britooo.looca.api.group.rede.Rede;
import com.github.britooo.looca.api.group.rede.RedeInterface;
import school.sptech.entity.Leitura;
import school.sptech.repository.LeituraRepository;

import java.time.LocalDateTime;
import java.util.List;

public class LeituraService {

    private final LeituraRepository repository;
    private Long id = 1L;

    public LeituraService(LeituraRepository repository) {
        this.repository = repository;
    }

    public void monitorarRede() throws InterruptedException {
        Looca looca = new Looca();

        List<RedeInterface> dados = looca.getRede().getGrupoDeInterfaces().getInterfaces();

        while (true) {

            Long bytesRecebidosAntes = getBytesRecebidos(dados);
            Long bytesEnviadosAntes = getBytesEnviados(dados);
            Long pacotesRecebidosAntes = getPacotesRecebidos(dados);
            Long pacotesEnviadosAntes = getPacotesEnviados(dados);

            Thread.sleep(5000);

            Long bytesRecebidosDepois = getBytesRecebidos(dados);
            Long bytesEnviadosDepois = getBytesEnviados(dados);
            Long pacotesRecebidosDepois = getPacotesRecebidos(dados);
            Long pacotesEnviadosDepois = getPacotesEnviados(dados);

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


            repository.salvar(new Leitura(id++, downloadMbps, "Mbps", LocalDateTime.now()));
            repository.salvar(new Leitura(id++, uploadMbps, "Mbps", LocalDateTime.now()));
            repository.salvar(new Leitura(id++, packetLoss, "%", LocalDateTime.now()));

            metricasDados(downloadMbps, uploadMbps, packetLoss);
            mostrarLeituraDeRede(repository);

        }
    }

    public void metricasDados(Double downloadMbps, Double uploadMbps, Double packetLoss) {
        System.out.println("\n========== MONITORAMENTO DE REDE ==========");
        System.out.printf("Velocidade de download: %.2f Mbps%n", downloadMbps);
        System.out.printf("Velocidade de upload: %.2f Mbps%n", uploadMbps);
        System.out.printf("Packet Loss: %.2f %%\n", packetLoss);
        System.out.println("-------------------------------------------");
    }

    public void mostrarLeituraDeRede(LeituraRepository repository) {
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

    public Long getBytesRecebidos(List<RedeInterface> dados) {
        return dados.stream().map(RedeInterface::getBytesRecebidos).reduce(0L, Long::sum);
    }

    public Long getBytesEnviados(List<RedeInterface> dados) {
        return dados.stream().map(RedeInterface::getBytesEnviados).reduce(0L, Long::sum);
    }

    public Long getPacotesRecebidos(List<RedeInterface> dados) {
        return dados.stream().map(RedeInterface::getPacotesRecebidos).reduce(0L, Long::sum);
    }

    public Long getPacotesEnviados(List<RedeInterface> dados) {
        return dados.stream().map(RedeInterface::getPacotesEnviados).reduce(0L, Long::sum);
    }

}
