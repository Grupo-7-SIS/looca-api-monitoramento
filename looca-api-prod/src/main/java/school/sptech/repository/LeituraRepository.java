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

    // Metodo para criar a tabela (pode ser chamado no setup)
    public void criarTabela() {
        String sql = "CREATE TABLE IF NOT EXISTS musica (id INT PRIMARY KEY, nome VARCHAR(100))";
        jdbcTemplate.execute(sql);
    }

    // Inserir uma música
    public void salvar(Leitura leitura) {
        String sql = "INSERT INTO leitura (id, nome) VALUES (?, ?)";
        jdbcTemplate.update(sql, leitura.getId());
    }

    // Buscar todas as músicas
    public List<Leitura> buscarTodas() {
        String sql = "SELECT * FROM leitura";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Leitura.class));
    }

    // Buscar música por id
    public Leitura buscarPorId(int id) {
        String sql = "SELECT * FROM leitura WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Leitura.class), id);
    }

    // Atualizar uma música
    public void atualizar(Leitura musica) {
        String sql = "UPDATE leitura SET nome = ? WHERE id = ?";
        jdbcTemplate.update(sql, musica.getId());
    }

    // Deletar uma música pelo id
    public void deletarPorId(int id) {
        String sql = "DELETE FROM leitura WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
}
