package org.wso2.carbon.test;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.synapse.AbstractSynapseHandler;
import org.apache.synapse.MessageContext;
import org.apache.synapse.core.axis2.Axis2MessageContext;

public class CustomAPIAuthenticationHandler extends AbstractSynapseHandler {

    private static final Log log = LogFactory.getLog(CustomAPIAuthenticationHandler.class);

    @Override
    public boolean handleRequestInFlow(MessageContext synCtx) {
        log.info("Request In Flow");
        return true;
    }

    @Override
    public boolean handleRequestOutFlow(MessageContext synCtx) {
        log.info("Response Out Flow");

        Map<String, Object> headers= (Map) ((Axis2MessageContext) synCtx).getAxis2MessageContext().getProperty(org.apache.axis2.context.MessageContext.TRANSPORT_HEADERS);

        String authorization=(String) headers.get("Authorization");
        String newAuthorization= authorization.substring(7);
        
        headers.put("Authorization", newAuthorization);
        synCtx.setProperty(org.apache.axis2.context.MessageContext.TRANSPORT_HEADERS, headers);

        log.info("authorization new Pablo:"+newAuthorization);
        return true;
    }

    @Override
    public boolean handleResponseInFlow(MessageContext synCtx) {
        log.info("Response In Flow");
        return true;
    }

    @Override
    public boolean handleResponseOutFlow(MessageContext synCtx) {
        log.info("Response Out Flow");
        return true;
    }
}
