package com.qlsv.qlsv;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvValidationException;
import com.qlsv.qlsv.DAO.NgayHocDAO;
import com.qlsv.qlsv.DAO.NguoiDungDAO;
import com.qlsv.qlsv.entities.*;

import javafx.animation.PauseTransition;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.FileChooser;
import javafx.util.Duration;
import org.apache.commons.codec.digest.DigestUtils;

import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

public class SinhVienController implements Initializable {
    @FXML
    private BorderPane sinhvienView;
    @FXML
    private ContextMenu contextMenu;
    @FXML
    private FlowPane noticePane;
    @FXML
    private Label noticeContent;
    @FXML
    private TableView<NguoiDung> danhSachSinhVien;
    @FXML
    private TableColumn<NguoiDung, Integer> danhSachMaSV;
    @FXML
    private TableColumn<NguoiDung, String> danhSachTenSinhVien;
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
    private TabPane tabList;
    @FXML
    private Tab editTab;
    @FXML
    private TextField editID;
    @FXML
    private TextField editName;
    @FXML
    private Button saveButton;
    @FXML
    private Tab addTab;
    @FXML
    private TextField addID;
    @FXML
    private TextField addName;
    @FXML
    private Button addButton;
    @FXML
    private Label nameFile;
    @FXML
    private Button chooseFile;
    @FXML
    private Button addFileButton;

    private String filePath;

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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<NguoiDung> dsSinhVien = FXCollections.observableList(NguoiDungDAO.danhSachSinhVien());
        danhSachMaSV.setCellValueFactory(new PropertyValueFactory<>("id"));
        danhSachTenSinhVien.setCellValueFactory(new PropertyValueFactory<>("hoTen"));
        danhSachSinhVien.setItems(dsSinhVien);

