package net.spantree.examples.drools.petstore;

public class OrderItem {
    private Order order;
    private Product product;
    private Integer quantity;

    public OrderItem(Order order, Product product, Integer quantity) {
        this.order = order;
        this.product = product;
        this.quantity = quantity;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OrderItem orderItem = (OrderItem) o;

        if (order != null ? !order.equals(orderItem.order) : orderItem.order != null) return false;
        if (product != null ? !product.equals(orderItem.product) : orderItem.product != null) return false;
        return quantity != null ? quantity.equals(orderItem.quantity) : orderItem.quantity == null;

    }

    @Override
    public int hashCode() {
        int result = order != null ? order.hashCode() : 0;
        result = 31 * result + (product != null ? product.hashCode() : 0);
        result = 31 * result + (quantity != null ? quantity.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "OrderItem{" +
                "order.id=" + order.getId() +
                ", product=" + product +
                ", quantity=" + quantity +
                '}';
    }
}
