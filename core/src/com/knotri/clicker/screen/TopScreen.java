package com.knotri.clicker.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector3;
import com.knotri.clicker.AbstractScreen;
import com.knotri.clicker.ItemUpgrade;
import com.knotri.clicker.MyGame;


/**
 * Created by k on 25.02.16.
 */
public class TopScreen extends AbstractScreen implements InputProcessor{
    static public class ItemRecord {
        TextureRegion image;
        String text;
        int result;



        public ItemRecord(TextureRegion image, String text,int result){
            this.image = image;
            this.text = text;
            this.result = result;
        }

        public ItemRecord(String text,int result){
            this.image = MyGame.atlas.findRegion("12");
            this.text = text;
            this.result = result;
            if(this.text == null){ this.text = "без именни"; }
        }

        public void draw(float y) {
            float margin = MyGame.DESIGN_WIDTH * 0.05f;
            float fillHeight = AbstractScreen.game.DESIGN_WIDTH * 0.2f;
            AbstractScreen.batch.setColor(1,1,1,0.5f);
            AbstractScreen.draw(AbstractScreen.game.blackTexture, 0, y, AbstractScreen.game.DESIGN_WIDTH, fillHeight);
            AbstractScreen.batch.setColor(1,1,1,1);
            AbstractScreen.draw(image, margin, y + margin, fillHeight - 2*margin, (fillHeight - 2*margin) * image.getRegionHeight() / image.getRegionWidth()  );

            AbstractScreen.drawText(MyGame.smallFont, text, MyGame.DESIGN_WIDTH * 0.25f, y + fillHeight - margin);
            AbstractScreen.drawText(MyGame.smallFont, "v: " + result, MyGame.DESIGN_WIDTH * 0.25f,  y + fillHeight - margin * 2);

//            AbstractScreen.drawText(MyGame.smallFont, "level: " + level, MyGame.DESIGN_WIDTH * 0.80f,  y + fillHeight - margin);
//            AbstractScreen.drawText(MyGame.smallFont, "cps: " + cps, MyGame.DESIGN_WIDTH * 0.80f,  y + fillHeight - margin*2 );
        }
    }


    public TopScreen(){
        MyGame.itemRecords.clear();
        MyGame.itemRecords.add(new ItemRecord(MyGame.atlas.findRegion("12"), "load...", 0));
        inputMultiplexer.addProcessor(this);
        stage.clear();

        MyGame.itemRecords.clear();
        String[] top = game.requestHandler.getTopRecord(MyGame.itemRecords).split(",");
        for(String str : top){
            MyGame.itemRecords.add(new ItemRecord(MyGame.atlas.findRegion("12"), str, 0));
        }


    }

    float heightAllItem = 0;

    @Override
    public void render(float delta){
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        drawBackground(game.globalBackground);
        //float offsetY = 20;

        float fillHeight = AbstractScreen.game.DESIGN_WIDTH * 0.2f;

        float drawY = camera.viewportHeight - fillHeight - offsetY;
        heightAllItem = 0;
        for(ItemRecord itemRecord : MyGame.itemRecords){
            itemRecord.draw(drawY);
            drawY -= fillHeight * 1.15f;
            heightAllItem += fillHeight * 1.15f;
        }
        batch.end();

        if(offsetY > 0){ offsetY -= 5; }
        if(offsetY < -heightAllItem + camera.viewportHeight) { offsetY += 5; }
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
        canScrooll = true;

        float fillHeight = AbstractScreen.game.DESIGN_WIDTH * 0.2f;

        float drawY = camera.viewportHeight - fillHeight - offsetY;
        heightAllItem = 0;
//        for(ItemUpgrade itemUpgrade : MyGame.itemUpgrades){
//
//            heightAllItem += fillHeight * 1.3f;
//            if( ans.y > drawY && ans.y < drawY + fillHeight){
//                itemUpgrade.levelUp();
//            }
//            drawY -= fillHeight * 1.3f;
//        }

        return false;
    }

    public boolean touchUp (int x, int y, int pointer, int button) {
        canScrooll = false;

        return false;
    }

    public boolean touchDragged (int x, int y, int pointer) {

        Vector3 worldCoordinates = new Vector3(x, y, 0);
        Vector3 ans = camera.unproject(worldCoordinates);

        if( Math.abs(startPos.y - ans.y) > 10){
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
