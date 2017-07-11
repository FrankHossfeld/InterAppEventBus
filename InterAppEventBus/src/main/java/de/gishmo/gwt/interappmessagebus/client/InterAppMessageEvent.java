package de.gishmo.gwt.interappmessagebus.client;

import elemental.js.json.JsJsonObject;
import elemental.json.Json;
import elemental.json.JsonObject;

import java.util.ArrayList;
import java.util.List;

public class IntaerAppMessageEvent {

  private final static String KEY_SOURCE              = "source";
  private final static String KEY_TARGET              = "target";
  private final static String KEY_EVENT_NAME          = "eventName";
  private final static String KEY_NUMBER_OF_PAREMETER = "numberOfParameter";
  private final static String KEY_PARAMETER           = "parameters";

  private String source;

  private String target;

  private String eventName;

  private List<String> parameters;

  private IntaerAppMessageEvent() {
  }

  public IntaerAppMessageEvent(String source,
                               String target,
                               String eventName) {
    this(source,
         target,
         eventName,
         new ArrayList<>());
  }

  public IntaerAppMessageEvent(String source,
                               String target,
                               String eventName,
                               List<String> parameter) {
    this.source = source;
    this.target = target;
    this.eventName = eventName;
    this.parameters = parameter;
  }

  public IntaerAppMessageEvent(String jsonString) {
    JsonObject jsonObject = Json.instance().parse(jsonString);

    // TODO
  }

  public String getSource() {
    return source;
  }

  public String getTarget() {
    return target;
  }

  public String getEventName() {
    return eventName;
  }

  public List<String> getParameters() {
    return parameters;
  }

  public String toJson() {
    JsonObject jsonObject = JsJsonObject.create();
    jsonObject.put(IntaerAppMessageEvent.KEY_SOURCE,
                   this.source);
    jsonObject.put(IntaerAppMessageEvent.KEY_TARGET,
                   this.target);
    jsonObject.put(IntaerAppMessageEvent.KEY_EVENT_NAME,
                   this.eventName);
    jsonObject.put(IntaerAppMessageEvent.KEY_NUMBER_OF_PAREMETER,
                   Integer.toString(parameters.size()));
    for (int i = 0; i < parameters.size(); i++) {
      jsonObject.put(IntaerAppMessageEvent.KEY_PARAMETER + Integer.toString(i),
                     parameters.get(i));
    }
    return jsonObject.toJson();
  }
}
