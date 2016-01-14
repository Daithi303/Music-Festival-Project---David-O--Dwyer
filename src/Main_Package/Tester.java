package Main_Package;

import java.io.Serializable;
import javax.swing.JOptionPane;

public class Tester implements Serializable{

	private static final long serialVersionUID = 1L;

	/*This is the main method which calls the menu main method which displays 
	 * to the user the options. An initial menu method in the manager class 
	 * is then called based on the user's choice.*/
	public static void main(String[] args) throws Exception {

		int option;
		 Manager x = new Manager();
		   
		   do {
			   
		   option = x.menuMain();
		  
		 
		  	switch(option)
		   		{
		   	case 1  : x.menuAddBand();
		   				break;		  
		   	case 2  : x.menuRemoveBand();
		   				break;
		   	case 3  : x.MenuRemoveAllBands();	
		   				break;
		   	case 4  : x.menuListBandsByPrice();
		   				break;
		   	case 5  : x.menulistBandsInAlphabeticalOrder();
		   				break;
		   	case 6  : x.menuDisplayTimetables();
	  		  		  	break;
		   	case 7  : x.menuAssignBandToTimeslot();
		   				break;
		   	case 8  : x.menuUnnassignBandfromTimeSlot();
		   				break;
		   	case 9  : x.menuSearchBandbyPrice();
		   				break;  
		   	case 10  : x.menuGetBandSubListbyPriceRange();
		   				break;
		   	case 11  : x.menuLoadFile();
						break; 	
		   	case 12  : x.menuSaveFile();
		   				break; 			
		   
		   	default : break;
		   		}

		   } while(option != 0);
		
		JOptionPane.showMessageDialog(null,"Thank you for using the system.","Festival Scheduling System",JOptionPane.PLAIN_MESSAGE);
		System.exit(0);

	}

}
