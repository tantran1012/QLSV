package com.qlsv.qlsv;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;

import java.io.IOException;
import java.util.Objects;
import java.util.Optional;


public class IndexController{

    @FXML
    private Button viewDsMon;
    @FXML
    private BorderPane mainPane;
    @FXML
    public Label sessionID;
    @FXML
    private Button logOutButton;
    @FXML
    private Button xemThoiKhoaBieu;
    @FXML
    private Button xemDsSinhVien;
    @FXML
    private Button xemLichHoc;
    @FXML
    private Button controlNavButton;

    private boolean isFull = true;
    @FXML
    private Text headerText;
    @FXML
    private AnchorPane navigationPane;
    @FXML
    private ImageView iconArrow;

    @FXML
    private void onClickLogOut(ActionEvent actionEvent) {
        ButtonType yesBtn = new ButtonType("Có", ButtonBar.ButtonData.OK_DONE);
        ButtonType noBtn = new ButtonType("Không", ButtonBar.ButtonData.CANCEL_CLOSE);
        Alert logOut = new Alert(Alert.AlertType.WARNING,"Xác nhận đăng xuất",yesBtn,noBtn);
        logOut.setTitle("Đăng xuất");
        logOut.setHeaderText("Xác nhận đăng xuất");
        logOut.setContentText("Bạn thật sự muốn đăng xuất không?");
        Optional<ButtonType> result = logOut.showAndWait();
        if (result.orElse(yesBtn) == yesBtn){
            logOut();
        }
    }

    private void logOut() {
        IndexApp.ChucVu = -1;
        IndexApp.SessionID = -1;
        IndexApp.viewState.showLogIn();
    }

    private void setMainPane(FXMLLoader fxmlLoader) {
        try {
            mainPane.setCenter(fxmlLoader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void onClickViewListCourses() {
        FXMLLoader fxmlLoader = new FXMLLoader(IndexApp.class.getResource("mon-hoc-view.fxml"));
        setMainPane(fxmlLoader);
    }

    @FXML
    private void onClickViewTimeTable(ActionEvent actionEvent) {
        FXMLLoader fxmlLoader = new FXMLLoader(IndexApp.class.getResource("thoi-khoa-bieu-view.fxml"));
        setMainPane(fxmlLoader);
    }

    @FXML
    private void onClickViewListStudent(ActionEvent actionEvent) {
        FXMLLoader fxmlLoader = new FXMLLoader(IndexApp.class.getResource("sinh-vien-view.fxml"));
        setMainPane(fxmlLoader);
    }

    @FXML
    private void onClickViewCheckIn(ActionEvent actionEvent) {
        FXMLLoader fxmlLoader = new FXMLLoader(IndexApp.class.getResource("lich-hoc-view.fxml"));
        setMainPane(fxmlLoader);
    }

    private void thuNhoButton(Button button){
        button.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        button.setAlignment(Pos.CENTER);
    }

    private void phongtoButton(Button button){
        button.setContentDisplay(ContentDisplay.LEFT);
        button.setAlignment(Pos.BASELINE_LEFT);
    }

    @FXML
    private void onClickMinimized(ActionEvent actionEvent) {
        if (isFull) {
            isFull = false;
            headerText.setVisible(false);
            thuNhoButton(viewDsMon);
            thuNhoButton(xemThoiKhoaBieu);
            thuNhoButton(xemDsSinhVien);
            thuNhoButton(xemLichHoc);
            thuNhoButton(controlNavButton);
            thuNhoButton(logOutButton);
            navigationPane.setPrefWidth(120);
            iconArrow.setImage(new Image(Objects.requireNonNull(IndexApp.class.getResourceAsStream("images/right-arrow.png"))));
        } else {
            isFull = true;
            headerText.setVisible(true);
            phongtoButton(viewDsMon);
            phongtoButton(xemThoiKhoaBieu);
            phongtoButton(xemDsSinhVien);
            phongtoButton(xemLichHoc);
            phongtoButton(controlNavButton);
            phongtoButton(logOutButton);
            navigationPane.setPrefWidth(360);
            iconArrow.setImage(new Image(Objects.requireNonNull(IndexApp.class.getResourceAsStream("images/left-arrow.png"))));
        }
    }
}