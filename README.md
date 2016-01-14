## Spantree Drools Examples

### Hello World Example

A simple project that changes a message from hello to goodbye using Drools (simplified version of
the [canonical Drools example](https://github.com/droolsjbpm/drools/blob/master/drools-examples/src/main/resources/org/drools/examples/helloworld/HelloWorld.drl))

### Pet Store Example

A reimagined Java Pet Store, using Drools to calculate things like discounts, warnings, and sales tax. Refactored and expanded based on use cases defined in [canonical Drools example](https://github.com/droolsjbpm/drools/blob/master/drools-examples/src/main/resources/org/drools/examples/petstore/PetStore.drl).

#### Rules Implemented

##### Notes

* When purchasing freshwater and saltwater fish, warn customer about housing in separate tanks.
* When purchasing new cat, remind customer to purchase cat food.

#### Promotions & Discounts

* When purchasing new dog and no dog food purchased, add a free sample of dog food to order.
* When purchasing 3 or more fish, get third fish for free.
* When purchasing 10 or more fish, get free aquarium

#### Sales Tax

* If customer is in Illinois, apply a 6.25% state sales tax.
* If customer is in Chicago and order is before January 1, 2016, apply an additional 3% local sales tax.
* If customer is in Chicago and order is after January 1, 2016, apply an additional 4% local sales tax.

#### Demo

The below demo shows the pet store Rules engine in action. Chicago-based customer buys the following on January 14, 2016:

* 10 Goldfish
* 2 Clownfish
* 1 Poodle
* 1 Cube Aquarium

After adding the order to the rules engine, the following information is applied to the order:

* Add a free sample bag of Blue Buffalo Lamb & Brown Rice to order and provide a 100% discount.
* Add a note about housing freshwater and saltwater fish in separate tanks.
* Apply a 50% discount on Cube Aquarium.
* Apply a 100% discount to the least expensive fish purchased.
* Apply a state sales tax of 6.25%.
* Apply a local sales tax (at the new higher Chicago rate) of 4%.

[![asciicast](https://asciinema.org/a/6xwqsee18ls0kj1lxh5bkpv2t.png)](https://asciinema.org/a/6xwqsee18ls0kj1lxh5bkpv2t)
