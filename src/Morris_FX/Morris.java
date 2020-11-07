package Morris_FX;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.geometry.Pos;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.application.Platform;
import javafx.scene.image.Image;

import java.io.FileInputStream;
import java.io.FileNotFoundException;


public class Morris extends Application {

    public static boolean turn = true;

    enum State {EMPTY, VOID, BLACK, WHITE};
    Scene scene1, scene2, scene3;


    public static void main(String[] args) {
        launch(args);
    }
    //public GameManager gameManager = new GameManager();
    @Override
    public void start(Stage primaryStage) throws FileNotFoundException {
        primaryStage.setTitle("Nine Mens Morris");

        Button menu = new Button("Menu");
        Button openMenu = new Button("Menu");
        openMenu.setOnAction(e -> primaryStage.setScene(scene2));
        Button exit = new Button("Exit");
        Button again = new Button("Play again");
        Button twoPlayer = new Button("Player vs Player");
        Button Ai = new Button("Player vs AI");
//set exit action for the exit button
        exit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                Platform.exit();
            }
        });

//creating a box for scene3 (game scene) to include the 3 above buttons
        HBox choices = new HBox();
        choices.getChildren().addAll(again, openMenu, exit);

//importing images!
        Image woodBoard = new Image(new FileInputStream("./images/Morris_Board_Wood.png"),550,550,false,true);
        Image blackMarble = new Image(new FileInputStream("./images/BlackMarble.png"), 40,40,false,true);
        Image whiteMarble = new Image(new FileInputStream("./images/WhiteMarble.png"), 40,40,false,true);
        BackgroundImage emptyBoard = new BackgroundImage(woodBoard,BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
        BackgroundImage blackPiece = new BackgroundImage(blackMarble,BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT);
        BackgroundImage whitePiece = new BackgroundImage(whiteMarble,BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT);

//set a new gameManager
        GameManager manager = new GameManager(false,whitePiece,blackPiece);
        manager.enableDebug();
        manager.CreateNewBoard();
        GridPane board = manager.getBoard().toGridPane();
        manager.resetBoard();
        manager.linkCells();


//setting the pane for game in the window
        BorderPane gameWindow = new BorderPane();
        gameWindow.setBottom(choices);

        Pane boardPane = new Pane(board);
        board.setPadding(new Insets((20), 10, 10, 20));
        boardPane.setBackground(new Background(emptyBoard));

        gameWindow.setCenter(boardPane);

//Scene 1
        GridPane first = new GridPane();
        first.setAlignment(Pos.CENTER);
        first.setHgap(10);
        first.setVgap(10);
        first.setPadding(new Insets((25), 25, 25, 25));

        Text welcome = new Text ("Welcome");
        menu.setOnAction(e -> primaryStage.setScene(scene2));
        twoPlayer.setOnAction(e -> primaryStage.setScene(scene3));

        welcome.setFont(Font.font("Tacoma", FontWeight.NORMAL, 20));
        first.add(welcome, 1, 0, 3, 1);
        first.add(menu, 0, 1);
        first.add(twoPlayer, 1, 1);
        first.add(Ai, 2,1);

        scene1 = new Scene(first, 600, 600);

//Scene 2
        GridPane second = new GridPane();
        second.setAlignment(Pos.CENTER);
        second.setHgap(10);
        second.setVgap(10);
        second.setPadding(new Insets((25), 25, 25, 25));

        Text menuLabel = new Text("Menu");
        Button play = new Button("Play");
        play.setOnAction(e -> {
            manager.resetBoard();
            primaryStage.setScene(scene3);
        });
        menuLabel.setFont(Font.font("Tacoma", FontWeight.NORMAL, 20));
        second.add(menuLabel, 0, 0, 2, 1);
        second.add(play, 1, 1);
        scene2 = new Scene(second, 600, 600);



//scene 3
        Label label3 = new Label("Game");
        again.setOnAction(e -> manager.resetBoard());
        scene3 = new Scene(gameWindow, 600, 600);


        primaryStage.setScene(scene1);
        primaryStage.show();
    }


 }






