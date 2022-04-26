package com.qlsv.qlsv;

import com.qlsv.qlsv.DAO.NgayHocDAO;
import com.qlsv.qlsv.DAO.NguoiDungDAO;
import com.qlsv.qlsv.entities.NgayHoc;
import com.qlsv.qlsv.entities.NguoiDung;
import com.qlsv.qlsv.entities.ThoiKhoaBieu;
import com.qlsv.qlsv.entities.TkbSv;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.stream.IntStream;

public class Index2Controller implements Initializable {
    @FXML
    private BorderPane mainPane;
    @FXML
    private AnchorPane navigationPane;
    @FXML
    private Text headerText;
    @FXML
    private Button homeButton;
    @FXML
    private Button profileButton;
    @FXML
    private Button logOutButton;
    @FXML
    private Button controlNavButton;
    @FXML
    private ImageView iconArrow;
    @FXML
    private TableView<TrangThaiMonHoc> danhSachMonDangHoc;
    @FXML
    private TableColumn<TrangThaiMonHoc, String> maMon;
    @FXML
    private TableColumn<TrangThaiMonHoc, String> tenMon;
    @FXML
    private TableColumn<TrangThaiMonHoc, Boolean> tuan1;
    @FXML
    private TableColumn<TrangThaiMonHoc, Boolean> tuan2;
    @FXML
    private TableColumn<TrangThaiMonHoc, Boolean> tuan3;
    @FXML
    private TableColumn<TrangThaiMonHoc, Boolean> tuan4;
    @FXML
    private TableColumn<TrangThaiMonHoc, Boolean> tuan5;
    @FXML
    private TableColumn<TrangThaiMonHoc, Boolean> tuan6;
    @FXML
    private TableColumn<TrangThaiMonHoc, Boolean> tuan7;
    @FXML
    private TableColumn<TrangThaiMonHoc, Boolean> tuan8;
    @FXML
    private TableColumn<TrangThaiMonHoc, Boolean> tuan9;
    @FXML
    private TableColumn<TrangThaiMonHoc, Boolean> tuan10;
    @FXML
    private TableColumn<TrangThaiMonHoc, Boolean> tuan11;
    @FXML
    private TableColumn<TrangThaiMonHoc, Boolean> tuan12;
    @FXML
    private TableColumn<TrangThaiMonHoc, Boolean> tuan13;
    @FXML
    private TableColumn<TrangThaiMonHoc, Boolean> tuan14;
    @FXML
    private TableColumn<TrangThaiMonHoc, Boolean> tuan15;
    @FXML
    private Label thongtinDiemDanh;
    @FXML
    private Button diemDanhButton;
    @FXML
    private Label hoTenSinhVien;

    private boolean isFull = true;

    private int tuan;

    private NgayHoc ngayHoc;

    private TkbSv tKBSv;

    private final NguoiDung sinhVien = NguoiDungDAO.layThongTinSinhVien(IndexApp.SessionID);
    @FXML
    private GridPane mainHome;
    @FXML
    private BorderPane mainView;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        hoTenSinhVien.setText(sinhVien.getHoTen());
        ObservableList<TrangThaiMonHoc> trangThaiMonHoc = FXCollections.observableList(NgayHocDAO.trangThaiMonHoc(sinhVien));
        maMon.setCellValueFactory(new PropertyValueFactory<>("maMon"));
        tenMon.setCellValueFactory(new PropertyValueFactory<>("tenMon"));
        IntStream.range(1, 16).forEachOrdered(i -> {
            getTuan(i).setCellValueFactory(trangThaiMonHocBooleanCellDataFeatures -> {
                TrangThaiMonHoc ttmh = trangThaiMonHocBooleanCellDataFeatures.getValue();
                SimpleBooleanProperty booleanProp = new SimpleBooleanProperty(ttmh.getTuan(i));
                booleanProp.addListener((observableValue, aBoolean, t1) -> ttmh.setTuan(i, t1));
                return booleanProp;
            });
            getTuan(i).setCellFactory(cell -> new CheckBoxTableCell<>());
        });
        danhSachMonDangHoc.setItems(trangThaiMonHoc);

