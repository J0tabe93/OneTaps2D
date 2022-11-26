package es.iesoretania.dam2.hlc;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.TimeUtils;

public class Personaje extends Actor {
    private static BitmapFont font;

    enum VerticalMovement {UP, NONE, DOWN}

    private static final int FRAME_COLS = 4, FRAME_ROWS = 1;
    Animation<TextureRegion> animacionActual, animacionInflado, animacionDesinflar;
    Texture textura1, textura2;
    float stateTime;
    VerticalMovement verticalMovement;
    long tiempoSalto;
    private static boolean gameOver = false;

    public boolean getGameOver() {
        return gameOver;
    }

    private static boolean pausa = false;

    public static void setGameOver(boolean g) {
        gameOver = g;
    }

    public boolean getPausa() {
        return pausa;
    }

    private final Sound salto = Gdx.audio.newSound(Gdx.files.internal("salto.mp3"));
    private final Sound pausaSound = Gdx.audio.newSound(Gdx.files.internal("pausa.mp3"));
    boolean reiniciarFrame = true;

    public Personaje() {
        if (font == null) {
            font = new BitmapFont(Gdx.files.internal("opfont.fnt"));
        }
        textura1 = new Texture(Gdx.files.internal("mdlInflado.png"));
        textura2 = new Texture(Gdx.files.internal("mdlDesinflar.png"));
        TextureRegion[][] tmp1 = TextureRegion.split(textura1, textura1.getWidth() / FRAME_COLS, textura1.getHeight() / FRAME_ROWS);
        TextureRegion[][] tmp2 = TextureRegion.split(textura2, textura2.getWidth() / FRAME_COLS, textura2.getHeight() / FRAME_ROWS);
        TextureRegion[] frames1 = new TextureRegion[FRAME_COLS * FRAME_ROWS];
        TextureRegion[] frames2 = new TextureRegion[FRAME_COLS * FRAME_ROWS];
        int index1 = 0;
        int index2 = 0;
        for (int i = 0; i < FRAME_ROWS; i++) {
            for (int j = 0; j < FRAME_COLS; j++) {
                frames1[index1++] = tmp1[i][j];
                frames2[index2++] = tmp2[i][j];
            }
        }
        animacionInflado = new Animation<>(0.02f, frames1);
        animacionDesinflar = new Animation<>(0.02f, frames2);
        animacionActual = animacionInflado;
        animacionActual.setPlayMode(Animation.PlayMode.LOOP_PINGPONG);
        stateTime = 0;
        verticalMovement = VerticalMovement.DOWN;
        setSize((float) textura1.getWidth() / FRAME_COLS, (float) textura1.getHeight() / FRAME_ROWS);
        setPosition(100, 480 - getHeight());
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        if (pausa) {
            this.toFront();
            font.setColor(Color.RED);
            font.draw(batch, "TO BE CONTINUED", getX() + 190, Gdx.graphics.getHeight() / 1.9f);
        }
        batch.draw(animacionActual.getKeyFrame(stateTime, true), getX(), getY());
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        stateTime += delta * 0.015f;
        if (!gameOver && !pausa) {
            if (TimeUtils.nanoTime() - tiempoSalto > 300000000 && verticalMovement != VerticalMovement.NONE) {
                verticalMovement = VerticalMovement.DOWN;
            }
            if (verticalMovement == VerticalMovement.UP) {
                this.moveBy(180 * delta, 175 * delta);
            }
            if (verticalMovement == VerticalMovement.DOWN) {
                this.moveBy(180 * delta, -175 * delta);
            }
        }
        if (verticalMovement == VerticalMovement.NONE && !pausa) {
            if (reiniciarFrame) {
                stateTime = 0;
                reiniciarFrame = false;
            }
            stateTime += delta * 0.015f;
            animacionActual.setPlayMode(Animation.PlayMode.NORMAL);
            animacionActual = animacionDesinflar;
            if (animacionActual.isAnimationFinished(stateTime)) {
                setGameOver(true);
                stateTime = 0;
            }
        } else processKeyboard();
    }

    public void processKeyboard() {
        if (!pausa) {
            if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
                salto.play();
                verticalMovement = VerticalMovement.UP;
                tiempoSalto = TimeUtils.nanoTime();
            }
        }
        if (Gdx.input.isButtonJustPressed(Input.Buttons.RIGHT)) {
            if (!pausa) {
                pausaSound.play();
                pausa = true;
            } else {
                pausaSound.stop();
                verticalMovement = VerticalMovement.DOWN;
                pausa = false;
            }
        }
    }
}