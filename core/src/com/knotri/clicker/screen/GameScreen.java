package com.knotri.clicker.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.knotri.clicker.AbstractScreen;
import com.knotri.clicker.MyGame;

/**
 * Created by k on 22.02.16.
 */
public class GameScreen extends AbstractScreen  {

    final float SKIN_SIZE = 504;
    float CENTER_MAIN_BUTTON;
    //TextureRegion skin2 = game.atlas.findRegion("skin2");

    public String getScoreText(){

        int _score = MyGame.score;
        String output = "" + _score % 1000;
        _score /= 1000;
        while(_score > 0 ){
            output = _score % 1000 + "," + output;
            _score /= 1000;
        }

        return output + " VIEWS!";
    }

    public InputAdapter inputAdapter = new InputAdapter() {
        @Override
        public boolean touchDown (int x, int y, int pointer, int button) {
            if( touchCoord(x,y).dst(MyGame.DESIGN_WIDTH / 2, CENTER_MAIN_BUTTON) < SKIN_SIZE / 2){
                MyGame.score += MyGame.cps;
                skinSizeDinamic += 15;
            }
            return false;
        }

    };


    public GameScreen(){
        stage.clear();
        inputMultiplexer.addProcessor(inputAdapter);










        //stage.addActor(createImageButton(skin.get("shop-icon", ImageButton.ImageButtonStyle.class), 1f));
    }

    @Override
    public void show(){
        setProportionalCameraWidth(MyGame.DESIGN_WIDTH);
    }


    float skinSizeDinamic = SKIN_SIZE;
    @Override
    public void render(float delta){
        //Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        //Gdx.gl.glClearColor();
        batch.begin();

        drawBackground(game.globalBackground);

        skinSizeDinamic -= (skinSizeDinamic - SKIN_SIZE) * 0.1f;
        float margin = (MyGame.DESIGN_WIDTH - skinSizeDinamic) / 2;
        float skinY = CENTER_MAIN_BUTTON - skinSizeDinamic/2;
        draw(MyGame.mainButton, margin, skinY, skinSizeDinamic);

        float rectangleHeight = MyGame.DESIGN_WIDTH * 0.25f;  // 25% - in psd
        batch.setColor(1,1,1,0.5f);
        draw(game.blackTexture, 0, camera.viewportHeight - rectangleHeight, MyGame.DESIGN_WIDTH, rectangleHeight);
        batch.setColor(1,1,1,1);
        drawText(MyGame.bigFont, getScoreText(), MyGame.DESIGN_WIDTH / 2, camera.viewportHeight  - MyGame.DESIGN_WIDTH * 0.1f, Align.center);
        drawText(MyGame.middleFont, "cps: " + MyGame.cps, MyGame.DESIGN_WIDTH / 2, camera.viewportHeight - MyGame.DESIGN_WIDTH * 0.2f, Align.center);


        batch.end();

        //stage.getBatch().setColor();
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
        //game.globalBackground
    }

    @Override
    public void hide(){
        //inputMultiplexer.removeProcessor(this);
        game.saveGame();
        game.requestHandler.saveRecord(game.score);
        inputMultiplexer.removeProcessor(inputAdapter);
        stage.clear();
    }


    @Override
    public void pause() {
        game.saveGame();
    }


    @Override
    public void resize(int width, int height){
        super.resize(width, height);


        stage.clear();
        float[] aspectRatio = {1, 1.504807692f, 1};
        ImageButton[] menuButtons = new ImageButton[3];
        menuButtons[0] = createImageButton(skin.get("shop-icon", ImageButton.ImageButtonStyle.class), 0.3f);
        menuButtons[0].addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.log("ff", "ff");
                game.setScreen(new ShopScreen());
            }
        });

        menuButtons[1] = createImageButton(skin.get("skin-icon", ImageButton.ImageButtonStyle.class), 0.3f);
        menuButtons[1].addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.log("ff", "ff");
                game.setScreen(new SkinScreen());
            }
        });

        menuButtons[2] = createImageButton(skin.get("leadboard", ImageButton.ImageButtonStyle.class), 0.3f);
        menuButtons[2].addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.log("ff", "ff");
                game.setScreen(new TopScreen());
            }
        });
        float calculetaAllWidth = 0;
        for(int i = 0; i < 3; i++){
            ImageButton imageButton = menuButtons[i];
            calculetaAllWidth += aspectRatio[i];
        }

        float offset = 0;
        for(int i = 0; i < 3; i++){
            ImageButton imageButton = menuButtons[i];


            imageButton.setSize( guiCamera.viewportWidth * aspectRatio[i] / calculetaAllWidth, guiCamera.viewportWidth / calculetaAllWidth);
            imageButton.setPosition( offset, guiCamera.viewportHeight * 0.15f);

            offset += guiCamera.viewportWidth * aspectRatio[i] / calculetaAllWidth;
            stage.addActor(imageButton);
        }


        CENTER_MAIN_BUTTON = camera.viewportHeight * 0.6f;


        //Gdx.input.setInputProcessor(stage);

        //stage.getViewport().update(width, height, true);
    }


}
