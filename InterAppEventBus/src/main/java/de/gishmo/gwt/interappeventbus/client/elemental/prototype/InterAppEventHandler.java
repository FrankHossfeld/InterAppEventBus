package de.gishmo.gwt.interappeventbus.client.elemental.prototype;

public interface InterAppEventHandler {

  String getType();

  void onEvent(String data);

}
