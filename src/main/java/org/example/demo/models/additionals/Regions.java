package org.example.demo.models.additionals;

import javafx.beans.property.SimpleStringProperty;
import org.example.demo.util.annotations.FieldInfo;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Regions {
    @FieldInfo(id = true, name = "ID")
    private SimpleStringProperty RegionId = new SimpleStringProperty();

    @FieldInfo(name = "Region Name", isDisplayValue = true)
    private SimpleStringProperty RegionName = new SimpleStringProperty();

    public Regions() {}

    @Override
    public String toString() {
        return RegionName.get();
    }

    public String getRegionId() {
        return RegionId.get();
    }

    public SimpleStringProperty regionIdProperty() {
        return RegionId;
    }

    public void setRegionId(String regionId) {
        this.RegionId.set(regionId);
    }

    public String getRegionName() {
        return RegionName.get();
    }

    public SimpleStringProperty regionNameProperty() {
        return RegionName;
    }

    public void setRegionName(String regionName) {
        this.RegionName.set(regionName);
    }
}
