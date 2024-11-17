import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Calculator extends Application {
    private TextField display;

    @Override
    public void start(Stage primaryStage) {
        display = new TextField();
        display.setEditable(false);
        display.setPrefHeight(50);
        display.setStyle("-fx-font-size: 18;");
        display.setAlignment(Pos.CENTER_RIGHT);

        GridPane buttonGrid = new GridPane();
        buttonGrid.setHgap(5);
        buttonGrid.setVgap(5);

        String[] buttonLabels = {
                "7", "8", "9", "/",
                "4", "5", "6", "*",
                "1", "2", "3", "-",
                "0", ".", "=", "+"
        };

        int row = 0;
        int col = 0;
        for (String label : buttonLabels) {
            Button button = new Button(label);
            button.setPrefSize(50, 50);
            button.setStyle("-fx-font-size: 18;");

            button.setOnAction(e -> handleButtonClick(label));

            buttonGrid.add(button, col, row);
            col++;
            if (col > 3) {
                col = 0;
                row++;
            }
        }

        Button clearButton = new Button("C");
        clearButton.setPrefSize(215, 50);
        clearButton.setStyle("-fx-font-size: 18;");
        clearButton.setOnAction(e -> display.setText(""));

        VBox root = new VBox(10);
        root.setPadding(new Insets(10));
        root.getChildren().addAll(display, buttonGrid, clearButton);

        Scene scene = new Scene(root);
        primaryStage.setTitle("Calculator");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void handleButtonClick(String value) {
        if (value.equals("=")) {
            try {
                String result = evaluateExpression(display.getText());
                display.setText(result);
            } catch (Exception e) {
                display.setText("error");
            }
        } else {
            display.setText(display.getText() + value);
        }
    }

    private String evaluateExpression(String expression) {
        try {
            return String.valueOf(eval(expression));
        } catch (Exception e) {
            return "error";
        }
    }

    private double eval(String str) {
        return new Object() {
            int pos = -1, ch;

            void nextChar() {
                ch = (++pos < str.length()) ? str.charAt(pos) : -1;
            }

            boolean eat(int charToEat) {
                while (ch == ' ') nextChar();
                if (ch == charToEat) {
                    nextChar();
                    return true;
                }
                return false;
            }

            double parse() {
                nextChar();
                double x = parseExpression();
                if (pos < str.length()) throw new RuntimeException("out of expectation: " + (char)ch);
                return x;
            }

            double parseExpression() {
                double x = parseTerm();
                for (;;) {
                    if      (eat('+')) x += parseTerm();
                    else if (eat('-')) x -= parseTerm();
                    else return x;
                }
            }

            double parseTerm() {
                double x = parseFactor();
                for (;;) {
                    if      (eat('*')) x *= parseFactor();
                    else if (eat('/')) x /= parseFactor();
                    else return x;
                }
            }

            double parseFactor() {
                if (eat('+')) return parseFactor();
                if (eat('-')) return -parseFactor();

                double x;
                int startPos = this.pos;
                if (eat('(')) {
                    x = parseExpression();
                    eat(')');
                } else if ((ch >= '0' && ch <= '9') || ch == '.') {
                    while ((ch >= '0' && ch <= '9') || ch == '.') nextChar();
                    x = Double.parseDouble(str.substring(startPos, this.pos));
                } else {
                    throw new RuntimeException("out of expectation: " + (char)ch);
                }
                return x;
            }
        }.parse();
    }

    public static void main(String[] args) {
        launch(args);
    }
}