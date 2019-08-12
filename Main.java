import javax.swing.JFrame;

public class Main {
	public static void main(String[] args){
		// The main menu, create a GUI object and define it's parameters 
		try{
			Gui TheGui= new Gui();
			TheGui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			TheGui.setSize(1520, 200); 
			TheGui.setVisible(true);
		}catch (Exception e){
			System.out.println("Erroe accured: " + e.getMessage());
		}		
	}
}
