package es.iesoretania.dam2.hlc.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import es.iesoretania.dam2.hlc.Actors.Manager;
import es.iesoretania.dam2.hlc.Actors.Personaje;
import es.iesoretania.dam2.hlc.Game.OneTaps2D;

public class TheEndScreen extends ScreenAdapter {
    private final OneTaps2D game;
    private final Sound gameOverSound = Gdx.audio.newSound(Gdx.files.internal("gameover.mp3"));
    private Stage stage;
    private Skin gameSkin;
    private Label lblGameOver;
    private Label lblPuntos;
    private Label lblVolver;


    public TheEndScreen(OneTaps2D game) {
        this.game = game;
        gameOverSound.play();
        stageBuild();
    }

    private void stageBuild() {
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        OrthographicCamera camera = new OrthographicCamera(800, 480);
        Viewport viewport= new ScreenViewport(camera);
        stage.setViewport(viewport);

        gameSkin = new Skin(Gdx.files.internal("skin/comic-ui.json"));

        lblGameOver= new Label("Game Over", gameSkin);
        lblGameOver.setWidth(Gdx.graphics.getWidth()/2f);
        lblGameOver.setHeight(Gdx.graphics.getHeight()/3f);
        lblGameOver.setFontScale(3,2);
        lblGameOver.setPosition(Gdx.graphics.getWidth()/2f-lblGameOver.getWidth()*1.25f,Gdx.graphics.getHeight()/3.8f);
        lblGameOver.setOrigin(Align.center);
        lblGameOver.setColor(Color.RED);

        lblPuntos= new Label(Manager.getScoreTotal() + " puntos.", gameSkin);
        lblPuntos.setWidth(Gdx.graphics.getWidth() / 6f);
        lblPuntos.setHeight(Gdx.graphics.getHeight() / 10f);
        lblPuntos.setPosition(Gdx.graphics.getWidth()/2f- lblPuntos.getWidth()*3.2f,Gdx.graphics.getHeight()/2f -lblPuntos.getHeight()*5f);
        lblPuntos.setOrigin(Align.center);

        lblVolver= new Label("Click para ir a la pantalla inicial", gameSkin);
        lblVolver.setWidth(Gdx.graphics.getWidth() / 6f);
        lblVolver.setHeight(Gdx.graphics.getHeight() / 10f);
        lblVolver.setPosition(Gdx.graphics.getWidth() / 2f - lblVolver.getWidth() *3.7f, Gdx.graphics.getHeight() / 2f - lblVolver.getHeight()*9f);


        stage.addActor(lblGameOver);
        stage.addActor(lblPuntos);
        stage.addActor(lblVolver);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0.3f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(delta);
        stage.draw();
        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                if (button == Input.Buttons.LEFT) {
                    game.setScreen(new CreditScreen(game));
                    gameOverSound.dispose();
                    Personaje.setGameOver(false);
                }
                return super.touchDown(screenX, screenY, pointer, button);
            }
        });
    }

    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        OrthographicCamera camera = new OrthographicCamera(width, height);
        Viewport viewport= new ScreenViewport(camera);
        stage.setViewport(viewport);
        gameSkin = new Skin(Gdx.files.internal("skin/comic-ui.json"));
        lblGameOver= new Label("Game Over", gameSkin);
        lblGameOver.setWidth(width/2f);
        lblGameOver.setHeight(height/3f);
        lblGameOver.setFontScale(3,2);
        lblGameOver.setPosition(width/2f-lblGameOver.getWidth()*1.25f,height/3.8f);
        lblGameOver.setOrigin(Align.center);
        lblGameOver.setColor(Color.RED);

        lblPuntos= new Label(Manager.getScoreTotal() + " puntos.", gameSkin);
        lblPuntos.setWidth(width / 6f);
        lblPuntos.setHeight(height / 10f);
        lblPuntos.setPosition(width/2f- lblPuntos.getWidth()*3.2f,height/2f -lblPuntos.getHeight()*5f);
        lblPuntos.setOrigin(Align.center);

        lblVolver= new Label("Click para ir a los creditos del juego", gameSkin);
        lblVolver.setWidth(width / 6f);
        lblVolver.setHeight(height / 10f);
        lblVolver.setPosition(width / 2f - lblVolver.getWidth() *3.8f, height / 2f - lblVolver.getHeight()*9f);

        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                if (button == Input.Buttons.LEFT) {
                    game.setScreen(new StartScreen(game));
                    gameOverSound.dispose();
                    Personaje.setGameOver(false);
                }
                return super.touchDown(screenX, screenY, pointer, button);
            }
        });

        stage.addActor(lblGameOver);
        stage.addActor(lblPuntos);
        stage.addActor(lblVolver);
        show();
    }
}