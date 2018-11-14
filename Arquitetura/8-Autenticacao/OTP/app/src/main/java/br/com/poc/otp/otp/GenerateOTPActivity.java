package br.com.poc.otp.otp;

import android.animation.Animator;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.spec.SecretKeySpec;

import br.com.poc.otp.PreferencesHelper;
import br.com.poc.otp.R;
import br.com.poc.otp.login.IUserService;
import br.com.poc.otp.service.WebModule;
import br.com.poc.otp.ui.CircleDisplay;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.schedulers.Schedulers;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static br.com.poc.otp.PreferencesHelper.ACCOUNT;
import static br.com.poc.otp.PreferencesHelper.HAS_TOKEN;
import static br.com.poc.otp.PreferencesHelper.SEED;

public class GenerateOTPActivity extends AppCompatActivity {

    private static final String symmetricSecret = "?c=RmC4[N<J'_H:B";
    private static final int DURATION = 36;

    private SecretKeySpec symmetricSecretKey;
    private Cipher cipher;
    private TextView tvContent;
    private Button btnSeed;
    private CircleDisplay circleDisplay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate_otp);

        btnSeed = findViewById(R.id.btn_seed);
        tvContent = findViewById(R.id.tv_content);

        configureAnimation();

        try {
            this.symmetricSecretKey = new SecretKeySpec(symmetricSecret.getBytes(StandardCharsets.UTF_8), "AES");
            this.cipher = Cipher.getInstance("AES");

            btnSeed.setOnClickListener(v -> {
                try {
                    Long seed = new Random().nextLong();
                    String base64Seed = encryptFile(String.valueOf(seed).getBytes());
                    WebModule
                            .create(IUserService.class)
                            .sendSeed(PreferencesHelper.getStringPreference(this, PreferencesHelper.HEADER), new SeedBody(PreferencesHelper.getStringPreference(this, PreferencesHelper.ACCOUNT), base64Seed))
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(() -> {
                                btnSeed.setVisibility(GONE);
                                circleDisplay.setVisibility(VISIBLE);
                                Toast.makeText(this, "Token gerado com sucesso!", Toast.LENGTH_LONG).show();
                                PreferencesHelper.saveBooleanPreference(this, HAS_TOKEN, true);
                                PreferencesHelper.saveLongPreference(this, SEED, seed);
                            }, throwable -> Toast.makeText(this, "Houve um erro ao processar sua solicitação!", Toast.LENGTH_LONG).show());
                } catch (Exception ignored) {
                }
            });

        } catch (Exception ignored) {
        }
    }

    private void generatePassword() {
        String account = PreferencesHelper.getStringPreference(this, ACCOUNT);
        try {
            if (PreferencesHelper.getBoolean(this, HAS_TOKEN)) {

                Long seed = PreferencesHelper.getLongPreference(this, SEED);
                TokenGenerator generator = new TokenGenerator(seed, account);

                tvContent.setText(String.format(getString(R.string.token), generator.generate().getNextToken()));

                controlAnimation();
            } else {
                btnSeed.setVisibility(VISIBLE);
                circleDisplay.setVisibility(GONE);
            }
        } catch (NoSuchAlgorithmException | InvalidKeyException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    private void configureAnimation() {
        circleDisplay = findViewById(R.id.circleDisplay);
        circleDisplay.setAnimDuration((int) TimeUnit.SECONDS.toMillis(DURATION));
        circleDisplay.setStepSize(10);
        circleDisplay.setFormatDigits(0);
        circleDisplay.setUnit("s");
        circleDisplay.setColor(getResources().getColor(R.color.red));
    }

    private void controlAnimation() {
        circleDisplay.getmDrawAnimator().addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                generatePassword();
            }

            @Override
            public void onAnimationCancel(Animator animation) {
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
            }
        });
        circleDisplay.showValue(DURATION, DURATION, true);
    }

    private String encryptFile(byte[] data)
            throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        this.cipher.init(Cipher.ENCRYPT_MODE, this.symmetricSecretKey);
        return Base64.encodeToString(this.cipher.doFinal(data), Base64.NO_WRAP);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @android.support.annotation.NonNull @NonNull String permissions[], @android.support.annotation.NonNull @NonNull int[] grantResults) {
    }

    @Override
    protected void onResume() {
        super.onResume();
        WebModule
                .create(IUserService.class)
                .hasToken(PreferencesHelper.getStringPreference(this, PreferencesHelper.HEADER), PreferencesHelper.getStringPreference(this, PreferencesHelper.ACCOUNT))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> {
                    btnSeed.setVisibility(GONE);
                    circleDisplay.setVisibility(VISIBLE);
                    tvContent.setVisibility(VISIBLE);
                    generatePassword();
                }, throwable -> {
                    btnSeed.setVisibility(VISIBLE);
                    circleDisplay.setVisibility(GONE);
                    tvContent.setVisibility(GONE);
                });
    }
}
