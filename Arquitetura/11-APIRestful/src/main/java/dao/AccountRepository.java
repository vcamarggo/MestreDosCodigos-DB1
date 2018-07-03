package dao;

import model.Account;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by vinicius.camargo on 02/07/2018
 */
public class AccountRepository {

    private static int lastId = 1;

    private static List<Account> accounts = new ArrayList<>(Arrays.asList(
            new Account(lastId++, 1000.0, ManagerRepository.findManager("Mary Josh")),
            new Account(lastId++, 3050.0, ManagerRepository.findManager("Ben Hoover")),
            new Account(lastId++, 700.0, ManagerRepository.findManager("Mary Josh"))));

    // Busca uma conta pelo id
    public static Account findAccount(int id) {
        return accounts.stream().filter(account -> account.id == id).findFirst().orElse(null);
    }

    // Cria uma nova conta
    public static Account createAccount(Account account) {
        account.id = lastId++;
        accounts.add(account);
        return account;
    }

    // Busca todas as contas
    public static List<Account> getAllAccount() {
        return accounts;
    }

    // Atualiza o saldo se o id da conta e seu gerente existirem
    public static Account updateAccount(Account accountUpdate) {
        Account account = accounts.stream().filter(acc -> acc.id == accountUpdate.id).findFirst().orElse(null);
        if (account == null || !accountUpdate.manager.equals(account.manager)) {
            return null;
        } else {
            account.setAmount(accountUpdate.amount);
            return account;
        }
    }

    public static boolean removeAccount(int accountId) {
        return accounts.removeIf(acc -> acc.id == accountId);
    }
}
