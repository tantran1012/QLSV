package com.qlsv.qlsv;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvValidationException;
import com.qlsv.qlsv.DAO.NgayHocDAO;
import com.qlsv.qlsv.DAO.NguoiDungDAO;
import com.qlsv.qlsv.DAO.ThoiKhoaBieuDAO;
import com.qlsv.qlsv.DAO.TkbSvDAO;
import com.qlsv.qlsv.entities.NgayHoc;
import com.qlsv.qlsv.entities.NguoiDung;
import com.qlsv.qlsv.entities.ThoiKhoaBieu;
import com.qlsv.qlsv.entities.TkbSv;
import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.util.Duration;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.time.LocalTime;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.IntStream;

public class LichHocController implements Initializable {
    @FXML
    private BorderPane lichHocView;
    @FXML
    private ContextMenu contextMenu;
    @FXML
    private FlowPane noticePane;
    @FXML
    private Label noticeContent;
    @FXML
    private TableView<ThoiKhoaBieu>danhSachThoiKhoaBieu;
    @FXML
    private TableColumn<ThoiKhoaBieu, String> danhSachMonHoc;
    @FXML
    private TableColumn<ThoiKhoaBieu, LocalTime> danhSachGioHoc;
    @FXML
    private TableColumn<ThoiKhoaBieu, String> danhSachPhongHoc;
    @FXML
    private TableView<TrangThaiMonHoc> danhSachSinhVienDangHoc;
    @FXML
    private TableColumn<TrangThaiMonHoc,Integer> maSinhVien;
    @FXML
    private TableColumn<TrangThaiMonHoc, String> hoTen;
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
    private Tab addTab;
    @FXML
    private ComboBox<Integer> addID;
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
    @FXML
    private Button addManyStudent;
    @FXML
    private Button saveCheckin;

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
        ObservableList<ThoiKhoaBieu> dsMonList = FXCollections.observableList(ThoiKhoaBieuDAO.thoiKhoaBieu());
        danhSachMonHoc.setCellValueFactory(thoiKhoaBieuStringCellDataFeatures -> new SimpleObjectProperty<>(thoiKhoaBieuStringCellDataFeatures.getValue().getMaMon().getTenMon()));
        danhSachGioHoc.setCellValueFactory(new PropertyValueFactory<>("gioBatDau"));
        danhSachPhongHoc.setCellValueFactory(new PropertyValueFactory<>("phongHoc"));
        danhSachThoiKhoaBieu.setItems(dsMonList);

