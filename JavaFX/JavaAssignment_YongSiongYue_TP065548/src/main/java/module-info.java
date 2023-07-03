module com.example.javaassignment_yongsiongyue_tp065548 {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;

    opens com.example.javaassignment_yongsiongyue_tp065548 to javafx.fxml;
    exports com.example.javaassignment_yongsiongyue_tp065548;
}