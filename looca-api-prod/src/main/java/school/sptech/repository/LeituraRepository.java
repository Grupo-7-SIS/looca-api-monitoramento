package school.sptech.repository;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import school.sptech.entity.Leitura;

import java.util.List;

public class LeituraRepository {

    private final JdbcTemplate jdbcTemplate;

    public LeituraRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void criarTabela() {
        String sql = "CREATE TABLE IF NOT EXISTS leitura (" +
                "id BIGINT PRIMARY KEY AUTO_INCREMENT," +
                " dado DOUBLE, " +
                "unidadeMedida varchar(50), " +
                "dataHora TIMESTAMP )";

        jdbcTemplate.execute(sql);
    }

    public void salvar(Leitura leitura) {
        String sql = "INSERT INTO leitura (id, dado, unidadeMedida, dataHora) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql, leitura.getId(),
                leitura.getDado(),
                leitura.getUnidadeMedida(),
                leitura.getDataHora());
    }

    public List<Leitura> buscarTodas() {
        String sql = "SELECT * FROM leitura";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Leitura.class));
    }

    public Leitura buscarPorId(int id) {
        String sql = "SELECT * FROM leitura WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Leitura.class), id);
    }

    public void atualizar(Leitura leitura) {
        String sql = "UPDATE leitura SET dado = ?, unidadeMedida = ? WHERE id = ?";
        jdbcTemplate.update(sql, leitura.getId(),
                leitura.getDado(),
                leitura.getUnidadeMedida());
    }

    public void deletarPorId(int id) {
        String sql = "DELETE FROM leitura WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

}
