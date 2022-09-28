/**
 * PlayerCharacter - A class which models a user-controlled character and contains attributes for a Character.
 *
 * @author Cameron Hofbauer
 * @version September 28, 2022
 */

public class PlayerCharacter extends Character {
    private CharacterType characterType;    // Player Character Type
    // private Item startingItem            // Player Starting Item
                                            // TODO: Implement Item class
    private Inventory inventory;            // Player Inventory

    public PlayerCharacter() {
        super();
        this.characterType = CharacterType.DEFAULT;
        this.inventory = new Inventory();
    }


    public CharacterType getCharacterType() {
        return characterType;
    }

    public void setCharacterType(CharacterType characterType) {
        this.characterType = characterType;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    // TODO create getter and setter method for startingItem
}
