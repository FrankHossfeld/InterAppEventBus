package de.gishmo.gwt.interappmessagebus.client.jsni;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArrayString;
import com.google.gwt.core.client.Scheduler;
import de.gishmo.gwt.interappmessagebus.client.jsni.prototype.InterAppMessageHandler;
import elemental.events.MessageEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InterAppMessageBus {

  private static Map<String, List<InterAppMessageHandler>> handlersByType = new HashMap<>();

  public static void fireEvent(String frameName,
                               String eventType) {
    _fireEvent(frameName,
               eventType,
               null);
  }

  private static native void _fireEvent(String frameName,
                                        String eventType,
                                        JavaScriptObject data)/*-{
      // post message
      debugger;
      var window = $wnd;
      var ua = window.navigator.userAgent;
      var safari = ua.indexOf("Safari");
      var chrome = ua.indexOf("Chrome");
      if (safari > 0) {
          if (chrome > 0) {
              window.frames[frameName].contentWindow.postMessage(data, '*');
          } else {
              window.frames[frameName].postMessage(data, '*');
          }
      } else {
          window.frames[frameName].contentWindow.postMessage(data, '*');
      }
  }-*/;

  public static void fireEvent(String frameName,
                               String eventType,
                               JsArrayString data) {
    JsArrayString dataWithEventName = ((JsArrayString) JsArrayString.createArray(0));
    dataWithEventName.push(frameName);
    dataWithEventName.push(eventType);
    if (data != null) {
      for (int i = 0; i < data.length(); i++) {
        dataWithEventName.push(data.get(i));
      }
    }
    _fireEvent(frameName,
               eventType,
               dataWithEventName);
  }

  public static void addListener(InterAppMessageHandler handler) {
    String type = handler.getType();
    if (type == null) {
      return;
    }
    List<InterAppMessageHandler> list = handlersByType.get(type);
    if (list == null) {
      list = new ArrayList<>();
      handlersByType.put(type,
                         list);
    }
    list.add(handler);
    _addListener(type);
  }

  private static native void _addListener(String type) /*-{
      function postMessageListener(e) {
//          var curUrl = $wnd.location.protocol + "//" + $wnd.location.hostname;
//          if (e.origin !== curUrl) return; // security check to verify that we receive event from trusted source
          $entry(@de.gishmo.gwt.interappmessagebus.client.jsni.InterAppMessageBus::pickListener(Ljava/lang/String;Lcom/google/gwt/core/client/JavaScriptObject;)(type, e));
      }

//      // Listen to message from child window
//      if ($wnd.BrowserDetect.browser == "Explorer") {
//          // fucking IE
//          $wnd.attachEvent("onmessage", postMessageListener, false);
//      } else {
      // "Normal" browsers
      $wnd.addEventListener("message", postMessageListener, false);
//      }
  }-*/;

  private static void pickListener(String typeParameter,
                                   final JavaScriptObject event) {
    GWT.debugger();
    MessageEvent messageEvent = event.cast();
    JsArrayString stringArray = (JsArrayString) messageEvent.getData();
    String type = stringArray.get(1);
    if (typeParameter.equals(type)) {
      JsArrayString data = ((JsArrayString) JsArrayString.createArray(0));
      for (int i = 2; i < stringArray.length(); i++) {
        data.push(stringArray.get(i));
      }
      final List<InterAppMessageHandler> list = handlersByType.get(type);
      if (list != null) {
        Scheduler.get()
                 .scheduleDeferred(() -> {
                   for (InterAppMessageHandler handler : list) {
                     handler.onEvent(data);
                   }
                 });
      }
    }
  }

  public static boolean isSupported() {
    return _isSupported();
  }

  private static native boolean _isSupported()/*-{
      return typeof CustomEvent == 'function' || document.createEvent('CustomEvent') != null;
  }-*/;
}
