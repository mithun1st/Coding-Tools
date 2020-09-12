package calculate;


public class Acls {
    
    //binary
    public static String b2d(String s){
        String binaryString=s;
        int decimal=Integer.parseInt(binaryString,2);
        return decimal+"";
    }
    
    public static String b2o(String s){
        String number = s;
	int bnum = Integer.parseInt(number, 2);
	String ostr = Integer.toOctalString(bnum);
        return ostr+"";
    }
    
    public static String b2h (String s){
        String str = "";
        int[] hex = new int[1000];
        int i = 1, j = 0, rem, dec = 0, bin;
        bin = Integer.parseInt(s);
        while (bin > 0) {
            rem = bin % 2;
            dec = dec + rem * i;
            i = i * 2;
            bin = bin / 10;
        }
        i = 0;
        while (dec != 0) {
            hex[i] = dec % 16;
            dec = dec / 16;
            i++;
        }
        for (j = i - 1; j >= 0; j--)
        {
            if (hex[j] > 9)
            {
                str+=(char)(hex[j] + 55)+"";
            } else
            {
                str+=hex[j]+"";
            }
        }
        return str;
    }
    
    //octal
    public static String o2b(String s){
        int[] octal_numvalues = {0, 1, 10, 11, 100, 101, 110, 111};
        long octal_num, tempoctal_num, binary_num, place;
        int rem;
        octal_num = Integer.parseInt(s);
        tempoctal_num = octal_num;
        binary_num = 0;
        place = 1;
        while (tempoctal_num != 0)
        {
            rem = (int)(tempoctal_num % 10);
            binary_num = octal_numvalues[rem] * place + binary_num;
            tempoctal_num /= 10;
            place *= 1000;
        }
        return binary_num+"";
    }
 
    public static String o2d(String s){
        String octalString=s;
        int decimal=Integer.parseInt(octalString,8);
        return decimal+"";
    }
 
    public static String o2h(String s){
        String octal_num, hex_num;
        int decnum;
		
        octal_num = s;
		
        decnum = Integer.parseInt(octal_num, 8);
        hex_num = Integer.toHexString(decnum);
		
        return hex_num.toUpperCase();
    }
    
    //decimal
    public static String d2b(String s){
      int dec = Integer.parseInt(s);
      String bin = Integer.toBinaryString(dec);
      return bin;
    }

    public static String d2o(String s){
        int rem,decimal=Integer.parseInt(s); 
        String octal="";   
        char octalchars[]={'0','1','2','3','4','5','6','7'};  
        while(decimal>0)  
        {  
           rem=decimal%8;   
           octal=octalchars[rem]+octal;   
           decimal=decimal/8;  
        }  
        return octal; 
    }

    public static String d2h(String s){
        int dec = Integer.parseInt(s);
        return Integer.toHexString(dec).toUpperCase();
    }
    
    //hexa
    public static String h2b(String s){  
        String digits = "0123456789ABCDEF";
        s = s.toUpperCase();
        int val = 0;
        for (int i = 0; i < s.length(); i++)
        {
            char c = s.charAt(i);
            int d = digits.indexOf(c);
            val = 16*val + d;
        }        
        String bin = Integer.toBinaryString(val);
        return bin;
    }
 
    public static String h2d(String s){
        int decimal=Integer.parseInt(s,16);
        return decimal+"";
    }
 
    public static String h2o(String s){
        String digits = "0123456789ABCDEF";
        s = s.toUpperCase();
        int val = 0;
        for (int i = 0; i < s.length(); i++)
        {
            char c = s.charAt(i);
            int d = digits.indexOf(c);
            val = 16*val + d;
        }
        int rem,decimal=val; 
        String octal="";   
        char octalchars[]={'0','1','2','3','4','5','6','7'};  
        while(decimal>0)  
        {  
           rem=decimal%8;   
           octal=octalchars[rem]+octal;   
           decimal=decimal/8;  
        }  
        return octal; 
        
    }
 

}
