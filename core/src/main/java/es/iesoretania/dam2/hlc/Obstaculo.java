package es.iesoretania.dam2.hlc;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Obstaculo extends Actor {
    Texture texturaDown;
    Texture texturaUp;
    int tipo;

    public Obstaculo(int tipo) {
        this.tipo = tipo;
        if (tipo == 0) {
            texturaDown = new Texture(Gdx.files.internal("mastDown.png"));
            setSize(texturaDown.getWidth() / 1.5f, texturaDown.getHeight());
            setPosition(790 - getWidth(), -130);
        } else {
            texturaUp = new Texture(Gdx.files.internal("mastUp.png"));
            setSize(texturaUp.getWidth() / 1.5f, texturaUp.getHeight());
            setPosition(790 - getWidth(), 610 - getHeight());
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        if (tipo == 0)
            batch.draw(texturaDown, getX(), getY(), getWidth(), getHeight());
        else
            batch.draw(texturaUp, getX(), getY(), getWidth(), getHeight());
    }

    @Override
    public void act(float delta) {
        super.act(delta);
    }
}