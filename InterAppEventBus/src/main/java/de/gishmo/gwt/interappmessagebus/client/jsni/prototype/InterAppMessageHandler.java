package de.gishmo.gwt.interappmessagebus.client.jsni.prototype;

import com.google.gwt.core.client.JsArrayString;

public interface InterAppMessageHandler {

  String getType();

  void onEvent(JsArrayString data);

}