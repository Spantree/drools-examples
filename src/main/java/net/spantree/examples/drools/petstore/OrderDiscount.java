package net.spantree.examples.drools.petstore;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class OrderDiscount {
    private Order order;
    private Set<OrderItem> items = new HashSet<>();
    private Double percentage;
    private Double amount;
    private String reason;

    public OrderDiscount(Order order, Collection<OrderItem> items, Double percentage, String reason) {
        this.order = order;
        addItems(items);
        this.percentage = percentage;
        this.reason = reason;
    }

    public OrderDiscount(Order order, OrderItem item, Double percentage, String reason) {
        this(order, Collections.singleton(item), percentage, reason);
    }

    public Order getOrder() {
        return order;
    }

    public Set<OrderItem> getItems() {
        return items;
    }

    public void addItems(Collection<OrderItem> items) {
        this.items.addAll(items);
    }

    public void addItem(OrderItem item) {
        this.items.add(item);
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Double getPercentage() {
        return percentage;
    }

    public void setPercentage(Double percentage) {
        this.percentage = percentage;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OrderDiscount that = (OrderDiscount) o;

        if (order != null ? !order.getId().equals(that.order.getId()) : that.order != null) return false;
        return reason != null ? reason.equals(that.reason) : that.reason == null;
    }

    @Override
    public int hashCode() {
        int result = order != null ? order.getId().hashCode() : 0;
        result = 31 * result + (reason != null ? reason.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "OrderDiscount{" +
                "order.id=" + order.getId() +
                ", items=" + items +
                ", percentage=" + percentage +
                ", amount=" + amount +
                ", reason='" + reason + '\'' +
                '}';
    }
}
