package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.repositories.CredentialRepository;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;

@Service
public class CredentialService {

    private CredentialRepository credentialRepository;
    private EncryptionService encryptionService;

    public CredentialService(CredentialRepository credentialRepository, EncryptionService encryptionService) {
        this.credentialRepository = credentialRepository;
        this.encryptionService = encryptionService;
    }

    private String generateRandomKey() {
        SecureRandom random = new SecureRandom();
        byte[] key = new byte[16];
        random.nextBytes(key);
        return Base64.getEncoder().encodeToString(key);
    }

    public List<Credential> getUserCredentials(Integer userId) {
        return credentialRepository.getUserCredentials(userId);
    }

    public Integer insert(Credential credential) {
        String key = generateRandomKey();
        credential.setKey(key);

        String hashedPassword = encryptionService.encryptValue(credential.getPassword(), key);
        credential.setPassword(hashedPassword);

        return credentialRepository.insert(credential);
    }

    public Integer update(Credential credential) {
        String key = generateRandomKey();
        credential.setKey(key);

        String hashedPassword = encryptionService.encryptValue(credential.getPassword(), key);
        credential.setPassword(hashedPassword);

        return credentialRepository.update(credential);
    }

    public Integer delete(Integer id) {
        return credentialRepository.delete(id);
    }
}
