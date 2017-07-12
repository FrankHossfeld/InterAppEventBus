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

  @SuppressWarnings("unused")
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

  /**
   * Erzeugt aus einem Json-String eine Instanz eines InterAppMessageEvent
   *
   * @param jsonString JSON-String eines InterAppMessageEvents
   * @return Instanz des InterAppMesssageEventmit den Daten des JSON-Strings
   */
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

  /**
   * URL der Source (aktuell nur zur Info)
   *
   * @return URL der Source
   */
  public String getSource() {
    return source;
  }

  /**
   * URL des Targets (aktuell nur zur Info)
   *
   * @return URL des Targets
   */
  public String getTarget() {
    return target;
  }

  /**
   * Name des Frames in den die Message gepostet werden soll
   *
   * @return ame des Frames in den die Message gepostet werden soll
   */
  public String getFrameName() {
    return frameName;
  }

  /**
   * Name des Events (required)
   *
   * @return Name des Events
   */
  public String getEventType() {
    return eventType;
  }

  /**
   * Liste der Parameter (Liste of Strings)
   *
   * @return Liste mit Parametern
   */
  public List<String> getParameters() {
    return parameters;
  }

  /**
   * Wandelt den Event in einen Json-String um
   *
   * @return Json_string des Events
   */
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

    /**
     * URL der Source (aktuell nur zur Info)
     *
     * @param source URL der Source
     * @return InterAppMesssageBus Builder
     */
    public Builder source(String source) {
      this.source = source;
      return this;
    }

    /**
     * URL des Targets (aktuell nur zur Info)
     *
     * @param target URL des Targets
     * @return InterAppMesssageBus Builder
     */
    public Builder target(String target) {
      this.target = target;
      return this;
    }

    /**
     * Name des Frames in den die Message gepostet werden soll (required)
     *
     * @param frameName Name des Frames
     * @return InterAppMesssageBus Builder
     */
    public Builder frameName(String frameName) {
      this.frameName = frameName;
      return this;
    }

    /**
     * Name des Events (required)
     *
     * @param eventType Name des Events
     * @return InterAppMesssageBus Builder
     */
    public Builder eventType(String eventType) {
      this.eventType = eventType;
      return this;
    }

    /**
     * Fügt einen String als Parameter zum Event hinzu. Es können 0 - n Parameter vom Typ String mitgegeben werdem.
     *
     * @param parameter Paramete des Events
     * @return InterAppMesssageBus Builder
     */
    public Builder add(String parameter) {
      parameters.add(parameter);
      return this;
    }

    /**
     * Erzeugt den Event mit den übergebenen Daten
     *
     * @return Instanz des InterAppMesssageEvent
     */
    public InterAppMessageEvent build() {
      return new InterAppMessageEvent(this);
    }
  }

}
