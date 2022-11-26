package es.iesoretania.dam2.hlc;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;

public class TheEndScreen extends ScreenAdapter {
    OneTaps2D game;
    private final Sound gameOverSound = Gdx.audio.newSound(Gdx.files.internal("gameover.mp3"));

    public TheEndScreen(OneTaps2D game) {
        this.game = game;
        gameOverSound.play();
    }

    @Override
    public void show() {
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
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.batch.begin();
        int height = Gdx.graphics.getHeight();
        game.font.draw(game.batch, "GAME OVER", Gdx.graphics.getWidth() / 3f, height * .8f);
        game.font.draw(game.batch, Manager.getScoreTotal() + " puntos.", Gdx.graphics.getWidth() / 3f, height * .55f);
        game.font.draw(game.batch, "Click para ir a la pantalla inicial.", Gdx.graphics.getWidth() / 3f, height * .3f);
        game.batch.end();
    }

    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
    }
}