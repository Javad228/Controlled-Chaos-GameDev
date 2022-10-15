package loot;

import main.KeyHandler;

public class Effect extends Item {
    private int speedMultiplier;
    private int damageModifier;
    private int effectTimer;
    private int customEffect; // what is this? is it supposed to be an int? ~KA


    public Effect(KeyHandler keyH, String[] imagePaths) {
        super(keyH, 20, imagePaths);
        this.speedMultiplier = 1;
        this.damageModifier = 1;
        this.effectTimer = 0;
        this.customEffect = 1;

        setDefaultValues();
        getImage(imagePaths);
    }

    public Effect(KeyHandler keyH, int speedMultiplier, int damageModifier, int effectTimer, int customEffect) {
        super(keyH, 20, new String[0]);
        this.speedMultiplier = speedMultiplier;
        this.damageModifier = damageModifier;
        this.effectTimer = effectTimer;
        this.customEffect = customEffect;
    }

    public void setDefaultValues() {
        this.setxCoord(300);
        this.setyCoord(300);
    }

    public int getSpeedMultiplier() {
        return speedMultiplier;
    }

    public void setSpeedMultiplier(int speedMultiplier) {
        this.speedMultiplier = speedMultiplier;
    }

    public int getDamageModifier() {
        return damageModifier;
    }

    public void setDamageModifier(int damageModifier) {
        this.damageModifier = damageModifier;
    }

    public int getEffectTimer() {
        return effectTimer;
    }

    public void setEffectTimer(int effectTimer) {
        this.effectTimer = effectTimer;
    }

    public int getCustomEffect() {
        return customEffect;
    }

    public void setCustomEffect(int customEffect) {
        this.customEffect = customEffect;
    }
}
