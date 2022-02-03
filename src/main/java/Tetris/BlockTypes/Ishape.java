package Tetris.BlockTypes;

import Tetris.Block;

public class Ishape extends Block {

    public Ishape() {
        super(new int[][] {{1}, {1}, {1}, {1}});
    }

    @Override
    public void turn() {
        if (getWidth() == 1) {
            setX(getX() - 1);
            setY(getY() + 1);
        }
        if (getHeight() == 1) {
            setX(getX() + 1);
            setY(getY() - 1);
        }
        super.turn();
    }
}
