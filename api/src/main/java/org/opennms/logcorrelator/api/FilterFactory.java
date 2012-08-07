package org.opennms.logcorrelator.api;


/**
 * Factory for a {@link Filter}.
 *
 * @author Dustin Frisch <fooker@lab.sh>
 */
public interface FilterFactory {
  /**
   * Creates a filter.
   *
   * @param expression the textual expression of the filter
   *
   * @return the filter
   */
  public abstract Filter create(final String expression);

}
