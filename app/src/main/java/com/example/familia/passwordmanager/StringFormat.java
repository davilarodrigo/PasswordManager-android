package com.example.familia.passwordmanager;

import java.text.Normalizer;

public class StringFormat {

    private static String symbols = "-_.+!#$%&'*=?^|~0123456789";

    public static boolean stringIncludesWebsiteName(String userString){

        if (getWebSiteName(userString).isEmpty())

        return false; else return true;

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

    public static String formatUserString(String string) {

        String finalString = "";

        for (int i = 0; i < string.length(); i++) {
            if (string.charAt(i) == '@') {
                return stringNormalize(finalString);
            }
            finalString += string.charAt(i);
        }
        return stringNormalize(finalString);
    }

    public static String removeWebsiteNameFromString(String userString){

        for (  int i = 0; i<userString.length();i++){
            if (userString.charAt(i) == ' ') {
                return userString.substring(i+1,userString.length());
            }}

        return userString;
    }

    public static String getWebSiteName(String userString){



        String websiteName="";

        for (int i = 0; i < userString.length(); i++) {

            if (userString.charAt(i) == ' ' && i+1!= userString.length())
                return formatWebsiteString(websiteName);

            websiteName += userString.charAt(i);
        }
        return "";

    }

    public static String formatWebsiteString(String string) {

        string = stringRemoveDomain(string);

        for (int i = string.length() - 1; i > 0; i--) {
            if (string.charAt(i) == '.') {
                return string.substring(i + 1, string.length());
            }
        }

        return stringNormalize(string);

    }

    public static String formatKeywordString(String string) {
        return Normalizer.normalize(string, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");

    }

}
