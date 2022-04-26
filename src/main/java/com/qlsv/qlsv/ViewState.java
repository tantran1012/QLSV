package com.qlsv.qlsv;

import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.kordamp.bootstrapfx.BootstrapFX;

import java.io.IOException;
import java.io.UncheckedIOException;

public class ViewState {

    private final ReadOnlyObjectWrapper<Parent> currentView = new ReadOnlyObjectWrapper<>();

    private Parent logInView;
    private Parent mainMenuView;
    private Parent changePasswordVỉew;
    public ReadOnlyObjectProperty<Parent> currentViewProperty() {
        return currentView.getReadOnlyProperty();
    }

    public void showLogIn() {
        mainMenuView = null;
        if (logInView == null) {
            try {
                FXMLLoader loader = new FXMLLoader(IndexApp.class.getResource("dang-nhap.fxml"));
                logInView = loader.load();
                logInView.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());
            } catch (IOException exc) {
                throw new UncheckedIOException(exc);
            }
        }
        currentView.set(logInView);
    }

    public void showMainMenu() {
        if (mainMenuView == null) {
            try {
                FXMLLoader loader = new FXMLLoader(IndexApp.class.getResource("main-view-"+ IndexApp.ChucVu +".fxml"));
                mainMenuView = loader.load();
                mainMenuView.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());
            } catch (IOException exc) {
                throw new UncheckedIOException(exc);
            }
        }
        currentView.set(mainMenuView);
    }

    public void showChangePassword() {
        if (changePasswordVỉew == null) {
            try {
                FXMLLoader fxml = new FXMLLoader(IndexApp.class.getResource("doi-mat-khau-lan-dau.fxml"));
                changePasswordVỉew = fxml.load();
                changePasswordVỉew.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());
            } catch (IOException e) {
                throw new UncheckedIOException(e);
            }
        }
        currentView.set(changePasswordVỉew);
    }
}
