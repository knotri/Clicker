package com.knotri.clicker;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.utils.viewport.ExtendViewport;

import java.util.Random;

/**
 * Created by knotri on 30.11.15.

 */

public abstract class AbstractScreen implements Screen { // VERSION 2.0;

    public static SpriteBatch batch = new SpriteBatch();
    public static MyGame game;
    public static Preferences prefs = Gdx.app.getPreferences("ClickerYoutube12");
    public static Stage stage = new Stage();
    public static Skin skin;
    public static InputMultiplexer inputMultiplexer = new InputMultiplexer();
    public static OrthographicCamera camera = new OrthographicCamera();
    public static OrthographicCamera guiCamera = new OrthographicCamera();
    public static boolean cameraUseWidth = true;
    public static float cameraOneSize;
    public static float guiK;
    public static BitmapFont defaultFont;


    public TextButton newButton(String str, Skin skin){
        TextButton button = new TextButton(str, skin);
        button.setWidth(guiCamera.viewportWidth);
        button.setHeight(guiCamera.viewportWidth / 3.7f);
        return button;
    }

    public TextButton newButton(String str, TextButton.TextButtonStyle style, float relativeWidth){
        TextButton button = new TextButton(str, style);
        float aspectRatio = button.getWidth() / button.getHeight();
        button.setWidth(guiCamera.viewportWidth * relativeWidth);
        button.setHeight(guiCamera.viewportWidth * relativeWidth / aspectRatio);
        return button;
    }

    public ImageButton createImageButton(ImageButton.ImageButtonStyle style, float relativeWidth){
        ImageButton button = new ImageButton(style);
        float aspectRatio = button.getWidth() / button.getHeight();
        button.setWidth(guiCamera.viewportWidth * relativeWidth);
        button.setHeight(guiCamera.viewportWidth * relativeWidth / aspectRatio);
        return button;
    }

    public static Random random = new Random(); // TODO

    public float randomFloat(){
        return random.nextFloat();
    }

    public float randomFloat(float min, float max){
        return min + (max-min)*randomFloat();
    }

