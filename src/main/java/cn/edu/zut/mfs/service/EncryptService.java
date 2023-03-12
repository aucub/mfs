package cn.edu.zut.mfs.service;

public interface EncryptService {
    String getPublicKey();

    String transformer(String username, String publicKey, String password);
}
