import javax.crypto.SecretKey;

public class Main {
    public static void main(String[] args) {
        try {
            Person alice = new Person("Alice");
            Person bob = new Person("Bob");

            // Generates and assigns symmetric key
            SecretKey sharedKey = CryptoUtils.generateAESKey();
            alice.setSymmetricKey(sharedKey);
            bob.setSymmetricKey(sharedKey);

            // Generates and assigns asymmetric key pairs
            alice.setAsymmetricKeyPair(CryptoUtils.generateRSAKeyPair());
            bob.setAsymmetricKeyPair(CryptoUtils.generateRSAKeyPair());

            // Demonstrates symmetric encryption
            System.out.println("\n---- Symmetric Encryption Demo ----");
            String message = "Hello, Bob!";
            byte[] encrypted = CryptoUtils.encryptAES(message.getBytes(), alice.getSymmetricKey());
            byte[] decrypted = CryptoUtils.decryptAES(encrypted, bob.getSymmetricKey());
            System.out.println("Original message: " + message);
            System.out.println("Decrypted message: " + new String(decrypted));

            // Demonstrates asymmetric encryption
            System.out.println("\n---- Asymmetric Encryption Demo ----");
            String secretMessage = "This is a secret message!";
            byte[] rsaEncrypted = CryptoUtils.encryptRSA(secretMessage.getBytes(), bob.getPublicKey());
            byte[] rsaDecrypted = CryptoUtils.decryptRSA(rsaEncrypted, bob.getPrivateKey());
            System.out.println("Original message: " + secretMessage);
            System.out.println("Decrypted message: " + new String(rsaDecrypted));

            // Demonstrates digital signature
            System.out.println("\n---- Digital Signature Demo ----");
            String signedMessage = "This message needs to be signed.";
            byte[] signature = CryptoUtils.sign(signedMessage.getBytes(), alice.getPrivateKey());
            boolean isValid = CryptoUtils.verifySignature(
                signedMessage.getBytes(), 
                signature, 
                alice.getPublicKey()
            );
            System.out.println("Message: " + signedMessage);
            System.out.println("Signature verification: " + (isValid ? "valid" : "invalid"));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
