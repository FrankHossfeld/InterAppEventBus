package de.gishmo.gwt.interappeventbus.client.jsni.prototype;

import com.google.gwt.core.client.JavaScriptObject;

public interface InterAppEventHandler {

  String getType();

  void onEvent(JavaScriptObject data);

}