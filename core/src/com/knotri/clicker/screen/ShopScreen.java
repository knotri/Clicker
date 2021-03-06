package com.knotri.clicker.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.knotri.clicker.AbstractScreen;
import com.knotri.clicker.ItemUpgrade;
import com.knotri.clicker.MyGame;

/**
 * Created by k on 23.02.16.
 */
public class ShopScreen extends AbstractScreen implements InputProcessor {

    public ShopScreen(){
        inputMultiplexer.addProcessor(this);
        stage.clear();
    }

    float heightAllItem = 0;

    @Override
    public void render(float delta){

        if(heightAllItem > camera.viewportHeight){
            if(offsetY > 0){ offsetY -= 5; }
            if(offsetY < -heightAllItem + camera.viewportHeight) { offsetY += 5; }
        } else {
            offsetY = 0;
        }

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        drawBackground(game.globalBackground);
        //float offsetY = 20;

        float fillHeight = AbstractScreen.game.DESIGN_WIDTH * 0.2f;

        float drawY = camera.viewportHeight - fillHeight - offsetY - camera.viewportWidth * 0.2f;
        heightAllItem = camera.viewportWidth * 0.2f;
        for(ItemUpgrade itemUpgrade : MyGame.itemUpgrades){
            itemUpgrade.draw(drawY);
            drawY -= fillHeight * 1.15f;
            heightAllItem += fillHeight * 1.15f;
        }

        drawText(MyGame.bigFont, "SHOP", camera.viewportWidth / 2, camera.viewportHeight * 0.92f - offsetY, Align.center);

        batch.end();





    }

    @Override
    public void hide(){
        inputMultiplexer.removeProcessor(this);
    }




    public boolean keyDown (int keycode) {
        if(keycode == Input.Keys.BACK || keycode == Input.Keys.ESCAPE){
            game.setScreen(new GameScreen());
        }
        return false;
    }

    public boolean keyUp (int keycode) {
        return false;
    }

    public boolean keyTyped (char character) {
        return false;
    }


    boolean canScrooll = false;
    Vector3 startPos = new Vector3(-1000,-1000,0);
    float offsetY = 0;
    public boolean touchDown (int x, int y, int pointer, int button){

        Vector3 worldCoordinates = new Vector3(x, y, 0);
        Vector3 ans = camera.unproject(worldCoordinates);

        startPos = ans;
        //canScrooll = true;

//        float fillHeight = AbstractScreen.game.DESIGN_WIDTH * 0.2f;
//
//        float drawY = camera.viewportHeight - fillHeight - offsetY - camera.viewportWidth * 0.2f;
//        heightAllItem = camera.viewportWidth * 0.2f;
//        for(ItemUpgrade itemUpgrade : MyGame.itemUpgrades){
//
//            heightAllItem += fillHeight * 1.15f;
//            if( ans.y > drawY && ans.y < drawY + fillHeight){
//                itemUpgrade.levelUp();
//            }
//            drawY -= fillHeight * 1.15f;
//        }

        return false;
    }

    public boolean touchUp (int x, int y, int pointer, int button) {
        if(!canScrooll) {
            Vector3 worldCoordinates = new Vector3(x, y, 0);
            Vector3 ans = camera.unproject(worldCoordinates);

            startPos = ans;
            //canScrooll = true;

            float fillHeight = AbstractScreen.game.DESIGN_WIDTH * 0.2f;

            float drawY = camera.viewportHeight - fillHeight - offsetY - camera.viewportWidth * 0.2f;
            heightAllItem = camera.viewportWidth * 0.2f;
            for (ItemUpgrade itemUpgrade : MyGame.itemUpgrades) {

                heightAllItem += fillHeight * 1.15f;
                if (ans.y > drawY && ans.y < drawY + fillHeight) {
                    itemUpgrade.levelUp();
                }
                drawY -= fillHeight * 1.15f;
            }
        }

        canScrooll = false;



        return false;
    }

    public boolean touchDragged (int x, int y, int pointer) {

        Vector3 worldCoordinates = new Vector3(x, y, 0);
        Vector3 ans = camera.unproject(worldCoordinates);

        if( Math.abs(startPos.y - ans.y) > 20){
            canScrooll = true;
        }

        if( canScrooll ){
            //guiCamera.position.add(new Vector3(0 , (startPos.y - ans.y) * guiK , 0));
            offsetY += (startPos.y - ans.y);
            startPos = ans;
            //guiCamera.update();
            //batch.setProjectionMatrix(guiCamera.combined);
            Gdx.app.log("", "offsetY = " + offsetY);
        }

        return false;
    }

    public boolean mouseMoved (int x, int y) {
        return false;
    }

    public boolean scrolled (int amount) {
        return false;
    }


}
