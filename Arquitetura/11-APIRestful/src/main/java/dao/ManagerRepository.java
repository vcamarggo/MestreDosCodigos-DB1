package dao;

import model.Manager;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Created by vinicius.camargo on 02/07/2018
 */
public class ManagerRepository {

    private static int lastId = 1;

    private static List<Manager> managers = new ArrayList<>(Arrays.asList(
            new Manager(lastId++,
                    "Mary Josh",
                    "josh.mary@mybank.com",
                    Date.from(LocalDate.of(1967, 6, 30).atStartOfDay().toInstant(ZoneOffset.UTC))),
            new Manager(lastId++,
                    "Ben Hoover",
                    "josh.mary@mybank.com",
                    Date.from(LocalDate.of(1975, 10, 3).atStartOfDay().toInstant(ZoneOffset.UTC)))));

    // Busca um gerente pelo nome
    public static Manager findManager(String name) {
        return managers.stream().filter(manager -> manager.name.equals(name)).findFirst().orElse(null);
    }

    // Cria um gerente
    public static boolean createManager(Manager manager) {
        manager.id = lastId++;
        return managers.add(manager);
    }

    // Busca todos os gerentes
    public static List<Manager> getAllManager() {
        return managers;
    }
}
