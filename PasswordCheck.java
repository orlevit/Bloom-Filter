import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PasswordCheck {
	private final static String SPECIALCHARACTERS = "`!@#$%^&*()-=_+|<>\\?\\{\\}\\[\\]~-"; // Type of special characters
	private static int MinimalConditions;								 // Numbers conditions validated
	private static int value;											 // The value of the password
	private static int NumberOfa_zChar;									 // Number of a-z characters
	private static int NumberOfA_ZChar;									 // Number of A-Z characters
	private static int NumberOf0_9Char;									 // Number of 0-9 characters
	private static int NumberOfSpecialChar;								 // Number Of Special characters
	private static int NumberOf0_9MiddleChar;							 // Number Of 0_9 Middle characters
	private static int NumberOfa_zMiddleChar;							 // Number Of a_z Middle characters
	private static int NumberOfA_ZMiddleChar;							 // Number Of A_Z Middle characters
	private static int OnlyLetters;										 // If only letters appear length equals to the password length 
	private static int OnlyNumbers;										 // If only numbers appear length equals to the password length 
	private static int SequenceOfa_zChar;								 // Number Of a_z Middle characters numbers minus the edges
	private static int SequenceOfA_ZChar;								 // Number Of A_Z Middle characters numbers minus the edges
	private static int SequenceOfNumbers;								 // Number Of 0_9 Middle characters numbers minus the edges
	private static int SequenceOfAscendingOrDescendingLetters;         	 // Sequence Of Ascending Or Descending Letters
	private static int SequenceOfAscendingOrDescendingNumbers;			 // Sequence Of Ascending Or Descending Numbers
	private static int SequenceOfAscendingOrDescendingSpecialChars;		 // Sequence Of Ascending Or Descending Special characters

	// Initial the different counters that assembles the value of the password
    public static void InitialCounters() {
    	MinimalConditions 							= 0;
    	value										= 0;
    	NumberOfa_zChar							 	= 0;
    	NumberOfA_ZChar							 	= 0;
    	NumberOf0_9Char					   		    = 0;
    	NumberOfSpecialChar							= 0;
    	NumberOf0_9MiddleChar						= 0;
    	NumberOfa_zMiddleChar						= 0;
    	NumberOfA_ZMiddleChar						= 0;
    	OnlyLetters									= 0;
    	OnlyNumbers									= 0;
    	SequenceOfa_zChar							= 0;
    	SequenceOfA_ZChar							= 0;
    	SequenceOfNumbers							= 0;
    	SequenceOfAscendingOrDescendingLetters		= 0;
    	SequenceOfAscendingOrDescendingNumbers		= 0;
    	SequenceOfAscendingOrDescendingSpecialChars	= 0;
    }
	
    // Test if the minimal requirements are checked
    public static boolean MinimalRequirements(String password) {
    	// Password is more than 8 characters
    	if(password.length() >= 8)
	    {
    	   MinimalConditions++;
	       Pattern Upperletter = Pattern.compile("[A-z]");			// Pattern that contains only A-Z
	       Pattern Lowerletter = Pattern.compile("[a-z]");			// Pattern that contains only a-z
	       Pattern digit = Pattern.compile("[0-9]");				// Pattern that contains only 0-9
	       Pattern special = Pattern.compile (SPECIALCHARACTERS);	// Pattern that contains only special characters

           Matcher hasUpperletter = Upperletter.matcher(password);
           Matcher hasLowerletter = Lowerletter.matcher(password);
           Matcher hasDigit = digit.matcher(password);
           Matcher hasSpecial = special.matcher(password);
           
           // check if the password contain a_z,A_Z,0_9 and special characters
           if (hasUpperletter.find()){
        	   MinimalConditions++;
           }
           if (hasLowerletter.find()){
        	   MinimalConditions++;
           }
           if (hasDigit.find()){
        	   MinimalConditions++;
           }
           if (hasSpecial.find()){
        	   MinimalConditions++;
           }
           // if less than 3 conditions apply 
           return MinimalConditions > 3;
	    }
	    	   return false;
	}
    
    // Characters for Increasing the password value
    public static void IncreaseValue(String password) {
    	  
        // String length values added
    	value += 4*password.length();
    			
    	// For each character check it's type and increase the suitable counter
    	for (int i=0; i < password.length() ;i++){    		
        	// Number Of a-z Characters
    		if(password.charAt(i) >= 'a' && password.charAt(i) <= 'z')
    			NumberOfa_zChar++;
    		
        	// Number Of A-Z Characters
    		if(password.charAt(i) >= 'A' && password.charAt(i) <= 'Z')
    			NumberOfA_ZChar++;
    		
        	// Number Of 0-9 Characters
    		if(password.charAt(i) >= '0' && password.charAt(i) <= '9')
    			NumberOf0_9Char++;   		
    		
        	// Number Of special Characters
    		if(SPECIALCHARACTERS.contains(password.charAt(i)+""))
    			NumberOfSpecialChar++;   
    		
        	// Number Of 0-9 inner Characters
    		if((password.charAt(i) >= '0' && password.charAt(i) <= '9') && (2 < NumberOf0_9Char))
    			NumberOf0_9MiddleChar++;
    		    		
        	// Number Of a-z inner Characters
    		if((password.charAt(i) >= 'a' && password.charAt(i) <= 'z') && (2 < NumberOfa_zChar))
    			NumberOfa_zMiddleChar++;
    		
        	// Number Of A-Z inner Characters
    		if((password.charAt(i) >= 'A' && password.charAt(i) <= 'Z') && (2 < NumberOfA_ZChar))
    			NumberOfA_ZMiddleChar++;
    	}
    	
    	// The score that each condition add to the final value as mentioned in the document 
    	value += (NumberOfa_zChar != 0? 2*(password.length()-NumberOfa_zChar):0) + (NumberOfA_ZChar != 0? 2*(password.length()-NumberOfA_ZChar) : 0)+
    			 (((NumberOfa_zChar != 0) || (NumberOfA_ZChar != 0) || (NumberOfSpecialChar != 0))? 4*NumberOf0_9Char : 0) + 
    			 6*NumberOfSpecialChar + 2*NumberOfa_zMiddleChar + 2*NumberOfA_ZMiddleChar + 2*NumberOf0_9MiddleChar + 
    			 (MinimalConditions > 3 ? 2*MinimalConditions : 0);
    }
    
    // Characters that Decrease the password value
    public static void DecreaseValue(String password) {
    	
    	String OnlyNumbersReg 							 = "\\d+";			// Regular expression for numbers
    	String OnlyLettersReg 							 = "[A-Za-z]+";		// Regular expression for letters
    	int NumberOfA_ZLettersSequence 					 = 0;				// Number Of A-Z Letters Sequence counter
    	int NumberOfa_zLettersSequence 					 = 0;				// Number Of a-z Letters Sequence counter
    	int NumberOf0_9LettersSequence 					 = 0;				// Number Of 0-9 Letters Sequence counter
    	int Ascending_DescendingLettersSequenceTemp 	 = 0;				// Counter for  Ascending Descending Letters Sequence in a row
    	int LastLetter									 = -1;				// Holds the Last Letter
    	int Ascending_DescendingDigitsSequenceTemp 		 = 0;				// Counter for  Ascending Descending Digits Sequence in a row
    	int LastDigit									 = -1;				// Holds the Last Digit
    	int Ascending_DescendingSpecialCharSequenceTemp	 = 0;				// Counter for  Ascending Descending Special characters Sequence in a row
       	int LastSpecialChar								 = -1;				// Holds the Last Special character

    	// Check if the password has only digits
    	if (password.matches(OnlyNumbersReg))
    		OnlyNumbers += password.length();
    	
    	// Check if the password has only letters    	
    	if (password.matches(OnlyLettersReg))
    		OnlyLetters += password.length();
    	
    	// For each character check it's type and decrease the suitable counter for sequence
    	for (int i=0; i < password.length() ;i++){ 
    		
        	// Number Of A-Z Characters
    		if(password.charAt(i) >= 'A' && password.charAt(i) <= 'Z'){
    			// initialize the other sequences, and add 1 to the current one
    			NumberOfa_zLettersSequence = 0;
    	    	NumberOf0_9LettersSequence = 0;
    	    	LastDigit				   = -1;
    	       	LastSpecialChar			   = -1;
    			NumberOfA_ZLettersSequence++;

    			/* Other sequences stops, check if the other sequences than the enter condition have more 
    			   than 2 characters of the same type in middle of sentence and initial with zero the counters */
    	    	if (Ascending_DescendingDigitsSequenceTemp > 2)
    	    		SequenceOfAscendingOrDescendingNumbers +=Ascending_DescendingDigitsSequenceTemp - 2;
    	    	
    	    	if (Ascending_DescendingSpecialCharSequenceTemp > 2)
    	    		SequenceOfAscendingOrDescendingSpecialChars +=Ascending_DescendingSpecialCharSequenceTemp - 2;
    	    	
    	    	Ascending_DescendingDigitsSequenceTemp       = 0;
    	    	Ascending_DescendingSpecialCharSequenceTemp  = 0;
    			
    			// check if the sequences have more than 2 characters of the same type in middle of sentence
    			if (LastLetter == -1){
    				LastLetter = password.charAt(i);
    				SequenceOfAscendingOrDescendingLetters++;
    			}
    			else if ((LastLetter + 1 == password.charAt(i)) || (LastLetter - 1 == password.charAt(i))){
    				SequenceOfAscendingOrDescendingLetters++;
    				LastLetter +=(LastLetter + 1 == password.charAt(i)? 1 : (-1));
    			}
    			
    			// Add the current sequence to the total sequence number
    			if (NumberOfA_ZLettersSequence > 1)
    				SequenceOfA_ZChar++;
    		}
        	// Number Of a-z Characters
    		if(password.charAt(i) >= 'a' && password.charAt(i) <= 'z'){

    			// initialize the other sequences, and add 1 to the current one    			
    			NumberOfA_ZLettersSequence = 0;
    			NumberOf0_9LettersSequence = 0;
    	    	LastDigit				   = -1;
    	       	LastSpecialChar			   = -1;
    			NumberOfa_zLettersSequence++;
    			    			
    			/* Other sequences stops, check if the other sequences than the enter condition have more 
 			       than 2 characters of the same type in middle of sentence and initial with zero the counters */
    	    	if (Ascending_DescendingDigitsSequenceTemp > 2)
    	    		SequenceOfAscendingOrDescendingNumbers +=Ascending_DescendingDigitsSequenceTemp - 2;
    	    	
    	    	if (Ascending_DescendingSpecialCharSequenceTemp > 2)
    	    		SequenceOfAscendingOrDescendingSpecialChars +=Ascending_DescendingSpecialCharSequenceTemp - 2;
    	    	
    	    	Ascending_DescendingDigitsSequenceTemp       = 0;
    	    	Ascending_DescendingSpecialCharSequenceTemp  = 0;
    			
    			// check if the sequences have more than 2 characters of the same type in middle of sentence
    			if (LastLetter == -1){
    				LastLetter = password.charAt(i);
    				SequenceOfAscendingOrDescendingLetters++;
    			}
    			else if ((LastLetter + 1 == password.charAt(i)) || (LastLetter - 1 == password.charAt(i))){
    				SequenceOfAscendingOrDescendingLetters++;
    				LastLetter +=(LastLetter + 1 == password.charAt(i)? 1 : (-1));
    			}
    			
    			// Add the current sequence to the total sequence number
    			if (NumberOfa_zLettersSequence > 1)
    				SequenceOfa_zChar++;
    		}
        	// Number Of 0-9 Characters
    		if(password.charAt(i) >= '0' && password.charAt(i) <= '9'){
    			
    			// initialize the other sequences, and add 1 to the current one
    			NumberOfa_zLettersSequence = 0;
    			NumberOfA_ZLettersSequence = 0;
    	    	LastLetter				   = -1;
    	       	LastSpecialChar			   = -1;
    			NumberOf0_9LettersSequence++;
    			
    			/* Other sequences stops, check if the other sequences than the enter condition have more 
			       than 2 characters of the same type in middle of sentence and initial with zero the counters */
    	    	if (Ascending_DescendingLettersSequenceTemp > 2)
    	    		SequenceOfAscendingOrDescendingLetters +=Ascending_DescendingLettersSequenceTemp - 2;
    	    	
    	    	if (Ascending_DescendingSpecialCharSequenceTemp > 2)
    	    		SequenceOfAscendingOrDescendingSpecialChars +=Ascending_DescendingSpecialCharSequenceTemp - 2;
    	    	
    	    	Ascending_DescendingLettersSequenceTemp     = 0;
    	    	Ascending_DescendingSpecialCharSequenceTemp = 0;

    			// check if the sequences have more than 2 characters of the same type in middle of sentence
    			if (LastDigit == -1){
    				LastDigit = password.charAt(i);
    				Ascending_DescendingDigitsSequenceTemp++;
    			}
    			else if ((LastDigit + 1 == password.charAt(i)) || (LastDigit - 1 == password.charAt(i))){
    				Ascending_DescendingDigitsSequenceTemp++;
    				LastDigit +=(LastDigit + 1 == password.charAt(i)? 1 : (-1));
    			}
    			
    			// Add the current sequence to the total sequence number
    			if (NumberOf0_9LettersSequence > 1)
    				SequenceOfNumbers++;
    			}
        	// Special Characters
    		if(SPECIALCHARACTERS.contains(password.charAt(i)+"")){  
    			
    			// initialize the other sequences, and add 1 to the current one
				NumberOfa_zLettersSequence = 0;
				NumberOfA_ZLettersSequence = 0;
    	    	NumberOf0_9LettersSequence = 0;
    	    	LastDigit				   = -1;
		    	LastLetter				   = -1;
		    	
    			/* Other sequences stops, check if the other sequences than the enter condition have more 
			       than 2 characters of the same type in middle of sentence and initial with zero the counters */
    	    	if (Ascending_DescendingDigitsSequenceTemp > 2)
    	    		SequenceOfAscendingOrDescendingNumbers +=Ascending_DescendingDigitsSequenceTemp - 2;
    	    	
		    	if (Ascending_DescendingLettersSequenceTemp > 2)
    	    		SequenceOfAscendingOrDescendingLetters +=Ascending_DescendingLettersSequenceTemp - 2;

		    	Ascending_DescendingDigitsSequenceTemp      = 0;
    	    	Ascending_DescendingLettersSequenceTemp     = 0;

    			// check if the sequences have more than 2 characters of the same type in middle of sentence
    	    	if (LastSpecialChar == -1){
    	    		LastSpecialChar = password.charAt(i);
    	    		Ascending_DescendingSpecialCharSequenceTemp++;    				    
    		    } 
    			else if ((SPECIALCHARACTERS.indexOf((int)(LastSpecialChar + 1) + "") == password.charAt(i)) ||
    					(SPECIALCHARACTERS.indexOf((int)(LastSpecialChar - 1) + "") == password.charAt(i))){
    				Ascending_DescendingSpecialCharSequenceTemp++;   
    				LastSpecialChar +=(LastSpecialChar + 1 == password.charAt(i)? 1 : (-1));
    			}
    		}
    	}
    			
    	/* Because the adding of the last sequence to the toatl sequence happens only at the start 
    	   of the next one, than the last sequence needed to be handled separately */
		if ((LastLetter == -1) && (LastSpecialChar == -1)){
			if (Ascending_DescendingDigitsSequenceTemp > 2)
	    		SequenceOfAscendingOrDescendingNumbers += Ascending_DescendingDigitsSequenceTemp - 2;
		}else if ((LastDigit == -1) && (LastSpecialChar == -1)){
   	    	if (Ascending_DescendingLettersSequenceTemp > 2)
   	    		SequenceOfAscendingOrDescendingLetters += Ascending_DescendingLettersSequenceTemp - 2;
   	    }else if ((LastLetter == -1) && (LastDigit == -1)){
   	    	if (Ascending_DescendingSpecialCharSequenceTemp > 2)
   	    		SequenceOfAscendingOrDescendingSpecialChars += Ascending_DescendingSpecialCharSequenceTemp - 2;
		}
    		
    	// The score that each condition subtract from the final value as mentioned in the document 
    	value -= (OnlyNumbers + OnlyLetters + 2*SequenceOfA_ZChar + 2*SequenceOfa_zChar + 
    			  2*SequenceOfNumbers + 3*SequenceOfAscendingOrDescendingNumbers +
    			  3*SequenceOfAscendingOrDescendingLetters + 3*SequenceOfAscendingOrDescendingSpecialChars);
    }
    
    // return value of the the password in range 0-100 
    public static int getValue(){
    	return (value > 100 ? 100 : value < 0 ? 0 : value);
    }
}
