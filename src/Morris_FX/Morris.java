package Morris_FX;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.SVGPath;
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
import javafx.stage.StageStyle;

import java.io.FileInputStream;
import java.io.FileNotFoundException;


public class Morris extends Application {

    public static boolean turn = true;

    enum State {EMPTY, VOID, BLACK, WHITE};
    Scene scene1, scene2, scene3;


    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) throws FileNotFoundException {
        primaryStage.initStyle(StageStyle.TRANSPARENT);
        primaryStage.setTitle("Nine Mens Morris");

        Button again = new Button("Play again");
        Button openMenu = new Button("Menu");
        openMenu.setOnAction(e -> primaryStage.setScene(scene2));

//importing images!
        Image woodBoard = new Image(new FileInputStream("./res/img/Morris_Board_Wood.png"),550,550,false,true);
        Image blackMarble = new Image(new FileInputStream("./res/img/Black_Marble.png"), 40,40,false,true);
        Image whiteMarble = new Image(new FileInputStream("./res/img/White_Marble.png"), 40,40,false,true);
        BackgroundImage emptyBoard = new BackgroundImage(woodBoard,BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
        BackgroundImage blackPiece = new BackgroundImage(blackMarble,BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT);
        BackgroundImage whitePiece = new BackgroundImage(whiteMarble,BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT);

        Image background = new Image(new FileInputStream("./res/img/background2.jpg"),600,600,false,true);
        BackgroundImage appBackground = new BackgroundImage(background,BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT);

        Image gear = new Image(new FileInputStream("./res/img/gear_Icon.png"), 35,35,false,true);
        ImageView gearIcon = new ImageView(gear);
        Image one = new Image(new FileInputStream("./res/img/1P_Icon.png"), 35,35,false,true);
        ImageView onePlayerIcon = new ImageView(one);
        Image two = new Image(new FileInputStream("./res/img/2P_Icon.png"), 37,37,false,true);
        ImageView twoPlayerIcon = new ImageView(two);
        Image marbles = new Image(new FileInputStream("./res/img/2Marbles.png"), 230,230,false, true);
        ImageView twoMarbles = new ImageView(marbles);
        twoMarbles.setLayoutX(285);
        twoMarbles.setLayoutY(270);

        Image classicButton = new Image(new FileInputStream("./res/img/buttons/button11.png"), 225,100,false,true);
        //BackgroundImage firstButton = new BackgroundImage(classicButton,BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT);
        ImageView firstButton = new ImageView(classicButton);
        firstButton.setLayoutY(222);
        firstButton.setLayoutX(7);
        ImageView secondButton = new ImageView(classicButton);
        secondButton.setLayoutY(332);
        secondButton.setLayoutX(7);
        ImageView thirdButton = new ImageView(classicButton);
        thirdButton.setLayoutY(442);
        thirdButton.setLayoutX(7);

//creating buttons

        Button twoPlayer = new Button("    TWO PLAYERS");
            twoPlayer.setId("twoPlayer");
            twoPlayer.setGraphic(twoPlayerIcon);
            twoPlayer.setLayoutY(205);
            twoPlayer.setLayoutX(22);
            twoPlayer.setMinSize(200,57);
            twoPlayer.setOnAction(e -> {
                primaryStage.setScene(scene3);
            });
        Button Ai = new Button("SINGLE PLAYER");
            Ai.setGraphic(onePlayerIcon);
            Ai.setLayoutY(315);
            Ai.setLayoutX(22);
            Ai.setMinSize(200,57);
            Ai.setOnAction(e -> {
                //gameManager.setPlayerVersusComputer();
                primaryStage.setScene(scene3);
            });
        Button menu = new Button();
            menu.setGraphic(gearIcon);
            menu.setLayoutY(425);
            menu.setLayoutX(22);
            menu.setMinSize(200,57);
            menu.setOnAction(e -> {
                primaryStage.setScene(scene2);
            });
        Button exit = new Button("X");
            exit.setId("X");
            exit.setMinSize(25, 25);
            //exit.setLayoutY(15);
            exit.setLayoutX(520);
            exit.setOnAction(new EventHandler<ActionEvent>(){
                @Override
                public void handle(ActionEvent actionEvent){
                Platform.exit();
            }
            });
        Button minimize = new Button("-");
            minimize.setId("minimize");
            minimize.setMinSize(25,30);

            minimize.setLayoutX(490);
            minimize.setOnAction(e -> {
                ((Stage)((Button)e.getSource()).getScene().getWindow()).setIconified(true);
            });



//creating a box for scene3 (game scene) to include the 3 above buttons
        HBox choices = new HBox();
        choices.getChildren().addAll(again, openMenu, exit);

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
        Pane first = new Pane();
        first.setBackground(new Background(appBackground));
        Pane firstTitle = new Pane();
        firstTitle.setMinSize(550, 500);
        firstTitle.setLayoutY(30);
        firstTitle.setId("firstTitle");

        Pane topBar = new Pane();
        topBar.setId("topBar");
        topBar.setMinSize(600, 30);

        topBar.setOnMousePressed(pressEvent -> {
            topBar.setOnMouseDragged(dragEvent -> {
                primaryStage.setX(dragEvent.getScreenX() - pressEvent.getSceneX());
                primaryStage.setY(dragEvent.getScreenY() - pressEvent.getSceneY());
            });
        });

        topBar.getChildren().addAll( minimize, exit);

        Label nine = new Label(" N I N E");
        nine.setId("smallTitle");
        Label mens = new Label("M E N S");
        mens.setId("smallTitle");
        Label morris = new Label("MORRIS");
        nine.setMaxSize(350,500);
        nine.setLayoutY(35);
        nine.setLayoutX(36);
        mens.setLayoutY(85);
        mens.setLayoutX(40);
        morris.setLayoutY(15);
        morris.setLayoutX(185);

        firstTitle.getChildren().addAll(nine, mens, morris, Ai, twoPlayer, menu);
        first.getChildren().addAll(topBar, firstTitle, twoMarbles, firstButton, secondButton, thirdButton);


        scene1 = new Scene(first, 600, 600);
        scene1.setFill(Color.TRANSPARENT);
        scene1.getStylesheets().add(Morris.class.getResource("StageDesign.css").toExternalForm());

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






