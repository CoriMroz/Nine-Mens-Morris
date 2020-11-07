package Morris_FX;

import javafx.scene.layout.BackgroundImage;

//GameManager controls the state of the board using logic checker
public class GameManager {

    public enum Stage {STAGE1,STAGE2,STAGE3}

    private boolean debug = false;
    public boolean mill = false;
    private Morris.State marble = null;
    boolean turn = true;
    boolean ai;

    Board board;
    Player player1;
    Player player2;
    int turnCounter = 0;

    Stage currentStage = Stage.STAGE1;

    BackgroundImage whitePiece;
    BackgroundImage blackPiece;

    LogicChecker checker;

    //we check to see if we are playing PvP or PvAI
    //then we pass in the background images and connect them to the players using them
    public GameManager(boolean Ai,BackgroundImage whitePiece, BackgroundImage blackPiece){
        ai = Ai;
        checker = new LogicChecker(this);
        this.whitePiece = whitePiece;
        this.blackPiece = blackPiece;

        player1 = new Player("Player1", Morris.State.WHITE,whitePiece);
        //check if AI for sprint 3 prep
        if(!Ai) {
            player2 = new Player("Player2", Morris.State.BLACK,blackPiece);
        }else{
            // player2 = new AIPlayer();
        }
    }


    public void enableDebug(){
        this.debug = true;
    }

    public void CreateNewBoard(){
        board = new Board(this);
    }

    public Board getBoard(){
        return board;
    }

    //this will reset the board, reset turn counter, set game stage to 1, and reset each player
    public void resetBoard(){
        board.resetBoard();
        turnCounter=0;
        currentStage = Stage.STAGE1;
        player1 = new Player("Player1", Morris.State.WHITE,whitePiece);
        if(!ai) {
            player2 = new Player("Player2", Morris.State.BLACK,blackPiece);
        }else{
            // player2 = new AIPlayer();
        }
    }

    //calls linkCells from Board Class
    public void linkCells(){
        board.linkCells();
    }

    public int getTurnCount(){
        return turnCounter;
    }

    public void countTurn(){
        if(turn)
            turnCounter++;
    }

    public boolean getTurn(){
        return turn;
    }

    public void switchTurn(){
        countTurn();
        turn =!turn;
        if(debug){
            //System.out.println("Turn " + getTurnCount());
        }
    }

    //this is called in HandleMouseClick to switch to stage 2
    //checks to see if each person has marbles to place still
    public boolean eitherPlayerHasMarbles(){
        return player1.remainingMarbles() || player2.remainingMarbles();
    }

    //gets the current player
    public Player getCurrentPlayer(){
        if(turn){
            return player1;
        }
        return player2;
    }

    //gets next player
    public Player getNextPlayer(){
        if(turn){
            return player2;
        }
        return player1;
    }

    //calls holdMarble from Player class to keep the cell information in memory
    public void holdMarble(Cell cell){
        getCurrentPlayer().holdMarble(cell);
    }

    //calls validMove function from LogicChecker
    public boolean validMove(Cell cell){
        return checker.validMove(cell);
    }

    //calls canPickup function in LogicChecker
    public boolean canPickup(Cell cell){
        return checker.canPickup(cell, getCurrentPlayer());
    }

    //first check to see if you are clicking on a marble with the same playState (BLACK, WHITE) as the other player (getNextPlayer)
    //then run mill check on their marble to make sure it isn't part of a mill itself
    //if not, call EmptyCell to remove marble image and set playState to EMPTY
    //set mill to false, and switch turns
    public void removeOpponent(Cell cell){
        if(getNextPlayer().myState == cell.playState){
            if(!checker.millCheck(cell)){
                cell.EmptyCell();
                getNextPlayer().removeMarble();
                mill = false;
                switchTurn();
            }
        }
    }

    //function to place a marble in a given cell during STAGE1
    //during stage 2, this function simply checks if a Player has picked up a marble to move it
    public boolean Place(Cell cell){
        Player current = getCurrentPlayer();

        switch (currentStage){
            case STAGE1:
                //in STAGE1 if you still have marbles to place and you are clicking on a valid cell then place marble
                if (current.remainingMarbles() && validMove(cell)) {
                    current.useMarble();
                    if (debug) {
                        System.out.println(current.name + " has " + current.getMarbles() + " remaining");
                    }
                    //if no player has any more to place, switch to stage 2
                    if(!eitherPlayerHasMarbles()) {
                        currentStage = Stage.STAGE2;
                        if(debug){
                            System.out.println("Switched stage");
                        }
                    }
                    return true;
                }
                break;

            case STAGE2:
                if(current.hasHeldMarble()){
                    return true;
                }
                break;
        }
        //Allowpickup

        return false;
    }

    public Stage getCurrentStage(){
        return currentStage;
    }

    //for AI use
    public void Place(int x, int y){

    }


}
