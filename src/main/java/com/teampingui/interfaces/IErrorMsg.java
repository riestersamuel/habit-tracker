package com.teampingui.interfaces;

import javafx.scene.control.Alert;

public interface IErrorMsg {
    Alert error (String windowTitle, String header, String description);
}
