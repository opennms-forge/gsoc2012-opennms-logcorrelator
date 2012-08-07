package org.opennms.logcorrelator.core;

import org.opennms.logcorrelator.config.Configuration;
import org.opennms.logcorrelator.config.CorrelatorConfiguration;
import org.opennms.logcorrelator.config.PreprocessorConfiguration;
import org.opennms.logcorrelator.config.ReceiverConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class Main {
  private static final Logger logger = LoggerFactory.getLogger(Main.class);

  public static void main(String[] args) throws Exception {

    Configuration config = new Configuration();

    ReceiverConfiguration udpSyslogReceiver = new ReceiverConfiguration();
    udpSyslogReceiver.setId("syslog_udp");
    udpSyslogReceiver.setType("org.opennms.logcorrelator.receivers.syslog.rfc5424.Rfc5424SyslogReceiverFactory");
    udpSyslogReceiver.setProperty("host", "127.0.0.1");
    udpSyslogReceiver.setProperty("port", "5140");
    udpSyslogReceiver.setProperty("protocoll", "udp");
    config.addReceiver(udpSyslogReceiver);

    ReceiverConfiguration tcpSyslogReceiver = new ReceiverConfiguration();
    tcpSyslogReceiver.setId("syslog_tcp");
    tcpSyslogReceiver.setType("org.opennms.logcorrelator.receivers.syslog.rfc5424.Rfc5424SyslogReceiverFactory");
    tcpSyslogReceiver.setProperty("host", "0.0.0.0");
    tcpSyslogReceiver.setProperty("port", "10514");
    tcpSyslogReceiver.setProperty("protocoll", "tcp");
    config.addReceiver(tcpSyslogReceiver);

    PreprocessorConfiguration dropByHostPreprocessor = new PreprocessorConfiguration();
    dropByHostPreprocessor.setId("drop_by_host");
    dropByHostPreprocessor.setType("org.opennms.logcorrelator.preprocessors.simple.DropTransmogrifierFactory");
    dropByHostPreprocessor.getFilters().setLanguage("org.opennms.logcorrelator.filters.mvel.MvelFilterFactory");
    dropByHostPreprocessor.getFilters().addFilter("host != \"localhost\"");
    config.addPreprocessor(dropByHostPreprocessor);
    
    PreprocessorConfiguration dropByBodyPreprocessor = new PreprocessorConfiguration();
    dropByBodyPreprocessor.setId("drop_by_body");
    dropByBodyPreprocessor.setType("org.opennms.logcorrelator.preprocessors.simple.DropTransmogrifierFactory");
    dropByBodyPreprocessor.getFilters().setLanguage("org.opennms.logcorrelator.filters.mvel.MvelFilterFactory");
    dropByBodyPreprocessor.getFilters().addFilter("body == \"xxx.*asdasd.*\"");
    config.addPreprocessor(dropByBodyPreprocessor);

    CorrelatorConfiguration correlator = new CorrelatorConfiguration();
//    correlator.setType("org.opennms.logcorrelator.correlators.nop.NopCorrelatorFactory");
    correlator.setType("org.opennms.logcorrelator.correlators.drools.DroolsCorrelatorFactory");
    config.setCorrelator(correlator);

    ProcessingContext context = ProcessingContext.create(config);
    context.init();
    context.start();
    
  }

}
