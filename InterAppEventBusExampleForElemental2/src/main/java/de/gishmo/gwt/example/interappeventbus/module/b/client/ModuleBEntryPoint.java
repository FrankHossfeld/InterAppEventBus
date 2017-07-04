package de.gishmo.gwt.example.interappeventbus.module.b.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import de.gishmo.gwt.interappeventbus.client.elemental2.document.base.InterAppEventBus;
import de.gishmo.gwt.interappeventbus.client.elemental2.prototype.InterAppEventHandler;

public class ModuleBEntryPoint
  implements EntryPoint {

	private FlowPanel protocolContainer;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.google.gwt.core.client.EntryPoint#onModuleLoad()
	 */
	@Override
	public void onModuleLoad() {
		Resources resources = GWT.create(Resources.class);
		ApplicaitonStyle style = resources.style();
		style.ensureInjected();

		FlowPanel container = new FlowPanel();

		Label headline = new Label("Module B");
		headline.addStyleName(style.headline());
		container.add(headline);

		protocolContainer = new FlowPanel();
		protocolContainer.addStyleName(style.protocolContainer());
		container.add(protocolContainer);

		InterAppEventBus.addListener(new InterAppEventHandler() {
			@Override
			public void onEvent(String data) {
				logEvent(getType(),
								 data);
			}

			@Override
			public String getType() {
				return "showDocument";
			}
		});

    InterAppEventBus.addListener(new InterAppEventHandler() {
      @Override
      public void onEvent(String data) {
        logEvent(getType(),
								 data);
      }

      @Override
      public String getType() {
        return "editDocument";
      }
    });

    InterAppEventBus.addListener(new InterAppEventHandler() {
      @Override
      public void onEvent(String data) {
        logEvent(getType(),
								 data);
      }

      @Override
      public String getType() {
        return "removeDocument";
      }
    });

		RootPanel.get("moduleB").add(container);
	}

	private void logEvent(String type,
												String docId) {
		protocolContainer.add(new Label("capture Event: >>" + type + "<< for DocId: >>" + docId + "<<"));
	}


	public interface Resources
		extends ClientBundle {

		@Source("application.css")
		ApplicaitonStyle style();

	}

	public interface ApplicaitonStyle
		extends CssResource {

		String headline();

		String protocolContainer();

	}
}
