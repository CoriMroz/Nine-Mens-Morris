package Morris_FX;

import javafx.scene.layout.BackgroundImage;

public class Player {
    int marbles = 9;
    int onBoard = 0;
    String name;
    Morris.State myState;
    BackgroundImage myMarble;
    Cell inPlay;

    //creating a player we pass in the name, the state (either BLACK, or WHITE) and the proper piece image
    public Player(String name, Morris.State myState, BackgroundImage myMarble){
        this.name = name;
        this.myState = myState;
        this.myMarble = myMarble;
    }

    public int getMarbles(){
        return marbles;
    }

    //decrease your starting marbles
    //increase the count of marbles on the board
    public void useMarble(){
        marbles -=1;
        onBoard++;
    }

    //decrease count of marbles on the board
    public void removeMarble(){
        onBoard--;
    }

    //check if there are still marbles to be places for STAGE1
    public boolean remainingMarbles(){
        return marbles!=0;
    }

    //this function holds in memory the state of the cell a Player picks a marble up from
    //cell inPlay is the cell that Player is moving from
    public void holdMarble(Cell inPlay){
        this.inPlay = inPlay;
    }

    //check if a Player is holding a marble
    public boolean hasHeldMarble(){
        return inPlay != null;
    }

    //when a marble is placed out of the hand, return inPlay to null
    public void resetMarble(){
        inPlay = null;
    }
}
