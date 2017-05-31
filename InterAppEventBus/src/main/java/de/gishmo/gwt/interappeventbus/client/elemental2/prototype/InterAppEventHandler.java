package de.gishmo.gwt.interappeventbus.client.elemental2.prototype;

public interface InterAppEventHandler {

  String getType();

  void onEvent(String data);

}