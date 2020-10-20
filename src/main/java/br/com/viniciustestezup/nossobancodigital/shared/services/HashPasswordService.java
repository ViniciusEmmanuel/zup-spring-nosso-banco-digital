package br.com.viniciustestezup.nossobancodigital.shared.services;

import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import org.springframework.stereotype.Component;

@Component
public class HashPasswordService {
    private static final int iterations = 8;
    private static final int memory = 65536;
    private static final int parallelism = 1;

    public static String hash(String password) {
        Argon2 argon2 = Argon2Factory.create();
        return argon2.hash(iterations, memory, parallelism, password.getBytes());
    }

    public static Boolean comparaHashPassword(String passwordSemHash, String passwordHash) {
        Argon2 argon2 = Argon2Factory.create();
        return argon2.verify(passwordHash, passwordSemHash.getBytes());
    }
}
