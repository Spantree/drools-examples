package net.spantree.examples.drools.petstore

import groovy.util.logging.Slf4j
import org.kie.api.KieServices
import org.kie.api.runtime.KieSession
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import spock.lang.Ignore
import spock.lang.IgnoreRest
import spock.lang.Specification

import java.text.DecimalFormat
import java.text.NumberFormat
import java.time.LocalDateTime

import static net.spantree.examples.drools.petstore.ProductType.*
import static net.spantree.examples.drools.petstore.FishType.*

@Slf4j
class PetStoreSpec extends Specification {
    String ksessionName = "PetStoreKS"

    KieSession ksession
    Logger ruleLogger

    Map<String, Product> products = [:]

    def setup() {
        def ks = KieServices.Factory.get()
        def kContainer = ks.getKieClasspathContainer()
        ksession = kContainer.newKieSession(ksessionName)

        ruleLogger = LoggerFactory.getLogger(ksessionName)
        ksession.setGlobal("log", ruleLogger)
        setupProducts()
    }

    Product addProduct(Product product) {
        def id = product.id
        if(products.containsKey(id)) {
            throw new IllegalArgumentException("${id} already exists in products")
        }
//        log.info "Adding product {}", product
        products.put(id, product)
        return product
    }

    Product addProduct(String id, ProductType type, String description, Double price) {
        def product = new Product(id: id, type: type, description: description, price: price)
        addProduct product
    }

    Fish addFish(String id, FishType fishType, String description, Double price) {
        def fish = new Fish(id: id, fishType: fishType, description: description, price: price)
        addProduct fish
    }

    OrderItem createOrderItem(Order order, String productId, Integer quantity) {
        def product = products.get(productId)
        if(product == null) {
            throw new IllegalArgumentException("Could not find product with id ${productId}")
        }
        new OrderItem(order: order, product: product, quantity: quantity)
    }

    def insertFacts(Collection facts) {
        facts.each { fact -> ksession.insert(fact) }
    }

    def insertFact(Object o) {
        ksession.insert(o)
    }

    void printOrder(Order order) {
        def dollarFormat = NumberFormat.getCurrencyInstance()
        println ""
        println "*"*80
        println "Order ${order.id}"
        println "Customer Name: ${order.customerName}"
        println "Customer City: ${order.customerCity}, ${order.customerState}"
        println "*"*80
        println ""

        print "Item".padRight(50)
        print "Type".padRight(14)
        print "Price".padRight(10)
        println "Quantity"
        println "-"*80

        order.items.each { item ->
            def product = item.product
            print product.description.padRight(50)
            print product.type.toString().padRight(14)
            print dollarFormat.format(product.price).padRight(10)
            println item.quantity
        }

        println "-"*80
        print "Subtotal".padRight(64)
        println dollarFormat.format(order.subtotal)
        println ""

        println "Notes"
        println "-"*80

        order.notes.each { note ->
            println note.message
        }
        println ""

        print "Discount".padRight(64)
        println "Amount"
        println "-"*80

        order.discounts.each { discount ->
            print discount.reason.padRight(64)
            println dollarFormat.format(discount.amount)
        }
        println "-"*80
        print "Total Discount".padRight(64)
        println dollarFormat.format(order.discountAmount)
        println ""

        println "*"*80

        print "Taxable Amount".padRight(64)
        println dollarFormat.format(order.taxableAmount)

        print "State Sales Tax".padRight(64)
        println dollarFormat.format(order.stateSalesTax)

        print "Local Sales Tax".padRight(64)
        println "${dollarFormat.format(order.localSalesTax)}"

        println "*"*80

        print "Total Balance".padRight(64)
        print dollarFormat.format(order.total)
    }

    def setupProducts() {
        // Dogs
        addProduct "bulldog",        DOG,        "Bulldog",                                 1500.00
        addProduct "poodle",         DOG,        "Poodle",                                  1200.00
        addProduct "dalmation",      DOG,        "Dalmation",                               1000.00
        addProduct "german_shep",    DOG,        "German Shephard",                         500.00

        // Dog Food
        addProduct "buff_lamb_rice", DOG_FOOD,   "Blue Buffalo Lamb & Brown Rice",          44.09
        addProduct "puri_chic_rice", DOG_FOOD,   "Purina Chicken and Rice",                 35.99

        // Cats
        addProduct "manx",           CAT,        "Manx Cat",                                250.00
        addProduct "persian",        CAT,        "Persian Cat",                             800.00
        addProduct "orange_tabby",   CAT,        "Orange Tabby Cat",                        200.00

        // Cat Food
        addProduct "meow_chic_salm", CAT_FOOD,   "Meow Mix Chicken & Salmon",               11.88
        addProduct "fanc_poul_beef", CAT_FOOD,   "Fancy Feast Poultry & Beef Variety Pack", 11.99

        // Fish
        addFish    "angelfish",      FRESHWATER, "Angelfish",                              6.00
        addFish    "koi",            FRESHWATER, "Koi",                                    9.00
        addFish    "goldfish",       FRESHWATER, "Goldfish",                               5.00
        addFish    "clownfish",      SALTWATER,  "Clown Fish",                             16.00

        // Fish food
        addProduct "bloodworms",     FISH_FOOD,  "Tetra Bloodworms",                       3.18
        addProduct "flakes",         FISH_FOOD,  "TetraFin Flakes",                        8.97

        // Fish tanks
        addProduct "cubeaqua",       FISH_TANK,  "Tetra Cube Aquarium",                    38.24
        addProduct "ledaqua",        FISH_TANK,  "GloFish LED Aquarium",                   51.98
    }

