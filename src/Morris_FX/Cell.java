package Morris_FX;

import javafx.scene.layout.Pane;

import java.awt.*;
import java.util.ArrayList;

public class Cell extends Pane {

    public java.util.List<Cell> moves = new ArrayList<Cell>();
    private Point location;
    public Morris.State playState = Morris.State.VOID;
    private GameManager owner;

    //references to  the playable 'nodes' on the board
    //creating the mapped out board fully in memory
    //each cell has a reference to the cell up/down/left/right and is null if there is not a valid spot
    public Cell up;
    public Cell down;
    public Cell left;
    public Cell right;

    public Cell(GameManager manager, Point here) {
        location = here;
        owner = manager;
        this.setPrefSize(2000, 2000);
        this.setOnMouseClicked(e -> handleMouseClick());
    }

    //sets cell state to empty and removes marble image
    public void EmptyCell(){
        this.playState = Morris.State.EMPTY;
        this.setBackground(null);
    }

    //returns a cell's Point(x, y) value
    public Point getPoint(){
        return location;
    }

    //this function can be cleaned up a bit
    //has stages for each stage. Stage 3 has yet to be implemented
    //Code flips game state in owner, owner controls gameState

    public void handleMouseClick() {
        if(owner.mill) {
            owner.removeOpponent(this);
            return;
        }
        else {
            owner.handle(this);
        }
    }



}