        addID.setText(String.valueOf(dsSinhVien.get(dsSinhVien.size()-1).getId()+1));
    }

    private void callNotice(String styleClass, String noticeText){
        noticePane.getStyleClass().clear();
        noticePane.getStyleClass().addAll("alert","alert-"+styleClass);
        noticeContent.setText(noticeText);
        noticePane.setVisible(true);
        PauseTransition pause = new PauseTransition(Duration.seconds(3));
        pause.setOnFinished(e -> {
            noticePane.setVisible(false);
        });
        pause.play();
    }

    private void showError(String error){
        callNotice("danger", error);
    }

    private void showSuccess(String successText){
        callNotice("success", successText);
    }

    @FXML
    private void onRightClick(ContextMenuEvent contextMenuEvent) {
        contextMenu.show(danhSachSinhVien.getSelectionModel().getTableView(),contextMenuEvent.getScreenX(),contextMenuEvent.getScreenY());
    }

    @FXML
    private void onPressDel(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.DELETE)
            onClickDelete();
    }

    @FXML
    private void onClickMenuAdd(ActionEvent actionEvent) {
        tabList.getSelectionModel().select(addTab);
    }

    @FXML
    private void onClickMenuEdit(ActionEvent actionEvent) {
        tabList.getSelectionModel().select(editTab);
    }

    @FXML
    private void onClickDelete() {
        NguoiDung sinhVien = danhSachSinhVien.getSelectionModel().getSelectedItem();
        ButtonType yesBtn = new ButtonType("Có", ButtonBar.ButtonData.OK_DONE);
        ButtonType noBtn = new ButtonType("Không", ButtonBar.ButtonData.CANCEL_CLOSE);
        Alert confirmDel = new Alert(Alert.AlertType.CONFIRMATION,"Bạn có muốn xóa sinh viên này không?",yesBtn,noBtn);
        confirmDel.setTitle("Xác nhận xóa");
        confirmDel.setHeaderText("Xác nhận xóa");
        Optional<ButtonType> result = confirmDel.showAndWait();
        if (result.orElse(yesBtn) == yesBtn) {
            int type;
            if (NguoiDungDAO.xoaSinhVien(sinhVien)){
                danhSachSinhVien.getItems().remove(sinhVien);
                showSuccess("Xóa sinh viên thành công");
            }
            else {
                if (sinhVien == null)
                    type = 0;
                else
                    type = 1;
                String[] thongBao = {"Không tìm thấy sinh viên này",
                                     "Vui lòng xóa lịch đăng ký học của sinh viên trước"};
                ButtonType xacNhan = new ButtonType("Xác nhận", ButtonBar.ButtonData.OK_DONE);
                Alert canNotDelete = new Alert(Alert.AlertType.ERROR,thongBao[type],xacNhan);
                canNotDelete.setTitle("Không thể xóa");
                canNotDelete.setHeaderText("Không thể xóa");
                canNotDelete.showAndWait();
            }
        }
    }

    @FXML
    private void onSelect(MouseEvent mouseEvent) {
        NguoiDung sinhVien = danhSachSinhVien.getSelectionModel().getSelectedItem();
        if (sinhVien != null) {
            editID.setText(String.valueOf(sinhVien.getId()));
            editName.setText(sinhVien.getHoTen());
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
        }
    }

    @FXML
    private void onClickEdit(ActionEvent actionEvent) {
        NguoiDung sinhVien = danhSachSinhVien.getSelectionModel().getSelectedItem();
        String [] thongBao = {"Không được bỏ trống họ tên"};
        if(editName.getText().equals("")){
            showError(thongBao[0]);
        } else {
            sinhVien.setHoTen(editName.getText());
            int index = danhSachSinhVien.getSelectionModel().getSelectedIndex();
            if (NguoiDungDAO.capNhatThongTinSinhVien(sinhVien)) {
                danhSachSinhVien.getItems().set(index, sinhVien);
                showSuccess("Sửa thông tin thành công");
            } else {
                showError("Không sửa được sinh viên này");
            }
        }
    }

    @FXML
    private void onClickAdd(ActionEvent actionEvent) {
        String [] thongBao = {"Không được bỏ trống họ tên"};
        if (addName.getText().equals(""))
            showError(thongBao[0]);
        else {
            themSinhVien(addID.getText(), addName.getText());
        }
    }

    private void themSinhVien(String mssv, String hoTen){
        NguoiDung sinhVien_new = new NguoiDung();
        String key = "ThisIsAKey";
        String password = DigestUtils.md5Hex(DigestUtils.md5Hex(mssv) + key);
        sinhVien_new.setId(Integer.valueOf(mssv));
        sinhVien_new.setHoTen(hoTen);
        sinhVien_new.setMatKhau(password);
        sinhVien_new.setChucVu(1);
        if (NguoiDungDAO.themSinhVien(sinhVien_new)){
            danhSachSinhVien.getItems().add(sinhVien_new);
            showSuccess("Thêm sinh viên mới thành công");
        } else {
            showError("Không thêm được sinh viên mới, vui lòng kiểm tra lại");
        }
    }


    @FXML
    private void onClickAddFile(ActionEvent actionEvent) throws IOException, CsvValidationException {
        CSVParser parser = new CSVParserBuilder().withSeparator(';').build();
        Reader reader = new FileReader(filePath, StandardCharsets.UTF_8);
        CSVReader csvReader = new CSVReaderBuilder(reader).withCSVParser(parser).build();
        String[] line1 = csvReader.peek();
        if (line1.length != 2) {
            showError("Lỗi file, vui lòng chọn file CSV đúng định dạng <mã sinh viên; họ tên>");
        } else {
            csvReader.forEach(line -> themSinhVien(line[0].replaceAll("\\D", ""), line[1]));
        }
    }

     @FXML
    private void onClickChooseFile(ActionEvent actionEvent) {
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Chọn danh sách sinh viên (.CSV)");
        FileChooser.ExtensionFilter csvFilter = new FileChooser.ExtensionFilter("CSV","*.csv");
        chooser.getExtensionFilters().add(csvFilter);
        File file = chooser.showOpenDialog(null);
        if (file != null){
            nameFile.setText(file.getName());
            filePath = file.getPath();
        }
    }
}
