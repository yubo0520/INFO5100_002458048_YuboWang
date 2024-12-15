import javax.crypto.SecretKey;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;

public class Person {
    private String name;
    private SecretKey symmetricKey;
    private KeyPair asymmetricKeyPair;
    
    public Person(String name) {
        this.name = name;
    }
    
    public String getName() {
        return name;
    }
    
    public void setSymmetricKey(SecretKey key) {
        this.symmetricKey = key;
    }
    
    public SecretKey getSymmetricKey() {
        return symmetricKey;
    }
    
    public void setAsymmetricKeyPair(KeyPair keyPair) {
        this.asymmetricKeyPair = keyPair;
    }
    
    public PublicKey getPublicKey() {
        return asymmetricKeyPair.getPublic();
    }
    
    public PrivateKey getPrivateKey() {
        return asymmetricKeyPair.getPrivate();
    }
} 