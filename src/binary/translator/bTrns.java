package binary.translator;

import java.util.ArrayList;


public class bTrns {
    
    //text to asci--
    public int[] tToa (String s){
        int[] d = new int[s.length()];
        for(int i = 0; i<s.length(); i++){
        d[i] = (char) s.charAt(i);
        }
        return d;
    }
    
    //asci to binary--
    public String aTob (int d){
        int cou=0;
        int[] b = {0, 0, 0, 0, 0, 0, 0, 0};
        while (d >= 1){
            b[cou] = d % 2;
            d = d/2;
            cou++;
        }
        String bs="";
        for (int i = 0; i < 8; i++) {
            bs=b[i]+""+bs;
        }
        return bs+" ";
    }
    
    //---------------------xxxxxxxxxx--------------------o
    
    //binary to string--
    public String[] bTos (String text){
        String s1 = "";
        String[] s2=new String [text.length()/8];
        int j=0;
        boolean sw=true;
        for (int i = 0; i<text.length(); i++){
            if(text.charAt(i)=='1' || text.charAt(i)=='0'){
                s1 += String.valueOf(text.charAt(i));
                sw=true;
                //System.out.print(""+text.charAt(i));
            }
            else{
                if(sw){
                    s2[j]=s1;
                    j++;
                    s1="";
                    sw=false;
                }
            }
        }
        return s2;
    }
    
    //string to asci--
    public int[] sToa(String[] ss){
        int cou=0, sum=0,p=7;
        ArrayList<Integer> anum = new ArrayList<Integer>(0);
        
        for(String te : ss){
           cou++;
        }
        
        for(int i=0; i<cou; i++){
            if(ss[i]!=null){
                for(int j=0; j<8; j++){
                    sum+=Integer.parseInt(Character.toString(ss[i].charAt(j)))*Math.pow(2, p);
                    p--;
                }
                anum.add(sum);
                sum=0;
                p=7;
            }
        }
        int[] num = new int[anum.size()];
        for(int i=0;i<anum.size();i++){
            num[i]=anum.get(i);
        }
        return num;
    }
    
    //asci to text
    public String aTot(int[] asn){
        String s="";
        for(int i=0;i<asn.length; i++){
            s += String.valueOf((char)asn[i]);
        }
        return s;
    }

}
