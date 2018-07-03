package src;


import dao.AccountRepository;
import model.Account;
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
public class AccountController {

    private final String BASE_ACCOUNT_URL = "/account/";

    // Retorna o JSON da conta ou um 404 not found
    @RequestMapping(value = BASE_ACCOUNT_URL + "{accountId}", method = RequestMethod.GET)
    public HttpEntity<Account> getAccount(@PathVariable int accountId) {
        Account account = AccountRepository.findAccount(accountId);
        if (account != null) {
            return ResponseEntity.ok(account);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Retorna um array JSON das contas
    @RequestMapping(value = BASE_ACCOUNT_URL, method = RequestMethod.GET)
    public HttpEntity<List<Account>> getAllAccount() {
        return ResponseEntity.ok(AccountRepository.getAllAccount());
    }

    // Retorna o JSON da conta inserida ou um 400 bad request
    @RequestMapping(value = BASE_ACCOUNT_URL, method = RequestMethod.POST)
    public HttpEntity<Account> postAccount(@RequestBody Account account) {
        Account acc = AccountRepository.createAccount(account);
        if (acc != null) {
            return ResponseEntity.ok(acc);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    // Retorna o JSON da conta modificada ou um 400 bad request
    @RequestMapping(value = BASE_ACCOUNT_URL, method = RequestMethod.PUT)
    public ResponseEntity putAccount(@RequestBody Account account) {
        Account acc = AccountRepository.updateAccount(account);
        if (acc != null) {
            return ResponseEntity.ok(acc);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    // Retorna o 200 ok se a conta for deletada ou um 404 bad request
    @RequestMapping(value = BASE_ACCOUNT_URL + "{accountId}", method = RequestMethod.DELETE)
    public ResponseEntity deleteAccount(@PathVariable int accountId) {
        if (AccountRepository.removeAccount(accountId)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
