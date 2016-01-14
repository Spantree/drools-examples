package net.spantree.examples.drools.petstore;

import java.time.LocalDateTime;
import java.util.*;

public class Order {
    private String id;

    private LocalDateTime date = LocalDateTime.now();

    private String customerName;
    private String customerCity;
    private String customerState;

    private Double subtotal = 0D;
    private Double discountAmount = 0D;
    private Double taxableAmount = 0D;
    private Double stateSalesTax = 0D;
    private Double localSalesTax = 0D;

    private Double total =  0D;

    private Set<OrderItem> items = new HashSet<>();
    private Set<OrderDiscount> discounts = new HashSet<>();
    private Set<OrderNote> notes = new HashSet<>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public void setDate(String dateString) {
        setDate(LocalDateTime.parse(dateString));
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerCity() {
        return customerCity;
    }

    public void setCustomerCity(String customerCity) {
        this.customerCity = customerCity;
    }

    public String getCustomerState() {
        return customerState;
    }

    public void setCustomerState(String customerState) {
        this.customerState = customerState;
    }

    public Double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(Double subtotal) {
        this.subtotal = subtotal;
    }

    public Double getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(Double discountAmount) {
        this.discountAmount = discountAmount;
    }

    public Double getTaxableAmount() {
        return taxableAmount;
    }

    public void setTaxableAmount(Double taxableAmount) {
        this.taxableAmount = taxableAmount;
    }

    public Double getStateSalesTax() {
        return stateSalesTax;
    }

    public void setStateSalesTax(Double stateSalesTax) {
        this.stateSalesTax = stateSalesTax;
    }

    public Double getLocalSalesTax() {
        return localSalesTax;
    }

    public void setLocalSalesTax(Double localSalesTax) {
        this.localSalesTax = localSalesTax;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public Set<OrderItem> getItems() {
        return items;
    }

    public void addItem(OrderItem item) {
        items.add(item);
    }

    public Set<OrderDiscount> getDiscounts() {
        return discounts;
    }

    public void addDiscount(OrderDiscount discount) {
        discounts.add(discount);
    }

    public Set<OrderNote> getNotes() {
        return notes;
    }

    public void addNote(OrderNote note) {
        notes.add(note);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Order order = (Order) o;

        if (id != null ? !id.equals(order.id) : order.id != null) return false;
        if (customerName != null ? !customerName.equals(order.customerName) : order.customerName != null) return false;
        if (customerCity != null ? !customerCity.equals(order.customerCity) : order.customerCity != null) return false;
        if (customerState != null ? !customerState.equals(order.customerState) : order.customerState != null)
            return false;
        if (subtotal != null ? !subtotal.equals(order.subtotal) : order.subtotal != null) return false;
        if (stateSalesTax != null ? !stateSalesTax.equals(order.stateSalesTax) : order.stateSalesTax != null)
            return false;
        if (localSalesTax != null ? !localSalesTax.equals(order.localSalesTax) : order.localSalesTax != null)
            return false;
        if (total != null ? !total.equals(order.total) : order.total != null) return false;
        return notes != null ? notes.equals(order.notes) : order.notes == null;

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (customerName != null ? customerName.hashCode() : 0);
        result = 31 * result + (customerCity != null ? customerCity.hashCode() : 0);
        result = 31 * result + (customerState != null ? customerState.hashCode() : 0);
        result = 31 * result + (subtotal != null ? subtotal.hashCode() : 0);
        result = 31 * result + (stateSalesTax != null ? stateSalesTax.hashCode() : 0);
        result = 31 * result + (localSalesTax != null ? localSalesTax.hashCode() : 0);
        result = 31 * result + (total != null ? total.hashCode() : 0);
        result = 31 * result + (notes != null ? notes.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id='" + id + '\'' +
                ", date=" + date +
                ", customerName='" + customerName + '\'' +
                ", customerCity='" + customerCity + '\'' +
                ", customerState='" + customerState + '\'' +
                ", subtotal=" + subtotal +
                ", discountAmount=" + discountAmount +
                ", stateSalesTax=" + stateSalesTax +
                ", localSalesTax=" + localSalesTax +
                ", total=" + total +
                ", items=" + items +
                ", discounts=" + discounts +
                ", notes=" + notes +
                '}';
    }
}
