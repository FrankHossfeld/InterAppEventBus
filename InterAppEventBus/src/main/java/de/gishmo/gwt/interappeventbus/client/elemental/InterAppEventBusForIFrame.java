package de.gishmo.gwt.interappeventbus.client.elemental;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
import de.gishmo.gwt.interappeventbus.client.GUID;
import de.gishmo.gwt.interappeventbus.client.elemental2.prototype.InterAppEventHandler;
import elemental.client.Browser;
import elemental.dom.Element;
import elemental.dom.NodeList;
import elemental.html.IFrameElement;
import elemental.html.Storage;
import elemental.html.Window;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InterAppEventBusForIFrame {

  private static Map<String, List<InterAppEventHandler>> handlersByType = new HashMap<>();

  public static void fireEvent(String eventType,
                               String targetOrignOrTransfer,
                               String iFrameName) {
    fireEvent(eventType,
              targetOrignOrTransfer,
              null,
              iFrameName);
  }

  public static void fireEvent(String eventType,
                               String targetOrignOrTransfer,
                               Object data,
                               String iFrameName) {
    GWT.debugger();
    Element el = Browser.getDocument()
                        .querySelector(iFrameName);

    if (el != null) {
      IFrameElement frameElement  = (IFrameElement) el;
      Window        contentWindow = frameElement.getContentWindow();
      if (contentWindow != null) {
        Storage storage    = Browser.getWindow()
                                    .getLocalStorage();
        String  dataString = (data == null) ? "" : data.toString();
        String  uuid       = GUID.get();
        storage.setItem(uuid,
                        dataString);

//        contentWindow.postMessage();

      }

    }
    NodeList nodeList = Browser.getWindow()
                               .getDocument()
                               .getElementsByTagName("iFrame");
//    Element  element;
//    for (Node node : nodeList) {
////      if (node.getNodeType())
//    }

    //    Window window = DomGlobal.window.top;
    //ra
    //    Storage storage = WebStorageWindow.of(window).localStorage;
    //    String dataString = (data == null) ? "" : data.toString();
    //    String uuid = GUID.get();
    //    storage.setItem(uuid,
    //                    dataString);
    //
    //    String ua = window.navigator.userAgent;
    //    int msie = ua.indexOf("MSIE ");
    //    if (msie > 0) {
    //      window.alert("InterAppEventBus not yet implemented for Ineternet Explorer ... sorry");
    //    } else {
    //      GWT.debugger();
    //      CustomEvent customEvent = new CustomEvent(eventType);
    //      // TODO CustomEvent rauswerfen und durch String ersetzen
    //      customEvent.initCustomEvent(eventType,
    //                                  true,
    //                                  true,
    //                                  uuid);
    //      Array<String> transferable = new Array<>();
    //      transferable.push(eventType);
    //      transferable.push(uuid);
    //
    //
    //
    //      DomGlobal.postMessage(transferable, "*");
    // //     DomGlobal.postMessage(customEvent, targetOrignOrTransfer);
    //    }
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
    GWT.debugger();
    //    DomGlobal.window.addEventListener("message",
    //                              (e) -> {
    //      GWT.debugger();
    //                                CustomEvent customEvent = (CustomEvent) e;
    ////                                Storage storage = WebStorageWindow.of(window).sessionStorage;
    ////                                String key = (String) customEvent.detail;
    ////                                String data = storage.getItem(key);
    ////                                storage.removeItem(key);
    ////                                pickListener(eventType,
    ////                                             data);
    //                              });

    //    Document document = DomGlobal.document;
    //    document.addEventListener(eventType,
    //                              (e) -> {
    //                                CustomEvent customEvent = (CustomEvent) e;
    //                                Window window = DomGlobal.window.top;
    //                                Storage storage = WebStorageWindow.of(window).sessionStorage;
    //                                String key = (String) customEvent.detail;
    //                                String data = storage.getItem(key);
    //                                storage.removeItem(key);
    //                                pickListener(eventType,
    //                                             data);
    //                              });
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
//    Window window = DomGlobal.window.top;
//    String ua     = window.navigator.userAgent;
//    if (!ua.contains("MSIE ")) {
//      return true;
//    } else {
//      if (ua.contains("MSIE 10.0")) {
//        return true;
//      } else if (ua.contains("MSIE 9.0")) {
//        return true;
//      }
//    }
    return false;
  }
}
