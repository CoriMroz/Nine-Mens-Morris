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

    public void useMarble(){
        marbles -=1;
        onBoard++;
    }

    public void removeMarble(){
        onBoard--;
    }

    public boolean remainingMarbles(){
        return marbles!=0;
    }

    public void holdMarble(Cell inPlay){
        this.inPlay = inPlay;
    }

    public boolean hasHeldMarble(){
        return inPlay != null;
    }

    public void resetMarble(){
        inPlay = null;
    }
}
