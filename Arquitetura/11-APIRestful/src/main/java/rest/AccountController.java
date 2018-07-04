package rest;


import dao.AccountRepository;
import model.Account;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.springframework.web.bind.annotation.RequestMethod.*;

/**
 * Created by vinicius.camargo on 02/07/2018
 */
@org.springframework.web.bind.annotation.RestController
public class AccountController {

    private final String BASE_ACCOUNT_URL = "/account/";

    // Retorna o JSON da conta ou um 404 not found
    @RequestMapping(value = BASE_ACCOUNT_URL, method = OPTIONS)
    public ResponseEntity getOptions() {
        HttpHeaders headers = new HttpHeaders();
        Set<HttpMethod> allowedMethods = Stream.of(HttpMethod.GET, HttpMethod.POST, HttpMethod.PUT, HttpMethod.DELETE).collect(Collectors.toSet());
        headers.setAllow(allowedMethods);
        headers.setContentType(MediaType.APPLICATION_JSON);

        String in = null;
        try {
            in = new String(Files.readAllBytes(Paths.get("./src/main/java/rest/doc.json")));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return ResponseEntity.ok().headers(headers).body(in);
    }


    // Retorna o JSON da conta ou um 404 not found
    @RequestMapping(value = BASE_ACCOUNT_URL + "{accountId}", method = GET)
    public HttpEntity<Account> getAccount(@PathVariable int accountId) {
        Account account = AccountRepository.findAccount(accountId);
        if (account != null) {
            return ResponseEntity.ok(account);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Retorna um array JSON das contas
    @RequestMapping(value = BASE_ACCOUNT_URL, method = GET)
    public HttpEntity<List<Account>> getAllAccount() {
        return ResponseEntity.ok(AccountRepository.getAllAccount());
    }

    // Retorna o JSON da conta inserida ou um 400 bad request
    @RequestMapping(value = BASE_ACCOUNT_URL, method = POST)
    public HttpEntity<Account> postAccount(@RequestBody Account account) {
        Account acc = AccountRepository.createAccount(account);
        if (acc != null) {
            return ResponseEntity.ok(acc);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    // Retorna o JSON da conta modificada ou um 400 bad request
    @RequestMapping(value = BASE_ACCOUNT_URL, method = PUT)
    public ResponseEntity putAccount(@RequestBody Account account) {
        Account acc = AccountRepository.updateAccount(account);
        if (acc != null) {
            return ResponseEntity.ok(acc);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    // Retorna o 200 ok se a conta for deletada ou um 404 bad request
    @RequestMapping(value = BASE_ACCOUNT_URL + "{accountId}", method = DELETE)
    public ResponseEntity deleteAccount(@PathVariable int accountId) {
        if (AccountRepository.removeAccount(accountId)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
