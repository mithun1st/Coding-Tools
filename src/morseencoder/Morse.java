package morseencoder;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

/**
 * The basic class (as an enum class) that encapsulates the logic of the converter
 * The decision to utilize an enum was due to the restricted and standardized number of symbols
 * used in the mapping to the equivalent encoded strings. The enum class is versatile and fast 
 * performance-wise, thus a decision to use it as a foundation for such a conversion is justified.
 * 
 * @author Athanasios Kontis
 */
public enum Morse {
    A (".-",    "a"),    B ("-...",  "b"),    C ("-.-.",  "c"),    D ("-..",   "d"),    E (".",     "e"),
    F ("..-.",  "f"),    G ("--.",   "g"),    H ("....",  "h"),    I ("..",    "i"),    J (".---",  "j"),
    K ("-.-",   "k"),    L (".-..",  "l"),    M ("--",    "m"),    N ("-.",    "n"),    O ("---",   "o"),
    P (".--.",  "p"),    Q ("--.-",  "q"),    R (".-.",   "r"),    S ("...",   "s"),    T ("-",     "t"),
    U ("..-",   "u"),    V ("...-",  "v"),    W (".--",   "w"),    X ("-..-",  "x"),    Y ("-.--",  "y"),
    Z ("--..",  "z"),    N0("-----", "0"),    N1(".----", "1"),    N2("..---", "2"),    N3("...--", "3"),
    N4("....-", "4"),    N5(".....", "5"),    N6("-....", "6"),    N7("--...", "7"),    N8("---..", "8"),
    N9("----.", "9");
    
    private final String encoded, plain;
    
    // static Map used exclusively for the decoding of "Morsified" text to plain text
    static final Map<String, String> morseToPlainMap = new HashMap<>(36);
    
    // Static block for Map initializarion
    static {
        for (Morse m: Morse.values()) {
            morseToPlainMap.put(m.encoded, m.plain);
        }
    }
    
    Morse (String encodedValue, String plainValue) {
        encoded = encodedValue;
        plain   = plainValue;
    }

    /**
     * Utility method that receives a string of plain text, sanitizes it by removing all excess spaces and 
     * subsequentely all characters that are not digits or latin letters, and converts it to a 
     * "morsified" version according to the native mappings within this enum class.
     * 
     * @param plainText the plain text to be converted to Morse code
     * @return A string containing the encoded text in Morse
     */
    public static String encode(String plainText) {
        StringBuilder encoded = new StringBuilder(plainText.length());
        String stringifiedPlainChar;
        String sanitizedText = plainText.trim().replace("\\s{2,}", " ").replaceAll("[^a-zA-Z0-9 ]", "").toUpperCase();
        for (char plainChar : sanitizedText.toCharArray()) {
            if (plainChar == ' ') {
                encoded.append("   ");  // encode every single space in plain text to 3 spaces
            }
            else {
                stringifiedPlainChar = Character.isDigit(plainChar) ? "N" + plainChar : String.valueOf(plainChar);
                encoded.append(Morse.valueOf(stringifiedPlainChar).encoded).append(' ');
            }
        }
        return encoded.toString();
    }
    
    /**
     * Decodes a string of Morse encoded text to a string of plain text according to the mappings available in 
     * the static HashMap collection 'morseToPlainMap'. We use a scanner object for tokenization of the string 
     * to "morse words" using a at-least-3-space string as a word delimiter (by convention). Each word is then
     * split again using a single-space delimiter to individual symbol-tokens which by turn are scanned for 
     * known "Morse" symbol mappings within the morseToPlainMap collection, in order to receive the decoded symbol.
     * 
     * @param encodedText A "morsified" string of text to be decoded back to plain text
     * @return A string containing the decoded text
     */
    public static String decode(String encodedText) {
        StringBuilder decoded = new StringBuilder(encodedText.length());
        try (Scanner scanner = new Scanner(encodedText)) {
            String morseWordPattern = "([.-]+\\s?)+";
            Set<String> morseKeys = morseToPlainMap.keySet();
            scanner.useDelimiter("\\s{3,}"); // 3-space delimiter by convention
            String morseWord;
            while (scanner.hasNext()) {
                if (scanner.hasNext(morseWordPattern)) {
                    morseWord = scanner.next(morseWordPattern);
                    for (String possibleMorseLetter : morseWord.split("\\s")) {
                        if (morseKeys.contains(possibleMorseLetter)) {
                            decoded.append(morseToPlainMap.get(possibleMorseLetter));
                        }
                        else {
                            decoded.append(possibleMorseLetter);
                        }
                    }
                    decoded.append(' ');    // add a space after each decoded word
                }
                else {
                    decoded.append(scanner.next());
                }
            }
        }
        return decoded.toString().trim();
    }
}
