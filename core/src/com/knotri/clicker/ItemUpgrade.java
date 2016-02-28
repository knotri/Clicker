package com.knotri.clicker;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.knotri.clicker.MyGame.*;

/**
 * Created by k on 23.02.16.
 */
public class ItemUpgrade {
    TextureRegion image;
    String text;
    int level;
    int price;
    int cps;

    public void levelUp(){
        if(MyGame.score >= price){
            MyGame.score -= price;
            level++;
            price *= 1.6f;
            MyGame.cps += cps;
        }

    }

    public ItemUpgrade(TextureRegion image, String text, int price, int cps){
        this.image = image;
        this.text = text;
        this.price = price;
        this.cps = cps;
    }

    public void draw(float y) {
        float margin = MyGame.DESIGN_WIDTH * 0.05f;
        float fillHeight = AbstractScreen.game.DESIGN_WIDTH * 0.2f;
        AbstractScreen.batch.setColor(1,1,1,0.5f);
        AbstractScreen.draw(AbstractScreen.game.blackTexture, 0, y, AbstractScreen.game.DESIGN_WIDTH, fillHeight);
        AbstractScreen.batch.setColor(1,1,1,1);
        AbstractScreen.draw(image, margin, y + margin, fillHeight - 2*margin, (fillHeight - 2*margin) * image.getRegionHeight() / image.getRegionWidth()  );

        AbstractScreen.drawText(MyGame.smallFont, text, MyGame.DESIGN_WIDTH * 0.25f, y + fillHeight - margin);
        AbstractScreen.drawText(MyGame.smallFont, "v: " + price, MyGame.DESIGN_WIDTH * 0.25f,  y + fillHeight - margin * 2);

        AbstractScreen.drawText(MyGame.smallFont, "level: " + level, MyGame.DESIGN_WIDTH * 0.80f,  y + fillHeight - margin);
        AbstractScreen.drawText(MyGame.smallFont, "cps: " + cps, MyGame.DESIGN_WIDTH * 0.80f,  y + fillHeight - margin*2 );
    }
}