    def "should add products to knowledge session"() {
        when: "we add all the products to the knowledge session"
        insertFacts products.values()

        then: "those products should exist in working memory"
        ksession.factCount == products.size()
    }

    def "should recommend buying cat food"() {
        setup:
        def order = new Order(id: "123", customerName: "Jim Davis")
        def cat = new OrderItem(order, products['orange_tabby'], 1)

        when: "we insert products"
        insertFacts products.values()

        and: "we place an order with a cat (but no food)"
        insertFact order
        insertFact cat

        and: "we fire all rules"
        ksession.fireAllRules()

        then: "we get a note reminding us to buy cat food"
        order.notes.contains(OrderNote.BUY_CAT_FOOD)
    }

    def "should give away free food with purchase of new dog"() {
        setup:
        def order = new Order(id: "234", customerName: "Lee Duncan")
        def dog = new OrderItem(order, products['german_shep'], 1)

        def expectedFood = products['buff_lamb_rice']

        when: "we insert products"
        insertFacts products.values()

        and: "we place an order with a dog (but no food)"
        insertFact order
        insertFact dog

        and: "we fire all rules"
        ksession.fireAllRules()

        then: "a sample of dog food should be automatically added to our cart"
        order.items.find { item ->
            item.product == expectedFood
        }

        and: "a 100% discount should be applied to the food"
        order.discounts.size() == 1
        order.discounts.find { discount ->
            discount.percentage == 1.0D && discount.items*.product.contains(expectedFood)
        }
        order.discountAmount == expectedFood.price
    }

    def "should warn if order contains both freshwater and saltwater fish"() {
        setup:
        def order = new Order(id: "345", customerName: "King Triton")
        def items = [
            new OrderItem(order, products['angelfish'], 1),
            new OrderItem(order, products['clownfish'], 1)
        ]

        when: "we insert products"
        insertFacts products.values()

        and: "we place an order with saltwater and freshwater fish"
        insertFact order
        insertFacts items

        and: "we fire all rules"
        ksession.fireAllRules()

        then:
        order.notes.contains(OrderNote.INCOMPATIBLE_FISH)
    }

    def "should discount tank to half price with purchase of ten fish or more"() {
        setup:
        def order = new Order(id: "456", customerName: "Ursula")
        def tank = new OrderItem(order, products['cubeaqua'], 1)
        def fish = [
            new OrderItem(order, products['angelfish'], 5),
            new OrderItem(order, products['goldfish'], 5)
        ]

        when: "we insert products"
        insertFacts products.values()

        and: "we place an order with 11 fish and a tank"
        insertFact order
        insertFacts fish
        insertFact tank

        and: "we fire all rules"
        ksession.fireAllRules()

        then:
        order.discounts.findAll { d ->
            d.items*.product.contains(products['cubeaqua']) &&
            d.reason == "buy ten fish get a tank half off"
            d.percentage = 0.5D
        }.size() == 1
    }

    def "should apply sales tax properly"() {
        setup:
        def order = new Order(
            id: "567",
            customerName: "Cedric Hurst",
            customerCity: "Chicago",
            customerState: "IL",
            date: "2016-01-14T08:09:00"
        )
        def fish = [
            new OrderItem(order, products['angelfish'], 2),
            new OrderItem(order, products['goldfish'], 1)
        ]
        def ilTax = new StateTaxRate("IL", 0.0625D)
        def chicagoOldTax = new LocalTaxRate("IL", "Chicago", 0.03D)
        def chicagoNewTax = new LocalTaxRate("IL", "Chicago", 0.04D, LocalDateTime.parse("2016-01-01T00:00:00"))

        when: "we insert products"
        insertFacts products.values()

        and: "we place an order with 3 fish (which should trigger a discount)"
        insertFact order
        insertFacts fish

        and: "we add state and local tax rates"
        insertFacts([ilTax, chicagoOldTax, chicagoNewTax])

        and: "we fire all rules"
        ksession.fireAllRules()

        then:
        order.stateSalesTax == (order.subtotal - order.discountAmount) * ilTax.rate
        order.localSalesTax == (order.subtotal - order.discountAmount) * chicagoNewTax.rate
        order.total == (order.subtotal - order.discountAmount + order.stateSalesTax + order.localSalesTax)
    }

    @IgnoreRest
    def "should do demo order"() {
        setup:
        log.info "Adding ${products.size()} products"
        insertFacts products.values()

        def taxes = [
            new StateTaxRate("IL", 0.0625D),
            new LocalTaxRate("IL", "Chicago", 0.03D),
            new LocalTaxRate("IL", "Chicago", 0.04D, LocalDateTime.parse("2016-01-01T00:00:00"))
        ]
        log.info "Adding ${taxes.size()} tax rates"
        insertFacts taxes

        when:
        def order = new Order(
            id: "567",
            customerName: "Cedric Hurst",
            customerCity: "Chicago",
            customerState: "IL",
            date: "2016-01-14T08:09:00"
        )

        log.info "Placing order ${order.id} on ${order.date} for ${order.customerName} in ${order.customerCity}, ${order.customerState}"
        insertFact order

        and:
        def dog = new OrderItem(order, products['poodle'], 1)
        def fish = [
            new OrderItem(order, products['clownfish'], 2),
            new OrderItem(order, products['goldfish'], 10)
        ]
        def tank = new OrderItem(order, products['cubeaqua'], 1)
        def items = [dog, fish, tank].flatten()

        log.info "...Adding items..."
        items.each { item ->
            log.info ".. ${item}"
            Thread.sleep(100)
        }
        insertFacts items

        Thread.sleep(2000)

        and:
        log.info "Firing all rules"
        ksession.fireAllRules()

        Thread.sleep(2000)

        then:
        printOrder order
    }
}

