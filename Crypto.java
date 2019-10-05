package encryptdecrypt;
import encryptdecrypt.algorithm.*;

public class Crypto {
    public static Algorithm getShift()
    {
        return new Shift();
    }

    public static Algorithm getUnicode()
    {
        return new Unicode();
    }
}
