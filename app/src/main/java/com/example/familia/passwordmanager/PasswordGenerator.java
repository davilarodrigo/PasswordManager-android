package com.example.familia.passwordmanager;

public class PasswordGenerator {

    private static String vowels = "aeiou";
    private static String numbers = "4310u";
    private static String consonants = "bcdfghjklmnpqrstvwxyz";

    public static String generate(String user, String web, String key, int type) {

        /* 1:shortpin 2:longpin 3:keyword 4:easy 5:avg 6:hard
         */
        String seed;

        user = StringFormat.formatUserString(user);
        web = StringFormat.formatWebsiteString(web);
        key = StringFormat.formatKeywordString(key);

        seed = StringEncryption.encodeHexSHA1(user + web + key);

        switch (type) {
            case 1:
                return pin(seed, 4); //short pin
            case 2:
                return pin(seed, 8); //long pin
            case 3:
                return keyword(seed);
            case 4:
                return simplePassword(seed);
            case 5:
                return averagePassword(seed);
            case 6:
                return StrongPassword(seed);
        }

        return "";
    }

    private static int lengthGet(String seed, int min, int max) {
        int finalValue = 0;

        for (int i = 0; i < seed.length(); i++) {
            finalValue += seed.charAt(i);
        }

        finalValue = finalValue % (max + 1 - min);

        return finalValue + min;

    }

    private static String pin(String seed, int length) {

        String pin = "";

        while (pin.length() < length) {

            for (int i = 0; i < seed.length(); i++) {
                if (Character.isDigit(seed.charAt(i))) {
                    pin += seed.charAt(i);

                    if (pin.length() == length) {
                        return pin;
                    }
                }
            }
            if (pin.length() == 0) {
                return "0";

            }
        }

        return pin;
    }

    private static String keyword(String seed) {

        int length = lengthGet(seed, 7, 11);

        String finalString = "";

        while (true) {
            for (int i = 0; i < seed.length(); i++) {
                if (i % 2 == 0) {
                    finalString += consonants.charAt(seed.charAt(i) % consonants.length());
                } else {
                    finalString += vowels.charAt(seed.charAt(i) % vowels.length());
                }
                if (finalString.length() == length) {
                    return finalString;
                }
            }
            seed = finalString;
        }

    }

    private static String simplePassword(String seed) {
        String pin;
        int length = lengthGet(seed, 7, 10);
        String finalString = "";

        finalString += consonants.charAt(seed.charAt(1) % consonants.length());
        finalString = finalString.toUpperCase();

        while (true) {
            for (int i = 0; i < seed.length(); i++) {
                if (i % 2 == 1) {
                    finalString += consonants.charAt(seed.charAt(i) % consonants.length());
                } else {
                    finalString += vowels.charAt(seed.charAt(i) % vowels.length());
                }
                if (finalString.length() == length) {

                    length = lengthGet(seed + finalString, 1, 4);
                    pin = pin(seed + finalString, 20).substring(8, 8 + length);
                    return finalString + pin;
                }
            }
            seed = finalString;
        }

    }

    private static String averagePassword(String seed) {

        int length = lengthGet(seed, 10, 16);
        String finalString = "";
        char ch;

        while (true) {
            for (int i = seed.length() - 1; i >= 0; i--) {
                if (i % 2 == 1) {
                    ch = consonants.charAt(seed.charAt(i) % consonants.length());

                } else {
                    ch = numbers.charAt(seed.charAt(i) % numbers.length());
                }

                if ((i + ch) % 3 == 0) {
                    finalString += String.valueOf(ch).toUpperCase();
                } else {
                    finalString += String.valueOf(ch);
                }

                if (finalString.length() == length) {

                    return finalString;
                }
            }
            seed = finalString;
        }

    }

    private static String StrongPassword(String seed) {
        int length = lengthGet(seed, 20, 30);
        String charList = vowels + consonants + "0123456789-%&*=?_.+!#$";
        String finalString = "";
        char ch;
        int j=1;

        for (int i = 0; i < length; i++) {
            ch = charList.charAt((seed.charAt(i)*j) % charList.length());
            j=seed.charAt(i);

            if ((i + ch) % 3 == 0) {
                finalString += String.valueOf(ch).toUpperCase();
            } else {
                finalString += String.valueOf(ch);
            }
        }

        return finalString;

    }
}
