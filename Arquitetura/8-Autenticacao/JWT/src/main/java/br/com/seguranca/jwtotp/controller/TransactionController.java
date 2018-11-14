package br.com.seguranca.jwtotp.controller;

import br.com.seguranca.jwtotp.dto.SeedDTO;
import br.com.seguranca.jwtotp.dto.TransactionDTO;
import br.com.seguranca.jwtotp.otp.TokenGenerator;
import br.com.seguranca.jwtotp.repository.SeedRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sun.misc.BASE64Decoder;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

@RestController
@RequestMapping("/banking/transacao")
public class TransactionController {

    @Autowired
    SeedRepository seedRepository;

    private static final String symmetricSecret = "?c=RmC4[N<J'_H:B";

    @PostMapping
    public ResponseEntity validateTransaction(@RequestBody TransactionDTO transactionDTO) throws Exception {
        Optional<SeedDTO> seedDTO = seedRepository.findById(transactionDTO.getAccount());
        if (!seedDTO.isPresent()) {
            return ResponseEntity.notFound().build();
        } else {
            String seed = seedDTO.get().getSeed();
            if (new TokenGenerator(decrypt(seed), transactionDTO.getAccount()).generate().isValid(transactionDTO.getOtp())) {
                return ResponseEntity.ok().build();
            } else {
                return ResponseEntity.status(401).build();
            }
        }
    }

    private Long decrypt(String encryptedValue) throws Exception {
        Cipher c = Cipher.getInstance("AES");
        SecretKeySpec symmetricSecretKey = new SecretKeySpec(symmetricSecret.getBytes(StandardCharsets.UTF_8), "AES");
        c.init(Cipher.DECRYPT_MODE, symmetricSecretKey);
        byte[] decodedValue = new BASE64Decoder().decodeBuffer(encryptedValue);
        byte[] decValue = c.doFinal(decodedValue);
        return Long.valueOf(new String(decValue));
    }
}
