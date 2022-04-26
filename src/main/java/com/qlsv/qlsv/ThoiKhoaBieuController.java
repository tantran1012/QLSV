package com.qlsv.qlsv;

import com.qlsv.qlsv.DAO.MonHocDAO;
import com.qlsv.qlsv.DAO.ThoiKhoaBieuDAO;
import com.qlsv.qlsv.entities.ThoiKhoaBieu;
import javafx.animation.PauseTransition;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.util.Duration;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicBoolean;

public class ThoiKhoaBieuController implements Initializable {
    @FXML
    private BorderPane thoikhoabieuView;
    @FXML
    private ContextMenu contextMenu;
    @FXML
    private MenuItem delete;
    @FXML
    private TableView<ThoiKhoaBieu> dsMon;
    @FXML
    private TableColumn<ThoiKhoaBieu, Integer> iD;
    @FXML
    private TableColumn<ThoiKhoaBieu, String> maMon;
    @FXML
    private TableColumn<ThoiKhoaBieu, String> tenMon;
    @FXML
    private TableColumn<ThoiKhoaBieu, LocalDate> startDay;
    @FXML
    private TableColumn<ThoiKhoaBieu, LocalDate> endDay;
    @FXML
    private TableColumn<ThoiKhoaBieu, Integer> weekDay;
    @FXML
    private TableColumn<ThoiKhoaBieu, LocalTime> startTime;
    @FXML
    private TableColumn<ThoiKhoaBieu, LocalTime> endTime;
    @FXML
    private TableColumn<ThoiKhoaBieu, String> room;
    @FXML
    private ComboBox<String> editListMaMon;
    @FXML
    private DatePicker editStartDay;
    @FXML
    private DatePicker editEndDay;
    @FXML
    private ChoiceBox<Integer> editWeekDay;
    @FXML
    private Spinner<LocalTime> editStartTime;
    @FXML
    private Spinner<LocalTime> editEndTime;
    @FXML
    private TextField editRoom;
    @FXML
    private Button saveEdit;
    @FXML
    private FlowPane noticePane;
    @FXML
    private Label noticeContent;
    @FXML
    private ComboBox<String> addListMaMon;
    @FXML
    private DatePicker addStartDay;
    @FXML
    private Spinner<LocalTime> addStartTime;
    @FXML
    private TextField addRoom;
    @FXML
    private Button saveAdd;
    @FXML
    private Tab editTab;
    @FXML
    private TabPane tabList;
    @FXML
    private Tab addTab;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<ThoiKhoaBieu> dsMonList = FXCollections.observableList(ThoiKhoaBieuDAO.thoiKhoaBieu());
        iD.setCellValueFactory(new PropertyValueFactory<>("id"));
        maMon.setCellValueFactory(thoiKhoaBieuStringCellDataFeatures -> new SimpleObjectProperty<>(thoiKhoaBieuStringCellDataFeatures.getValue().getMaMon().getId()));
        tenMon.setCellValueFactory(thoiKhoaBieuStringCellDataFeatures -> new SimpleObjectProperty<>(thoiKhoaBieuStringCellDataFeatures.getValue().getMaMon().getTenMon()));
        startDay.setCellValueFactory(new PropertyValueFactory<>("ngayBatDau"));
        endDay.setCellValueFactory(new PropertyValueFactory<>("ngayKetThuc"));
        weekDay.setCellValueFactory(new PropertyValueFactory<>("thu"));
        startTime.setCellValueFactory(new PropertyValueFactory<>("gioBatDau"));
        endTime.setCellValueFactory(new PropertyValueFactory<>("gioKetThuc"));
        room.setCellValueFactory(new PropertyValueFactory<>("phongHoc"));
        dsMon.setItems(dsMonList);


        ObservableList<String> dsMonHocList = FXCollections.observableList(MonHocDAO.danhSachMaMonHoc());
        editListMaMon.setItems(dsMonHocList);
        addListMaMon.setItems(dsMonHocList);

