package tn.esprit.autoloc;

public final class Utils {

    private Utils() {}

    // true si la chaîne est utilisable (ni null, ni vide, ni que des espaces)
    public static boolean isNotEmpty(String s) {
        return s != null && !s.trim().isEmpty();
    }
}