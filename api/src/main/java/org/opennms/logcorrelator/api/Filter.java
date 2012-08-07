package org.opennms.logcorrelator.api;


public interface Filter {

  public abstract boolean match(final Message message);

}
