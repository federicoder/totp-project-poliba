package com.secuirty.demo.service.dto;

import dev.samstevens.totp.code.HashingAlgorithm;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class Parameter {

    HashingAlgorithm algorithm = HashingAlgorithm.SHA1;
    int digits = 6;
    

    // GETTER & SETTER
    public HashingAlgorithm getAlgorithm() {
        return algorithm;
    }
    public int getNumberOfCode() {
        return digits;
    }

    public void setAlgorithm(String algorithm) {
            switch (algorithm) {
                case ("SHA-1" ):
                this.algorithm  = HashingAlgorithm.SHA1;
                    break;
                case ("SHA1" ):
                this.algorithm  = HashingAlgorithm.SHA1;
                    break;
               case ("SHA-256"):
               this.algorithm  = HashingAlgorithm.SHA256;
                    break;
               case ("SHA256"):
               this.algorithm  = HashingAlgorithm.SHA256;
                    break;
               case ("SHA-512"):
               this.algorithm  = HashingAlgorithm.SHA512;
                    break;
               case ("SHA512"):
               this.algorithm  = HashingAlgorithm.SHA512;
                    break;               
                default:
                this.algorithm  = HashingAlgorithm.SHA1;
                    break;
            }
    }
    public void setNumberOfCode(String digits) {
        this.digits = Integer.parseInt(digits);
    }
    public void setNumberOfCode(int digits) {
        this.digits = digits;
    }
}