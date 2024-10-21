package org.example.demo.models.additionals;

import javafx.beans.property.*;
import org.example.demo.util.annotations.FieldInfo;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Packages {
    @FieldInfo(id = true, name = "ID")
    private SimpleIntegerProperty PackageId =  new SimpleIntegerProperty();

    @FieldInfo(name = "Package Name", isDisplayValue = true)
    private SimpleStringProperty PkgName =  new SimpleStringProperty();
    private SimpleDoubleProperty PkgBasePrice =  new SimpleDoubleProperty();
    private SimpleDoubleProperty PkgAgencyCommission =  new SimpleDoubleProperty();

    public Packages() {}

    @Override
    public String toString() {
        return PkgName.get();
    }

    public int getPackageId() {
        return PackageId.get();
    }

    public void setPackageId(int packageId) {
        this.PackageId.set(packageId);
    }

    public String getPkgName() {
        return PkgName.get();
    }

    public void setPkgName(String pkgName) {
        this.PkgName.set(pkgName);
    }

    public double getPkgBasePrice() {
        return PkgBasePrice.get();
    }

    public void setPkgBasePrice(int pkgBasePrice) {
        this.PkgBasePrice.set(pkgBasePrice);
    }

    public double getPkgAgencyCommission() {
        return PkgAgencyCommission.get();
    }

    public void setPkgAgencyCommission(int pkgAgencyCommission) {
        this.PkgAgencyCommission.set(pkgAgencyCommission);
    }
}
