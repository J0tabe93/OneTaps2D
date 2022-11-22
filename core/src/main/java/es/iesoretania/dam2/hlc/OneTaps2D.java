package es.iesoretania.dam2.hlc;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class OneTaps2D extends ApplicationAdapter {
	Stage stage;
	TiledMap map;
	OrthographicCamera camera;
	OrthogonalTiledMapRenderer mapRenderer;
	private int mapWidthInPixels;
	private int mapHeightInPixels;
	private float offsetX, offsetY;

	@Override
	public void create() {
		stage = new Stage();
		camera=new OrthographicCamera();
		map = new TmxMapLoader().load("ocean.tmx");
		mapRenderer=new OrthogonalTiledMapRenderer(map);
		Gdx.input.setInputProcessor(stage);
		MapProperties properties = map.getProperties();
		int tileWidth = properties.get("tilewidth", Integer.class);
		int tileHeight = properties.get("tileheight", Integer.class);
		int mapWidthInTiles = properties.get("width", Integer.class);
		int mapHeightInTiles = properties.get("height", Integer.class);
		mapWidthInPixels = mapWidthInTiles * tileWidth;
		mapHeightInPixels = mapHeightInTiles * tileHeight;

		offsetX = 0;
		offsetY = 0;

		camera.setToOrtho(false, 800, 480);

		Viewport viewport = new ScreenViewport(camera);
		stage.setViewport(viewport);
	}

	@Override
	public void render() {
		Gdx.gl.glClearColor(0, 0, 0, 1f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		stage.act(Gdx.graphics.getDeltaTime());
		offsetX+= 50 * Gdx.graphics.getDeltaTime();
		if (offsetX > mapWidthInPixels - camera.viewportWidth*1.2) offsetX = 0;
		camera.position.x=camera.viewportWidth/2+offsetX;
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