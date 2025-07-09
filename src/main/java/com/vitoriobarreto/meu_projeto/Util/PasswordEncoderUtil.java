package com.vitoriobarreto.meu_projeto.Util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordEncoderUtil {
    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String rawPassword = "pass"; // Sua senha em texto puro
        String encodedPassword = encoder.encode(rawPassword);
        System.out.println("Senha criptografada para '" + rawPassword + "': " + encodedPassword);

        rawPassword = "admin"; // Sua senha em texto puro para admin
        encodedPassword = encoder.encode(rawPassword);
        System.out.println("Senha criptografada para '" + rawPassword + "': " + encodedPassword);
    }
}