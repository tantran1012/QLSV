package com.qlsv.qlsv;

import com.qlsv.qlsv.DAO.MonHocDAO;
import com.qlsv.qlsv.entities.MonHoc;
import javafx.animation.PauseTransition;
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
import java.util.Optional;
import java.util.ResourceBundle;

public class MonHocController implements Initializable {
    @FXML
    private TableView<MonHoc> dsMon;
    @FXML
    private TableColumn<MonHoc, String> maMon;
    @FXML
    private TableColumn<MonHoc, String> tenMon;
    @FXML
    private TextField themMaMon;
    @FXML
    private TextField themTenMon;
    @FXML
    private Button themButton;
    @FXML
    private BorderPane monHocView;
    @FXML
    private FlowPane noticePane;
    @FXML
    private TextField suaMaMon;
    @FXML
    private TextField suaTenMon;
    @FXML
    private Button suaButton;
    @FXML
    private ContextMenu contextMenu;
    @FXML
    private MenuItem delete;
    @FXML
    private Label noticeContent;
    @FXML
    private TabPane tabList;
    @FXML
    private Tab editTab;
    @FXML
    private Tab addTab;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<MonHoc> dsMonList = FXCollections.observableList(MonHocDAO.danhSachMonHoc());
        maMon.setCellValueFactory(new PropertyValueFactory<>("id"));
        tenMon.setCellValueFactory(new PropertyValueFactory<>("tenMon"));
        dsMon.setItems(dsMonList);
    }

    @FXML
    private void onClickAdd(ActionEvent actionEvent) {
        MonHoc mh = new MonHoc(themMaMon.getText(), themTenMon.getText());
        String [] thongBao = {"Kh??ng ???????c b??? tr???ng m?? m??n", "Kh??ng ???????c b??? tr???ng t??n m??n"};
        if(themMaMon.getText().equals("")){
            callNotice("danger",thongBao[0]);
        } else if(themTenMon.getText().equals("")) {
            callNotice("danger",thongBao[1]);
        } else {
            if (MonHocDAO.themMon(mh)) {
                dsMon.getItems().add(mh);
                callNotice("success","Th??m m??n m???i th??nh c??ng");
            } else {
                callNotice("danger","Kh??ng th??m ???????c m??n m???i, vui l??ng ki???m tra l???i");
            }
        }
    }

    @FXML
    private void onClickDelete() {
        MonHoc mh = dsMon.getSelectionModel().getSelectedItem();
        ButtonType yesBtn = new ButtonType("C??", ButtonBar.ButtonData.OK_DONE);
        ButtonType noBtn = new ButtonType("Kh??ng", ButtonBar.ButtonData.CANCEL_CLOSE);
        Alert confirmDel = new Alert(Alert.AlertType.CONFIRMATION,"B???n c?? mu???n x??a m??n n??y kh??ng?",yesBtn,noBtn);
        confirmDel.setTitle("X??c nh???n x??a");
        confirmDel.setHeaderText("X??c nh???n x??a");
        Optional<ButtonType> result = confirmDel.showAndWait();
        if (result.orElse(yesBtn) == yesBtn){
            String [] thongBao = {"Kh??ng t??m th???y m??n n??y", "Vui l??ng x??a th???i kh??a bi???u c?? m??n n??y tr?????c"};
            int type;
            if(MonHocDAO.xoaMon(mh)) {
                dsMon.getItems().remove(mh);
            }
            else{
                if (mh == null)
                    type = 0;
                else
                    type = 1;
                ButtonType xacNhan = new ButtonType("X??c nh???n", ButtonBar.ButtonData.OK_DONE);
                Alert canNotDelete = new Alert(Alert.AlertType.ERROR,thongBao[type],xacNhan);
                canNotDelete.setTitle("Kh??ng th??? x??a");
                canNotDelete.setHeaderText("Kh??ng th??? x??a");
                canNotDelete.showAndWait();
            }
        }
    }

    @FXML
    private void onClickEdit() {
        MonHoc mh = dsMon.getSelectionModel().getSelectedItem();
        String [] thongBao = {"Kh??ng ???????c b??? tr???ng m?? m??n", "Kh??ng ???????c b??? tr???ng t??n m??n"};
        if(suaMaMon.getText().equals("")){
            callNotice("danger",thongBao[0]);
        } else if(suaTenMon.getText().equals("")){
            callNotice("danger",thongBao[1]);
        } else {
            mh.setId(suaMaMon.getText());
            mh.setTenMon(suaTenMon.getText());
            int index = dsMon.getSelectionModel().getSelectedIndex();
            if (MonHocDAO.capNhatThongTinMon(mh)) {
                dsMon.getItems().set(index, mh);
                callNotice("success", "s???a m??n h???c th??nh c??ng");
            } else {
                callNotice("danger", "Kh??ng s???a ???????c m??n n??y, vui l??ng ki???m tra l???i");
            }
        }
    }


    @FXML
    private void onSelect(MouseEvent actionEvent) {
        MonHoc monHoc = dsMon.getSelectionModel().getSelectedItem();
        if (monHoc != null) {
            suaMaMon.setText(monHoc.getId());
            suaTenMon.setText(monHoc.getTenMon());
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
    private void onClickMenuAdd(ActionEvent actionEvent) {
        tabList.getSelectionModel().select(addTab);
    }

    @FXML
    private void onClickMenuEdit(ActionEvent actionEvent) {
        tabList.getSelectionModel().select(editTab);
    }
}
