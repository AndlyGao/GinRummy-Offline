package utils;

import android.view.animation.TranslateAnimation;

public class TraslateAnimationManager {
    public TranslateAnimation TAnimation[];

    public int offsetTime = 150;
    public int inc = 40, size = 0;
    public float srcX, srcY, destX, destY;

    public TraslateAnimationManager() {

    }

    public void setObjects(int size) {
        TAnimation = new TranslateAnimation[size];
        this.size = size;

    }

    public void setPositions(float srcX, float destX, float srcY, float destY) {
        this.srcX = srcX;
        this.srcY = srcY;
        this.destX = destX;
        this.destY = destY;

        for (int i = 0; i < size; i++) {
            TAnimation[i] = new TranslateAnimation(0, destX - srcX, 0, destY - srcY);
            TAnimation[i].setFillAfter(false);
            TAnimation[i].setDuration(1000);
            TAnimation[i].setStartOffset(offsetTime);
            offsetTime += inc;
        }
    }

    public TranslateAnimation[] getAnimationObject() {
        return TAnimation;
    }


}
