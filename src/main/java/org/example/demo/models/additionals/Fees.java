package org.example.demo.models.additionals;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import org.example.demo.util.annotations.FieldInfo;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Fees {
    @FieldInfo(id = true, name = "ID")
    private SimpleStringProperty FeeId =  new SimpleStringProperty();

    @FieldInfo(name = "Fee Amount", isDisplayValue = true)
    private SimpleDoubleProperty FeeAmt = new SimpleDoubleProperty();

    public Fees(SimpleStringProperty feeId, SimpleDoubleProperty feeAmt) {
        FeeId = feeId;
        FeeAmt = feeAmt;
    }

    public Fees() {}

    @Override
    public String toString() {
        return FeeId.get();
    }

    public String getFeeId() {
        return FeeId.get();
    }

    public SimpleStringProperty feeIdProperty() {
        return FeeId;
    }

    public void setFeeId(String feeId) {
        this.FeeId.set(feeId);
    }

    public double getFeeAmt() {
        return FeeAmt.get();
    }

    public SimpleDoubleProperty feeAmtProperty() {
        return FeeAmt;
    }

    public void setFeeAmt(double feeAmt) {
        this.FeeAmt.set(feeAmt);
    }
}
