package character;

/**
 * CharacterType - An enum defining a PlayerCharacter's type.
 *
 * @author Cameron Hofbauer
 * @version September 28, 2022
 */

public enum CharacterType {
    DEFAULT     // Default Player Type
    ;

    public static CharacterType getCharacterType(String s) {
        if (s.compareTo("DEFAULT") == 0) return DEFAULT;

        return DEFAULT;
    }
}
