package de.gishmo.gwt.interappeventbus.client.elemental2;

import com.google.gwt.core.client.Scheduler;
import de.gishmo.gwt.interappeventbus.client.GUID;
import de.gishmo.gwt.interappeventbus.client.elemental2.prototype.InterAppEventHandler;
import elemental2.dom.CustomEvent;
import elemental2.dom.Document;
import elemental2.dom.DomGlobal;
import elemental2.dom.Window;
import elemental2.webstorage.Storage;
import elemental2.webstorage.WebStorageWindow;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InterAppEventBus {

  private static Map<String, List<InterAppEventHandler>> handlersByType = new HashMap<>();

  public static void fireEvent(String eventType) {
    fireEvent(eventType,
              null);
  }

  public static void fireEvent(String eventType,
                               Object data) {

        Window   window   = DomGlobal.window;

        Storage storage    = WebStorageWindow.of(window).sessionStorage;
        String  dataString = (data == null) ? "" : data.toString();
        String  uuid       = GUID.get();
        storage.setItem(uuid,
                        dataString);

        String ua = window.navigator.userAgent;
        int msie = ua.indexOf("MSIE ");
        if (msie > 0) {
          window.alert("InterAppEventBus not yet implemented for Ineternet Explorer ... sorry");
        } else {
          CustomEvent customEvent = new CustomEvent(eventType);
          customEvent.initCustomEvent(eventType,
                                      true,
                                      true,
                                      uuid);
          window.dispatchEvent(customEvent);
        }
  }

  public static void addListener(InterAppEventHandler handler) {
    String type = handler.getType();
    if (type == null) {
      return;
    }
    List<InterAppEventHandler> list = handlersByType.get(type);
    if (list == null) {
      list = new ArrayList<>();
      handlersByType.put(type,
                         list);
    }
    list.add(handler);
    _addListener(type);
  }

  private static void _addListener(String eventType) {
    Document document = DomGlobal.document;
    DomGlobal.window.addEventListener(eventType,
                                      (e) -> {
                                        CustomEvent customEvent = (CustomEvent) e;
                                        Window      window      = DomGlobal.window.top;
                                        Storage     storage     = WebStorageWindow.of(window).sessionStorage;
                                        String      key         = (String) customEvent.detail;
                                        String      data        = storage.getItem(key);
                                        storage.removeItem(key);
                                        pickListener(eventType,
                                                     data);
                                      });
  }

  private static void pickListener(String type,
                                   String data) {
    final List<InterAppEventHandler> list = handlersByType.get(type);
    if (list != null) {
      Scheduler.get()
               .scheduleDeferred(() -> {
                 for (InterAppEventHandler handler : list) {
                   handler.onEvent(data);
                 }
               });
    }
  }

  public static boolean isSupported() {
    Window window = DomGlobal.window.top;
    String ua     = window.navigator.userAgent;
    if (!ua.contains("MSIE ")) {
      return true;
    } else {
      if (ua.contains("MSIE 10.0")) {
        return true;
      } else if (ua.contains("MSIE 9.0")) {
        return true;
      }
    }
    return false;
  }
}
