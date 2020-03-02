package pickup.stateTransition;

/**
 * Created by YongGang on 2017/1/22.
 */
public class TransitionEntry {
  private String from;
  private String to;

  public String getFrom() {
    return from;
  }

  public void setFrom(String from) {
    this.from = from;
  }

  public String getTo() {
    return to;
  }

  public void setTo(String to) {
    this.to = to;
  }

  public TransitionEntry() {
  }

  public TransitionEntry(String from, String to) {
    this.from = from;
    this.to = to;
  }
}
