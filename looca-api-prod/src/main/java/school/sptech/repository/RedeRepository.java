package school.sptech.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.dao.EmptyResultDataAccessException;
import school.sptech.dto.RedeDTO;

import java.util.List;

public class RedeRepository {

    private final JdbcTemplate jdbcTemplate;

    public RedeRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Long buscarIdComponenteRede(Long idMaquina) {
        try {
            String sql = """
                SELECT idComponente 
                FROM componente 
                WHERE idMaquina = ? 
                AND tipoComponente = 'REDE' 
                LIMIT 1
                """;
            return jdbcTemplate.queryForObject(sql, Long.class, idMaquina);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public void salvarDadosRede(Long idMaquina, Long idComponente, Double download, Double upload, Double packetLoss) {
        String sql = """
            INSERT INTO rede (idMaquina, idComponente, download, upload, packetLoss, dthCaptura)
            VALUES (?, ?, ?, ?, ?, NOW())
            """;
        jdbcTemplate.update(sql, idMaquina, idComponente, download, upload, packetLoss);
    }

    public List<RedeDTO> buscarDadosRede() {
        String sql = """
            SELECT r.idRede, r.idMaquina, r.idComponente, r.download, r.upload, r.packetLoss, r.dthCaptura
            FROM rede r
            ORDER BY dthCaptura DESC
            """;
        return jdbcTemplate.query(sql, (rs, rowNum) -> new RedeDTO(
                rs.getLong("idRede"),
                rs.getLong("idMaquina"),
                rs.getLong("idComponente"),
                rs.getDouble("download"),
                rs.getDouble("upload"),
                rs.getDouble("packetLoss"),
                rs.getTimestamp("dthCaptura").toLocalDateTime()
        ));
    }
}
