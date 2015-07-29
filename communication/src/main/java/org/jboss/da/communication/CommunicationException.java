package org.jboss.da.communication;

/**
 *
 * @author Honza Brázdil <jbrazdil@redhat.com>
 */
public class CommunicationException extends Exception {

    public CommunicationException(String message) {
        super(message);
    }

    public CommunicationException(String message, Throwable cause) {
        super(message, cause);
    }

    public CommunicationException(Throwable cause) {
        super(cause);
    }

}