        List<Integer> thu = List.of(new Integer[]{2, 3, 4, 5, 6, 7, 8});
        ObservableList<Integer> listThu = FXCollections.observableList(thu);
        editWeekDay.setItems(listThu);
        addStartTime.setValueFactory(new TimeSpinner().getValueFactory());
    }

    @FXML
    private void onClickDelete() {
        ThoiKhoaBieu tKB = dsMon.getSelectionModel().getSelectedItem();
        ButtonType yesBtn = new ButtonType("Có", ButtonBar.ButtonData.OK_DONE);
        ButtonType noBtn = new ButtonType("Không", ButtonBar.ButtonData.CANCEL_CLOSE);
        Alert confirmDel = new Alert(Alert.AlertType.CONFIRMATION,"Bạn có muốn xóa lịch học môn này không?",yesBtn,noBtn);
        confirmDel.setTitle("Xác nhận xóa");
        confirmDel.setHeaderText("Xác nhận xóa");
        Optional<ButtonType> result = confirmDel.showAndWait();
        if (result.orElse(yesBtn) == yesBtn) {
            String[] thongBao = {"Không tìm thấy lịch học này", "Vui lòng xóa lịch đăng ký học môn này của sinh viên trước"};
            int type;
            if (ThoiKhoaBieuDAO.xoaMon(tKB)){
                dsMon.getItems().remove(tKB);
                callNotice("success", "Xóa lịch học thành công");
            }
            else {
                if (tKB == null)
                    type = 0;
                else
                    type = 1;
                ButtonType xacNhan = new ButtonType("Xác nhận", ButtonBar.ButtonData.OK_DONE);
                Alert canNotDelete = new Alert(Alert.AlertType.ERROR,thongBao[type],xacNhan);
                canNotDelete.setTitle("Không thể xóa");
                canNotDelete.setHeaderText("Không thể xóa");
                canNotDelete.showAndWait();
            }
        }
    }

    @FXML
    private void onRightClick(ContextMenuEvent contextMenuEvent) {
        contextMenu.show(dsMon.getSelectionModel().getTableView(),contextMenuEvent.getScreenX(),contextMenuEvent.getScreenY());
    }

    @FXML
    private void onPressDel(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.DELETE)
            onClickDelete();
    }

    @FXML
    private void onSelect(MouseEvent mouseEvent) {
        ThoiKhoaBieu tKB = dsMon.getSelectionModel().getSelectedItem();
        if (tKB != null) {
            editListMaMon.getSelectionModel().select(tKB.getMaMon().getId());
            editStartDay.setValue(tKB.getNgayBatDau());
            editEndDay.setValue(tKB.getNgayKetThuc());
            editWeekDay.setValue(tKB.getThu());
            editStartTime.setValueFactory(new TimeSpinner(tKB.getGioBatDau()).getValueFactory());
            editEndTime.setValueFactory(new TimeSpinner(tKB.getGioKetThuc()).getValueFactory());
            editRoom.setText(tKB.getPhongHoc());
        }
    }

    @FXML
    private void onClickEdit(ActionEvent actionEvent) {
        ThoiKhoaBieu tKb = dsMon.getSelectionModel().getSelectedItem();
        String [] thongBao = {"Không được bỏ trống phòng học"};
        if (editRoom.getText().equals("")){
            callNotice("danger",thongBao[0]);
        } else {
            tKb.setMaMon(MonHocDAO.layThongTinMon(editListMaMon.getSelectionModel().getSelectedItem()));
            tKb.setNgayBatDau(editStartDay.getValue());
            tKb.setNgayKetThuc(editStartDay.getValue().plusWeeks(14));
            tKb.setGioBatDau(editStartTime.getValue());
            tKb.setGioKetThuc(editStartTime.getValue().plusMinutes(210));
            tKb.setThu(editStartDay.getValue().getDayOfWeek().getValue()+1);
            tKb.setPhongHoc(editRoom.getText());
            AtomicBoolean check = new AtomicBoolean(false);
            for(ThoiKhoaBieu tkb : dsMon.getItems()){
                if (tkb.compare(tKb)){
                    check.set(true);
                    break;
                }
            }
            int index = dsMon.getSelectionModel().getSelectedIndex();
            if (check.get())
                callNotice("danger","Lịch học bị trùng, vui lòng kiểm tra lại");
            else if (ThoiKhoaBieuDAO.capNhatThongTinMon(tKb)) {
                dsMon.getItems().set(index, tKb);
                callNotice("success","Sửa lịch học thành công");
            } else {
                callNotice("danger","Không sửa được lịch học này, vui lòng kiểm tra lại");
            }
        }
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

    @FXML
    private void onClickAdd(ActionEvent actionEvent) {
        ThoiKhoaBieu tkb_new = new ThoiKhoaBieu();
        String [] thongBao = {"Không được bỏ trống mã môn",
                              "Không được bỏ trống ngày bắt đầu",
                              "Không được bỏ trống giờ bắt đầu",
                              "Không được bỏ trống phòng học"};
        if (addListMaMon.getSelectionModel().getSelectedItem() == null){
            callNotice("danger", thongBao[0]);
        } else if (addStartDay.getValue() == null) {
            callNotice("danger", thongBao[1]);
        } else if (addStartTime.getValue() == null){
            callNotice("danger", thongBao[2]);
        } else if (addRoom.getText().equals("")){
            callNotice("danger", thongBao[3]);
        } else {
            tkb_new.setMaMon(MonHocDAO.layThongTinMon(addListMaMon.getSelectionModel().getSelectedItem()));
            tkb_new.setNgayBatDau(addStartDay.getValue());
            tkb_new.setNgayKetThuc(addStartDay.getValue().plusWeeks(14));
            tkb_new.setGioBatDau(addStartTime.getValue());
            tkb_new.setGioKetThuc(addStartTime.getValue().plusMinutes(210));
            tkb_new.setThu(addStartDay.getValue().getDayOfWeek().getValue()+1);
            tkb_new.setPhongHoc(addRoom.getText());
            AtomicBoolean check = new AtomicBoolean(false);
            for(ThoiKhoaBieu tkb : dsMon.getItems()){
                if (tkb.compare(tkb_new)){
                    check.set(true);
                    break;
                }
            }
            if (check.get())
                callNotice("danger","Lịch học bị trùng, vui lòng kiểm tra lại");
            else if (ThoiKhoaBieuDAO.themMonVaoThoiKhoaBieu(tkb_new)){
                callNotice("success","Thêm lịch học mới thành công");
                dsMon.getItems().add(tkb_new);
            } else {
                callNotice("danger", "Không thêm được lịch học mới, vui lòng kiểm tra lại");
            }
        }
    }

    @FXML
    private void onClickMenuAdd(ActionEvent actionEvent) {
        tabList.getSelectionModel().select(addTab);
    }

    @FXML
    private void onClickMenuEdit(ActionEvent actionEvent) {
        tabList.getSelectionModel().select(editTab);
    }
}