        ObservableList<Integer> dsSinhVien = FXCollections.observableList(NguoiDungDAO.danhSachMaSinhVien());
        addID.setItems(dsSinhVien);
        addID.setEditable(true);
        addID.getSelectionModel().select(0);
    }

    private void callNotice(String styleClass, String noticeText){
        noticePane.getStyleClass().clear();
        noticePane.getStyleClass().addAll("alert","alert-"+styleClass);
        noticeContent.setText(noticeText);
        noticePane.setVisible(true);
        PauseTransition pause = new PauseTransition(Duration.seconds(4));
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

    private void showWarning(String warningText){
        callNotice("warning", warningText);
    }

    @FXML
    private void onClickMenuAdd(ActionEvent actionEvent) {
        tabList.getSelectionModel().select(addTab);
    }

    @FXML
    private void onClickDelete() {
        TrangThaiMonHoc sinhVien = danhSachSinhVienDangHoc.getSelectionModel().getSelectedItem();
        ButtonType yesBtn = new ButtonType("C??", ButtonBar.ButtonData.OK_DONE);
        ButtonType noBtn = new ButtonType("Kh??ng", ButtonBar.ButtonData.CANCEL_CLOSE);
        Alert confirmDel = new Alert(Alert.AlertType.CONFIRMATION,"B???n c?? mu???n x??a sinh vi??n kh???i l???p n??y kh??ng?",yesBtn,noBtn);
        confirmDel.setTitle("X??c nh???n x??a");
        confirmDel.setHeaderText("X??c nh???n x??a");
        Optional<ButtonType> result = confirmDel.showAndWait();
        if (result.orElse(yesBtn) == yesBtn) {
            if (NgayHocDAO.xoaNgayHoc(sinhVien.getTkbSv())){
                danhSachSinhVienDangHoc.getItems().remove(sinhVien);
                TkbSvDAO.xoaMon(sinhVien.getTkbSv());
                showSuccess("X??a sinh vi??n kh???i m??n th??nh c??ng");
            }
            else {
                String[] thongBao = {"Kh??ng th??? x??a, c?? l???i x???y ra, vui l??ng ki???m tra l???i"};
                ButtonType xacNhan = new ButtonType("X??c nh???n", ButtonBar.ButtonData.OK_DONE);
                Alert canNotDelete = new Alert(Alert.AlertType.ERROR,thongBao[0],xacNhan);
                canNotDelete.setTitle("Kh??ng th??? x??a");
                canNotDelete.setHeaderText("Kh??ng th??? x??a");
                canNotDelete.showAndWait();
            }
        }
    }

    @FXML
    private void onSelect() {
        tabList.setDisable(false);
        ThoiKhoaBieu thoiKhoaBieu = danhSachThoiKhoaBieu.getSelectionModel().getSelectedItem();
        if (thoiKhoaBieu != null) {
            ObservableList<TrangThaiMonHoc> trangThaiMonHoc = FXCollections.observableList(NgayHocDAO.trangThaiMonHoc(thoiKhoaBieu));
            maSinhVien.setCellValueFactory(new PropertyValueFactory<>("maSinhVien"));
            hoTen.setCellValueFactory(new PropertyValueFactory<>("hoTen"));
            IntStream.range(1, 16).forEachOrdered(i -> {
                getTuan(i).setCellValueFactory(trangThaiMonHocBooleanCellDataFeatures -> {
                    TrangThaiMonHoc ttmh = trangThaiMonHocBooleanCellDataFeatures.getValue();
                    SimpleBooleanProperty booleanProp = new SimpleBooleanProperty(ttmh.getTuan(i));
                    booleanProp.addListener((observableValue, aBoolean, t1) -> ttmh.setTuan(i, t1));
                    return booleanProp;
                });
                getTuan(i).setCellFactory(cell -> new CheckBoxTableCell<>());
            });
            danhSachSinhVienDangHoc.setItems(trangThaiMonHoc);
        }
    }

    @FXML
    private void onRightClick(ContextMenuEvent contextMenuEvent) {
        contextMenu.show(danhSachSinhVienDangHoc.getSelectionModel().getTableView(),contextMenuEvent.getScreenX(),contextMenuEvent.getScreenY());

    }

    @FXML
    private void onPressDel(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.DELETE)
            onClickDelete();
    }

    @FXML
    private void onClickAdd(ActionEvent actionEvent) {
        String [] thongBao = {"Th??m sinh vi??n v??o l???p th??nh c??ng", "sinh vi??n n??y ???? ??? trong l???p h???c ho???c ???? h???c c??ng m??n"};
        NguoiDung sinhVien = NguoiDungDAO.layThongTinSinhVien(Integer.parseInt(String.valueOf(addID.getSelectionModel().getSelectedItem())));
        if (themSinhVien(sinhVien,danhSachThoiKhoaBieu.getSelectionModel().getSelectedItem()))
            showSuccess(thongBao[0]);
        else {
            showError(thongBao[1]);
        }
    }

    private boolean themSinhVien(NguoiDung sinhVien, ThoiKhoaBieu thoiKhoaBieu) {
        TkbSv tkb_Sv = new TkbSv();
        tkb_Sv.setMaSV(sinhVien);
        tkb_Sv.setSTTMon(thoiKhoaBieu);
        if (TkbSvDAO.kiemTraHocCungMon(sinhVien, thoiKhoaBieu.getMaMon()))
        {
            return false;
        } else if (TkbSvDAO.themSinhVienVaoMon(tkb_Sv)) {
            List<Boolean> diemDanh = new ArrayList<>(Collections.nCopies(15,false));
            IntStream.rangeClosed(1, 15).forEach(i -> {
                NgayHoc ngayHoc = new NgayHoc();
                ngayHoc.setMaTkbSv(tkb_Sv);
                ngayHoc.setTuanHoc(i);
                ngayHoc.setDiemDanh(false);
                NgayHocDAO.themNgayHoc(ngayHoc);
            });
            TrangThaiMonHoc ttmh = new TrangThaiMonHoc();
            ttmh.setData(sinhVien.getId(), sinhVien.getHoTen(), diemDanh);
            danhSachSinhVienDangHoc.getItems().add(ttmh);
            return true;
        } else return false;
    }

    @FXML
    private void onClickChooseFile(ActionEvent actionEvent) {
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Ch???n danh s??ch sinh vi??n (.CSV)");
        FileChooser.ExtensionFilter csvFilter = new FileChooser.ExtensionFilter("CSV","*.csv");
        chooser.getExtensionFilters().add(csvFilter);
        File file = chooser.showOpenDialog(null);
        if (file != null){
            nameFile.setText(file.getName());
            filePath = file.getPath();
            addFileButton.setDisable(false);
        }
    }

    @FXML
    private void onClickAddFile(ActionEvent actionEvent) throws IOException, CsvValidationException {
        CSVParser parser = new CSVParserBuilder().withSeparator(';').build();
        Reader reader = new FileReader(filePath, StandardCharsets.UTF_8);
        CSVReader csvReader = new CSVReaderBuilder(reader).withCSVParser(parser).build();
        String[] line1 = csvReader.peek();
        if (line1.length != 2) {
            showError("L???i file, vui l??ng ch???n file CSV ????ng ?????nh d???ng <m?? sinh vi??n; h??? t??n>");
        } else {
            AtomicReference<Boolean> isExist = new AtomicReference<>(true);
            AtomicReference<Boolean> isContain = new AtomicReference<>(false);
            AtomicInteger count = new AtomicInteger();
            ThoiKhoaBieu thoiKhoaBieu = danhSachThoiKhoaBieu.getSelectionModel().getSelectedItem();
            csvReader.forEach(line -> {
                String mssv = line[0].replaceAll("\\D", "");
                String hoTenSV = line[1];
                NguoiDung sinhVien = NguoiDungDAO.layThongTinSinhVien(Integer.parseInt(mssv));
                if (sinhVien != null) {
                    TkbSv tkb_sv = TkbSvDAO.layThongTinMon(sinhVien,thoiKhoaBieu);
                    if (tkb_sv != null) {
                        isContain.set(true);
                    } else {
                        count.getAndIncrement();
                        themSinhVien(sinhVien, thoiKhoaBieu);
                    }
                } else {
                    isExist.set(false);
                }
            });
            if(isExist.get() && !isContain.get()) {
                showSuccess("Th??m sinh vi??n m???i v??o l???p th??nh c??ng");
            } else if (isExist.get() && isContain.get() && count.get() > 0) {
                showWarning("Th??m sinh vi??n v??o l???p th??nh c??ng; M???t v??i sinh vi??n ???? c?? trong l???p n??y");
            } else if (isExist.get() && isContain.get() && count.get() == 0) {
                showError("C??c sinh vi??n tr??n ?????u c?? trong l???p n??y");
            } else if (!isExist.get() && isContain.get() && count.get() > 0) {
                showWarning("???? th??m [" + count.get() +"] sinh vi??n, m???t v??i sinh vi??n ???? c?? trong l???p n??y; C?? sinh vi??n ch??a c?? trong danh s??ch sinh vi??n, vui l??ng v??o danh s??ch sinh vi??n ????? th??m tr?????c");
            } else if (!isExist.get() && isContain.get() && count.get() == 0) {
                showError("Kh??ng th??m ???????c sinh vi??n m???i: C?? sinh vi??n ch??a c?? trong danh s??ch sinh vi??n; C?? sinh vi??n ???? c?? trong l???p n??y");
            } else if (!isExist.get() && !isContain.get() && count.get() > 0) {
                showWarning("???? th??m [" + count.get() +"] sinh vi??n; C?? sinh vi??n ch??a c?? trong danh s??ch sinh vi??n, vui l??ng v??o danh s??ch sinh vi??n ????? th??m tr?????c");
            } else if (!isExist.get() && !isContain.get() && count.get() == 0) {
                showError("Kh??ng th??m ???????c sinh vi??n m???i; Ch??a c?? trong danh s??ch sinh vi??n, vui l??ng v??o danh s??ch sinh vi??n ????? th??m tr?????c");
            }
        }
    }

    @FXML
    private void onClickAddManyStudent(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxml = new FXMLLoader(IndexApp.class.getResource("them-nhieu-sinh-vien.fxml"));
        Scene scene = new Scene(fxml.load());
        Stage secondStage = new Stage();
        ThemSinhVienController controller = fxml.getController();
        controller.run(danhSachThoiKhoaBieu.getSelectionModel().getSelectedItem());
        secondStage.setScene(scene);
        secondStage.setTitle("Ch???n sinh vi??n th??m v??o m??n");
        secondStage.initModality(Modality.APPLICATION_MODAL);
        secondStage.initOwner(lichHocView.getScene().getWindow());
        secondStage.show();
    }

    @FXML
    private void onSelectStudent(ActionEvent actionEvent) {
        NguoiDung sinhVien = NguoiDungDAO.layThongTinSinhVien(Integer.parseInt(String.valueOf(addID.getSelectionModel().getSelectedItem())));
        addName.setText(sinhVien.getHoTen());
    }

    @FXML
    private void onClickSaveCheckin(ActionEvent actionEvent) {
        AtomicReference<Boolean> check = new AtomicReference<>(true);
        for(TrangThaiMonHoc ttmh : danhSachSinhVienDangHoc.getItems()){
            check.set(NgayHocDAO.capNhatThongTinNgayHoc(ttmh));
            if (!check.get())
                break;
        }
        if(check.get())
            showSuccess("???? c???p nh???t th??ng tin ??i???m danh");
        else showError("C?? l???i, vui l??ng ki???m tra l???i");
    }

    @FXML
    private void onClickMenuRefresh(ActionEvent actionEvent) {
        danhSachSinhVienDangHoc.refresh();
        danhSachThoiKhoaBieu.refresh();
    }
}
