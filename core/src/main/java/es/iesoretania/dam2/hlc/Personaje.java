package es.iesoretania.dam2.hlc;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.utils.TimeUtils;

public class Personaje extends Actor {
    enum VerticalMovement {UP, NONE, DOWN}

    private static final int FRAME_COLS = 4, FRAME_ROWS = 1;


    Animation<TextureRegion> animacionActual;
    Texture textura;
    float stateTime;
    VerticalMovement verticalMovement;
    long tiempoSalto;
    private static boolean gameOver=false;

    public Personaje() {
        textura = new Texture(Gdx.files.internal("mdlInflado.png"));
        TextureRegion[][] tmp = TextureRegion.split(textura, textura.getWidth() / FRAME_COLS, textura.getHeight() / FRAME_ROWS);
        TextureRegion[] frames = new TextureRegion[FRAME_COLS * FRAME_ROWS];
        int index1 = 0;
        for (int i = 0; i < FRAME_ROWS; i++) {
            for (int j = 0; j < FRAME_COLS; j++) {
                frames[index1++] = tmp[i][j];
            }
        }
        animacionActual = new Animation<>(0.02f, frames);
        stateTime = 0f;
        verticalMovement = VerticalMovement.DOWN;
        setSize((float) textura.getWidth() / FRAME_COLS, (float) textura.getHeight() / FRAME_ROWS);
        setPosition(100, 240);

        addListener(new PersonajeInputListener());
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(animacionActual.getKeyFrame(stateTime, true), getX(), getY());
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        if (TimeUtils.nanoTime() - tiempoSalto > 300000000 && !getGameOver()) {
            verticalMovement = VerticalMovement.DOWN;
        }

        if (verticalMovement == VerticalMovement.UP) {
            this.moveBy(175 * delta, 175 * delta);
            stateTime += delta * 0.07f;
        }
        if (verticalMovement == VerticalMovement.DOWN) {
            this.moveBy(175 * delta, -175 * delta);
            stateTime += delta * 0.07f;
        }
        if(getGameOver()){
            verticalMovement=VerticalMovement.NONE;
        }
    }

    class PersonajeInputListener extends InputListener {
        @Override
        public boolean keyDown(InputEvent event, int keycode) {
            if (keycode == Input.Keys.SPACE)
                verticalMovement = VerticalMovement.UP;
            tiempoSalto = TimeUtils.nanoTime();
            return true;
        }

        @Override
        public boolean keyUp(InputEvent event, int keycode) {
            if (keycode == Input.Keys.SPACE)
                verticalMovement = VerticalMovement.UP;
            return true;
        }
    }
    public boolean getGameOver() {
        return gameOver;
    }
}