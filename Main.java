package encryptdecrypt;

import encryptdecrypt.algorithm.Algorithm;
import encryptdecrypt.algorithm.Shift;
import encryptdecrypt.algorithm.Unicode;

import java.io.*;
import java.util.HashMap;


public class Main {
    private static HashMap<String, String> Params() {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("mode", "enc");
        params.put("data", "");
        params.put("key", "0");
        params.put("in", null);
        params.put("out", null);
        params.put("alg", "shift");
        return params;
    }

    private static void checkParam(String param, String value) throws Exception {
        char[] arr = value.toCharArray();
        if (arr[0] == '-')
            throw new Exception("forbidden symbol for value");
        if (param.equals("mode")) {
            if (!value.equals("enc") && !value.equals("dec"))
                throw new Exception("mode should be enc|dec");
        } else if (param.equals("key")) {
            try {
                double d = Double.parseDouble(value);
            } catch (NumberFormatException | NullPointerException nfe) {
                throw new Exception("key should to be number");
            }
        }
    }

    private static String readFile(String path) throws Exception {
        File file = new File(path);
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String str = null, line;
        while ((line = reader.readLine()) != null) {
            if (str == null)
                str = "";
            else
                str += "\n";
            str += line;
        }
        reader.close();
        return str;
    }

    private static void writeToFile(String str, String path) throws Exception {
        BufferedWriter writer = new BufferedWriter(new FileWriter(path));
        writer.write(str);
        writer.close();
    }

    public static void main(String[] args) {
        HashMap<String, String> params = Params();
        String currentKey = null;
        String str;
        try {
            for (String arg : args) {
                if (currentKey == null) {
                    currentKey = "";
                    char[] arr = arg.toCharArray();
                    boolean type = arr[0] == '-';
                    if (type) {
                        for (int i = 0; i < arr.length - 1; i++) {
                            currentKey += Character.toString(arr[i + 1]);
                        }
                    }
                } else {
                    if (!params.containsKey(currentKey))
                        throw new Exception("Wrong param " + currentKey);
                    checkParam(currentKey, arg);
                    params.replace(currentKey, arg);
                    currentKey = null;
                }
            }

            boolean reverse = params.get("mode").equals("dec");
            str = params.get("data");
            if (str.length() == 0) {
                String path = params.get("in");
                if (path == null)
                    throw new Exception("there is no data or file path");
                else
                    str = readFile(path);
            }
            int shift = Integer.parseInt(params.get("key"));
            String algorithm = params.get("alg");
            Algorithm method;
            switch (algorithm) {
                case "unicode":
                    method = Crypto.getUnicode();
                    break;
                case "shift":
                    method = Crypto.getShift();
                    break;
                default:
                    throw new Exception("there is no method");
            }
            str = (reverse) ? method.decrypt(str, shift) : method.encrypt(str, shift);
            String output = params.get("out");
            if (output == null)
                System.out.println(str);
            else
                writeToFile(str, output);
        } catch (Exception e) {
            throw new Error(e.getMessage());
        }
    }
}
