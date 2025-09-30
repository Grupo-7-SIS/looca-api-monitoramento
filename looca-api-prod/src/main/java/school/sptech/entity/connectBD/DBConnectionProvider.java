package school.sptech.entity.connectBD;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.jdbc.core.JdbcTemplate;

public class DBConnectionProvider {

    private final JdbcTemplate jdbcTemplate;
    private final BasicDataSource basicDataSource;

    public DBConnectionProvider() {
        BasicDataSource basicDataSource = new BasicDataSource();
        basicDataSource.setUrl("jdbc:h2:mem:leitura;DB_CLOSE_DELAY=-1");
        basicDataSource.setUsername("looca");
        basicDataSource.setPassword("");
        basicDataSource.setDriverClassName("org.h2.Driver");

        this.basicDataSource = basicDataSource;
        this.jdbcTemplate = new JdbcTemplate(basicDataSource);
    }

    public BasicDataSource getBasicDataSource() {
        return basicDataSource;
    }

    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }
}
