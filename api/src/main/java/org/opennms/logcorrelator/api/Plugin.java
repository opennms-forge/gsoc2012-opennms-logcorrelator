/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.opennms.logcorrelator.api;


/**
 *
 * @author Dustin Frisch <fooker@lab.sh>
 */
public interface Plugin {

  /**
   * Initialize the plugin.
   */
  public abstract void init();

  /**
   * Destroy the plugin.
   */
  public abstract void destroy();

  /**
   * Start the plugin.
   */
  public abstract void start();

  /**
   * Stop the plugin.
   */
  public abstract void stop();

}
