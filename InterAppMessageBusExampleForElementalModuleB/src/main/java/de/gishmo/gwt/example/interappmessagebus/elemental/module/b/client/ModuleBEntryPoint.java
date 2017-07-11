package de.gishmo.gwt.example.interappmessagebus.elemental.module.b.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import de.gishmo.gwt.interappmessagebus.client.InterAppMessageEvent;
import de.gishmo.gwt.interappmessagebus.client.elemental.InterAppMessageBus;
import de.gishmo.gwt.interappmessagebus.client.elemental.prototype.InterAppMessageHandler;

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

    InterAppMessageBus.addListener(new InterAppMessageHandler() {
      @Override
      public String getEventType() {
        return "showDocument";
      }

      @Override
      public void onEvent(InterAppMessageEvent event) {
        logEvent(event.getEventType(),
                 event.getParameters()
                      .get(0));
      }
    });

    InterAppMessageBus.addListener(new InterAppMessageHandler() {
      @Override
      public String getEventType() {
        return "editDocument";
      }

      @Override
      public void onEvent(InterAppMessageEvent event) {
        logEvent(event.getEventType(),
                 event.getParameters()
                      .get(0));
      }
    });

    InterAppMessageBus.addListener(new InterAppMessageHandler() {
      @Override
      public String getEventType() {
        return "removeDocument";
      }

      @Override
      public void onEvent(InterAppMessageEvent event) {
        logEvent(event.getEventType(),
                 event.getParameters()
                      .get(0));
      }
    });

    RootPanel.get()
             .add(container);
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
