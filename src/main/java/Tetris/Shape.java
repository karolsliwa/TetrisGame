package Tetris;

public enum Shape {
    L,
    SQUARE,
    LINE,
    HALFX,
    LOPPOSITE,
    ZIGZAG,
    ZIGZAGOPPOSITE;

    public int[][] toIntMatrix() {
        return switch (this) {
            case L -> new int[][] {{1, 0}, {1, 0}, {1, 1}};
            case SQUARE -> new int[][] {{1, 1}, {1, 1}};
            case LINE -> new int[][] {{1}, {1}, {1}, {1}};
            case HALFX -> new int[][] {{1, 0}, {1, 1}, {1, 0}};
            case LOPPOSITE -> new int[][] {{0, 1}, {0, 1}, {1, 1}};
            case ZIGZAG -> new int[][] {{1, 0}, {1, 1}, {0, 1}};
            case ZIGZAGOPPOSITE -> new int[][] {{0, 1}, {1, 1}, {1, 0}};
        };
    }
    public int[][] rotate(int[][] shape) {
        int w = shape[0].length;
        int h = shape.length;
        int[][] newShape = new int[w][h];
        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h; j++) {
                newShape[i][j] = shape[h - j - 1][i];
            }
        }
        return newShape;
    }
    public int[][][] Rotations(Block block) {
        int[][][] rotations = new int[4][][];
        int[][] shape = this.toIntMatrix();
        rotations[0] = shape;
        for (int i = 1; i < 4; i++) {
            rotations[i] = rotate(rotations[i - 1]);
        }
        return rotations;
    }
}
