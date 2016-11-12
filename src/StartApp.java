import userInterface.UI;

public class StartApp {
	public static void main(String args[]) {
		controller.Controller ctr = new controller.Controller();
		
		@SuppressWarnings("unused")
		UI ui= new UI(ctr);
	}
}