    public static BitmapFont fontGeneration(int size) {

        String FONT_CHARACTERS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"
                + "0123456789][_!$%#@|\\/?-+=()*&.;:,{}\"´`'<>";
        String RUSSIAN_CHARACTERS = "АБВГДЕЁЖЗИІЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯ"
                + "абвгдеёжзиійклмнопрстуфхцчшщъыьэюя";

        BitmapFont font;
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("8477.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.borderColor = Color.BLACK;
        parameter.shadowColor = new Color(0,0,0,1);
//        parameter.shadowOffsetY = 1;
//        parameter.shadowOffsetX = 1;
        parameter.borderWidth = 2;
        parameter.size = size;
        parameter.characters = RUSSIAN_CHARACTERS + FONT_CHARACTERS;
        font = generator.generateFont(parameter);
        generator.dispose(); // don't forget to dispose to avoid memory leaks!
        return font;

    }



    public void update(){
        //do nothing
    }


    @Override
    public void render(float delta){
        //stage.act(delta);
        //stage.draw();
    }

    public void drawBackground(Texture tex){
        float imageHeight = camera.viewportHeight;
        float imageWidth = imageHeight * tex.getWidth() / tex.getHeight();
        float x = (camera.viewportWidth - imageWidth) / 2;
        draw(tex, x, 0, imageWidth);
    }

    public void drawBackground(TextureRegion tex){
        float imageHeight = camera.viewportHeight;
        float imageWidth = imageHeight * tex.getRegionWidth() / tex.getRegionHeight();
        float x = (camera.viewportWidth - imageWidth) / 2;
        draw(tex, x, 0, imageWidth);
    }


    public void draw(Texture tex, float x, float y, float width){
        float aspectRatio = tex.getWidth() / (float)tex.getHeight();
        draw(tex, x, y, width, width / aspectRatio);
    }

    public void draw(TextureRegion tex, float x, float y, float width){
        float aspectRatio = tex.getRegionWidth() / (float)tex.getRegionHeight();
        draw(tex, x, y, width, width / aspectRatio);
    }

    public static void draw(Texture tex, float x, float y, float width, float height){
        batch.draw(tex, x * guiK, y * guiK, width * guiK, height * guiK);
    }

    public static void draw(TextureRegion tex, float x, float y, float width, float height){
        batch.draw(tex, x * guiK, y * guiK, width * guiK, height * guiK);
    }

    public static void drawText(String str, float x, float y, int align){
        drawText(defaultFont, str, x, y, align);
    }

    public static void drawText(String str, float x, float y){
        drawText(defaultFont, str, x, y, -1);
    }



    public static void drawText(BitmapFont font, String str, float x, float y){
            font.draw(batch, str, x*guiK, y*guiK);
    }

    public static void drawText(BitmapFont font, String str, float x, float y, int align){
        if(align != -1){
            BitmapFont.TextBounds bounds = font.getBounds(str);
            if(align == Align.center){
                font.draw(batch, str, x*guiK - bounds.width/2, y*guiK + bounds.height/2);
            }
        }else{
            font.draw(batch, str, x*guiK, y*guiK);
        }
    }




//    public static BitmapFont getOpponentFont(int size) {
//        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(
//                Gdx.files.internal("fonts/ProximaNova-Bold.ttf"));
//        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
//        FileHandle file = Gdx.files.internal("russian.txt");
//        parameter.characters = file.readString("windows-1251");;
//        parameter.size = size;
//        parameter.color = Color.BLACK;
//        BitmapFont font = generator.generateFont(parameter);
//        font.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
//        return font;
//    }

//    protected void setDisplayStage(Stage stage) {
//        displayStage = stage;
//        Gdx.input.setInputProcessor(stage);
//    }

    @Override
    public void show() {
//        if(stage != null){
//            stage.dispose();
//            Gdx.app.log("stage", "stage dispose, in AbstractScreen");
//        }
//
////        if(skin != null){
////            skin.dispose();
////            skin = new
////        }
//        Gdx.app.log("stage", "stage = new Stage(), in AbstractScreen");
//        stage = new Stage();



        //InputProcessor inputProcessorOne = stage;
        //InputProcessor inputProcessorTwo = new CustomInputProcessorTwo();


//        inputMultiplexer.addProcessor(stage);
//        Gdx.input.setInputProcessor(inputMultiplexer);
    }





    public void setProportionalCameraWidth(float width){
        cameraUseWidth = true;
        cameraOneSize = width;
        myResize();
    }

    public void setProportionalCameraHeight(float height){
        cameraUseWidth = false;
        cameraOneSize = height;
        myResize();
    }

    public static Vector2 touchCoord(int x, int y){
        Vector3 worldCoordinates = new Vector3(x, y, 0);
        Vector3 ans = camera.unproject(worldCoordinates);
        return new Vector2(ans.x, ans.y);
    }

    public void myResize(){
        float height = Gdx.graphics.getHeight();
        float width = Gdx.graphics.getWidth();
        if(cameraUseWidth){
            camera.viewportWidth = cameraOneSize;
            camera.viewportHeight = cameraOneSize * height/width;
        }else{
            camera.viewportWidth = cameraOneSize * width/height;
            camera.viewportHeight = cameraOneSize;
        }


        camera.position.x = camera.viewportWidth/2;
        camera.position.y = camera.viewportHeight/2;

        camera.update();

        batch.setProjectionMatrix(guiCamera.combined);

        guiCamera.viewportHeight = height;
        guiCamera.viewportWidth = width;
        guiCamera.position.x = guiCamera.viewportWidth/2;
        guiCamera.position.y = guiCamera.viewportHeight/2;

        guiCamera.update();

        guiK = guiCamera.viewportWidth / camera.viewportWidth;
    }

    @Override
    public void resize(int width, int height) {
        stage.setViewport(new ExtendViewport(width, height));
        stage.getViewport().update(width, height, true);

        if(cameraUseWidth){
            camera.viewportWidth = cameraOneSize;
            camera.viewportHeight = cameraOneSize * height/width;
        }else{
            camera.viewportWidth = cameraOneSize * width/height;
            camera.viewportHeight = cameraOneSize;
        }


        camera.position.x = camera.viewportWidth/2;
        camera.position.y = camera.viewportHeight/2;

        camera.update();

        batch.setProjectionMatrix(guiCamera.combined);

        guiCamera.viewportHeight = height;
        guiCamera.viewportWidth = width;
        guiCamera.position.x = guiCamera.viewportWidth/2;
        guiCamera.position.y = guiCamera.viewportHeight/2;

        guiCamera.update();

        guiK = guiCamera.viewportWidth / camera.viewportWidth;

        MyGame.bigFont = fontGeneration(width / 13);
        MyGame.middleFont = fontGeneration(width / 18);
        MyGame.smallFont = fontGeneration(width / 35);
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {
//        if(stage != null) {
//            stage.clear();
//            Gdx.app.log("stage", "stage clean in abstractScreen, class" + this.getClass().toString());
//        }else{
//            stage = new Stage();
//        }
    }

    @Override
    public void dispose() {

    }

    ///////////////////////////////////////////////////////

}
