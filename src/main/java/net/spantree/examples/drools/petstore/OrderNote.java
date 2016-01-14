package net.spantree.examples.drools.petstore;

public enum OrderNote {
    BUY_CAT_FOOD("Don't forget to buy cat food for your new cat."),
    BUY_DOG_FOOD("Don't forget to buy dog food for your new dog."),
    INCOMPATIBLE_FISH("Order contains both saltwater and freshwater fish, house in separate tanks.");

    private String message;

    private OrderNote(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
