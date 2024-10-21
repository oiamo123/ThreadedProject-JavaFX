package org.example.demo.models.additionals;

import javafx.beans.property.SimpleIntegerProperty;
import org.example.demo.util.annotations.FieldInfo;

public class Products_Suppliers {
    @FieldInfo(name = "ID", id = true)
    private SimpleIntegerProperty ProductSupplierId =  new SimpleIntegerProperty();

    public Products_Suppliers() {}

    @Override
    public String toString() {
        return ProductSupplierId.toString();
    }

    public int getProductSupplierId() {
        return ProductSupplierId.get();
    }

    public SimpleIntegerProperty productSupplierIdProperty() {
        return ProductSupplierId;
    }

    public void setProductSupplierId(int productSupplierId) {
        this.ProductSupplierId.set(productSupplierId);
    }
}
