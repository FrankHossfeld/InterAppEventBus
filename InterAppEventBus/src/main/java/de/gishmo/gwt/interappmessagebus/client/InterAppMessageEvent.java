package de.gishmo.gwt.interappmessagebus.client;

import elemental.js.json.JsJsonObject;
import elemental.json.Json;
import elemental.json.JsonObject;

import java.util.ArrayList;
import java.util.List;

public class InterAppMessageEvent {

  private final static String KEY_SOURCE              = "source";
  private final static String KEY_TARGET              = "target";
  private final static String KEY_FRAME_NAME          = "frameName";
  private final static String KEY_EVENT_TYPE          = "eventType";
  private final static String KEY_NUMBER_OF_PAREMETER = "numberOfParameter";
  private final static String KEY_PARAMETER           = "parameters";

  private String       source;
  private String       target;
  private String       frameName;
  private String       eventType;
  private List<String> parameters;

  private InterAppMessageEvent() {
  }

  private InterAppMessageEvent(Builder builder) {
    assert builder.source != null : "source has no value!";
    assert builder.target != null : "source has no target!";
    assert builder.frameName != null : "frameName has no target!";
    assert builder.eventType != null : "eventType has no value!";

    this.source = builder.source;
    this.target = builder.target;
    this.frameName = builder.frameName;
    this.eventType = builder.eventType;
    this.parameters = builder.parameters;
  }

  public static InterAppMessageEvent parseJson(String jsonString) {
    JsonObject jsonObject = Json.instance()
                                .parse(jsonString);
    int parameterCount;
    try {
      parameterCount = Integer.parseInt(jsonObject.get(InterAppMessageEvent.KEY_NUMBER_OF_PAREMETER));
    } catch (NumberFormatException e) {
      parameterCount = 0;
    }
    InterAppMessageEvent.Builder builder = InterAppMessageEvent.builder()
                                                               .source(jsonObject.get(InterAppMessageEvent.KEY_SOURCE))
                                                               .target(jsonObject.get(InterAppMessageEvent.KEY_TARGET))
                                                               .frameName(jsonObject.get(InterAppMessageEvent.KEY_FRAME_NAME))
                                                               .eventType(jsonObject.get(InterAppMessageEvent.KEY_EVENT_TYPE));
    for (int i = 0; i < parameterCount; i++) {
      builder.add(jsonObject.get(InterAppMessageEvent.KEY_PARAMETER + Integer.toString(i)));
    }
    return builder.build();
  }

  public static Builder builder() {
    return new Builder();
  }

  public String getSource() {
    return source;
  }

  public String getTarget() {
    return target;
  }

  public String getFrameName() {
    return frameName;
  }

  public String getEventType() {
    return eventType;
  }

  public List<String> getParameters() {
    return parameters;
  }

  public String toJson() {
    JsonObject jsonObject = JsJsonObject.create();
    jsonObject.put(InterAppMessageEvent.KEY_SOURCE,
                   this.source);
    jsonObject.put(InterAppMessageEvent.KEY_TARGET,
                   this.target);
    jsonObject.put(InterAppMessageEvent.KEY_FRAME_NAME,
                   this.frameName);
    jsonObject.put(InterAppMessageEvent.KEY_EVENT_TYPE,
                   this.eventType);
    jsonObject.put(InterAppMessageEvent.KEY_NUMBER_OF_PAREMETER,
                   Integer.toString(parameters.size()));
    for (int i = 0; i < parameters.size(); i++) {
      jsonObject.put(InterAppMessageEvent.KEY_PARAMETER + Integer.toString(i),
                     parameters.get(i));
    }
    return jsonObject.toJson();
  }

  public static final class Builder {
    String source;
    String target;
    String frameName;
    String eventType;
    List<String> parameters = new ArrayList<>();

    public Builder source(String source) {
      this.source = source;
      return this;
    }

    public Builder target(String target) {
      this.target = target;
      return this;
    }

    public Builder frameName(String frameName) {
      this.frameName = frameName;
      return this;
    }

    public Builder eventType(String eventType) {
      this.eventType = eventType;
      return this;
    }

    public Builder add(String parameter) {
      parameters.add(parameter);
      return this;
    }

    public InterAppMessageEvent build() {
      return new InterAppMessageEvent(this);
    }
  }

}
