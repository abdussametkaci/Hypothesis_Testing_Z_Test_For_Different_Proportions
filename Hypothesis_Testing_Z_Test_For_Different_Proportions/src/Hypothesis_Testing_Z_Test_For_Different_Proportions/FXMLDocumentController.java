/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Hypothesis_Testing_Z_Test_For_Different_Proportions;

import java.awt.Color;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 *
 * @author Abdussamet KACI
 */
public class FXMLDocumentController implements Initializable {

    @FXML
    private Label label;

    @FXML
    private ComboBox<String> alternativeComboBox;

    @FXML
    private TextField proportionATextField;

    @FXML
    private TextField proportionBTextField;

    @FXML
    private TextField countATextField;

    @FXML
    private TextField countBTextField;

    @FXML
    private TextField significanceLevelTextField;

    @FXML
    private Button drawButton;

    @FXML
    private LineChart<?, ?> lineChart;

    @FXML
    private Label graphLabel;

    @FXML
    private Label resultLabel;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        zGraph(-4, 4, Color.ORANGE);

        lineChart.setCreateSymbols(false);
        lineChart.setAlternativeRowFillVisible(false);
        lineChart.setLegendVisible(false);

        alternativeComboBox.getItems().addAll(
                "!=",
                ">",
                "<"
        );

    }

    @FXML
    void drawButtonAction(ActionEvent event) {
        String alternativeText = alternativeComboBox.getValue();
        String proportionAText = proportionATextField.getText();
        String counAText = countATextField.getText();
        String proportionBText = proportionBTextField.getText();
        String countBText = countBTextField.getText();
        String significanceLeveltext = significanceLevelTextField.getText();

        if (!hasProblem(alternativeText, proportionAText, counAText, proportionBText, countBText, significanceLeveltext)) {
            double pA = Double.valueOf(proportionAText);
            int n = Integer.parseInt(counAText);
            double pB = Double.valueOf(proportionBText);
            int m = Integer.parseInt(countBText);
            double significanceLevel = Double.valueOf(significanceLeveltext);

            String graphText = "       Acceptence \n          Region" + "\n            " + (1 - significanceLevel);
            graphLabel.setText(graphText);

            clearChart();
            zGraph(-4, 4, Color.ORANGE);

            if (alternativeText.equals("!=")) { // two sided tail
                twoSided(pA, n, pB, m, significanceLevel);
            } else if (alternativeText.equals(">")) { // right tail
                rightTail(pA, n, pB, m, significanceLevel);
            } else if (alternativeText.equals("<")) { // left tail
                leftTail(pA, n, pB, m, significanceLevel);
            }

        }

    }

    // draw the graph
    public void zGraph(double start, double finish, Color color) {
        XYChart.Series series = new XYChart.Series<>();

        double integral; // fonction in integral
        double value;
        for (double i = start; i <= finish; i += 0.01) {
            integral = Math.exp(-(i * i) / 2);
            value = ((1 / Math.sqrt(2 * Math.PI)) * integral); // constant * integral

            series.getData().add(new XYChart.Data(i, value));

        }

        lineChart.getData().add(series);

        setColor(series, color);
    }

    // test statistic
    public double ZValue(double pA, int n, double pB, int m) {
        double Z = (pA - pB) / (Math.sqrt(var(pA, n, pB, m)));
        return formatDouble(Z, 2);
    }

    public double pooled(double pA, int n, double pB, int m) {
        double pooled = (n * pA + m * pB) / (n + m);
        return pooled;
    }

    // variance
    public double var(double pA, int n, double pB, int m) {
        double p = pooled(pA, n, pB, m);
        double var = p * (1 - p) * (1.0 / n + 1.0 / m);
        return var;
    }

    // critical value
    public double z(double significanceLevel) {
        double integral;
        double num;
        double precision = 0.0005;
        boolean founded = false;
        // this while loop was written for finding closet value
        // because somtimes exact value is not founded from table value
        while (precision <= 0.01) {
            for (double i = -4; i < 4; i += 0.01) {
                integral = integrate(-4, i);
                num = (1 / Math.sqrt(2 * Math.PI)) * integral;
                double format = formatDouble(num, 3);
                // z tablosunda en yakin degeri bulur
                if (Math.abs((1 - significanceLevel) - format) <= precision) { // experimental closest value
                    founded = true;
                    return formatDouble(i, 2);
                }
            }
            
            if(!founded){
                precision *= 2;
            }
        }

        return -1; // not founded
    }

    // function
    public static double f(double x) {
        return Math.exp(-x * x / 2);
    }

    // Simpson rule for integration
    public static double integrate(double a, double b) {
        int N = 10000;                    // precision parameter
        double h = (b - a) / (N - 1);     // step size

        // 1/3 terms
        double sum = 1.0 / 3.0 * (f(a) + f(b));

        // 4/3 terms
        for (int i = 1; i < N - 1; i += 2) {
            double x = a + h * i;
            sum += 4.0 / 3.0 * f(x);
        }

        // 2/3 terms
        for (int i = 2; i < N - 1; i += 2) {
            double x = a + h * i;
            sum += 2.0 / 3.0 * f(x);
        }

        return sum * h;
    }

    // fill the area between start and finish points on graph
    public void fillArea(double start, double finish, Color color) {
        XYChart.Series seriesArea = new XYChart.Series<>();
        double integral;
        double value;
        for (double i = start; i <= finish; i += 0.05) {
            integral = Math.exp(-(i * i) / 2);
            value = ((1 / Math.sqrt(2 * Math.PI)) * integral);

            seriesArea.getData().add(new XYChart.Data(i, value));
            seriesArea.getData().add(new XYChart.Data(i, 0));// fill the area
        }
        lineChart.getData().add(seriesArea);

        setColor(seriesArea, color);
    }

    // two sided test
    public void twoSided(double pA, int n, double pB, int m, double significanceLevel) {
        double testStatistic = ZValue(pA, n, pB, m);
        double down = -z(significanceLevel / 2);
        double up = z(significanceLevel / 2);

        if (Math.abs(testStatistic) >= z(significanceLevel / 2)) {
            fillArea(-4, down, Color.RED);
            fillArea(up, 4, Color.RED);
            resultLabel.setText("H0: reject");
            String message = "Test statistic Z = " + testStatistic + " is rejection region: (-Infinity, " + down + "] U [" + up + ", Infinity)";
            msgBox("The null hypotessis H0 can be rejected.\n" + message);
        } else {
            fillArea(down, up, Color.GREEN);
            resultLabel.setText("H0: accept");
            String message = "Test statistic Z = " + testStatistic + " is acception region: (" + down + ", " + up + ")";
            msgBox("The null hypotessis H0 can be accepted.\n" + message);
        }
    }

    // right tail test
    public void rightTail(double pA, int n, double pB, int m, double significanceLevel) {
        double testStatistic = ZValue(pA, n, pB, m);
        double criticalValue = z(significanceLevel);
        if (testStatistic >= criticalValue) {
            fillArea(criticalValue, 4, Color.RED);
            resultLabel.setText("H0: reject");
            String message = "Test statistic Z = " + testStatistic + " is rejection region: [" + criticalValue + ", Infinity)";
            msgBox("The null hypotessis H0 can be rejected.\n" + message);
        } else {
            fillArea(-4, criticalValue, Color.GREEN);
            resultLabel.setText("H0: accept");
            String message = "Test statistic Z = " + testStatistic + " is acceptance region: (-Infinity, " + criticalValue + ")";
            msgBox("The null hypotessis H0 can be accepted.\n" + message);
        }
    }

    // left tail test
    public void leftTail(double pA, int n, double pB, int m, double significanceLevel) {
        double testStatistic = ZValue(pA, n, pB, m);
        double criticalValue = -z(significanceLevel);
        if (testStatistic <= criticalValue) {
            fillArea(-4, criticalValue, Color.RED);
            resultLabel.setText("H0: reject");
            String message = "Test statistic Z = " + testStatistic + " is rejection region: (-Infinity, " + criticalValue + "]";
            msgBox("The null hypotessis H0 can be rejected.\n" + message);
        } else {
            fillArea(criticalValue, 4, Color.GREEN);
            resultLabel.setText("H0: accept");
            String message = "Test statistic Z = " + testStatistic + " is acceptance region: (" + criticalValue + ", Infinity)";
            msgBox("The null hypotessis H0 can be accepted.\n" + message);
        }
    }

    // if any value is not given, throw an error message
    public boolean hasProblem(String alternativeText, String proportionAText, String counAText,
            String proportionBText, String countBText, String significanceLeveltext) {

        if (alternativeText == null) {
            msgBox("Please choose alternative hypothesis situation!");
            return true;
        } else if (proportionAText.isEmpty()) {
            msgBox("Please write proportion A");
            return true;
        } else if (Double.valueOf(proportionAText) > 1.0 || Double.valueOf(proportionAText) < 0) {
            msgBox("Proportion A cannot be greather than 1 or less than 0");
            return true;
        } else if (counAText.isEmpty()) {
            msgBox("Please write count A");
            return true;
        } else if (Double.valueOf(counAText) <= 0) {
            msgBox("Count A cannot be 0 or negative");
            return true;
        } else if (proportionBText.isEmpty()) {
            msgBox("Please write proportion B");
            return true;
        } else if (Double.valueOf(proportionBText) > 1.0 || Double.valueOf(proportionBText) < 0) {
            msgBox("Proportion B cannot be greather than 1 or less than 0");
            return true;
        } else if (countBText.isEmpty()) {
            msgBox("Please write count B");
            return true;
        } else if (Double.valueOf(countBText) <= 0) {
            msgBox("Count B cannot be 0 or negative");
            return true;
        } else if (significanceLeveltext.isEmpty()) {
            msgBox("Please write significance level");
            return true;
        } else if (Double.valueOf(significanceLeveltext) < 0 || Double.valueOf(significanceLeveltext) > 1) {
            msgBox("Significance level cannot be less than 0 or greather than 1");
            return true;
        }

        return false;
    }

    // show the how many digits after from dot
    public double formatDouble(double number, int numberOfDigits) {
        return Math.ceil(number * Math.pow(10, numberOfDigits)) / Math.pow(10, numberOfDigits);
    }

    public void msgBox(String text) {
        Alert alert = new Alert(AlertType.INFORMATION, text, ButtonType.OK);
        alert.setHeaderText(null);
        alert.showAndWait();
    }

    public void clearChart() {
        if (!lineChart.getData().isEmpty()) {
            lineChart.getData().clear();
        }
    }

    // change the color of graph and area
    public void setColor(XYChart.Series series, Color color) {
        // set color
        Node line = series.getNode().lookup(".chart-series-line");

        String rgb = String.format("%d, %d, %d",
                (int) (color.getRed() * 255),
                (int) (color.getGreen() * 255),
                (int) (color.getBlue() * 255));

        line.setStyle("-fx-stroke: rgba(" + rgb + ", 1.0);");
    }

}
