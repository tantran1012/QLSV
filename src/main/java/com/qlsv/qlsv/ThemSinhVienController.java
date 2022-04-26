package com.qlsv.qlsv;

import com.qlsv.qlsv.DAO.NgayHocDAO;
import com.qlsv.qlsv.DAO.NguoiDungDAO;
import com.qlsv.qlsv.DAO.TkbSvDAO;
import com.qlsv.qlsv.entities.NgayHoc;
import com.qlsv.qlsv.entities.NguoiDung;
import com.qlsv.qlsv.entities.ThoiKhoaBieu;
import com.qlsv.qlsv.entities.TkbSv;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.*;
import java.util.stream.IntStream;

public class ThemSinhVienController {
    @FXML
    private VBox addMoreStudentPane;
    @FXML
    private CheckBox selectAll;
    @FXML
    private TableView<NguoiDung> addManyStudentTable;
    @FXML
    private TableColumn<NguoiDung, Integer> studentIDColumn;
    @FXML
    private TableColumn<NguoiDung, String> studentNameColumn;
    @FXML
    private Button addMoreStudentButton;

    private ThoiKhoaBieu thoiKhoaBieu;

    public void run(ThoiKhoaBieu tkb) {
        this.thoiKhoaBieu = tkb;
        List<NguoiDung> danhSachSinhVien = NguoiDungDAO.danhSachSinhVien();
        danhSachSinhVien.removeIf(sinhVien -> TkbSvDAO.kiemTraHocCungMon(sinhVien, thoiKhoaBieu.getMaMon()));
        ObservableList<NguoiDung> dsSinhVien = FXCollections.observableList(danhSachSinhVien);
        studentIDColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        studentNameColumn.setCellValueFactory(new PropertyValueFactory<>("hoTen"));
        addManyStudentTable.setItems(dsSinhVien);

        addManyStudentTable.setRowFactory(tableView2 -> {
            final TableRow<NguoiDung> row = new TableRow<>();

            row.addEventFilter(MouseEvent.MOUSE_PRESSED, event -> {
                final int index = row.getIndex();

                if (index >= 0 && index < addManyStudentTable.getItems().size()) {
                    if (addManyStudentTable.getSelectionModel().isSelected(index))
                        addManyStudentTable.getSelectionModel().clearSelection(index);
                    else
                        addManyStudentTable.getSelectionModel().select(index);

                    event.consume();
                }
            });
            return row;
        });
        addManyStudentTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }

    @FXML
    private void onSelected(ActionEvent actionEvent) {
        if(selectAll.isSelected()){
            addManyStudentTable.getSelectionModel().selectAll();
        } else {
            addManyStudentTable.getSelectionModel().clearSelection();
        }
    }

    @FXML
    private void onClickAdd(ActionEvent actionEvent) {
        addManyStudentTable.getSelectionModel().getSelectedItems().forEach(sinhVien ->{
            themSinhVien(sinhVien,thoiKhoaBieu);
        });
        ButtonType xacNhan = new ButtonType("Đã hiểu", ButtonBar.ButtonData.OK_DONE);
        Alert addSuccess = new Alert(Alert.AlertType.INFORMATION,"Thêm sinh viên vào môn học thành công",xacNhan);
        addSuccess.setTitle("Thêm sinh viên");
        addSuccess.setHeaderText("Đã thêm sinh viên ");
        Optional<ButtonType> result = addSuccess.showAndWait();
        if(result.orElse(xacNhan) == xacNhan) {
            Stage stage = (Stage) addMoreStudentPane.getScene().getWindow();
            stage.close();
        }
    }

    private void themSinhVien(NguoiDung sinhVien, ThoiKhoaBieu thoiKhoaBieu) {
        TkbSv tkb_Sv = new TkbSv();
        tkb_Sv.setMaSV(sinhVien);
        tkb_Sv.setSTTMon(thoiKhoaBieu);
        if (TkbSvDAO.themSinhVienVaoMon(tkb_Sv)) {
            IntStream.rangeClosed(1, 15).forEach(i -> {
                NgayHoc ngayHoc = new NgayHoc();
                ngayHoc.setMaTkbSv(tkb_Sv);
                ngayHoc.setTuanHoc(i);
                ngayHoc.setDiemDanh(false);
                NgayHocDAO.themNgayHoc(ngayHoc);
            });
        }
    }
}
