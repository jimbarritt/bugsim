/*
 * Copyright 2003 Systems Union Holdings Ltd. All rights reserved.
 * Systems Union Proprietary / Confidential. Use is subject to license terms.
 */
package com.ixcode.framework.web.action;

/**
 * Represents a single event - can have a message key associated with it to provide
 * and i18n'd message
 *
 * @author Jim Barritt
 * @version $Revision: 1.3 $
 *          $Id: ActionEvent.java,v 1.3 2004/08/31 06:42:30 rdjbarri Exp $
 */
public class ActionEvent {

    public ActionEvent(String eventId, String messageKey) {
        _eventId = eventId;
        _messageKey = messageKey;
    }

    public String getEventId() {
        return _eventId;
    }



    public String getMessageKey() {
        return _messageKey;
    }


    public String toString() {
        return _eventId;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ActionEvent)) return false;

        final ActionEvent event = (ActionEvent)o;

        if (!_eventId.equals(event._eventId)) return false;

        return true;
    }

    public int hashCode() {
        return _eventId.hashCode();
    }

    private String _eventId;
    private String _messageKey;

}
