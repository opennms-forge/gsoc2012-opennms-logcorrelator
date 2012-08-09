package org.opennms.logcorrelator.preprocessors.opennms;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.filter.HTTPBasicAuthFilter;
import org.opennms.logcorrelator.api.Message;
import org.opennms.logcorrelator.api.MessageAccessor;
import org.opennms.logcorrelator.api.MessageDeclarator;
import org.opennms.logcorrelator.api.Transmogrifier;
import org.opennms.netmgt.model.OnmsNode;
import org.opennms.netmgt.model.OnmsNodeList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class NodeAssignmentTransmogrifier implements Transmogrifier {
  private final Logger logger = LoggerFactory.getLogger(NodeAssignmentTransmogrifier.class);

  private String baseUrl;

  private String username;

  private String password;

  private String nodeCriteriaName;

  private String messageValueName;

  private Client client;

  private WebResource restResource;

  private WebResource nodesResource;

  public MessageAccessor<Object> valueAccessor;

  public MessageAccessor<OnmsNode> nodeAccessor;

  public NodeAssignmentTransmogrifier(final String baseUrl,
                                      final String username,
                                      final String password,
                                      final String nodeCriteriaName,
                                      final String messageValueName) {
    this.baseUrl = baseUrl;

    this.username = username;
    this.password = password;

    this.nodeCriteriaName = nodeCriteriaName;
    this.messageValueName = messageValueName;
  }

  @Override
  public void transmogrify(final Transmogrifier.Context context,
                           final Message message) {

    // Get value to query for
    final Object value = message.get(this.valueAccessor);

    // Query for node definition with the value
    final OnmsNodeList nodes = this.nodesResource.queryParam(this.nodeCriteriaName,
                                                             value.toString()).get(OnmsNodeList.class);

    if (nodes.getCount() == 0) {
      // If there is no node matching to the message, the message will be
      // forwarded unchanged
      context.pass(message);

    } else if (nodes.getCount() == 1) {
      // If the message has an exact match to a node, attach the node to the
      // message and forward the message
      message.set(this.nodeAccessor, nodes.get(0));
      context.pass(message);

    } else {

      // If the message matches to multiple nodes, duplicate the message for
      // each node found, attach the node information to the copy and pass it
      // back to the pipeline
      for (OnmsNode node : nodes.getNodes()) {
        final Message copy = context.copyMessage(message);
        copy.set(this.nodeAccessor, node);

        context.pass(copy);
      }
    }
  }

  @Override
  public void registerMessageDeclaration(final MessageDeclarator declarator) {
    this.valueAccessor = declarator.registerField(this.messageValueName, Object.class);
    this.nodeAccessor = declarator.registerField("node", OnmsNode.class);
  }

  @Override
  public void init() {
    this.client = Client.create();
    this.client.setFollowRedirects(true);

    this.restResource = this.client.resource(this.baseUrl).path("rest");
    this.restResource.addFilter(new HTTPBasicAuthFilter(this.username,
                                                        this.password));

    this.nodesResource = this.restResource.path("nodes");
  }

  @Override
  public void destroy() {
    this.client.destroy();
  }

  @Override
  public void start() {
  }

  @Override
  public void stop() {
  }

}