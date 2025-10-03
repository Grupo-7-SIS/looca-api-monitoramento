package school.sptech.entity;

import java.time.LocalDateTime;

public class Leitura {

    private Long idLeitura;
    private Long idComponente;
    private Long idMaquina;
    private Double dado;
    private LocalDateTime dthCaptura;
    private Long idNucleo;

    public Leitura() {}

    public Leitura(Long idLeitura, Long idComponente, Long idMaquina, Double dado,
                   LocalDateTime dthCaptura, Long idNucleo) {
        this.idLeitura = idLeitura;
        this.idComponente = idComponente;
        this.idMaquina = idMaquina;
        this.dado = dado;
        this.dthCaptura = dthCaptura;
        this.idNucleo = idNucleo;
    }

    public Long getIdLeitura() { return idLeitura; }
    public void setIdLeitura(Long idLeitura) { this.idLeitura = idLeitura; }

    public Long getIdComponente() { return idComponente; }
    public void setIdComponente(Long idComponente) { this.idComponente = idComponente; }

    public Long getIdMaquina() { return idMaquina; }
    public void setIdMaquina(Long idMaquina) { this.idMaquina = idMaquina; }

    public Double getDado() { return dado; }
    public void setDado(Double dado) { this.dado = dado; }

    public LocalDateTime getDthCaptura() { return dthCaptura; }
    public void setDthCaptura(LocalDateTime dthCaptura) { this.dthCaptura = dthCaptura; }

    public Long getIdNucleo() { return idNucleo; }
    public void setIdNucleo(Long idNucleo) { this.idNucleo = idNucleo; }
}
