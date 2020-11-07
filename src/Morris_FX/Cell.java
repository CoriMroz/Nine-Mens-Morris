package Morris_FX;

import javafx.scene.layout.Background;
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

    public void EmptyCell(){
        this.playState = Morris.State.EMPTY;
        this.setBackground(null);
    }

    public Point getPoint(){
        return location;
    }

    public void handleMouseClick() {
        //Code flips game state in owner, owner controls gamestate.
        //mill starts as false, if there is a mill it flips to true, owner can remove a piece
        if(owner.mill) {
            owner.removeOpponent(this);
            //removeOpponent checks to make sure it is a valid piece to be removed
            System.out.println("Please remove a piece");
            return;
        }

        switch (owner.getCurrentStage()) {
            case STAGE1:
                //Place checks if your click is on a valid cell
                if (owner.Place(this)) {
                    this.setBackground(new Background(owner.getCurrentPlayer().myMarble));
                    this.playState = owner.getCurrentPlayer().myState;
                    if(owner.checker.millCheck(this)){
                        owner.mill = true;
                        System.out.println("Mill!");
                        break;
                    }
                    owner.switchTurn();
                }
                break;
            case STAGE2:
                //if y
                if(owner.Place(this)) {
                    if (owner.validMove(this)){
                        this.setBackground(new Background(owner.getCurrentPlayer().myMarble));
                        this.playState = owner.getCurrentPlayer().myState;
                        owner.getCurrentPlayer().resetMarble();
                        if(owner.checker.millCheck(this)){
                            owner.mill = true;
                            System.out.println("Mill!");
                            break;
                        }
                        owner.switchTurn();
                    }
                }else{
                    if (owner.canPickup(this)) {
                        owner.holdMarble(this);
                        this.EmptyCell();
                        break;
                    } else {
                        System.out.println("Some error");
                    }
                }
                break;
            case STAGE3:
                break;
            default:
                System.out.println("Uh, what?");
                break;
        }
    }

}
