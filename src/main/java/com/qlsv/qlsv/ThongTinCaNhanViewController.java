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
            showError("Không được bỏ trống mật khẩu cũ");
        } else if (!password.equals(nguoiDung.getMatKhau())){
            showError("Mật khẩu cũ không đúng");
        } else {
            if (newPassword.getText().equals("")){
                showError("Chưa nhập mật khẩu mới");
            } else if (confirmNewPassword.getText().equals("")){
                showError("Chưa nhập xác nhận mật khẩu");
            } else if (!newPassword.getText().equals(confirmNewPassword.getText())){
                showError("Xác nhận mật khẩu không đúng");
            } else if (newPassword.getText().length() < 8) {
                showError("Mật khẩu phải chứa ít nhất 8 ký tự");
            } else if (newPassword.getText().equals(String.valueOf(IndexApp.SessionID))) {
                showError("Mật khẩu không được trùng với mã số sinh viên");
            } else {
                String newPass = DigestUtils.md5Hex(DigestUtils.md5Hex(newPassword.getText()) + key);
                nguoiDung.setMatKhau(newPass);
                if (NguoiDungDAO.capNhatThongTinSinhVien(nguoiDung)){
                    showSuccess("Đổi mật khẩu thành công");
                    oldPassword.setText("");
                    newPassword.setText("");
                    confirmNewPassword.setText("");
                }
            }
        }
    }
}
