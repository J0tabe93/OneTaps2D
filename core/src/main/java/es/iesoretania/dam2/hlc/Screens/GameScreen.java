package es.iesoretania.dam2.hlc.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import es.iesoretania.dam2.hlc.Actors.Manager;
import es.iesoretania.dam2.hlc.Actors.Obstaculo;
import es.iesoretania.dam2.hlc.Actors.Personaje;
import es.iesoretania.dam2.hlc.Game.OneTaps2D;
import es.iesoretania.dam2.hlc.Enums.VerticalMovement;

public class GameScreen extends ScreenAdapter {
    private final OneTaps2D game;
    private Stage stage;
    private TiledMap map;
    private OrthographicCamera camera;
    private OrthogonalTiledMapRenderer mapRenderer;
    private int mapWidthInPixels;
    private float offsetX;
    private Personaje personaje;
    private Obstaculo obstaculo1;
    private Obstaculo obstaculo2;
    private static boolean pase1 = true;
    private int altura;
    private float alturaInicial1, alturaInicial2;
    private final Sound gameSound;

    public GameScreen(OneTaps2D game, Sound gameSound) {
        this.game = game;
        this.gameSound = gameSound;
        this.gameSound.loop();
        stageBuild();
    }

    private void stageBuild() {
        stage = new Stage();
        camera = new OrthographicCamera();
        map = new TmxMapLoader().load("ocean.tmx");
        mapRenderer = new OrthogonalTiledMapRenderer(map);
        Gdx.input.setInputProcessor(stage);
        personaje = new Personaje();
        obstaculo1 = new Obstaculo(0);
        obstaculo2 = new Obstaculo(1);
        Actor score = new Manager(personaje, obstaculo1, obstaculo2);
        stage.addActor(personaje);
        stage.addActor(obstaculo1);
        stage.addActor(obstaculo2);
        stage.addActor(score);

        MapProperties properties = map.getProperties();
        int tileWidth = properties.get("tilewidth", Integer.class);
        int mapWidthInTiles = properties.get("width", Integer.class);
        mapWidthInPixels = mapWidthInTiles * tileWidth;

        offsetX = 0;
        camera.setToOrtho(false, 800, 480);
        Viewport viewport = new ScreenViewport(camera);
        stage.setViewport(viewport);
        alturaInicial1 = obstaculo1.getY();
        alturaInicial2 = obstaculo2.getY();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(delta);
        if (offsetX > mapWidthInPixels - camera.viewportWidth * 1.2) {
            offsetX = 0;
            personaje.setPosition(100, personaje.getY());
            if (pase1) {
                altura = MathUtils.random(10, 120);
                obstaculo1.setX(obstaculo1.getX() - camera.viewportWidth / 1.672f);
                obstaculo2.setX(obstaculo2.getX() - camera.viewportWidth / 1.672f);
                pase1 = false;
            } else {
                altura = MathUtils.random(10, 120) * -1;
                obstaculo1.setX(obstaculo1.getX() - camera.viewportWidth / 1.672f);
                obstaculo2.setX(obstaculo2.getX() - camera.viewportWidth / 1.672f);
                pase1 = true;
            }
        }
        if (obstaculo1.getX() < personaje.getX() - 150) {
            obstaculo1.setPosition(personaje.getX() + 700, alturaInicial1 - altura);
            obstaculo2.setPosition(personaje.getX() + 700, alturaInicial2 - altura);
        }
        if (personaje.getY() > camera.viewportHeight - personaje.getHeight()) {
            personaje.setY(camera.viewportHeight - personaje.getHeight());
        }
        if (!personaje.getGameOver() && !personaje.getPausa()) {
            gameSound.resume();
        } else if (personaje.getGameOver() && !personaje.getPausa()) {
            gameSound.dispose();
            game.setScreen(new TheEndScreen(game));
        } else if (personaje.getPausa()) {
            personaje.setVerticalMovement(VerticalMovement.NONE);
            gameSound.pause();
        }
        if (personaje.getVerticalMovement() !=VerticalMovement.NONE)
            offsetX += 180 * delta;

        camera.position.x = camera.viewportWidth / 2 + offsetX;
        camera.update();
        mapRenderer.setView(camera);
        mapRenderer.render();
        stage.draw();
    }

    @Override
    public void dispose() {
        stage.dispose();
        map.dispose();
        mapRenderer.dispose();
    }
}