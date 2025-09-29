package school.sptech.entity;

import java.time.LocalDateTime;

public class LoocaCaptura {

    private Long id;
    private Double dado;
    private String unidadeMedida;
    private LocalDateTime dataHora;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getDado() {
        return dado;
    }

    public void setDado(Double dado) {
        this.dado = dado;
    }

    public String getUnidadeMedida() {
        return unidadeMedida;
    }

    public void setUnidadeMedida(String unidadeMedida) {
        this.unidadeMedida = unidadeMedida;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }
}
