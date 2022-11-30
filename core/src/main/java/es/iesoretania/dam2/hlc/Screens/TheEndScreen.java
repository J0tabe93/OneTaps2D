package es.iesoretania.dam2.hlc.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import es.iesoretania.dam2.hlc.Actors.Manager;
import es.iesoretania.dam2.hlc.Actors.Personaje;
import es.iesoretania.dam2.hlc.Game.OneTaps2D;

public class TheEndScreen extends ScreenAdapter {
    private final OneTaps2D game;
    private final Sound gameOverSound = Gdx.audio.newSound(Gdx.files.internal("gameover.mp3"));

    public TheEndScreen(OneTaps2D game) {
        this.game = game;
        gameOverSound.play();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0.3f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.getBatch().begin();
        game.getFont().draw(game.getBatch(), "GAME OVER", Gdx.graphics.getWidth() / 2.45f, Gdx.graphics.getHeight() * .8f);
        game.getFont().draw(game.getBatch(), Manager.getScoreTotal() + " puntos.", Gdx.graphics.getWidth() / 2.45f, Gdx.graphics.getHeight() * .55f);
        game.getFont().draw(game.getBatch(), "Click para ir a la pantalla inicial.", Gdx.graphics.getWidth() / 3.3f, Gdx.graphics.getHeight() * .3f);
        game.getBatch().end();
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
    public void hide() {
        Gdx.input.setInputProcessor(null);
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
        Gdx.gl.glClearColor(0, 0, 0.3f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.getBatch().begin();
        game.getFont().draw(game.getBatch(), "GAME OVER", width / 2.45f, height * .8f);
        game.getFont().draw(game.getBatch(), Manager.getScoreTotal() + " puntos.", width / 2.45f, height * .55f);
        game.getFont().draw(game.getBatch(), "Click para ir a la pantalla inicial.", width / 3.3f, height * .3f);
        game.getBatch().end();
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
        show();
    }
}