package uw.haptic2015;

/**
 * Created by jerrychen on 6/17/15.
 */
public class Config {
    //common var
    public float frictionCoefficient;
    public final String frictionUnit = "";
    public float mass;
    public final String massUnit = "kg";
    public float gravity;
    public final String gravityUnit = "m/s^2";

    //slope scene
    public float slopeAngle;
    public final String slopeUnit = "degrees";

    //spring scene
    public float springCoefficient;
    public final String springUnit = "m/s^2";

    public Config() {
        this.frictionCoefficient = 30;
        this.mass = 50;
        this.gravity = -9.81f;
        this.slopeAngle = 30;
        this.springCoefficient = 30;
    }

    public float getFrictionCoefficient() {
        return frictionCoefficient;
    }

    public void setFrictionCoefficient(float frictionCoefficient) {
        this.frictionCoefficient = frictionCoefficient;
    }

    public float getMass() {
        return mass;
    }

    public void setMass(float mass) {
        this.mass = mass;
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
        this.slopeAngle = slopeAngle;
    }

    public float getSpringCoefficient() {
        return springCoefficient;
    }

    public void setSpringCoefficient(float springCoefficient) {
        this.springCoefficient = springCoefficient;
    }
}
