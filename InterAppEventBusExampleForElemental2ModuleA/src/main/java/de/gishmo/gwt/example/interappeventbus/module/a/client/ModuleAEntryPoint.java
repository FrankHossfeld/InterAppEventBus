package de.gishmo.gwt.example.interappeventbus.module.a.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JsArrayString;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.*;
import de.gishmo.gwt.interappeventbus.client.elemental2.base.InterAppEventBus;

public class ModuleAEntryPoint
  implements EntryPoint {

  private FlowPanel protocolContainer;
	private TextBox tbDocId;

	@Override
	public void onModuleLoad() {
		if (!InterAppEventBus.isSupported()) {
			Window.alert("Sorry, Custom event is not supported in this browser.");
		}

    Resources resources = GWT.create(Resources.class);
    ApplicaitonStyle style = resources.style();
    style.ensureInjected();

    FlowPanel container = new FlowPanel();

    Label headline = new Label("Module A");
    headline.addStyleName(style.headline());
    container.add(headline);

    FlowPanel formularContianer = new FlowPanel();
    formularContianer.addStyleName(style.formularContainer());
    container.add(formularContianer);

    VerticalPanel vp = new VerticalPanel();
    formularContianer.add(vp);

    HorizontalPanel hp01 = new HorizontalPanel();
    vp.add(hp01);
    Label label = new Label("DocId:");
    label.addStyleName(style.label());
    hp01.add(label);

    tbDocId = new TextBox();
    tbDocId.addStyleName(style.textBox());
    hp01.add(tbDocId);

    HorizontalPanel hp02 = new HorizontalPanel();
    vp.add(hp02);

    Button showDocIdbutton = new Button("Send DocID");
    showDocIdbutton.addStyleName(style.formularButton());
		showDocIdbutton.addClickHandler(event -> {
      JsArrayString data = ((JsArrayString) JsArrayString
                                              .createArray(0));
      data.push("DocId: " + tbDocId.getText());
      InterAppEventBus.fireEvent("showDocument", data);
      protocolContainer.add(new Label("Fire Event: >>showDocument<< for DocId: >>" + tbDocId.getText() + "<<"));
    });
    hp02.add(showDocIdbutton);

    Button editDocIdbutton = new Button("Edit DocID");
    editDocIdbutton.addStyleName(style.formularButton());
    editDocIdbutton.addClickHandler(event -> {
      JsArrayString data = ((JsArrayString) JsArrayString
                                              .createArray(0));
      data.push("DocId: " + tbDocId.getText());
      InterAppEventBus.fireEvent("editDocument", data);
      protocolContainer.add(new Label("Fire Event: >>editDocument<< for DocId: >>" + tbDocId.getText() + "<<"));
    });
    hp02.add(editDocIdbutton);

		Button removeDocIdbutton = new Button("Remove DocId");
    removeDocIdbutton.addStyleName(style.formularButton());
		removeDocIdbutton.addClickHandler(event -> {
      JsArrayString data = ((JsArrayString) JsArrayString
                                              .createArray(0));
      data.push("DocId" + tbDocId.getText());
      InterAppEventBus.fireEvent("removeDocument", data);
      protocolContainer.add(new Label("Fire Event: >>removeDocument<< for DocId: >>" + tbDocId.getText() + "<<"));
    });
    hp02.add(removeDocIdbutton);

    protocolContainer = new FlowPanel();
    protocolContainer.addStyleName(style.protocolContainer());
    container.add(protocolContainer);

		RootPanel.get("moduleA").add(container);
	}

 public interface Resources
    extends ClientBundle {

    @Source("application.css")
    ApplicaitonStyle style();

  }

  public interface ApplicaitonStyle
    extends CssResource {

    String headline();

    String formularContainer();

    String label();

    String textBox();

    String formularButton();

    String protocolContainer();

  }
}
