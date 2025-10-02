package school.sptech.mapper;

import school.sptech.dto.LeituraDTO;
import school.sptech.entity.Leitura;

public class LeituraMapper {
    public static LeituraDTO leituraToDTO(Leitura leitura) {
        return new LeituraDTO(leitura.getId(), leitura.getDado(), leitura.getUnidadeMedida(), leitura.getDataHora());
    }

    public static Leitura leituraToEntity(LeituraDTO leituraDTO) {
        return new Leitura(
                leituraDTO.getId(),
                leituraDTO.getDado(),
                leituraDTO.getUnidadeMedida(),
                leituraDTO.getDataHora());
    }
}
