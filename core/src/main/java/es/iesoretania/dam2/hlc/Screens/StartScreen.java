package es.iesoretania.dam2.hlc.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.audio.Sound;
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
import es.iesoretania.dam2.hlc.Game.OneTaps2D;

public class StartScreen extends ScreenAdapter {
    private final OneTaps2D game;
    private Stage stage;
    private final Skin gameSkin;
    private final Image logo;
    private final Image fondo;
    private final Sound menuSound = Gdx.audio.newSound(Gdx.files.internal("menu.mp3"));
    private final Sound gameSound = Gdx.audio.newSound(Gdx.files.internal("juego.mp3"));
    private long tiempoEscala;
    private static boolean scale = true;

    public StartScreen(OneTaps2D game) {
        this.game = game;
        menuSound.loop(0.5f);
        tiempoEscala = TimeUtils.nanoTime();
        Texture texturaLogo = new Texture(Gdx.files.internal("ot2dlogo.png"));
        Texture texturaFondo = new Texture(Gdx.files.internal("fondo.png"));
        fondo = new Image(texturaFondo);
        logo = new Image(texturaLogo);
        gameSkin = new Skin(Gdx.files.internal("skin/comic-ui.json"));
        Gdx.graphics.setResizable(false);
    }

    @Override
    public void show() {
        stage = new Stage();

        fondo.setWidth(Gdx.graphics.getWidth());
        fondo.setHeight(Gdx.graphics.getHeight());
        fondo.setPosition(0, 0);

        logo.setWidth(Gdx.graphics.getWidth() / 1.5f);
        logo.setHeight(Gdx.graphics.getHeight() / 3.5f);
        logo.setPosition(Gdx.graphics.getWidth() / 2f - logo.getWidth() / 2, Gdx.graphics.getHeight() - logo.getHeight() * 1.25f);
        logo.setOrigin(Align.center);

        TextButton tbComenzar = new TextButton("Comenzar", gameSkin);
        tbComenzar.setWidth(Gdx.graphics.getWidth() / 4f);
        tbComenzar.setHeight(Gdx.graphics.getHeight() / 10f);
        tbComenzar.setPosition(Gdx.graphics.getWidth() / 2f - tbComenzar.getWidth() / 2, Gdx.graphics.getHeight() / 2f - tbComenzar.getHeight() * 3.5f);
        tbComenzar.setColor(0,0.35f,0.65f,1);
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
        tbSalir.setHeight(Gdx.graphics.getHeight() / 10f);
        tbSalir.setPosition(Gdx.graphics.getWidth() / 2f - tbSalir.getWidth() / 2, Gdx.graphics.getHeight() / 2f - tbSalir.getHeight()*4.5f);
        tbSalir.setColor(0,0.35f,0.65f,1);
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
        stage.addActor(fondo);
        stage.addActor(logo);
        stage.addActor(tbComenzar);
        stage.addActor(tbSalir);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if (scale) {
            logo.scaleBy(0.03f * delta);
            if (TimeUtils.nanoTime() - tiempoEscala > 600000000) {
                tiempoEscala = TimeUtils.nanoTime();
                scale = false;
            }
        } else {
            logo.scaleBy(-0.03f * delta);
            if (TimeUtils.nanoTime() - tiempoEscala > 600000000) {
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