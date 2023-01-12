package es.iesoretania.dam2.hlc.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import es.iesoretania.dam2.hlc.Game.OneTaps2D;

public class CreditScreen extends ScreenAdapter {
    private final OneTaps2D game;
    private Stage stage;


    public CreditScreen(OneTaps2D game) {
        this.game = game;
        stageBuild();
    }

    private void stageBuild() {
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        OrthographicCamera camera = new OrthographicCamera(800, 480);
        Viewport viewport = new ScreenViewport(camera);
        stage.setViewport(viewport);

        Texture texturaFondo = new Texture(Gdx.files.internal("Creditos.png"));
        Image fondo = new Image(texturaFondo);
        fondo.setWidth(Gdx.graphics.getWidth());
        fondo.setHeight(Gdx.graphics.getHeight());
        fondo.setPosition(0, 0);
        stage.addActor(fondo);

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
                    game.setScreen(new StartScreen(game));
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

        Texture texturaFondo = new Texture(Gdx.files.internal("Creditos.png"));
        Image fondo = new Image(texturaFondo);
        fondo.setWidth(width);
        fondo.setHeight(height);
        fondo.setPosition(-400, -240);

        OrthographicCamera camera = new OrthographicCamera(800, 480);
        Viewport viewport = new ScreenViewport(camera);
        stage.setViewport(viewport);
        stage.addActor(fondo);
        show();
    }
}
