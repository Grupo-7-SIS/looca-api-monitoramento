package school.sptech.mapper;

import school.sptech.dto.LeituraDTO;
import school.sptech.entity.Leitura;

public class LeituraMapper {

    public static LeituraDTO leituraToDTO(Leitura leitura, String nomeComponente, String unidadeMedida) {
        return new LeituraDTO(
                leitura.getIdLeitura(),
                leitura.getIdComponente(),
                leitura.getIdMaquina(),
                leitura.getDado(),
                leitura.getDthCaptura(),
                leitura.getIdNucleo(),
                nomeComponente,
                unidadeMedida
        );
    }

    public static Leitura leituraToEntity(LeituraDTO dto) {
        Leitura leitura = new Leitura();
        leitura.setIdLeitura(dto.getIdLeitura());
        leitura.setIdComponente(dto.getIdComponente());
        leitura.setIdMaquina(dto.getIdMaquina());
        leitura.setDado(dto.getDado());
        leitura.setDthCaptura(dto.getDthCaptura());
        leitura.setIdNucleo(dto.getIdNucleo());
        return leitura;
    }
}
