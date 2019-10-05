package bean;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public enum CipherBean {
    INSTANCE;
    IvParameterSpec ivParameterSpec;
    SecretKeySpec secretKeySpec;
    Cipher cipher;
    byte[] salt;


    //this method initializes the above variables
    public void setParameters(String key, String IV, String salt) throws NoSuchAlgorithmException, NoSuchPaddingException {


        // Get Cipher Instance
        this.cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");

        // Create SecretKeySpec
        this.secretKeySpec = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "AES");

        // Create IvParameterSpec
        this.ivParameterSpec = new IvParameterSpec(IV.getBytes(StandardCharsets.UTF_8));

        this.salt = salt.getBytes();
    }


    //this is the method for encryption
    public void encrypt(File file) throws Exception {

        String path = file.getAbsolutePath();//get path of file

        if (path.substring(path.length() - 4).equals(".enc")) {
            return;
        }

        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivParameterSpec);

        File destinationFile = new File(path.concat(".enc"));//creation of new file with .enc extension
        if (destinationFile.exists()) {
            if (destinationFile.delete())
                destinationFile = new File(path.concat(".enc"));
        }

        BufferedInputStream in = new BufferedInputStream(new FileInputStream(path));//read from this file
        FileOutputStream out = new FileOutputStream(destinationFile, true);//write to this file

        byte[] ibuf = new byte[1024];
        int len;
        while ((len = in.read(ibuf)) != -1) {
            byte[] obuf = cipher.update(ibuf, 0, len);
            if (obuf != null)
                out.write(obuf);
        }

        byte[] obuf = cipher.doFinal();
        if (obuf != null)
            out.write(obuf);//encrypted file is created
        in.close();
        out.close();

        File outFile = new File(path);
        if (outFile.exists()) {
            if (!outFile.delete())
                throw new Exception("File deletion failed");
            //original file is deleted
        }
    }


    //this method is used for decryption of files
    public void decrypt(File file) throws Exception {

        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivParameterSpec);

        String path;

        if (!file.getAbsolutePath().substring(file.getAbsolutePath().length() - 4).equals(".enc")) {
            path = file.getAbsolutePath().concat(".enc");
        } else path = file.getAbsolutePath();

        File destinationFile = new File(file.getAbsolutePath());
        if (destinationFile.exists()) {
            if (destinationFile.delete())
                destinationFile = new File(file.getAbsolutePath());
        }

        BufferedInputStream in = new BufferedInputStream(new FileInputStream(path));//read from encrypted file

        FileOutputStream out = new FileOutputStream(destinationFile, true);//write to original file

        byte[] ibuf = new byte[1024];
        int len;
        while ((len = in.read(ibuf)) != -1) {
            byte[] obuf = cipher.update(ibuf, 0, len);
            if (obuf != null)
                out.write(obuf);
        }
        byte[] obuf = cipher.doFinal();
        if (obuf != null) {
            out.write(obuf);//file is decrypted
        }
        in.close();
        out.close();

        File outFile = new File(path);
        if (outFile.exists())
            if (!outFile.delete())
                throw new Exception("File deletion failed");//encrypted file is deleted
    }

    //this method is for password hashing
    public String getSecurePassword(String passwordToHash) {
        String newPassword = null;
        MessageDigest md;
        try {
            // Select the message digest for the hash computation -> SHA-256
            md = MessageDigest.getInstance("SHA-256");

            // Passing the salt to the digest for the computation
            md.update(salt);

            // Generate the salted hash
            byte[] hashedPassword = md.digest(passwordToHash.getBytes(StandardCharsets.UTF_8));

            StringBuilder sb = new StringBuilder();
            for (byte b : hashedPassword) {
                sb.append(String.format("%02x", b));
            }
            newPassword = sb.toString();
            // System.out.println(newPassword);
        } catch (NoSuchAlgorithmException e) {

            e.printStackTrace();
        }
        return newPassword;
    }

}



