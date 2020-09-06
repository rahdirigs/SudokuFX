package org.example;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 * JavaFX App
 */
public class App extends Application {

    TextField[][] textFields = new TextField[9][9];

    @Override
    public void start(Stage stage) {
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(0);
        grid.setVgap(0);
        grid.setPadding(new Insets(25, 25, 25, 25));

        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                TextField t = new TextField();
                t.setPrefSize(40, 40);
                t.setAlignment(Pos.CENTER);
                textFields[c][r] = t;
                textFields[c][r].setStyle("-fx-border-color:rgb(0, 0, 0)");
                grid.add(t, c, r);
            }
        }

        Button clear = new Button("CLEAR");
        HBox h = new HBox(10);
        h.getChildren().add(clear);
        h.setAlignment(Pos.BOTTOM_LEFT);

        grid.add(h, 0, 10, 3, 1);
        clear.setOnAction(event -> {
            for (int r = 0; r < 9; r++) {
                for (int c = 0; c < 9; c++) {
                    textFields[c][r].setText("");
                    textFields[c][r].setStyle("-fx-border-color:rgb(0, 0, 0)");
                }
            }
        });

        Button solve = new Button("SOLVE");
        HBox s = new HBox(10);
        s.getChildren().add(solve);
        s.setAlignment(Pos.BOTTOM_RIGHT);

        grid.add(s, 6, 10, 3, 1);
        solve.setOnAction(event -> {
            Solver solver = new Solver();
            Board in = new Board();
            for (int r = 0; r < 9; r++) {
                for (int c = 0; c < 9; c++) {
                    Integer val;
                    try {
                        val = Integer.valueOf(textFields[c][r].getText().trim());
                        in.set(c, r, val);
                    } catch(NumberFormatException e) {
                    }
                }
            }
            Board sol = solver.solveBoard(in);
            for (int r = 0; r < 9; r++) {
                for (int c = 0; c < 9; c++) {
                    if (textFields[c][r].getText().trim().equals("")) {
                        if (sol == null) {
                            textFields[c][r].setStyle("-fx-background-color:rgb(255,200,200)");
                        } else {
                            textFields[c][r].setStyle("-fx-border-color:rgb(0,0,0)");
                            textFields[c][r].setText(""+sol.get(c, r));
                        }
                    }
                }
            }
        });

        var scene = new Scene(grid, 640, 640);
        stage.setTitle("9x9 Sudoku Solver");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}