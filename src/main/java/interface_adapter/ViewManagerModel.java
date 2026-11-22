package interface_adapter;

/**
 * Holds the name of the view that should currently be on screen.
 */
public class ViewManagerModel extends ViewModel<String> {

  public ViewManagerModel() {
    super("view manager");
    this.setState("");
  }
}