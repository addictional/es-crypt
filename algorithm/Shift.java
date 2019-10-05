package encryptdecrypt.algorithm;

public class Shift implements Algorithm {
    private final int MAX_CHAR_L = 122;
    private final int MIN_CHAR_L = 97;
    private final int MAX_CHAR_U = 90;
    private final int MIN_CHAR_U = 65;

    private char cipher(char a,int shift,boolean reverse){
        if((a >MAX_CHAR_L || a < MIN_CHAR_L)&&(a >MAX_CHAR_U || a < MIN_CHAR_U))
            return a;
        int max,min;
        boolean lowerCase =  a <= MAX_CHAR_L && a >= MIN_CHAR_L;
        max = lowerCase ? MAX_CHAR_L: MAX_CHAR_U;
        min = lowerCase ? MIN_CHAR_L: MIN_CHAR_U;
        int radius = max-min;
        int center = min+radius/2 + (radius%2);
        int charPosition = a;
        if(reverse){
            if(charPosition == center)
                return 'i';
            while (shift>0){
                int maxShift = Math.abs(min-a);
                if(maxShift>=shift){
                    a = (char)(a-shift);
                    shift = 0;
                }else{
                    shift -=(maxShift+1);
                    a= (char)max;
                }
            }
        }else{
            if(charPosition == center)
                return 'm';
            while (shift>0){
                int maxShift = max-a;
                if(maxShift>=shift){
                    a = (char)(a+shift);
                    shift = 0;
                }else{
                    shift -=(maxShift+1);
                    a= (char)min;
                }
            }
        }
        return a;
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
