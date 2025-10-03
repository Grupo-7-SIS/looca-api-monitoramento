package school.sptech.repository;
import org.springframework.jdbc.core.JdbcTemplate;
import school.sptech.dto.LeituraDTO;

import java.util.List;

public class LeituraRepository {

    private final JdbcTemplate jdbcTemplate;

    public LeituraRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void salvarComponenteSeNaoExistir(Long idMaquina, String nomeComponente, String unidadeMedida) {
        String sqlCheck = "SELECT COUNT(*) FROM componente WHERE idMaquina = ? AND nomeComponente = ?";
        Integer count = jdbcTemplate.queryForObject(sqlCheck, Integer.class, idMaquina, nomeComponente);
        if (count == null || count == 0) {
            String sqlInsert = "INSERT INTO componente (idMaquina, nomeComponente, unidadeMedida) VALUES (?, ?, ?)";
            jdbcTemplate.update(sqlInsert, idMaquina, nomeComponente, unidadeMedida);
        }
    }

    public Long buscarIdComponente(Long idMaquina, String nomeComponente) {
        String sql = "SELECT idComponente FROM componente WHERE idMaquina = ? AND nomeComponente = ? LIMIT 1";
        return jdbcTemplate.queryForObject(sql, Long.class, idMaquina, nomeComponente);
    }

    public void salvarLeitura(Long idComponente, Long idMaquina, Double dado, Integer idNucleo) {
        String sql = "INSERT INTO leitura (idComponente, idMaquina, dado, dthCaptura, idNucleo) VALUES (?, ?, ?, NOW(), ?)";
        jdbcTemplate.update(sql, idComponente, idMaquina, dado, idNucleo);
    }

    public List<LeituraDTO> buscarTodasDTOComComponente() {
        String sql = """
                SELECT l.idLeitura, l.idComponente, l.idMaquina, l.dado, l.dthCaptura, l.idNucleo,
                       c.nomeComponente, c.unidadeMedida
                FROM leitura l
                JOIN componente c ON l.idComponente = c.idComponente
                ORDER BY l.dthCaptura DESC
                """;
        return jdbcTemplate.query(sql, (rs, rowNum) -> new LeituraDTO(
                rs.getLong("idLeitura"),
                rs.getLong("idComponente"),
                rs.getLong("idMaquina"),
                rs.getDouble("dado"),
                rs.getTimestamp("dthCaptura").toLocalDateTime(),
                rs.getObject("idNucleo") != null ? rs.getLong("idNucleo") : null,
                rs.getString("nomeComponente"),
                rs.getString("unidadeMedida")
        ));
    }
}
