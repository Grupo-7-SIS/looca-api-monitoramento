package school.sptech;

import school.sptech.entity.connectBD.DBConnectionProvider;
import school.sptech.repository.LeituraRepository;
import school.sptech.service.LeituraService;


public class Main {
    public static void main(String[] args) throws InterruptedException {

        DBConnectionProvider db = new DBConnectionProvider();
        LeituraRepository repository = new LeituraRepository(db.getJdbcTemplate());

        LeituraService leituraService = new LeituraService(repository);

        leituraService.monitorarRede();

        }
    }
