package edu.wsu.model.entities;

import static edu.wsu.model.NestorRunner.GROUND_Y;

public class Cannon implements Entity {

    private int x;
    public int y;

    public Cannon() {
        x = START_X;
        y = GROUND_Y - getHeight() - 7;
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
        return 80;
    }

    @Override
    public int getHeight() {
        return 60;
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
        return Type.Cannon;
    }
}