        List<TkbSv> monDangKy = sinhVien.getTkbSvs().stream().toList();
        LocalDate today = LocalDate.now();
        LocalTime timeNow = LocalTime.now();
        String [] thongTin = {"bạn chưa điểm danh đấy, mau điểm danh đi",
                "bạn đã điểm danh có mặt, hãy học chăm chỉ nhé",
                "Hôm nay không có học môn nào cả!",
                "Hôm nay có lịch học môn [",
                "Đã qua giờ học môn ["
        };
        for (TkbSv tkbSv : monDangKy) {
            ThoiKhoaBieu thoiKhoaBieu = tkbSv.getSTTMon();
            long days = ChronoUnit.DAYS.between(thoiKhoaBieu.getNgayBatDau(), today);
            //Nếu hiện tại nằm giữa ngày học
            if (days % 7 == 0 && today.isAfter(thoiKhoaBieu.getNgayBatDau()) && today.isBefore(thoiKhoaBieu.getNgayKetThuc())) {
                tuan = (int) ((days / 7) + 1);
                //Nếu hiện tại nằm giữa giờ học
                if (timeNow.isAfter(thoiKhoaBieu.getGioBatDau()) && timeNow.isBefore(thoiKhoaBieu.getGioKetThuc())) {
                    if (tkbSv.getNgayHoc(tuan).getDiemDanh()) {
                        thongtinDiemDanh.setText("Đã đến giờ học môn [" + thoiKhoaBieu.getMaMon().getTenMon() + "], " + thongTin[1] +
                                System.lineSeparator() + "Đây là tuần học thứ [" + tuan + "]");
                        diemDanhButton.setDisable(true);
                        diemDanhButton.setText("Đã điểm danh");
                    } else {
                        thongtinDiemDanh.setText("Đã đến giờ học môn [" + thoiKhoaBieu.getMaMon().getTenMon() + "], " + thongTin[0] +
                                System.lineSeparator() + "Đây là tuần học thứ [" + tuan + "]");
                        diemDanhButton.setDisable(false);
                        diemDanhButton.setText("Điểm danh");
                        ngayHoc = NgayHocDAO.layThongTinNgayHoc(tuan,tkbSv);
                        tKBSv = tkbSv;
                    }
                } else if (timeNow.isBefore(thoiKhoaBieu.getGioBatDau())) {
                    thongtinDiemDanh.setText(thongTin[3] + thoiKhoaBieu.getMaMon().getTenMon() + "], Chuẩn bị học đi nào" +
                            System.lineSeparator() + "Đây là tuần học thứ [" + tuan + "]");
                } else if (timeNow.isAfter(thoiKhoaBieu.getGioKetThuc())) {
                    thongtinDiemDanh.setText(thongTin[4] + thoiKhoaBieu.getMaMon().getTenMon() + "]" +
                            System.lineSeparator() + "Đây là tuần học thứ [" + tuan + "]");
                    continue;
                }
                break;
            }
            else {
                if (thongtinDiemDanh.getText().equals(""))
                    thongtinDiemDanh.setText(thongTin[2]);
            }
        }
    }
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

    private TableColumn<TrangThaiMonHoc, Boolean> getTuan(int i) {
        return switch (i) {
            case 1 -> tuan1;
            case 2 -> tuan2;
            case 3 -> tuan3;
            case 4 -> tuan4;
            case 5 -> tuan5;
            case 6 -> tuan6;
            case 7 -> tuan7;
            case 8 -> tuan8;
            case 9 -> tuan9;
            case 10 -> tuan10;
            case 11 -> tuan11;
            case 12 -> tuan12;
            case 13 -> tuan13;
            case 14 -> tuan14;
            case 15 -> tuan15;
            default -> throw new IllegalStateException("Unexpected value: " + i);
        };
    }

    private void setMainPane(FXMLLoader fxmlLoader) {
        try {
            mainView.setCenter(fxmlLoader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }
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
            thuNhoButton(homeButton);
            thuNhoButton(profileButton);
            thuNhoButton(controlNavButton);
            thuNhoButton(logOutButton);
            navigationPane.setPrefWidth(120);
            iconArrow.setImage(new Image(Objects.requireNonNull(IndexApp.class.getResourceAsStream("images/right-arrow.png"))));
        } else {
            isFull = true;
            headerText.setVisible(true);
            phongtoButton(homeButton);
            phongtoButton(profileButton);
            phongtoButton(controlNavButton);
            phongtoButton(logOutButton);
            navigationPane.setPrefWidth(360);
            iconArrow.setImage(new Image(Objects.requireNonNull(IndexApp.class.getResourceAsStream("images/left-arrow.png"))));
        }
    }
    @FXML
    private void onClickDiemDanh(ActionEvent actionEvent) {
        ngayHoc.setDiemDanh(true);
        NgayHocDAO.capNhatThongTinNgayHoc(ngayHoc);
        diemDanhButton.setText("Đã điểm danh");
        diemDanhButton.setDisable(true);
        danhSachMonDangHoc.getItems().forEach(trangThaiMonHoc -> {
            if (trangThaiMonHoc.getTkbSv().equals(tKBSv))
                trangThaiMonHoc.setTuan(tuan, true);
        });
        danhSachMonDangHoc.refresh();
        thongtinDiemDanh.setText("Đã điểm danh môn [" + tKBSv.getSTTMon().getMaMon().getTenMon() + "]" +
                System.lineSeparator() + "Đây là tuần học thứ [" + tuan + "]");
    }

    @FXML
    private void onClickHome(ActionEvent actionEvent) {
        mainView.setCenter(mainHome);
    }

    @FXML
    private void onClickProfile(ActionEvent actionEvent) {
        FXMLLoader fxmlLoader = new FXMLLoader(IndexApp.class.getResource("thong-tin-ca-nhan-view.fxml"));
        setMainPane(fxmlLoader);
    }
}
