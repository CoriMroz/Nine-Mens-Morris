package Morris_FX;

public class LogicChecker {
    GameManager owner;

    public LogicChecker(GameManager owner){
        this.owner = owner;
    }

    public boolean validMove(Cell cell){
        switch (owner.currentStage) {
            case STAGE1:
                return cell.playState == Morris.State.EMPTY;
            case STAGE2:
                if (cell.playState == Morris.State.EMPTY){
                    if(cell.moves.contains(owner.getCurrentPlayer().inPlay)) {
                        return true;
                    }
                }
                break;
        }
        return false;
    }

    //check formation of mills recursively. reset the recursively called cell after each loop
    public boolean millCheck (Cell cell){
        Cell recursiveCell = cell;
        int vertical = 1;
        int horizontal = 1;

        //check vertical first, check up pointer until either the reference is null or the playstate is not the 'color' of the given cell
        while(recursiveCell.up != null && recursiveCell.up.playState == cell.playState) {
            recursiveCell = recursiveCell.up;
            vertical++;
        }
        recursiveCell = cell;
        while(recursiveCell.down != null && recursiveCell.down.playState == cell.playState) {
            recursiveCell = recursiveCell.down;
            vertical++;
        }
        if (vertical == 3) {
            return true;
        }
        recursiveCell = cell;

        //now check horizontal
        while(recursiveCell.left != null && recursiveCell.left.playState == cell.playState) {
            recursiveCell = recursiveCell.left;
            horizontal++;
        }
        recursiveCell = cell;
        while(recursiveCell.right != null && recursiveCell.right.playState == cell.playState) {
            recursiveCell = recursiveCell.right;
            horizontal++;
        }
        if (horizontal == 3) {
            return true;
        }

        return false;
    }




    public boolean moveCheck (Cell cell){
        int counter = 0;
        if(cell.up != null && cell.up.playState == Morris.State.EMPTY) {
            counter++;
        }
        if(cell.down != null && cell.down.playState == Morris.State.EMPTY) {
            counter++;
        }
        if(cell.left != null && cell.left.playState == Morris.State.EMPTY) {
            counter++;
        }
        if(cell.right != null && cell.right.playState == Morris.State.EMPTY) {
            counter++;
        }
        if(counter > 0) {
            System.out.println("you may move");
            return true;
        }
        else {
            System.out.println("no possible moves");
            return false;
        }
    }

    public boolean canPickup(Cell cell, Player current){
        if(cell.playState == current.myState && moveCheck(cell)){
            if(!current.hasHeldMarble()){
                return true;
            }
        }
        return false;
    }
}

