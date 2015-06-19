package uw.haptic2015;

/**
 * Created by jerrychen on 6/17/15.
 */

public class Config {
    //common var
    public float frictionCoefficient;
    public final String frictionUnit = "";
    public float density;
    public final String densityUnit = "kg";
    public float gravity;
    public final String gravityUnit = "m/s^2";

    //slope scene
    public float slopeAngle;
    public final String slopeUnit = "degrees";

    //spring scene
    public float springCoefficient;
    public final String springUnit = "m/s^2";

    public Config() {
        this.frictionCoefficient = 0.2f;
        this.density = 3f;
        this.gravity = -9.8f;
        this.slopeAngle = 30;
        this.springCoefficient = 30;
    }

    public float getFrictionCoefficient() {
        return frictionCoefficient;
    }

    public void setFrictionCoefficient(float frictionCoefficient) {
        this.frictionCoefficient = frictionCoefficient;
    }

    public float getDensity() {
        return density;
    }

    public void setDensity(float density) {
        this.density = density;
    }

    public float getGravity() {
        return gravity;
    }

    public void setGravity(float gravity) {
        this.gravity = gravity;
    }

    public float getSlopeAngle() {
        return slopeAngle;
    }

    public void setSlopeAngle(float slopeAngle) {
        if (slopeAngle > 45) slopeAngle = 45;
        else if (slopeAngle < 1) slopeAngle = 1;

        this.slopeAngle = slopeAngle;
    }

    public float getSpringCoefficient() {
        return springCoefficient;
    }

    public void setSpringCoefficient(float springCoefficient) {
        this.springCoefficient = springCoefficient;
    }
}
