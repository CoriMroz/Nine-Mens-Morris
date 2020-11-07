package Morris_FX;

import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;
import java.awt.*;

public class Board {
    Cell[][] board = new Cell[7][7];
    public Morris.State playState = Morris.State.VOID;

    private GameManager owner;

    //create a new board, iterate over a 7x7 board and add a cell in each spot with a point value too
    public Board(GameManager owner){
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 7; j++) {
                board[i][j] = new Cell(owner, new Point(i,j));
            }
        }
    }

    public GridPane toGridPane(){
        return toGridPane(550,550,Pos.CENTER);
    }
    //put the board in a gridpane, overload if we don't want to give specific variables

    public GridPane toGridPane(int height, int width,Pos loc){
        GridPane retval = new GridPane();
        retval.setMaxSize(height,width);
        retval.setMinSize(height,width);
        retval.setAlignment(loc);
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 7; j++) {
                retval.add(board[i][j], j, i);
            }
        }
        return retval;
    }

    //resets the board, the top and bottom 3 rows can be called in a single for loop
    //the second for loop deals with the middle row with 6 places
    //then set the playState to empty and remove the background
    public void resetBoard() {
        int p = 6;
        for (int t = 0; t < 3; t++) {
            board[t][t].playState = Morris.State.EMPTY;
            board[t][p].playState = Morris.State.EMPTY;
            board[t][3].playState = Morris.State.EMPTY;

            board[p][t].playState = Morris.State.EMPTY;
            board[p][3].playState = Morris.State.EMPTY;
            board[p][p].playState = Morris.State.EMPTY;

            p--;
        }
        for (int t = 0; t < 7; t++) {
            board[3][t].playState = Morris.State.EMPTY;
            if (t == 2)
                t++;
        }

        for (int i = 0; i < 7; i++)
            for (int j = 0; j < 7; j++)
                if (board[i][j].playState == Morris.State.EMPTY)
                    board[i][j].setBackground(null);
    }

    //take the location of a given cell. Going right takes the x value from point and checks the spots to the right
    //if it goes out of bounds or hits the middle of the board, return null
    //if it goes right and finds an empty cell, return that cell to be linked to the cell we gave the function
    Cell findRight(Point location){
        int i = location.x + 1;
        int j = location.y;
        for (; i < 7; i++){

            if( i == 3 && j == 3){
                return null;
            }
            if(board[i][j].playState == Morris.State.EMPTY){
                return board[i][j];
            }
        }
        return null;
    }

    //same but left
    Cell findLeft(Point location){
        int i = location.x - 1;
        int j = location.y;
        for (; i >= 0; i--){

            if( i == 3 && j == 3){
                return null;
            }
            if(board[i][j].playState == Morris.State.EMPTY){
                return board[i][j];
            }
        }
        return null;
    }

    //same but down
    Cell findDown(Point location){
        int i = location.x;
        int j = location.y + 1;
        for (; j < 7; j++){

            if( i == 3 && j == 3){
                return null;
            }
            if(board[i][j].playState == Morris.State.EMPTY){
                return board[i][j];
            }
        }
        return null;
    }

    //same but up
    Cell findUp(Point location){
        int i = location.x;
        int j = location.y - 1;
        for (; j >= 0; j--){

            if( i == 3 && j == 3){
                return null;
            }
            if(board[i][j].playState == Morris.State.EMPTY){
                return board[i][j];
            }
        }
        return null;
    }

    //takes all the find functions and iterates over the entire board
    //for each playable cell make a pointer to the cell up, right, down, and left of that cell
    //also add that linked cell to the list of playable cells for each cell
    //this list is used to check to find a place to move
    public void linkCells(){
        for(int j = 0; j < 7; j++) {
            for (int i = 0; i < 7; i++) {
                if(board[i][j].playState == Morris.State.EMPTY) {

                    board[i][j].up = findUp(board[i][j].getPoint());
                    board[i][j].moves.add(board[i][j].up);

                    board[i][j].right = findRight(board[i][j].getPoint());
                    board[i][j].moves.add(board[i][j].right);

                    board[i][j].down = findDown(board[i][j].getPoint());
                    board[i][j].moves.add(board[i][j].down);

                    board[i][j].left = findLeft(board[i][j].getPoint());
                    board[i][j].moves.add(board[i][j].left);
                }
            }
        }
    }

}