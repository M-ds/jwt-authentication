package nl.pcsw.auth.text;

public class TextUtils {

    public static boolean hasText(String stringToCheck) {
        if (stringToCheck == null || stringToCheck.trim().isEmpty()) return false;
        return containsText(stringToCheck);
    }

    public static boolean isNullOrBlank(String string) {
        return string == null || string.trim().isBlank();
    }

    private static boolean containsText(String string) {
        int length = string.length();

        for (int i = 0; i < length; i++) {
            if (!Character.isWhitespace(string.toCharArray()[i])) return true;
        }
        return false;
    }
}
