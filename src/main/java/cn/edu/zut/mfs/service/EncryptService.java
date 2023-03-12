package cn.edu.zut.mfs.service;

public interface EncryptService {
    String getLoginPublicKey();

    String transformer(String username, String publicKey, String password);
}
