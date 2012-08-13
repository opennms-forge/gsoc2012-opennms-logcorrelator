package org.opennms.logcorrelator.config;

import java.net.URL;
import java.util.logging.Level;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;
import org.opennms.logcorrelator.config.xml.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class Utils {
  private final static Logger logger = LoggerFactory.getLogger(Utils.class);

  public static final String CONFIG_PROPERTY = "opennms.logcorrelator.config";

  private static JAXBContext context;

  static {
    try {
      context = JAXBContext.newInstance(Configuration.class);

    } catch (JAXBException ex) {
      logger.error(null, ex);
    }
  }

  public static Configuration getConfig() {

    try {
      final URL url = new URL(System.getProperty(CONFIG_PROPERTY, "file:config.xml"));

      final StreamSource source = new StreamSource(url.openStream());

      final Unmarshaller unmarshaller = context.createUnmarshaller();

      final JAXBElement<Configuration> config = unmarshaller.unmarshal(source, Configuration.class);

      return config.getValue();

    } catch (final Exception ex) {
      java.util.logging.Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);

      throw new RuntimeException(ex);
    }
  }

}
