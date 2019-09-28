package util;

import java.security.SecureRandom;
import java.util.Random;


public class GeneratorClass {

    //this method generates Secret Key
    public static String GenerateKey() {

        String base = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&";
        StringBuilder sb = new StringBuilder();
        String Result;
        for (int i = 0; i < 16; i++) {
            Random r = new Random();
            int index = r.nextInt(base.length());
            char c = base.charAt(index);
            sb.append(c);
        }
        Result = sb.toString();
        return (Result);
    }

    //this method generates IVKey
    public static String GenerateIV() {
        String base = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&";
        StringBuilder sb = new StringBuilder();
        String Result;
        for (int i = 0; i < 16; i++) {
            Random r = new Random();
            int index = r.nextInt(base.length());
            char c = base.charAt(index);
            sb.append(c);
        }
        Result = sb.toString();
        return (Result);
    }

    //this method generates salt
    public static byte[] GenerateSalt() {
        SecureRandom r3 = new SecureRandom();
        byte[] salt = new byte[16];
        r3.nextBytes(salt);
        return (salt);
    }

}
	
		
	
    	
    	


    	
	    


