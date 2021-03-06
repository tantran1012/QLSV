package com.qlsv.qlsv;

import com.qlsv.qlsv.DAO.NguoiDungDAO;
import com.qlsv.qlsv.entities.NguoiDung;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.FlowPane;
import javafx.util.Duration;
import org.apache.commons.codec.digest.DigestUtils;

import java.net.URL;
import java.util.ResourceBundle;

public class ThongTinCaNhanViewController implements Initializable {
    @FXML
    private Label studentID;
    @FXML
    private Label studentName;
    @FXML
    private PasswordField oldPassword;
    @FXML
    private PasswordField newPassword;
    @FXML
    private PasswordField confirmNewPassword;
    @FXML
    private Button changePasswordButton;

    private final NguoiDung nguoiDung = NguoiDungDAO.layThongTinSinhVien(IndexApp.SessionID);
    @FXML
    private FlowPane noticePane;
    @FXML
    private Label noticeContent;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        studentID.setText(String.valueOf(IndexApp.SessionID));
        studentName.setText(nguoiDung.getHoTen());
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
    private void onClickChangePassword(ActionEvent actionEvent) {
        String key = "ThisIsAKey";
        String password = DigestUtils.md5Hex(DigestUtils.md5Hex(oldPassword.getText()) + key);
        if(oldPassword.getText().equals("")) {
            showError("Kh??ng ???????c b??? tr???ng m???t kh???u c??");
        } else if (!password.equals(nguoiDung.getMatKhau())){
            showError("M???t kh???u c?? kh??ng ????ng");
        } else {
            if (newPassword.getText().equals("")){
                showError("Ch??a nh???p m???t kh???u m???i");
            } else if (confirmNewPassword.getText().equals("")){
                showError("Ch??a nh???p x??c nh???n m???t kh???u");
            } else if (!newPassword.getText().equals(confirmNewPassword.getText())){
                showError("X??c nh???n m???t kh???u kh??ng ????ng");
            } else if (newPassword.getText().length() < 8) {
                showError("M???t kh???u ph???i ch???a ??t nh???t 8 k?? t???");
            } else if (newPassword.getText().equals(String.valueOf(IndexApp.SessionID))) {
                showError("M???t kh???u kh??ng ???????c tr??ng v???i m?? s??? sinh vi??n");
            } else {
                String newPass = DigestUtils.md5Hex(DigestUtils.md5Hex(newPassword.getText()) + key);
                nguoiDung.setMatKhau(newPass);
                if (NguoiDungDAO.capNhatThongTinSinhVien(nguoiDung)){
                    showSuccess("?????i m???t kh???u th??nh c??ng");
                    oldPassword.setText("");
                    newPassword.setText("");
                    confirmNewPassword.setText("");
                }
            }
        }
    }
}
