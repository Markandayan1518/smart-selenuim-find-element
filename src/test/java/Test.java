import com.schneider.electric.config.ElementConfig;

import org.openqa.selenium.By;

public class Test {

  public static void main(String[] args) {
    By by = ElementConfig.getBy("login.next.button.id");
    System.out.println("by = " + by);
  }
}
