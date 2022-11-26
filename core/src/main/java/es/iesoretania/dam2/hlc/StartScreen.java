package es.iesoretania.dam2.hlc;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.TimeUtils;

public class StartScreen extends ScreenAdapter {
    private final OneTaps2D game;
    private Stage stage;
    Texture texturaLogo;
    Image logo;
    private final Sound menuSound = Gdx.audio.newSound(Gdx.files.internal("menu.mp3"));
    private final Sound gameSound = Gdx.audio.newSound(Gdx.files.internal("juego.mp3"));
    long tiempoEscala;
    private static boolean scale = true;

    public StartScreen(OneTaps2D game) {
        this.game = game;
        menuSound.loop();
        tiempoEscala = TimeUtils.nanoTime();
    }

    @Override
    public void show() {
        stage = new Stage();
        Skin gameSkin = new Skin(Gdx.files.internal("skin/comic-ui.json"));
        texturaLogo = new Texture(Gdx.files.internal("ot2dlogo.png"));

        logo = new Image(texturaLogo);
        logo.setWidth(Gdx.graphics.getWidth() / 2f);
        logo.setHeight(150);
        logo.setPosition(Gdx.graphics.getWidth() / 2f - logo.getWidth() / 2, Gdx.graphics.getHeight() - logo.getHeight() * 1.5f);
        logo.setColor(Color.WHITE);
        logo.setOrigin(Align.center);

        TextButton tbComenzar = new TextButton("Comenzar", gameSkin);
        tbComenzar.setWidth(Gdx.graphics.getWidth() / 4f);
        tbComenzar.setHeight(45);
        tbComenzar.setPosition(Gdx.graphics.getWidth() / 2f - tbComenzar.getWidth() / 2, Gdx.graphics.getHeight() / 2f - tbComenzar.getHeight() * 2.5f);
        tbComenzar.addListener(new InputListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                menuSound.dispose();
                game.setScreen(new GameScreen(game, gameSound));
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });

        TextButton tbSalir = new TextButton("Salir", gameSkin);
        tbSalir.setWidth(Gdx.graphics.getWidth() / 6f);
        tbSalir.setHeight(37);
        tbSalir.setPosition(
                Gdx.graphics.getWidth() / 2f - tbSalir.getWidth() / 2,
                Gdx.graphics.getHeight() / 4f - tbSalir.getHeight()
        );
        tbSalir.addListener(new InputListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                System.exit(0);
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });
        Gdx.input.setInputProcessor(stage);
        stage.addActor(logo);
        stage.addActor(tbComenzar);
        stage.addActor(tbSalir);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if (scale) {
            logo.scaleBy(0.02f * delta);
            if (TimeUtils.nanoTime() - tiempoEscala > 500000000) {
                tiempoEscala = TimeUtils.nanoTime();
                scale = false;
            }
        } else {
            logo.scaleBy(-0.02f * delta);
            if (TimeUtils.nanoTime() - tiempoEscala > 500000000) {
                tiempoEscala = TimeUtils.nanoTime();
                scale = true;
            }
        }
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
    }
}