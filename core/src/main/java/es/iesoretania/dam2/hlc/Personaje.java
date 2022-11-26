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
    Animation<TextureRegion> animacionActual;
    Texture textura;
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

    public Personaje() {
        if (font == null) {
            font = new BitmapFont(Gdx.files.internal("opfont.fnt"));
        }
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
        if (!gameOver && !pausa) {
            processKeyboard();
            if (TimeUtils.nanoTime() - tiempoSalto > 300000000) {
                verticalMovement = VerticalMovement.DOWN;
            }

            if (verticalMovement == VerticalMovement.UP) {
                this.moveBy(180 * delta, 175 * delta);
                stateTime += delta * 0.07f;
            }
            if (verticalMovement == VerticalMovement.DOWN) {
                this.moveBy(180 * delta, -175 * delta);
                stateTime += delta * 0.07f;
            }
        } else {
            processKeyboard();
            verticalMovement = VerticalMovement.NONE;
        }
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
                pausa = false;
            }
        }
    }
}