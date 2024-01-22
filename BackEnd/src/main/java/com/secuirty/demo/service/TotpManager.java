package com.secuirty.demo.service;

import dev.samstevens.totp.code.*;
import dev.samstevens.totp.exceptions.QrGenerationException;
import dev.samstevens.totp.qr.QrData;
import dev.samstevens.totp.qr.QrGenerator;
import dev.samstevens.totp.qr.ZxingPngQrGenerator;
import dev.samstevens.totp.secret.DefaultSecretGenerator;
import dev.samstevens.totp.secret.SecretGenerator;
import dev.samstevens.totp.time.SystemTimeProvider;
import dev.samstevens.totp.time.TimeProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import static dev.samstevens.totp.util.Utils.getDataUriForImage;

import java.util.Optional;

@Service
@Slf4j
public class TotpManager {
    Integer numberOfDigits;
    HashingAlgorithm typeOfAlgorithm;
    Integer period;

    public String generateSecret() {
        SecretGenerator generator = new DefaultSecretGenerator();
        return generator.generate();
    }

    public String getUriForImage(String secret, Optional<String> nrOfDigits, Optional<String> typeAlgorithm,
            Optional<String> periodOfOTP) {
        numberOfDigits = 6;
        typeOfAlgorithm = HashingAlgorithm.SHA1;
        period = 60;
        if (nrOfDigits.isPresent()) {
            numberOfDigits = Integer.parseInt(nrOfDigits.get());
        }
        if (typeAlgorithm.isPresent()) {
            switch (typeAlgorithm.get()) {
                case ("SHA-1"):
                typeOfAlgorithm = HashingAlgorithm.SHA1;
                    break;
               case ("SHA-256"):
                typeOfAlgorithm = HashingAlgorithm.SHA256;
                    break;
               case ("SHA-512"):
                typeOfAlgorithm = HashingAlgorithm.SHA512;
                    break;            
                default:
                    typeOfAlgorithm = HashingAlgorithm.SHA1;
                    break;
            }

        }
        if (periodOfOTP.isPresent()) {
            period = Integer.parseInt(periodOfOTP.get());
        } 
        QrData data = new QrData.Builder()
                .label("Two-factor-auth-test")
                .secret(secret)
                .issuer("Test Poliba TOTP")
                .algorithm(typeOfAlgorithm)
                .digits(numberOfDigits)
                .period(period)
                .build();

        System.out.println(data.getDigits());
        QrGenerator generator = new ZxingPngQrGenerator();
        byte[] imageData = new byte[0];

        try {
            imageData = generator.generate(data);
        } catch (QrGenerationException e) {
            log.error("unable to generate QrCode");
        }

        String mimeType = generator.getImageMimeType();

        return getDataUriForImage(imageData, mimeType);
    }

    public boolean verifyCode(String code, String secret) {
        TimeProvider timeProvider = new SystemTimeProvider();
        CodeGenerator codeGenerator = new DefaultCodeGenerator(HashingAlgorithm.SHA1, 8);
        CodeVerifier verifier = new DefaultCodeVerifier(codeGenerator, timeProvider);
        boolean result = verifier.isValidCode(secret, code);
        return result;
    }
}
