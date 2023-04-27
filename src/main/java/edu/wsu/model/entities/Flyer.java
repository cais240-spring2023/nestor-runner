package edu.wsu.model.entities;

import static edu.wsu.model.NestorRunner.GROUND_Y;

public class Flyer implements Entity {

    private int x;
    private int y;

    public Flyer() {
        x = START_X;
        y = GROUND_Y - getHeight() - 50;
    }

    @Override
    public int getX() {
        return x;
    }

    @Override
    public void setX(int x) {
        this.x = x;
    }

    @Override
    public int getY() {
        return y;
    }

    @Override
    public void setY(int y) {
        this.y = y;
    }

    @Override
    public int getWidth() {
        return 30;
    }

    @Override
    public int getHeight() {
        return 20;
    }

    @Override
    public boolean hasPassedLeft() {
        return x + getWidth() <= 0;
    }

    @Override
    public void moveLeft(int amountPixels) {
        x -= amountPixels;
    }

    @Override
    public void moveRight(int amountPixels) {
        x += amountPixels;
    }

    @Override
    public void moveUp(int amountPixels) {
        y -= amountPixels;
    }

    @Override
    public void moveDown(int amountPixels) {
        y += amountPixels;
    }

    @Override
    public Type type() {
        return Type.Flyer;
    }
}
