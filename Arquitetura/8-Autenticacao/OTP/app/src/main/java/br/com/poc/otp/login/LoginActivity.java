package br.com.poc.otp.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import br.com.poc.otp.PreferencesHelper;
import br.com.poc.otp.R;
import br.com.poc.otp.otp.GenerateOTPActivity;
import br.com.poc.otp.service.WebModule;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import static br.com.poc.otp.PreferencesHelper.ACCOUNT;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final EditText etAccount = findViewById(R.id.et_account);
        final EditText etPassword = findViewById(R.id.et_password);
        final Button btnLogin = findViewById(R.id.btn_login);

        btnLogin.setOnClickListener(v -> {
            String account = etAccount.getText().toString();
            String password = etPassword.getText().toString();

            WebModule
                    .create(IUserService.class)
                    .doLogin(account, password)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            loginResponse -> {
                                PreferencesHelper.saveStringPreference(this, ACCOUNT, account);
                                startActivity(new Intent(this, GenerateOTPActivity.class));
                            },
                            throwable -> Toast.makeText(this, "HOUVE UM ERRO AO PROCESSAR SOLICITACAO", Toast.LENGTH_LONG).show());
        });
    }

}
