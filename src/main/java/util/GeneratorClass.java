package util;

import java.util.Random;


public class GeneratorClass {

    //this method generates Secret Key
    public static String Generate() {

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

}
	
		
	
    	
    	


    	
	    


