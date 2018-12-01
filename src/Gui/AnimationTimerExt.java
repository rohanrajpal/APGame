package Gui;

import javafx.animation.Animation;
import javafx.animation.AnimationTimer;

public abstract class AnimationTimerExt extends AnimationTimer {
    private long sleepNs = 0;

    long prevTime = 0;

    /**
     * This is a constructor adapter class for animation timer which lets you specify time to adjust frequency of animation timer.
     * @param sleepMs
     */
    public AnimationTimerExt( long sleepMs) {
        this.sleepNs = sleepMs * 1_000_000;
    }

    /**
     * default handle function to reduce speed of animation time
     * @param now
     */
    @Override
    public void handle(long now) {

        // some delay
        if ((now - prevTime) < sleepNs) {
            return;
        }

        prevTime = now;

        handle();
    }

    /**
     * Used in child classes if any to overwrite handle
     */
    public abstract void handle();

}
