package com.qlsv.qlsv;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;


public class IndexApp extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(IndexApp.class.getResource("dang-nhap.fxml"));
//        Scene scene = new Scene(fxmlLoader.load());
        viewState.showLogIn();
        Scene scene = new Scene(viewState.currentViewProperty().get());
        scene.rootProperty().bind(viewState.currentViewProperty());
        stage.getIcons().add(new Image(Objects.requireNonNull(IndexApp.class.getResourceAsStream("images/hcmus-logo.png"))));
        stage.setMaximized(true);
        stage.setMinWidth(1280);
        stage.setMinHeight(720);
        stage.setTitle("Quản lý sinh viên!");
        stage.setScene(scene);
        stage.show();
    }

    public static int SessionID;
    public static int ChucVu;
    public static ViewState viewState = new ViewState();
    public static void main(String[] args) {
        launch();
    }
}