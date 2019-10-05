package encryptdecrypt.algorithm;

public class Unicode implements Algorithm {

    private final int MAX = '\uFFFF';
    private final int MIN = '\u0000';

    private char cipher(char a,int shift,boolean reverse){
        if(reverse){
            while (shift>0){
                int maxShift  = a - MAX;
                if(shift>maxShift){
                    shift -= (maxShift+1);
                    a = (char) MAX;
                }
                else{
                    a = (char)(a -shift);
                    shift = 0;
                }
            }
        }
        else{
            while (shift>0){
                int maxShift = MAX-a;
                if(shift>maxShift){
                    shift -= (maxShift+1);
                    a = (char)MIN;
                }
                else{
                    a = (char)(a +shift);
                    shift = 0;
                }
            }
        }
        return  a;
    }

    @Override
    public String encrypt(String str, int key) {
        char[] strArr = str.toCharArray();
        str = "";
        for (char character: strArr) {
            str += String.valueOf(cipher(character,key,false));
        }
        return str;
    }

    @Override
    public String decrypt(String str, int key) {
        char[] strArr = str.toCharArray();
        str = "";
        for (char character: strArr) {
            str += String.valueOf(cipher(character,key,true));
        }
        return str;
    }
}

