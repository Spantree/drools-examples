#!/bin/bash

touch src/test/groovy/net/spantree/examples/drools/petstore/PetStoreSpec.groovy
./gradlew -Dtest.single=PetStoreSpec test