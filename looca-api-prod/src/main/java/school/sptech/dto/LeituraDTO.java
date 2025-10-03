package school.sptech.dto;

import java.time.LocalDateTime;

public class LeituraDTO {

    private Long idLeitura;
    private Long idComponente;
    private Long idMaquina;
    private Double dado;
    private LocalDateTime dthCaptura;
    private Long idNucleo;
    private String nomeComponente;
    private String unidadeMedida;

    public LeituraDTO(Long idLeitura, Long idComponente, Long idMaquina, Double dado,
                      LocalDateTime dthCaptura, Long idNucleo, String nomeComponente, String unidadeMedida) {
        this.idLeitura = idLeitura;
        this.idComponente = idComponente;
        this.idMaquina = idMaquina;
        this.dado = dado;
        this.dthCaptura = dthCaptura;
        this.idNucleo = idNucleo;
        this.nomeComponente = nomeComponente;
        this.unidadeMedida = unidadeMedida;
    }

    public Long getIdLeitura() { return idLeitura; }
    public Long getIdComponente() { return idComponente; }
    public Long getIdMaquina() { return idMaquina; }
    public Double getDado() { return dado; }
    public LocalDateTime getDthCaptura() { return dthCaptura; }
    public Long getIdNucleo() { return idNucleo; }
    public String getNomeComponente() { return nomeComponente; }
    public String getUnidadeMedida() { return unidadeMedida; }
}
