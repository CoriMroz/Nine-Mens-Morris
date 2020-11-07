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
        //mill starts as false, if there is a mill it flips to true, owner can remove a piece
        if(owner.mill) {
            owner.removeOpponent(this);
            //removeOpponent checks to make sure it is a valid piece to be removed
            System.out.println("Please remove a piece");
            return;
        }

        switch (owner.getCurrentStage()) {
            case STAGE1:
                //Place checks if your click is on a valid cell then set proper background  and playState of cell
                if (owner.Place(this)) {
                    this.setBackground(new Background(owner.getCurrentPlayer().myMarble));
                    this.playState = owner.getCurrentPlayer().myState;
                    //check for Mill. If so, set to true and break to beginning without changing turn
                    if(owner.checker.millCheck(this)){
                        owner.mill = true;
                        System.out.println("Mill!");
                        break;
                    }
                    owner.switchTurn();
                }
                break;
            case STAGE2:
                //owner.Place checks if the user has just picked up a marble, then validMove checks for valid moves
                //if so, set the proper background and playState of the cell
                if(owner.Place(this)) {
                    if (owner.validMove(this)){
                        this.setBackground(new Background(owner.getCurrentPlayer().myMarble));
                        this.playState = owner.getCurrentPlayer().myState;
                        owner.getCurrentPlayer().resetMarble();
                        //check for mill on placed piece
                        if(owner.checker.millCheck(this)){
                            owner.mill = true;
                            System.out.println("Mill!");
                            break;
                        }
                        owner.switchTurn();
                    }
                }else{
                    //if the current Player has not picked up a marble, check if the marble they clicked on can be picked up
                    //if so, hold the playState of that cell in holdMarble and empty the cell where the marble was and break
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
