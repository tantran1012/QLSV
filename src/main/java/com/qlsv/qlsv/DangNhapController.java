package com.qlsv.qlsv;

import com.qlsv.qlsv.DAO.NguoiDungDAO;
import com.qlsv.qlsv.entities.NguoiDung;
import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.apache.commons.codec.digest.DigestUtils;

import java.io.IOException;


public class DangNhapController {
    @FXML
    private TextField matKhau;
    @FXML
    private Button dangNhap;
    @FXML
    private Label thongBaoLoi;
    @FXML
    private TextField maSo;
    @FXML
    private BorderPane loginPage;

    @FXML
    private void onClickLogin() {
        int result;
        String key = "ThisIsAKey";
        if (maSo.getText().equals(""))
            result = 3;
        else if (matKhau.getText().equals(""))
            result = 4;
        else {
            String hashPass = DigestUtils.md5Hex(DigestUtils.md5Hex(matKhau.getText()) + key);
            result = NguoiDungDAO.dangNhap(Integer.parseInt(maSo.getText()), hashPass);
        }
        String[] thongBao = {"Sai Tài khoản", "Sai mật khẩu", "Chưa nhập tài khoản", "Chưa nhập mật khẩu"};
        if (result != 5){
            thongBaoLoi.setText("Có lỗi: " + thongBao[result-1]);
            PauseTransition pause = new PauseTransition(Duration.seconds(3));
            pause.setOnFinished(e -> {
                thongBaoLoi.setText("");
            });
            pause.play();
        }
        else{
            IndexApp.SessionID = Integer.parseInt(maSo.getText());
            NguoiDung nguoiDung = NguoiDungDAO.layThongTinSinhVien(IndexApp.SessionID);
            String hashPass = DigestUtils.md5Hex(DigestUtils.md5Hex(String.valueOf(IndexApp.SessionID)) + key);
            IndexApp.ChucVu = NguoiDungDAO.layThongTinSinhVien(Integer.parseInt(maSo.getText())).getChucVu();
            if (nguoiDung.getMatKhau().equals(hashPass))
                IndexApp.viewState.showChangePassword();
            else
                IndexApp.viewState.showMainMenu();
        }
    }

    @FXML
    private void onPressEnter(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ENTER)
            onClickLogin();
    }
}
