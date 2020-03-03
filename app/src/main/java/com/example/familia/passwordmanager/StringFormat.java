package com.example.familia.passwordmanager;


import java.text.Normalizer;

public class StringFormat {

    static String userName;
    static String websiteName;

    private static String symbols = "-_.+!#$%&'*=?^|~0123456789";

    private static boolean stringIncludesWebsiteName(String userString){
        //for(int i=userString.length()-1;i>0;i--)
        return false;

    }

    private static String stringRemoveDomain(String string) {
        String domainList[] = {"com", "org", "edu", "gov", "mil", "net", "int"};

        for (int i = string.length() - 1; i > 0; i--) {
            if ((string.charAt(i) == '.') && (i + 4 <= string.length())) {

                for (int j = 0; j < 7; j++) {

                    if (string.substring(i + 1, i + 4).equals(domainList[j])) {

                        return string.substring(0, i);

                    }
                }
            }
        }
        return string;
    }

    private static String stringNormalize(String string) {

        String charList = symbols;

        String finalString = "";
        char character;

        string = Normalizer.normalize(string, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
        string = string.toLowerCase();

        for (int i = 0; i < string.length(); i++) {
            character = Character.toLowerCase(string.charAt(i));
            if (character > 96 && character < 123) {
                finalString += character;
            } else {

                if (charList.contains(String.valueOf(character))) {
                    finalString += character;

                }
            }

        }

        return finalString;

    }

    public static String userStringStandardize(String string) {

        String finalString = "";

        for (int i = 0; i < string.length(); i++) {
            if (string.charAt(i) == '@') {
                return stringNormalize(finalString);
            }
            finalString += string.charAt(i);
        }
        return stringNormalize(finalString);
    }

    public static String getWebSiteName(String userString){


        String finalString = "";

        boolean characterExists = false;

        for (int i = 0; i < userString.length(); i++) {
            if (characterExists)
            {
                finalString += userString.charAt(i);
            }
            if (userString.charAt(i) == '@' || userString.charAt(i) == '#') {
                characterExists=true;
            }

        }

        if (characterExists) {
            return websiteStringStandardize(finalString);
        }
        else return "";

    }

    public static String websiteStringStandardize(String string) {

        string = stringRemoveDomain(string);

        for (int i = string.length() - 1; i > 0; i--) {
            if (string.charAt(i) == '.') {
                return string.substring(i + 1, string.length());
            }
        }

        return stringNormalize(string);

    }

    public static String keyStringStandardize(String string) {
        return Normalizer.normalize(string, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");

    }

}
