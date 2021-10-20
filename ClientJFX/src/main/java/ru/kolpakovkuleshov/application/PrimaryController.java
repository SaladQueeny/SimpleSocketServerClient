package ru.kolpakovkuleshov.application;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import org.jzy3d.chart.AWTChart;
import ru.kolpakovkuleshov.helpfulClasses.Logs;
import ru.kolpakovkuleshov.charts.Charts;
import ru.kolpakovkuleshov.charts.JavaFXChartFactory;
import ru.kolpakovkuleshov.helpfulClasses.Generalities;
import ru.kolpakovkuleshov.helpfulClasses.ProcessData;

public class PrimaryController {

    @FXML
    private Slider slider_chart;

    @FXML
    private Pane chart_pain;

    @FXML
    private Button exit_button;

    @FXML
    private Button start_button;

    @FXML
    private TextField tchange;

    @FXML
    private TextField tend;

    @FXML
    private TextField tstart;

    @FXML
    private TextField xchange;

    @FXML
    private TextField xend;

    @FXML
    private TextField xstart;

    @FXML
    private TextField ychange;

    @FXML
    private TextField yend;

    @FXML
    private TextField ystart;

    private void sendRequestToServer() throws IOException {
        try (Generalities generalities = new Generalities("127.0.0.1", 8080)) {
            generalities.writeLine(ProcessData.createRequest());
            String response = generalities.readLine();
            ProcessData.getDataFromJson(response);
        } catch (IOException e) {
            Alert a1 = new Alert(Alert.AlertType.ERROR);
            a1.setTitle("ERROR");
            a1.setContentText("Can't join to server!");
            a1.setHeaderText("Server error!");
            a1.show();
            e.printStackTrace();
        }
    }

    private boolean isGoodData() {
        Pattern p = Pattern.compile("[-]?[0-9]+(.[0-9]+)?");
        Matcher match = p.matcher(xstart.getText().trim());
        int check =0;
        if(match.matches()){
            check++;
            match = p.matcher(xend.getText().trim());
            if(match.matches()){
                check++;
                match = p.matcher(xchange.getText().trim());
                if(match.matches()){
                    check++;
                    match = p.matcher(ystart.getText().trim());
                    if(match.matches()){
                        check++;
                        match = p.matcher(yend.getText().trim());
                        if(match.matches()){
                            check++;
                            match = p.matcher(ychange.getText().trim());
                            if(match.matches()){
                                check++;
                                match = p.matcher(tstart.getText().trim());
                                if(match.matches()){
                                    check++;
                                    match = p.matcher(tend.getText().trim());
                                    if(match.matches()){
                                        check++;
                                        match = p.matcher(tchange.getText().trim());
                                        if(match.matches()){
                                            check++;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        if(check==9){
            return true;
        }else{
            return false;
        }
    }

    private void getStartData() {
        ProcessData.getStartData(Double.parseDouble(xstart.getText().trim()), Double.parseDouble(xend.getText().trim()), Double.parseDouble(xchange.getText().trim()),
                Double.parseDouble(ystart.getText().trim()), Double.parseDouble(yend.getText().trim()), Double.parseDouble(ychange.getText().trim()),
                Double.parseDouble(tstart.getText().trim()), Double.parseDouble(tend.getText().trim()), Double.parseDouble(tchange.getText().trim()));
    }

    public void initSlider() {
        slider_chart.setMin(ProcessData.t_start);
        slider_chart.setMax(ProcessData.t_end);
        slider_chart.setValue(ProcessData.t_start);
        slider_chart.setBlockIncrement(ProcessData.t_change);
        slider_chart.setSnapToTicks(true);
        slider_chart.setMajorTickUnit(ProcessData.t_change);
        slider_chart.setMinorTickCount(0);
    }

    public boolean check = false;
    List<ImageView> imageViewList = new ArrayList<>();
    public double previous_T;
    @FXML
    void initialize() {
        Logs.writeLog(this.getClass(), "initialize", Level.INFO);
        slider_chart.setOnMouseClicked(mouseEvent -> {
            check = true;
        });

        slider_chart.setOnDragDetected(mouseEvent -> {
            check = true;
        });

        slider_chart.setOnMouseReleased(mouseEvent -> {
            if (check) {
                Double currentT = slider_chart.getValue();
                if (ProcessData.t.contains(currentT)) {
                    chart_pain.getChildren().clear();
                    chart_pain.getChildren().add(imageViewList.get(ProcessData.t.indexOf(currentT)));
                } else {
                    //notification
                    Alert a1 = new Alert(Alert.AlertType.ERROR);
                    a1.setTitle("ERROR");
                    a1.setContentText("We don't have this T!");
                    a1.setHeaderText("Incorrect data!");
                    a1.show();
                }
                if (previous_T != currentT){

                }else{
                    Alert a1 = new Alert(Alert.AlertType.WARNING);
                    a1.setTitle("WARNING");
                    a1.setContentText("We have this T!");
                    a1.setHeaderText("Duplicate T!");
                    a1.show();
                }
                check = false;
                previous_T = currentT;
            }

        });
        start_button.setOnAction(event -> {

            if (isGoodData()) {
                getStartData();
                try {
                    sendRequestToServer();
                    initSlider();
                    Charts chart = new Charts();
                    JavaFXChartFactory factory = new JavaFXChartFactory();
                    List<AWTChart> chartsList = chart.getAWTCharts(factory, ProcessData.x, ProcessData.y, ProcessData.t, ProcessData.z);
                    chart_pain.getChildren().clear();
                    imageViewList.clear();
                    for (int i = 0; i < chartsList.size(); i++) {
                        ImageView imageView = factory.bindImageView(chartsList.get(i));
                        imageViewList.add(imageView);
                    }
                    chart_pain.getChildren().add(imageViewList.get(0));
                } catch (IOException e) {
                    //notification
                    Alert a1 = new Alert(Alert.AlertType.ERROR);
                    a1.setTitle("ERROR");
                    a1.setContentText("Can't join to server!");
                    a1.setHeaderText("Server error!");
                    a1.show();
                    System.out.println("Something wrong with server");
                    e.printStackTrace();
                }
                System.out.println("We got your data");
            } else {
                //notification
                Alert a1 = new Alert(Alert.AlertType.ERROR);
                a1.setTitle("ERROR");
                a1.setContentText("The data entered is incorrect or there is not enough data!");
                a1.setHeaderText("Incorrect data!");
                a1.show();
            }

        });

        exit_button.setOnAction(event -> {
            System.exit(0);
        });


    }

}
