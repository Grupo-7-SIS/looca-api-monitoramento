package school.sptech.entity.connectBD;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.jdbc.core.JdbcTemplate;

public class DBConnectionProvider {

    private final JdbcTemplate jdbcTemplate;
    private final BasicDataSource basicDataSource;

    public DBConnectionProvider() {
        BasicDataSource basicDataSource = new BasicDataSource();
        basicDataSource.setUrl("jdbc:mysql://54.145.9.123:3306/cyberbeef?useSSL=false&serverTimezone=UTC");
        basicDataSource.setUsername("root");  // seu usuário do MySQL
        basicDataSource.setPassword("stevejobs");  // sua senha do MySQL
        basicDataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");

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
