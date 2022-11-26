package es.iesoretania.dam2.hlc;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class OneTaps2D extends Game {
    BitmapFont font;
    SpriteBatch batch;
    ShapeRenderer shapeRenderer;

    @Override
    public void create() {
        font = new BitmapFont(Gdx.files.internal("opfont.fnt"));
        batch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();
        setScreen(new StartScreen(this));
    }

    @Override
    public void dispose() {
        font.dispose();
        batch.dispose();
    }
}