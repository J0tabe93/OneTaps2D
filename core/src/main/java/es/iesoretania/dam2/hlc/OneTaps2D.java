package es.iesoretania.dam2.hlc;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import es.iesoretania.dam2.hlc.Screens.StartScreen;

public class OneTaps2D extends Game {
    private BitmapFont font;
    private SpriteBatch batch;

    @Override
    public void create() {
        font = new BitmapFont(Gdx.files.internal("opfont.fnt"));
        batch = new SpriteBatch();
        setScreen(new StartScreen(this));
    }

    @Override
    public void dispose() {
        font.dispose();
        batch.dispose();
    }

    public BitmapFont getFont() {
        return font;
    }

    public SpriteBatch getBatch() {
        return batch;
    }
}