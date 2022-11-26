package es.iesoretania.dam2.hlc;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.TimeUtils;

public class Manager extends Actor {
    private static BitmapFont font;
    private final Personaje personaje;
    private final Obstaculo obstaculo1;
    private final Obstaculo obstaculo2;
    private int score;
    private static int scoreTotal;
    long aumentanPuntos;
    boolean cambioPuntos = true;

    public Manager(Personaje personaje, Obstaculo obstaculo1, Obstaculo obstaculo2) {
        this.personaje = personaje;
        this.obstaculo1 = obstaculo1;
        this.obstaculo2 = obstaculo2;
        if (font == null) {
            font = new BitmapFont(Gdx.files.internal("opfont.fnt"));
        }
        score = 0;
        aumentanPuntos = TimeUtils.nanoTime();
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        font.draw(batch, "Puntos: " + score, personaje.getX() - 80, 460);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if (TimeUtils.nanoTime() - aumentanPuntos > 1000000000 * 3f && personaje.verticalMovement != Personaje.VerticalMovement.NONE && cambioPuntos) {
            score += 100;
            aumentanPuntos = TimeUtils.nanoTime();
            cambioPuntos = false;
        }
        if (TimeUtils.nanoTime() - aumentanPuntos > 1000000000 * 5f && personaje.verticalMovement != Personaje.VerticalMovement.NONE && !cambioPuntos) {
            score += 100;
            aumentanPuntos = TimeUtils.nanoTime();
        }
        if (personaje.getY() < 0 ||
                Intersector.overlaps(new Circle(personaje.getX() + personaje.getWidth() / 2, personaje.getY() + personaje.getHeight() / 2, 20),
                        new Rectangle(obstaculo1.getX(), obstaculo1.getY(), obstaculo1.getWidth(), obstaculo1.getHeight())) ||
                Intersector.overlaps(new Circle(personaje.getX() + personaje.getWidth() / 2, personaje.getY() + personaje.getHeight() / 2, 20),
                        new Rectangle(obstaculo2.getX(), obstaculo2.getY(), obstaculo2.getWidth(), obstaculo2.getHeight()))) {
            scoreTotal = score;
            personaje.verticalMovement = Personaje.VerticalMovement.NONE;
        }
    }

    public static int getScoreTotal() {
        return scoreTotal;
    }
}
