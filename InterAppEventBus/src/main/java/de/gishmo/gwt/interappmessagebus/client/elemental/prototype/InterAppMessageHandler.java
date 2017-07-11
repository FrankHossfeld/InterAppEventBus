package de.gishmo.gwt.interappmessagebus.client.elemental.prototype;

import de.gishmo.gwt.interappmessagebus.client.InterAppMessageEvent;

public interface InterAppMessageHandler {

  String getEventType();

  void onEvent(InterAppMessageEvent event);

}