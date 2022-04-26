module com.qlsv.qlsv {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires java.persistence;
    requires org.hibernate.orm.core;
    requires org.hibernate.commons.annotations;
    requires java.naming;
    requires java.sql;
    requires java.xml.bind;
    requires org.apache.commons.codec;
    requires com.opencsv;

    exports com.qlsv.qlsv;
    exports com.qlsv.qlsv.entities;

    opens com.qlsv.qlsv to javafx.fxml;
    opens com.qlsv.qlsv.entities to org.hibernate.orm.core, javafx.base;
}