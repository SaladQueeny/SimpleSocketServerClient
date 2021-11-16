package ru.kolpakovkuleshov.application;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.application.Platform;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.jzy3d.chart.AWTChart;
import ru.kolpakovkuleshov.App;
import ru.kolpakovkuleshov.helpfulClasses.Logs;
import ru.kolpakovkuleshov.charts.Charts;
import ru.kolpakovkuleshov.charts.JavaFXChartFactory;
import ru.kolpakovkuleshov.helpfulClasses.Generalities;
import ru.kolpakovkuleshov.helpfulClasses.ProcessData;

public class PrimaryController {

    @FXML
    private Button update_button;

    @FXML
    private Slider slider_chart;

    @FXML
    private volatile Pane chart_pain;

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

    @FXML
    private Button change_path_button;

    public String response;
    public boolean checkcheck = true;

    private void sendRequestToServer() {
        Task task = new Task<Void>() {
            @Override
            public Void call() {
                Generalities generalities = new Generalities("127.0.0.1", 8000);
                String request = ProcessData.createRequest();
                generalities.writeLine(request);
                response = null;

                checkcheck = true;
                response = generalities.readLine();
                while (response != null) {
                    System.out.println("get callback");
                    ProcessData.getDataFromJson(response);
                    response = generalities.readLine();
                }
                return null;
            }
        };
        new Thread(task).start();

    }

    private boolean isGoodData() {
        Pattern p = Pattern.compile("[-]?[0-9]+(.[0-9]+)?");
        Matcher match = p.matcher(xstart.getText().trim());
        int check = 0;
        if (match.matches()) {
            check++;
            match = p.matcher(xend.getText().trim());
            if (match.matches()) {
                check++;
                match = p.matcher(xchange.getText().trim());
                if (match.matches()) {
                    check++;
                    match = p.matcher(ystart.getText().trim());
                    if (match.matches()) {
                        check++;
                        match = p.matcher(yend.getText().trim());
                        if (match.matches()) {
                            check++;
                            match = p.matcher(ychange.getText().trim());
                            if (match.matches()) {
                                check++;
                                match = p.matcher(tstart.getText().trim());
                                if (match.matches()) {
                                    check++;
                                    match = p.matcher(tend.getText().trim());
                                    if (match.matches()) {
                                        check++;
                                        match = p.matcher(tchange.getText().trim());
                                        if (match.matches()) {
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
        Logs.writeLog(this.getClass(), new Throwable().getStackTrace()[0].getMethodName(),
                "Check correct data", Level.SEVERE, true);
        if (check == 9) {
            return true;
        } else {
            return false;
        }

    }

    private void getStartData() {
        ProcessData.setStartData(Double.parseDouble(xstart.getText().trim()), Double.parseDouble(xend.getText().trim()), Double.parseDouble(xchange.getText().trim()),
                Double.parseDouble(ystart.getText().trim()), Double.parseDouble(yend.getText().trim()), Double.parseDouble(ychange.getText().trim()),
                Double.parseDouble(tstart.getText().trim()), Double.parseDouble(tend.getText().trim()), Double.parseDouble(tchange.getText().trim()));
        Logs.writeLog(this.getClass(), new Throwable().getStackTrace()[0].getMethodName(),
                "Got start data", Level.SEVERE, true);
    }

    public void initSlider() {
        slider_chart.setMin(ProcessData.t.get(0));
        slider_chart.setMax(ProcessData.t.get(ProcessData.t.size() - 1));
        slider_chart.setValue(ProcessData.t.get(0));
        slider_chart.setBlockIncrement(ProcessData.t_change);
        slider_chart.setSnapToTicks(true);
        slider_chart.setMajorTickUnit(ProcessData.t_change);
        slider_chart.setMinorTickCount(0);
        Logs.writeLog(this.getClass(), new Throwable().getStackTrace()[0].getMethodName(),
                "init slider with current t", Level.SEVERE, true);
    }

    public boolean check = false;
    public volatile List<ImageView> imageViewList = new ArrayList<>();
    public double previous_T;
    public boolean suicide = true;

    public void createCharts() {
        Charts chart = new Charts();
        JavaFXChartFactory factory = new JavaFXChartFactory();
        List<AWTChart> chartsList = chart.getAWTCharts(factory, ProcessData.x, ProcessData.y, ProcessData.t, ProcessData.z);
        if (suicide) {
            chart_pain.getChildren().clear();
            imageViewList.clear();
            suicide = false;
        }

        for (int i = 0; i < chartsList.size(); i++) {
            ImageView imageView = factory.bindImageView(chartsList.get(i));
            imageViewList.add(imageView);

        }
    }

    @FXML
    void initialize() {
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
                    Alert a1 = new Alert(Alert.AlertType.ERROR);
                    a1.setTitle("ERROR");
                    a1.setContentText("We don't have this T!");
                    a1.setHeaderText("Incorrect data!");
                    a1.show();

                }

                if (previous_T != currentT) {

                } else {
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
                sendRequestToServer();
            } else {
                //notification
                Alert a1 = new Alert(Alert.AlertType.ERROR);
                a1.setTitle("ERROR");
                a1.setContentText("The data entered is incorrect or there is not enough data!");
                a1.setHeaderText("Incorrect data!");
                a1.show();
            }
        });

        update_button.setOnAction(event -> {
            if (ProcessData.isCreated.get(ProcessData.isCreated.size() - 1) != true) {
                initSlider();
                createCharts();
                chart_pain.getChildren().clear();
                chart_pain.getChildren().add(imageViewList.get(0));
            }
        });


        change_path_button.setOnAction(actionEvent -> {
            openNewScene(change_path_button, "first_scene");
        });

        exit_button.setOnAction(event -> {
            try {
                Files.walk(Paths.get(""), 1)
                        .forEach(file -> {
                            if (file.toFile().isFile() && file.toFile().getPath().endsWith(".class")) {
                                System.out.println(file.getFileName());
                                file.toFile().delete();
                                System.out.println("deleted");
                            }
                        });
            } catch (IOException e) {
                e.printStackTrace();
            }

            System.exit(0);
        });

    }

    protected void openNewScene(Button button, String scene) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(scene + ".fxml"));
            Parent root = null;
            root = fxmlLoader.load();
            Stage window = (Stage) button.getScene().getWindow();
            window.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
