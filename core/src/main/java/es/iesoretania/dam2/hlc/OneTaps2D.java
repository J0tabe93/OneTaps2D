package es.iesoretania.dam2.hlc;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class OneTaps2D extends ApplicationAdapter {
    Stage stage;
    TiledMap map;
    OrthographicCamera camera;
    OrthogonalTiledMapRenderer mapRenderer;
    private int mapWidthInPixels;
    private float offsetX;
    Actor personaje;
    Actor obstaculo1;
    Actor obstaculo2;
    static boolean pase1 = true;
    int altura;
    float alturaInicial1, alturaInicial2;

    @Override
    public void create() {
        stage = new Stage();
        camera = new OrthographicCamera();
        map = new TmxMapLoader().load("ocean.tmx");
        mapRenderer = new OrthogonalTiledMapRenderer(map);
        Gdx.input.setInputProcessor(stage);
        personaje = new Personaje();
        obstaculo1 = new Obstaculo(0);
        obstaculo2 = new Obstaculo(1);
        stage.addActor(personaje);
        stage.addActor(obstaculo1);
        stage.addActor(obstaculo2);
        stage.setKeyboardFocus(personaje);
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
    public void render() {
        Gdx.gl.glClearColor(0, 0, 0, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(Gdx.graphics.getDeltaTime());
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
        if (personaje.getY() < 0 ||
                Intersector.overlaps(new Rectangle(personaje.getX(), personaje.getY(), personaje.getWidth(), personaje.getHeight()),
                new Rectangle(obstaculo1.getX(), obstaculo1.getY(), obstaculo1.getWidth(), obstaculo1.getHeight())) ||
                Intersector.overlaps(new Rectangle(personaje.getX(), personaje.getY(), personaje.getWidth(), personaje.getHeight()),
                new Rectangle(obstaculo2.getX(), obstaculo2.getY(), obstaculo2.getWidth(), obstaculo2.getHeight()))) {

            Personaje.setGameOver(true);
        } else offsetX += 175 * Gdx.graphics.getDeltaTime();

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