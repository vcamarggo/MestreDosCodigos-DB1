package br.com.poc.otp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import br.com.poc.otp.PreferencesHelper;
import br.com.poc.otp.R;
import br.com.poc.otp.login.LoginActivity;
import br.com.poc.otp.otp.GenerateOTPActivity;

import static br.com.poc.otp.PreferencesHelper.HAS_TOKEN;

/**
 * Created by vinicius.camargo on 02/10/2018
 */
public class SplashScreenActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);
        new Handler().postDelayed(this::startApplication, 1000);
    }


    private void startApplication() {
        boolean hasToken = PreferencesHelper.getBoolean(this, HAS_TOKEN);
        startActivity(new Intent(this, hasToken ? GenerateOTPActivity.class : LoginActivity.class));
    }

}
