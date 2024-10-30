package org.example.demo.models.additionals;

import javafx.beans.property.SimpleStringProperty;
import org.example.demo.util.annotations.FieldInfo;

public class TripTypes {
    @FieldInfo(id = true, name = "ID")
    private SimpleStringProperty TripTypeId = new SimpleStringProperty();

    @FieldInfo(name = "Trip Type")
    private SimpleStringProperty TTName = new SimpleStringProperty();

    public TripTypes() {}

    @Override
    public String toString() {
        return TTName.get();
    }

    public String getTripTypeId() {
        return TripTypeId.get();
    }

    public SimpleStringProperty tripTypeIdProperty() {
        return TripTypeId;
    }

    public void setTripTypeId(String tripTypeId) {
        this.TripTypeId.set(tripTypeId);
    }

    public String getTTName() {
        return TTName.get();
    }

    public SimpleStringProperty TTNameProperty() {
        return TTName;
    }

    public void setTTName(String TTName) {
        this.TTName.set(TTName);
    }
}
