package de.gishmo.gwt.interappeventbus.client.jsni.base;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import de.gishmo.gwt.interappeventbus.client.jsni.prototype.InterAppEventHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Sam
 * 
 */
public class InterAppEventBus {

	private static Map<String, List<InterAppEventHandler>> handlersByType = new HashMap<String, List<InterAppEventHandler>>();

	public static void fireEvent(String eventType) {
		_fireEvent(eventType, null);
	}

	public static void fireEvent(String eventType, JavaScriptObject data) {
		_fireEvent(eventType, data);
	}

	private static native void _fireEvent(String eventType,
			JavaScriptObject data)/*-{
		var ua = window.navigator.userAgent;
		var msie = ua.indexOf("MSIE ");
		var event = null;
		if (msie > 0) {
			//since IE9 doesn't support constructor initialization
			event = document.createEvent('CustomEvent');
			event.initCustomEvent(eventType, false, false, data);
		} else {
			//DOM Level 4 Api as default
			event = new CustomEvent(eventType, {
				'detail' : data
			});
		}
		//$doc instead document, since doucment refer to iframe document
		// whereas $doc refer to parent document
//			$wnd.parent.document.dispatchEvent(event);
		$doc.dispatchEvent(event);
	}-*/;

	public static void addListener(InterAppEventHandler handler) {
		String type = handler.getType();
		if (type == null)
			return;
		List<InterAppEventHandler> list = handlersByType.get(type);
		if (list == null) {
			list = new ArrayList<>();
			handlersByType.put(type, list);
		}
		list.add(handler);
		_addListener(type);
	}

	private static native void _addListener(String type) /*-{
		//$doc instead document, since doucment refer to iframe document
		// whereas $doc refer to parent document
		$doc.addEventListener(
						type,
						function(e) {
							$entry(@de.gishmo.gwt.interappeventbus.client.jsni.base.InterAppEventBus::pickListener(Ljava/lang/String;Lcom/google/gwt/core/client/JavaScriptObject;)(type,e.detail));
						}, false);
	}-*/;

	private static void pickListener(String type, final JavaScriptObject data) {
		final List<InterAppEventHandler> list = handlersByType.get(type);
		if (list != null) {
			Scheduler.get().scheduleDeferred(new ScheduledCommand() {
				@Override
				public void execute() {
					for (InterAppEventHandler handler : list) {
						handler.onEvent(data);
					}
				}
			});
		}
	}

	public static boolean isSupported() {
		return _isSupported();
	}

	private static native boolean _isSupported()/*-{
		return typeof CustomEvent == 'function' || document.createEvent('CustomEvent')!=null;
	}-*/;
}