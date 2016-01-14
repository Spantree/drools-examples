package net.spantree.examples.drools.helloworld

import org.kie.api.KieServices
import org.kie.api.runtime.KieContainer
import org.kie.api.runtime.KieSession
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import spock.lang.Specification

class HelloWorldSpec extends Specification {
    String ksessionName = "HelloWorldKS"

    KieSession ksession
    Logger ruleLogger

    def setup() {
        def ks = KieServices.Factory.get()
        def kContainer = ks.getKieClasspathContainer()
        ksession = kContainer.newKieSession(ksessionName)

        ruleLogger = LoggerFactory.getLogger(ksessionName)
        ksession.setGlobal("log", ruleLogger)
    }

    def "should say goodbye"() {
        when:
        def message = new Message(message: "Hello world", status: MessageStatus.HELLO)
        ksession.insert(message)
        ksession.fireAllRules()

        then:
        message.message == "Goodbye cruel world"
        message.status == MessageStatus.GOODBYE
    }
}
