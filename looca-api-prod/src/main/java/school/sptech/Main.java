package school.sptech;

import school.sptech.entity.connectBD.DBConnectionProvider;
import school.sptech.repository.RedeRepository;
import school.sptech.service.RedeService;


public class Main {
    public static void main(String[] args) throws InterruptedException {

        DBConnectionProvider db = new DBConnectionProvider();
        RedeRepository repository = new RedeRepository(db.getJdbcTemplate());
        RedeService redeService = new RedeService(repository);
        redeService.monitorarRede();

        }
    }
