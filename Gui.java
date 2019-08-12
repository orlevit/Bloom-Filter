import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.security.NoSuchAlgorithmException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class Gui extends JFrame  {
	
	private static final long serialVersionUID = 1L;
	
	private JPanel LogInPanel;						// The panel that contain all the items
	private JPanel AdministratorPanel;				// The panel of the bloom filter
	private JButton blogin;							// The log in button 
	private JButton clearFilter;					// Clear the filter
	private JButton OnOff;							// The button that turn\on the current user name slits in the filter
	private JLabel TheFilter;						// The name text for the bloom filter
	private JLabel username;						// The user name informative text
	private JLabel userEnding;						// The ending of the mail
	private JLabel password;						// The user name password text
	private JLabel HeadLine;						// The headline for the user
	private JLabel PasswordStrength;				// The the percentage of the password strength.	
	private JLabel UsersList;						// User list text
	private JTextField UserInsertedName;			// The current users name
	private JTextField pass;						// The current password
	private BloomFilter TheBloomFilter;				// The bloom filter
	private String UsersStringList;					// All the users that enter the system
	private boolean TurnOnOrOff;					// Show the current user name slots(yellow) or turn it off
	private JTextField[][] BloomArray;				// The bloom filter representation 

  public Gui() throws NoSuchAlgorithmException {
    super("Bloom Filter Login");

    blogin = new JButton("Login");	 
    clearFilter = new JButton("Clear");	
    LogInPanel = new JPanel();
	AdministratorPanel = new JPanel();
    HeadLine = new JLabel("Jmail Log In");
    UserInsertedName = new JTextField(15);
    pass = new JPasswordField(15);
    userEnding = new JLabel(".Jmail.com");
    TheFilter = new JLabel("Bloom Filter");
    username = new JLabel("User name:");
    password = new JLabel("Password:");
    UsersList = new JLabel(Constants.LEFT_STARTING_SENTENCE + Constants.CLOSE_BRACKET);
	BloomArray = new JTextField[2][Constants.M_FILTER_SIZE];
	TheBloomFilter = new BloomFilter();
	UsersStringList = new String("");
	OnOff = new JButton("I/O");
	TurnOnOrOff = false;
	PasswordStrength = new JLabel("das");
	PasswordStrength.setVisible(false);

	// Make the bloom filter representation , two row, first row is indexes and second row is occupied or empty
	for(int i=0; i<2; i++){
		    JTextField newJTextField;
            for(int j=0; j< Constants.M_FILTER_SIZE; j++){
            	if (i==0){
	            	newJTextField= new JTextField(j+(j<10 ? " " : ""));
	            	newJTextField.setHorizontalAlignment(SwingConstants.CENTER);
	            	newJTextField.setBackground(Color.LIGHT_GRAY);
            	}else{
            		newJTextField= new JTextField(j+(j<10 ? " " : ""));
	            		newJTextField.setForeground(Color.WHITE);
		            	newJTextField.setBackground(Color.WHITE);
	            	}
	            	BloomArray[i][j]= newJTextField;
	        	    AdministratorPanel.add(newJTextField);
	            }
	     }

		// Set the locations of the different items
	    setLocation(100,280);
	    LogInPanel.setLayout (null); 	   
	    HeadLine.setBounds(110,10,190,20);
	    TheFilter.setBounds(850,10,70,20);
	    UserInsertedName.setBounds(70,30,150,20);
	    userEnding.setBounds(220,30,70,20);
	    pass.setBounds(70,65,150,20);
	    blogin.setBounds(70,100,70,20);
	    clearFilter.setBounds(150,100,70,20);
	    username.setBounds(3,28,80,20);
	    password.setBounds(3,63,80,20);
	    AdministratorPanel.setBounds(300,30,1200,55);
	    UsersList.setBounds(305,105,1190,20);
	    OnOff.setBounds(305,85,50,20);
	    PasswordStrength.setBounds(220,65,70,20);
	    
	    // Add the items to the panel
	    LogInPanel.add(HeadLine);
	    LogInPanel.add(TheFilter);
	    LogInPanel.add(blogin);
	    LogInPanel.add(clearFilter);
	    LogInPanel.add(UserInsertedName);
	    LogInPanel.add(pass);
	    LogInPanel.add(username);
	    LogInPanel.add(userEnding);
	    LogInPanel.add(password);
	    LogInPanel.add(AdministratorPanel);
	    LogInPanel.add(UsersList);
	    LogInPanel.add(OnOff);
	    LogInPanel.add(PasswordStrength);

	    // Add the buttons and text to the handler
	    handleButtons handelr = new handleButtons(); 
	    UserInsertedName.addActionListener(handelr);
	    clearFilter.addActionListener(handelr);
	    blogin.addActionListener(handelr);
	    OnOff.addActionListener(handelr);
	    pass.addActionListener(handelr);

	    // Add the panel the main panel
	    add(LogInPanel);
  }
  
  // Defining the listener
  private class handleButtons implements ActionListener{
		public void actionPerformed(ActionEvent event){
			// If the user enter a user name, switch the slots in the bloom representation
			if(event.getSource() == UserInsertedName){
				TheBloomFilter.CurrUserNameCheck(UserInsertedName.getText());
				TurnOnOrOff = true;
				
				//  Enter the users set to the bloom filter representation and mark the current user name
		        for (int j = 0; j < Constants.M_FILTER_SIZE; j++){			        
		        	if ((BloomArray[1][j].getBackground() == Color.YELLOW) &&
		        		(TheBloomFilter.getAllUsersBloomArray().get(j))){
		        		BloomArray[1][j].setBackground(Color.BLACK);
        				BloomArray[1][j].setForeground(Color.BLACK);
		        	}else if(BloomArray[1][j].getBackground() == Color.YELLOW){
		        		BloomArray[1][j].setBackground(Color.WHITE);
        				BloomArray[1][j].setForeground(Color.WHITE);
		        	}
		        	if (TheBloomFilter.getCurrentUserBloomArray().get(j)){
		        		BloomArray[1][j].setBackground(Color.YELLOW);
        				BloomArray[1][j].setForeground(Color.YELLOW);
		        	}
		        }
			}
			// Switch the slots in the bloom representation on or off	
			if(event.getSource() == OnOff){
				 for (int j = 0; j < Constants.M_FILTER_SIZE; j++){			        
			        	if ((BloomArray[1][j].getBackground() == Color.YELLOW) &&
			        		(TheBloomFilter.getAllUsersBloomArray().get(j))){
			        		BloomArray[1][j].setBackground(Color.BLACK);
	        				BloomArray[1][j].setForeground(Color.BLACK);
			        	}else if(BloomArray[1][j].getBackground() == Color.YELLOW){
			        		BloomArray[1][j].setBackground(Color.WHITE);
	        				BloomArray[1][j].setForeground(Color.WHITE);
			        	}
				 }
				// Change the current user bloom filter representation slots from on to off and vice versa
				if (!TurnOnOrOff)
			        for (int j = 0; j < Constants.M_FILTER_SIZE; j++){		        
			        	if (TheBloomFilter.getCurrentUserBloomArray().get(j)){
			        		BloomArray[1][j].setBackground(Color.YELLOW);
	        				BloomArray[1][j].setForeground(Color.YELLOW);
			        	}
			        }
				TurnOnOrOff = !TurnOnOrOff;
			}
			// If the user enter a password check it's strength
			if(event.getSource() == pass){
				PasswordCheck.InitialCounters();
				if (!PasswordCheck.MinimalRequirements(pass.getText())){
					JOptionPane.showMessageDialog(null, "Minimal conditions for passwword, should be:" + "\n" +
												  "* At leat 8 Characters" + "\n" +
												  "* Big letters" + "\n" +
												  "* Digits" + "\n" +
												  "* Special Characters");
					}
				PasswordCheck.IncreaseValue(pass.getText());
				PasswordCheck.DecreaseValue(pass.getText());
				PasswordStrength.setText("" + PasswordCheck.getValue() + "%");
				PasswordStrength.setVisible(true);				
			}
			/* If the user try to log in check password's strength, whether user already in user set and mark it 
			   in the bloom representation array*/ 
			if (event.getSource() == blogin){
				PasswordCheck.InitialCounters();
				if ((UserInsertedName.getText().isEmpty()) || (pass.getText().isEmpty()))
		 			JOptionPane.showMessageDialog(null, "You must enter a user name and password");				
				else{
					// Check if the password valid
					if (!PasswordCheck.MinimalRequirements(pass.getText())){
					JOptionPane.showMessageDialog(null, "Minimal conditions for passwword, should be:" + "\n" +
												  "* At leat 8 Characters" + "\n" +
												  "* Big letters" + "\n" +
												  "* Digits" + "\n" +
												  "* Special Characters");
					}else{
						// Check if the user exists and if is exists turn the slots in the bloom representation on
						if (TheBloomFilter.contains(UserInsertedName.getText())){
				 			JOptionPane.showMessageDialog(null, "Name may already existes in the system");
						}else{
							TheBloomFilter.add(UserInsertedName.getText());
							UsersStringList += UserInsertedName.getText() + ",";
							UsersList.setText(Constants.LEFT_STARTING_SENTENCE + UsersStringList +  Constants.CLOSE_BRACKET); 
					        for (int j = 0; j < Constants.M_FILTER_SIZE; j++){
				        		BloomArray[1][j].setBackground(Color.WHITE);
		        				BloomArray[1][j].setForeground(Color.WHITE);				       
					        	if (TheBloomFilter.getAllUsersBloomArray().get(j)){
					        		BloomArray[1][j].setBackground(Color.BLACK);
			        				BloomArray[1][j].setForeground(Color.BLACK);				        	
					        	}
					        }
						}
					}
					PasswordCheck.IncreaseValue(pass.getText());
					PasswordCheck.DecreaseValue(pass.getText());
					PasswordStrength.setText("" + PasswordCheck.getValue() + "%");
					PasswordStrength.setVisible(true);				
				}
			}
			// Clear all data that the users entered
			if (event.getSource() == clearFilter){
				for (int j = 0; j < Constants.M_FILTER_SIZE; j++){
	        		BloomArray[1][j].setBackground(Color.WHITE);
    				BloomArray[1][j].setForeground(Color.WHITE);
				}
				TheBloomFilter.clear();
				UserInsertedName.setText("");
				pass.setText("");
				UsersStringList = "";
				TurnOnOrOff = false;
				PasswordStrength.setText("");
			    UsersList.setText(Constants.LEFT_STARTING_SENTENCE + Constants.CLOSE_BRACKET);
			}
		}
  }
}
	