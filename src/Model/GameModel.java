package Model;

public class GameModel {
    private SnakeModel snake;

    public SnakeModel getSnake() {
        return snake;
    }

    public void setSnake(SnakeModel snake) {
        this.snake = snake;
    }



    public GameModel(SnakeModel snake) {
        this.snake = snake;
    }
}
