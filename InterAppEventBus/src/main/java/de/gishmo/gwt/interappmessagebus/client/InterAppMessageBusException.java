package de.gishmo.gwt.interappmessagebus.client;

public class InterAppMessageBusException
  extends Exception {

  public InterAppMessageBusException() {
  }

  public InterAppMessageBusException(String message) {
    super(message);
  }

  public InterAppMessageBusException(String message,
                                     Throwable cause) {
    super(message,
          cause);
  }

  public InterAppMessageBusException(Throwable cause) {
    super(cause);
  }

  public InterAppMessageBusException(String message,
                                     Throwable cause,
                                     boolean enableSuppression,
                                     boolean writableStackTrace) {
    super(message,
          cause,
          enableSuppression,
          writableStackTrace);
  }
}
