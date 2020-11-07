package Morris_FX;

import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;
import java.awt.*;

public class Board {
    Cell[][] board = new Cell[7][7];
    public Morris.State playState = Morris.State.VOID;

    private GameManager owner;
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