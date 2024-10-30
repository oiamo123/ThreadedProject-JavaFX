package org.example.demo.models;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import org.example.demo.util.annotations.FieldInfo;

import java.util.Date;

@FieldInfo(name = "Invoice")
public class Invoices {
    // Invoice properties
    @FieldInfo(name = "ID", id = true)
    private SimpleIntegerProperty InvoiceId;

    @FieldInfo(name = "Date", isDate = true)
    private SimpleObjectProperty<Date> InvoiceDate;

    @FieldInfo(name = "Fees", isDouble = true)
    private SimpleDoubleProperty Fees;

    @FieldInfo(name = "Total", isDouble = true)
    private SimpleDoubleProperty Total;

    @FieldInfo(name = "Tax", isDouble = true)
    private SimpleDoubleProperty TotalTax;

    @FieldInfo(name = "Commission", isDouble = true)
    private SimpleDoubleProperty CommissionTotal;

    @FieldInfo(name = "Booking Detail ID", isInt = true)
    private SimpleIntegerProperty BookingDetailId;

    @FieldInfo(name = "Customer ID", isInt = true)
    private SimpleIntegerProperty CustomerId;

    @FieldInfo(name = "Package ID", isInt = true)
    private SimpleIntegerProperty PackageId;

    public Invoices() {
        this.InvoiceId = new SimpleIntegerProperty();
        this.InvoiceDate = new SimpleObjectProperty<Date>();
        this.Fees = new SimpleDoubleProperty();
        this.Total = new SimpleDoubleProperty();
        this.TotalTax = new SimpleDoubleProperty();
        this.CommissionTotal = new SimpleDoubleProperty();
        this.BookingDetailId = new SimpleIntegerProperty();
        this.CustomerId = new SimpleIntegerProperty();
        this.PackageId = new SimpleIntegerProperty();
    }

    public int getBookingDetailId() {
        return BookingDetailId.get();
    }

    public SimpleIntegerProperty bookingDetailIdProperty() {
        return BookingDetailId;
    }

    public void setBookingDetailId(int bookingDetailId) {
        this.BookingDetailId.set(bookingDetailId);
    }

    public int getInvoiceId() {
        return InvoiceId.get();
    }

    public SimpleIntegerProperty invoiceIdProperty() {
        return InvoiceId;
    }

    public void setInvoiceId(int invoiceId) {
        this.InvoiceId.set(invoiceId);
    }

    public Date getInvoiceDate() { return InvoiceDate.get(); }

    public SimpleObjectProperty<Date> invoiceDateProperty () { return InvoiceDate; }

    public void setInvoiceDate(Date invoiceDate) {
        this.InvoiceDate.set(invoiceDate);
    }

    public double getFees() {
        return Fees.get();
    }

    public SimpleDoubleProperty feesProperty() {
        return Fees;
    }

    public void setFees(double fees) {
        this.Fees.set(fees);
    }

    public double getTotal() {
        return Total.get();
    }

    public SimpleDoubleProperty totalProperty() {
        return Total;
    }

    public void setTotal(double total) {
        this.Total.set(total);
    }

    public double getTotalTax() {
        return TotalTax.get();
    }

    public SimpleDoubleProperty totalTaxProperty() {
        return TotalTax;
    }

    public void setTotalTax(double totalTax) {
        this.TotalTax.set(totalTax);
    }

    public double getCommissionTotal() {
        return CommissionTotal.get();
    }

    public SimpleDoubleProperty commissionTotalProperty() {
        return CommissionTotal;
    }

    public void setCommissionTotal(double commissionTotal) {
        this.CommissionTotal.set(commissionTotal);
    }

    public int getCustomerId() {
        return CustomerId.get();
    }

    public SimpleIntegerProperty customerIdProperty() {
        return CustomerId;
    }

    public void setCustomerId(int customerId) {
        this.CustomerId.set(customerId);
    }

    public int getPackageId() {
        return PackageId.get();
    }

    public SimpleIntegerProperty packageIdProperty() {
        return PackageId;
    }

    public void setPackageId(int packageId) {
        this.PackageId.set(packageId);
    }
}
