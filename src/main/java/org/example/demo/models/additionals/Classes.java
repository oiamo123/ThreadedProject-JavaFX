package org.example.demo.models.additionals;

import javafx.beans.property.SimpleStringProperty;
import org.example.demo.util.annotations.FieldInfo;

public class Classes {
    @FieldInfo(id = true, name = "ID")
    private SimpleStringProperty ClassId =  new SimpleStringProperty();

    @FieldInfo(name = "Class Name", isDisplayValue = true)
    private SimpleStringProperty ClassName =  new SimpleStringProperty();

    public Classes() {}

    @Override
    public String toString() {
        return ClassName.get();
    }

    public String getClassId() {
        return ClassId.get();
    }

    public SimpleStringProperty classIdProperty() {
        return ClassId;
    }

    public void setClassId(String classId) {
        this.ClassId.set(classId);
    }

    public String getClassName() {
        return ClassName.get();
    }

    public SimpleStringProperty classNameProperty() {
        return ClassName;
    }

    public void setClassName(String className) {
        this.ClassName.set(className);
    }
}
