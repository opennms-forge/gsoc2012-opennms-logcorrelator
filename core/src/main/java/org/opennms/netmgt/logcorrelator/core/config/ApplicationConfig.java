package org.opennms.netmgt.logcorrelator.core.config;

import org.opennms.netmgt.logcorrelator.MessageProcessor;
import org.opennms.netmgt.logcorrelator.core.MessageProcessorImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfig {
	
	@Bean(name = "messageBroker")
	public MessageProcessor messageBroker() {
		final MessageProcessor messageBroker = new MessageProcessorImpl();
		
		return messageBroker;
	}
	
}
