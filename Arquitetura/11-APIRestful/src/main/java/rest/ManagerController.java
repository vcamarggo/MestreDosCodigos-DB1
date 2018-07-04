package rest;


import dao.ManagerRepository;
import model.Manager;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * Created by vinicius.camargo on 02/07/2018
 */
@org.springframework.web.bind.annotation.RestController
public class ManagerController {

    private final String BASE_MANAGER_URL = "/manager/";

    // Retorna o JSON do gerente ou um 404 not found
    @RequestMapping(value = BASE_MANAGER_URL + "{managerName}", method = RequestMethod.GET)
    public HttpEntity<Manager> getManager(@PathVariable String managerName) {
        Manager manager = ManagerRepository.findManager(managerName);
        if (manager != null) {
            return ResponseEntity.ok(manager);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Retorna um array JSON dos gerentes
    @RequestMapping(value = BASE_MANAGER_URL, method = RequestMethod.GET)
    public HttpEntity<List<Manager>> getAllManager() {
        List<Manager> managers = ManagerRepository.getAllManager();
        return ResponseEntity.ok(managers);
    }

    // Retorna o JSON do gerente inserido ou um 400 bad request
    @RequestMapping(value = BASE_MANAGER_URL, method = RequestMethod.POST)
    public ResponseEntity postManager(@RequestBody Manager manager) {
        if (ManagerRepository.createManager(manager)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

}
