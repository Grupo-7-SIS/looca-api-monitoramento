package school.sptech.mapper;

import school.sptech.dto.LeituraDTO;
import school.sptech.entity.Leitura;

public class LeituraMapper {
    public static LeituraDTO leituraDTO(Leitura leitura) {
        return new LeituraDTO(leitura.getId());
    }
}
