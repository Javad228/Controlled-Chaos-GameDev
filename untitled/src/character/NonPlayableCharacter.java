package character;

import combat.DamageType;

/**
 * NonPlayableCharacter - An abstract class which defines attributes for a character that is not created by the user.
 *
 * @author Cameron Hofbauer
 * @version September 28, 2022
 */

public abstract class NonPlayableCharacter extends Entity {
    private int damagePerHit;               // Amount of damage a NonPlayableCharacter can inflict on other Characters
    private DamageType damageType;          // Type of damage a NonPlayableCharacter can inflict
    //private script pathfindingScript      // Pathfinding Script
                                            // TODO: Update pathfindingScript when fully implemented
    //private pattern projectilePattern     // Projectile Pattern
                                            // TODO: Update projectilePattern when fully implemented
    private double attackCooldown;          // Amount of time for a NonPlayableCharacter has to wait in between attacks

    /**
     *  Empty constructor to create a generic NonPlayableCharacter
     */
    public NonPlayableCharacter() {
        super();
        this.damagePerHit = 0;
        this.damageType = DamageType.DEFAULT;
        this.attackCooldown = 1;
    }

    public int getDamagePerHit() {
        return damagePerHit;
    }

    public void setDamagePerHit(int damagePerHit) {
        this.damagePerHit = damagePerHit;
    }

    public double getAttackCooldown() {
        return attackCooldown;
    }

    public void setAttackCooldown(double attackCooldown) {
        this.attackCooldown = attackCooldown;
    }
}
