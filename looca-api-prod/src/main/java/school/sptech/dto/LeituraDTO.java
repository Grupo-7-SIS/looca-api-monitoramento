package school.sptech.dto;

import java.time.LocalDateTime;

public class LeituraDTO {

    private Long id;
    private Double dado;
    private String unidadeMedida;
    private LocalDateTime dataHora;

    public LeituraDTO(Long id, Double dado, String unidadeMedida, LocalDateTime dataHora) {
        this.id = id;
        this.dado = dado;
        this.unidadeMedida = unidadeMedida;
        this.dataHora = dataHora;
    }

    public Long getId() {
        return id;
    }

    public Double getDado() {
        return dado;
    }

    public String getUnidadeMedida() {
        return unidadeMedida;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }
}
