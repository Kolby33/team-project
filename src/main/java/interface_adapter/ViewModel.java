package interface_adapter;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

/**
 * Base class for view models. A view model holds a state object and notifies
 * its listeners (views) when that state changes.
 * @param <T> the type of state this view model holds
 */
public class ViewModel<T> {
  private final String viewName;
  private final PropertyChangeSupport support = new PropertyChangeSupport(this);
  private T state;

  public ViewModel(String viewName) {
    this.viewName = viewName;
  }

  public String getViewName() {
    return viewName;
  }

  public T getState() {
    return state;
  }

  public void setState(T state) {
    this.state = state;
  }

  /**
   * Tells listeners that the state has changed, using the property name "state".
   */
  public void firePropertyChanged() {
    support.firePropertyChange("state", null, state);
  }

  /**
   * Tells listeners that the given property has changed.
   * @param propertyName the property that changed
   */
  public void firePropertyChanged(String propertyName) {
    support.firePropertyChange(propertyName, null, state);
  }

  public void addPropertyChangeListener(PropertyChangeListener listener) {
    support.addPropertyChangeListener(listener);
  }
}