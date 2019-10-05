package encryptdecrypt.algorithm;

/**
 * Interface for algo
 */
public interface Algorithm {

    public String encrypt(String str, int key);

    public String decrypt(String str, int key);
}
