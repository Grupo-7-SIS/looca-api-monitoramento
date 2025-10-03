package school.sptech.service;

import com.github.britooo.looca.api.core.Looca;
import com.github.britooo.looca.api.group.rede.RedeInterface;
import school.sptech.dto.LeituraDTO;
import school.sptech.repository.LeituraRepository;

import java.util.List;

public class LeituraService {

    private final LeituraRepository repository;

    public LeituraService(LeituraRepository repository) {
        this.repository = repository;
    }

    public void monitorarRede() throws InterruptedException {
        Looca looca = new Looca();
        List<RedeInterface> dados = looca.getRede().getGrupoDeInterfaces().getInterfaces();

        Long idMaquina = 1L;

        repository.salvarComponenteSeNaoExistir(idMaquina, "Download", "Mbps");
        repository.salvarComponenteSeNaoExistir(idMaquina, "Upload", "Mbps");
        repository.salvarComponenteSeNaoExistir(idMaquina, "Packet Loss", "%");

        Long idDownload = repository.buscarIdComponente(idMaquina, "Download");
        Long idUpload = repository.buscarIdComponente(idMaquina, "Upload");
        Long idPacketLoss = repository.buscarIdComponente(idMaquina, "Packet Loss");

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

            double downloadMbps = (bytesRecebidosDepois - bytesRecebidosAntes) * 8.0 / 1_000_000.0;
            double uploadMbps = (bytesEnviadosDepois - bytesEnviadosAntes) * 8.0 / 1_000_000.0;

            double diferencaPacotesRecebidos = pacotesRecebidosDepois - pacotesRecebidosAntes;
            double diferencaPacotesEnviados = pacotesEnviadosDepois - pacotesEnviadosAntes;

            double packetLoss = 0.0;
            if (diferencaPacotesEnviados > 0) {
                packetLoss = (diferencaPacotesEnviados - diferencaPacotesRecebidos) / diferencaPacotesEnviados * 100.0;
                packetLoss = Math.max(0.0, Math.min(packetLoss, 100.0));
            }

            repository.salvarLeitura(idDownload, idMaquina, downloadMbps, null);
            repository.salvarLeitura(idUpload, idMaquina, uploadMbps, null);
            repository.salvarLeitura(idPacketLoss, idMaquina, packetLoss, null);

            mostrarPainel(downloadMbps, uploadMbps, packetLoss);
            mostrarLeituras();
        }
    }

    private void mostrarPainel(double download, double upload, double packetLoss) {
        System.out.println("\n========== MONITORAMENTO DE REDE ==========");
        System.out.printf("Velocidade de download: %.2f Mbps%n", download);
        System.out.printf("Velocidade de upload: %.2f Mbps%n", upload);
        System.out.printf("Packet Loss: %.2f %%\n", packetLoss);
        System.out.println("-------------------------------------------");
    }

    private void mostrarLeituras() {
        List<LeituraDTO> leituras = repository.buscarTodasDTOComComponente();
        System.out.printf("%-10s %-20s %-12s %-12s %-10s %-20s%n", "ID LEITURA", "COMPONENTE", "UNIDADE", "ID MÁQUINA", "DADO", "DATA");
        for (LeituraDTO leitura : leituras) {
            System.out.printf("%-10d %-20s %-12s %-12d %-10.2f %-20s%n",
                    leitura.getIdLeitura(),
                    leitura.getNomeComponente(),
                    leitura.getUnidadeMedida(),
                    leitura.getIdMaquina(),
                    leitura.getDado(),
                    leitura.getDthCaptura());
        }
    }

    private Long getBytesRecebidos(List<RedeInterface> dados) {
        return dados.stream().map(RedeInterface::getBytesRecebidos).reduce(0L, Long::sum);
    }

    private Long getBytesEnviados(List<RedeInterface> dados) {
        return dados.stream().map(RedeInterface::getBytesEnviados).reduce(0L, Long::sum);
    }

    private Long getPacotesRecebidos(List<RedeInterface> dados) {
        return dados.stream().map(RedeInterface::getPacotesRecebidos).reduce(0L, Long::sum);
    }

    private Long getPacotesEnviados(List<RedeInterface> dados) {
        return dados.stream().map(RedeInterface::getPacotesEnviados).reduce(0L, Long::sum);
    }
}
