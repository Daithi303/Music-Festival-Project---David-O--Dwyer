package Main_Package;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class Manager implements Serializable{

	private static final long serialVersionUID = 1L;
	static TimeSlotSet tent_a;
	static TimeSlotSet tent_b;
	static TimeSlotSet tent_c;
	BandMap bandMap;
	
	
	/*This constructer initialises three objects of the class TimeSlotSet
	 * and a single object of the class BandMap.*/
	public Manager()
	{
		tent_a = new TimeSlotSet();
		tent_b = new TimeSlotSet();
		tent_c = new TimeSlotSet();
		bandMap = new BandMap();
	}
	
	
	/*This method displays to the user the menu options and returns the user's choice.*/
	public int menuMain()
	{
		int option = 0;
		
		   String opt1 = new String("1. Add a band");
		   String opt2 = new String("2. Remove a band");
		   String opt3 = new String("3. Remove all bands");
		   String opt4 = new String("4. List bands by price (from highest to lowest)");
		   String opt5 = new String("5. List bands in aphabetical order");
		   String opt6 = new String("6. Display Timetables");
		   String opt7 = new String("7. Assign a band to a timeslot");
		   String opt8 = new String("8. Unassign a band from a timeslot");
		   String opt9 = new String("9. Search for a band by a specific price (binary search)");
		   String opt10 = new String("10. List bands by price range (binary search)");
		   String opt11 = new String("11. Load Files");
		   String opt12 = new String("12. Save Files");
		   String opt13 = new String("0. Exit System");
		   String msg = new String("Enter Option:");
		   JTextField opt = new JTextField("");
		   
		   Object message[] = new Object[15];
		   
		   message[0] = opt1;
		   message[1] = opt2;
		   message[2] = opt3;
		   message[3] = opt4;
		   message[4] = opt5;
		   message[5] = opt6;
		   message[6] = opt7;
		   message[7] = opt8;
		   message[8] = opt9;
		   message[9] = opt10;
		   message[10] = opt11;
		   message[11] = opt12;
		   message[12] = opt13;
		   message[13] = msg;
		   message[14] = opt;
		  

		   int response = JOptionPane.showConfirmDialog(null,message,"Festival Scheduling System",JOptionPane.OK_CANCEL_OPTION,
				   JOptionPane.PLAIN_MESSAGE);
	  
		   
		   if(response == JOptionPane.CANCEL_OPTION || response == JOptionPane.CLOSED_OPTION)
				   {JOptionPane.showMessageDialog(null,"Thank you for using the system.","Festival Scheduling System",JOptionPane.PLAIN_MESSAGE);
					System.exit(0);}
				   else
				   	{				 
					   option = Integer.parseInt( opt.getText());   
				   	}
			
				   return option;
			
		}
	/*This method,upon validation, creates an object of type Band with the values 
	 * returned from the takeInBandValues method, then calls the aadBandToSet method.*/
	public void menuAddBand()
	{
		String[] values = takeInBandValues();
		if(values == null)
		;
		else
		{Band b = new Band(values[0], Double.parseDouble(values[1]), values[2]);
   		bandMap.addBandToSet(b);
		}
	}
	
	
	/*This methods first takes in a name of a band from the user. It then checks 
	 * to see if a band with that name exists within the bandMap. If it does it then,depending on its 
	 * genre will search the appropriate TimeSlotSet object for a TimeSlot with that band
	 * assigned. If it finds one it unassigns that band from the TimeSlot, then removes the band from
	 * the bandMap*/
	public void menuRemoveBand()
	{
		
		if(bandMap.isEmpty())
		{
			JOptionPane.showMessageDialog(null,"There are currently no bands stored in the system.","Festival Scheduling System",JOptionPane.PLAIN_MESSAGE);
		}
		else
		{String msg = "Please enter the name of the band you wish to remove.";
		JTextField ans = new JTextField();
		Object message[] = new Object[2];

		message[0] = msg;
		message[1] = ans;
		
		int response = JOptionPane.showConfirmDialog(null,message,"Festival Scheduling System",JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE);
		
		if(response == JOptionPane.CANCEL_OPTION || response == JOptionPane.CLOSED_OPTION)
			  ;
		else
		{
			
			String bandName = bandMap.searchSetByName(ans.getText());
			
			if(bandName == null)
				{JOptionPane.showMessageDialog(null,"The band cannot be removed as there is no band stored in the system with that name.","Festival Scheduling System",JOptionPane.PLAIN_MESSAGE);}
			else
			{
			ArrayList<Band> a = BandMap.getSubList(null,bandName,null);

			Band b = a.get(0);

			ArrayList<TimeSlot> c;
			if(b.getGenre() == "Rock")
			{c = tent_a.SubListByBandName(bandName);}

			else if(b.getGenre() == "Folk")
			{c = tent_b.SubListByBandName(bandName);}
			
			else
			{c = tent_c.SubListByBandName(bandName);}
			if(c.size() == 0)
				;
			else
			{TimeSlot t = c.get(0);
			unassignBandFromTimeSlot(b,t);}
			bandMap.removeBandFromSet(bandName);

			}
		
		}
		
		}
		
	}
	
	
	/*This method clears the bandMap then uses nested for loops to iterate over all
	 * three of the TimeSlotSet objects to unassign all bands from all the TimeSlots.*/
	public void MenuRemoveAllBands()
	{
		int response = JOptionPane.showConfirmDialog(null, "Are you sure you want to remove all bands from the system?", "Festival Scheduling System", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
	
		if(response == JOptionPane.CANCEL_OPTION || response == JOptionPane.CLOSED_OPTION)
		;
		else
		{
			bandMap.clearBandMap();
			
			ArrayList<TimeSlot> a = new ArrayList<TimeSlot>(tent_a.getTimeslot_collection());
			ArrayList<TimeSlot> b = new ArrayList<TimeSlot>(tent_b.getTimeslot_collection());
			ArrayList<TimeSlot> c = new ArrayList<TimeSlot>(tent_c.getTimeslot_collection());
			ArrayList <ArrayList<TimeSlot>> x = new ArrayList<ArrayList<TimeSlot>>();
			x.add(a);x.add(b);x.add(c);
			Band band = new Band("",0.0,"");
			for(ArrayList<TimeSlot> e : x)
			{
				for(TimeSlot t : e)
				{
					unassignBandFromTimeSlot(band,t);
				}
			}
			   JOptionPane.showMessageDialog(null,"All bands have been removed from the system","Festival Scheduling System",JOptionPane.PLAIN_MESSAGE);

		}
	}
	
	/*This Band checks to see if the bandMap is empty. If it isn't it then 
	 * calls on the listBandsInAlphabeticalOrder method from the BandMap class.*/
	public void menulistBandsInAlphabeticalOrder()
	{
		if(bandMap.isEmpty())
		{
			JOptionPane.showMessageDialog(null,"There are currently no bands stored in the system.","Festival Scheduling System",JOptionPane.PLAIN_MESSAGE);
		}
		else
		{
			bandMap.listBandsInAlphabeticalOrder();
		}
		
	}
	
	/*This Band checks to see if the bandMap is empty. If it isn't it then 
	 * calls on the listBandsByPrice method from the BandMap class.*/
	public void menuListBandsByPrice()
	{
		if(bandMap.isEmpty())
		{
			JOptionPane.showMessageDialog(null,"There are currently no bands stored in the system.","Festival Scheduling System",JOptionPane.PLAIN_MESSAGE);
		}
		else
		{
			bandMap.listBandsByPrice();
		}
	}
	
	
	/*This method asks the user to select one of the three type of 
	 * tent (TimeSlotSet objects). It then, depending on the user's choice will
	 * call the getTimeTable method from the TimeSlotSet class over the 
	 * appropriate TimeSlotSet object.*/
	public void menuDisplayTimetables()
	{
		
		String[] tents = {"Rock Tent ","Folk Tent","Dance Tent"};
		String msg = "Please select a tent";
		JComboBox<String> choice = new JComboBox<String>(tents);
		Object message[] = new Object[2];

		message[0] = msg;
		message[1] = choice;
		
		int response = JOptionPane.showConfirmDialog(null,message,"Festival Scheduling System",JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE);

		if(response == JOptionPane.CANCEL_OPTION || response == JOptionPane.CLOSED_OPTION)
			;
		else
		{
			if(choice.getSelectedIndex() == 0)
			{
				
				JOptionPane.showMessageDialog(null,tent_a.getTimeTable(0),"Festival Scheduling System - Rock Tent",JOptionPane.PLAIN_MESSAGE);

				
				
			}
			
			else if(choice.getSelectedIndex() == 1)
			{
				JOptionPane.showMessageDialog(null,tent_b.getTimeTable(1),"Festival Scheduling System - Folk Tent",JOptionPane.PLAIN_MESSAGE);

			}
			
			else if(choice.getSelectedIndex() == 2)
			{
				JOptionPane.showMessageDialog(null,tent_c.getTimeTable(2),"Festival Scheduling System - Dance Tent",JOptionPane.PLAIN_MESSAGE);

			}
		}
	}
	
	/*This method passes its parameter values on to the method getSublist which will, in turn,
	 * return an arrayList containing a sublist of the bandMap. The sublist returned depends
	 *  on the values of the parameters. It then displays to the user the list of bands from the sublist.
	 *  A Band object is then created and returned depending on the user's choice.*/
	public static Band getBandChoice(String s,String s1, String s2)
	{
		ArrayList<Band> tempBandList = BandMap.getSubList(s,s1,s2);
		
		if(tempBandList.size() == 0)
		{
			JOptionPane.showMessageDialog(null,"There are currently no bands available for your request","Festival Scheduling System",JOptionPane.PLAIN_MESSAGE);;
		}
		else
		{
		String msg = "Please select a band";
		String[] options = new String[tempBandList.size()];
		for(int i = 0;i < tempBandList.size();i++)
		{
			options[i] = tempBandList.get(i).getName();
		}
		
		
		int response_a = getComboBox(options,msg);
		
		if(response_a == -666)
		{
			return null;
		}
		
		
		Band a = tempBandList.get(response_a);
			return a;

		}
		
			return null;
	}
	
	/*This method calls on the getTimeslot_collection method over one of the three 
	 * TimeSlotSet objects,depending on the parameter value.*/
	public static Set<TimeSlot> getTentTimeSlots(int choice)
	{
		if(choice == 0)
		{return tent_a.getTimeslot_collection();}
		
		else if(choice == 1)
		{return tent_b.getTimeslot_collection();}
		
		else if(choice == 2)
		{return  tent_c.getTimeslot_collection();}
		
		return null;
	}
	
	/*This method calls the getBandChoice method displays a list of unassigned bands to the user
	 * then returns the users choice., Then,depending on the genre of the chosen band, a list of available TimeSlots are
	 * displayed to the user.The user's choice of band and TimeSlot are then sent into the
	 * assignBandToTimeSlot method.*/
	public void menuAssignBandToTimeslot()
	{
		Band a = getBandChoice("Unassigned",null,null);
		

		if(a == null)
			;
		else
			{
			try
			{
			String msg_a;
				ArrayList<TimeSlot> tempTimeSlotList = null;
				if(a.getGenre().equals("Rock"))
					{
						tempTimeSlotList = tent_a.SubListByBandName("");
						if(tempTimeSlotList.isEmpty())
						{throw new Exception("There are no timeslots available.");}
						msg_a = "Please select a timeslot from the Rock Tent";
					}
				else if(a.getGenre().equals("Folk"))
					{
						tempTimeSlotList = tent_b.SubListByBandName("");
						if(tempTimeSlotList.isEmpty())
						{throw new Exception("There are no timeslots available.");}
						msg_a = "Please select a timeslot from the Folk Tent";
					}
				else
				{
						tempTimeSlotList = tent_c.SubListByBandName("");
						if(tempTimeSlotList.isEmpty())
						{throw new Exception("There are no timeslots available.");}
						msg_a = "Please select a timeslot from the Dance Tent";
				}
					
					String[] options_a = new String[tempTimeSlotList.size()];
					for(int i = 0;i < tempTimeSlotList.size();i++)
					{
						options_a[i] = tempTimeSlotList.get(i).toString();
					}
					
					int response_b = getComboBox(options_a,msg_a);
					
					if(response_b == JOptionPane.CANCEL_OPTION || response_b == JOptionPane.CLOSED_OPTION || response_b == -666)
					{
						;
					}
					else
					{
						assignBandToTimeSlot(a,tempTimeSlotList.get(response_b));
						JOptionPane.showMessageDialog(null,"The band has been assigned to the timeslot.","Festival Scheduling System",JOptionPane.PLAIN_MESSAGE);

					}
					
				
					
			}catch(Exception e)
			{
				JOptionPane.showMessageDialog(null,e.getMessage(),"Festival Scheduling System",JOptionPane.PLAIN_MESSAGE);

			}
			}
		
}
		

	/*This method calls on the getBandChoice method to display a list of assigned bands to the user.
	 * The user's choice is then returned. Then, depending on the genre of the Band the appropriate TimeSlotSet
	 * is used to retrieve the TimeSlot which has that Band assigned to it. The Band and the TimeSlot are then sent into the 
	 * unassignBandFromTimeSlot method.*/
	public void menuUnnassignBandfromTimeSlot()
	{

		Band a = getBandChoice("Unassigned",null,"Not Null");
		if(a == null)
		{
			;
		}
		else
			{
				ArrayList<TimeSlot> temp;
				if(a.getGenre().equals("Rock"))
				{	temp = tent_a.SubListByBandName(a.getName());
					TimeSlot t = temp.get(0);
					unassignBandFromTimeSlot(a,t);
				}
				
				else if(a.getGenre().equals("Folk"))
				{
					temp = tent_b.SubListByBandName(a.getName());
					TimeSlot t = temp.get(0);
					unassignBandFromTimeSlot(a,t);
				}
				else if(a.getGenre().equals("Dance"))
				{
					temp = tent_c.SubListByBandName(a.getName());
					TimeSlot t = temp.get(0);
					unassignBandFromTimeSlot(a,t);
				}
				JOptionPane.showMessageDialog(null,"The band has been unassigned from it's timeslot","Festival Scheduling System - Rock Tent",JOptionPane.PLAIN_MESSAGE);

			}
		
	}
	
	/*This method takes in a Double value. It then uses a binary search to search
	 * the bandMap for a band with the same Double value stored as it's price variable.
	 * A dummy Band object is created with the user's Double value as its price. This band object
	 * is used as the key parameter in the Binary Search method and a Comparator is written in-line via
	 * a Lambda expression. This is also sent into the Binary Search method as a parameter.*/
	public void menuSearchBandbyPrice() throws NumberFormatException
	{
			if(bandMap.isEmpty())
			{JOptionPane.showMessageDialog(null,"There are currently no bands available for your request","Festival Scheduling System",JOptionPane.PLAIN_MESSAGE);}
			else
			{	
			String msg = "Please enter the price by wish you wish to locate a band :";
			JTextField ans = new JTextField();
			Object message[] = new Object[2];
			message[0] = msg;
			message[1] = ans;
		
			boolean formComplete = true;
			do
			{
				formComplete = true;
		
				int response = JOptionPane.showConfirmDialog(null, message, "Festival Scheduling System", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
				if(response == JOptionPane.CANCEL_OPTION || response == JOptionPane.CLOSED_OPTION)
					;
				else
				{
			   
				try
				{	
			   	Pattern p2 = Pattern.compile("  |[a-zA-Z'\\s]|,");
				Matcher m2; 
				m2 = p2.matcher(ans.getText());
				if(ans.getText().length() == 0)
				{throw new EmptyStringException();}
				if(m2.find())
				{throw new NumberFormatException();}
				if(Math.signum(Double.parseDouble(ans.getText())) == -1)
				{throw new NegativeNumberException(ans.getText());}
				Double d = Double.parseDouble(ans.getText());
				ArrayList<Band> bandList = new ArrayList<Band>(BandMap.getSubList(null, null, null));
				Collections.sort(bandList, (Band x, Band y)->{return (int) (Math.round(x.getPrice()) - Math.round(y.getPrice()));});
				int index = getBandIndexByPrice(bandList,d);
						
				if(Math.signum(index)==-1)
				{JOptionPane.showMessageDialog(null,"There is no band within the system with that price","Festival Scheduling System - Band with entered price",JOptionPane.PLAIN_MESSAGE);}
				else
				{JOptionPane.showMessageDialog(null,BandMap.getJTable(BandMap.getSubList(null,bandList.get(index).getName().toLowerCase(),null)),"Festival Scheduling System - Band with entered price",JOptionPane.PLAIN_MESSAGE);}
			   }
			   catch(EmptyStringException e)
			   {	
				   formComplete = false;
				   ans.setText("");
				   JOptionPane.showMessageDialog(null,e.getMessage(),"Festival Scheduling System",JOptionPane.PLAIN_MESSAGE);
			   }
			   catch(NumberFormatException e)
			   {	
				   formComplete = false;
				   ans.setText("");
				   JOptionPane.showMessageDialog(null,e.getMessage(),"Festival Scheduling System",JOptionPane.PLAIN_MESSAGE);
			   }
			   catch(NegativeNumberException e)
			   {	
				   formComplete = false;
				   ans.setText("");
				   JOptionPane.showMessageDialog(null,e.getMessage(),"Festival Scheduling System",JOptionPane.PLAIN_MESSAGE);
			   }
		   	}

		} while(!formComplete);
	
			}
	}
	
	/*This method works very similarly to the menuSearchBandByPrice but uses
	 * the negative value returned from the Binary Search method 
	 * (if a value cannot be exactly matched) to locate the index values of the objects
	 * nearest to the user's value entered (Both greater and less than the value).
	 * A subList is then created and displayed to the user.*/
	public void menuGetBandSubListbyPriceRange()
	{
			if(bandMap.isEmpty())
			{JOptionPane.showMessageDialog(null,"There are currently no bands available for your request","Festival Scheduling System",JOptionPane.PLAIN_MESSAGE);}
			else
			{	
			String msg1 = "Please enter the search parameters.";
			String[] options = {"Bands with prices greater than or equal to entered amount","Bands with prices less than or equal to entered amount"};
			JComboBox<String> combo = new JComboBox<String>(options);
			String msg2 = "Amount";
			JTextField amount = new JTextField();
			Object[] message = new Object[4];
			message[0] = msg1;
			message[1] = combo;
			message[2] = msg2;
			message[3] = amount;
			
			boolean formComplete = true;
			do
			{
				formComplete = true;
			
			int response = JOptionPane.showConfirmDialog(null, message,"Festival Scheduling System",JOptionPane.OK_CANCEL_OPTION,JOptionPane.PLAIN_MESSAGE);
			if(response == JOptionPane.CANCEL_OPTION || response == JOptionPane.CLOSED_OPTION)
				;
			else
			{
		   
			try
			{	
		   	Pattern p2 = Pattern.compile("  |[a-zA-Z'\\s]|,");
			Matcher m2; 
			m2 = p2.matcher(amount.getText());
			if(amount.getText().length() == 0)
			{throw new EmptyStringException();}
			if(m2.find())
			{throw new NumberFormatException();}
			if(Math.signum(Double.parseDouble(amount.getText())) == -1)
			{throw new NegativeNumberException(amount.getText());}
			Double d = Double.parseDouble(amount.getText());
			ArrayList<Band> subList; 
			ArrayList<Band> bandList = BandMap.getSubList(null,null,null);
			Collections.sort(bandList, (Band x, Band y)->{return (int) (Math.round(x.getPrice()) - Math.round(y.getPrice()));});
			int index = getBandIndexByPrice(bandList,d);
			
			
			if(combo.getSelectedIndex() == 0)
			{	
				if(Math.signum(index) == -1)
				{
					index = -index-1;
					if(index > bandList.size()-1)
					{
						   JOptionPane.showMessageDialog(null,"There is no band stored in the system with a price higher than the amount entered.","Festival Scheduling System",JOptionPane.PLAIN_MESSAGE);
					}
					else
					{
						subList =  new ArrayList<Band>(bandList.subList(index, bandList.size()));
						Collections.reverse(subList);
						JOptionPane.showMessageDialog(null,BandMap.getJTable(subList),"Festival Scheduling System",JOptionPane.PLAIN_MESSAGE);

					}
				}
				else
				{
					subList =  new ArrayList<Band>(bandList.subList(index, bandList.size()));
					Collections.reverse(subList);
					JOptionPane.showMessageDialog(null,BandMap.getJTable(subList),"Festival Scheduling System",JOptionPane.PLAIN_MESSAGE);
				}
			}
			else if(combo.getSelectedIndex() == 1)
			{
				System.out.println(index);
				if(Math.signum(index) == -1)
				{index = -index-2;
					if(index < 0)
					{
						   JOptionPane.showMessageDialog(null,"There is no band stored in the system with a price lower than the amount entered.","Festival Scheduling System",JOptionPane.PLAIN_MESSAGE);
					}
					else
					{
						subList =  new ArrayList<Band>(bandList.subList(0,index+1));
						Collections.reverse(subList);
						JOptionPane.showMessageDialog(null,BandMap.getJTable(subList),"Festival Scheduling System",JOptionPane.PLAIN_MESSAGE);

					}
				}
				else
				{
					subList =  new ArrayList<Band>(bandList.subList(0,index+1));
					Collections.reverse(subList);
					JOptionPane.showMessageDialog(null,BandMap.getJTable(subList),"Festival Scheduling System",JOptionPane.PLAIN_MESSAGE);
				}
			}
			}
			 catch(EmptyStringException e)
			   {	
				   formComplete = false;
				   amount.setText("");
				   JOptionPane.showMessageDialog(null,e.getMessage(),"Festival Scheduling System",JOptionPane.PLAIN_MESSAGE);
			   }
			   catch(NumberFormatException e)
			   {	
				   formComplete = false;
				   amount.setText("");
				   JOptionPane.showMessageDialog(null,e.getMessage(),"Festival Scheduling System",JOptionPane.PLAIN_MESSAGE);
			   }
			   catch(NegativeNumberException e)
			   {	
				   formComplete = false;
				   amount.setText("");
				   JOptionPane.showMessageDialog(null,e.getMessage(),"Festival Scheduling System",JOptionPane.PLAIN_MESSAGE);
			   }
			}
			} while(!formComplete);
		
		}
	}
	
	/*This method calls on methods from the BandMap class and the TimeSlotSet class
	 *which will load from files to the BandMap object and the three TimeSlotSet objects.
	 *Objects can be loaded either from the custom files or the default files.*/
	public void menuLoadFile() throws ClassNotFoundException, IOException
	{
		String msg = new String("Would you like to load the default files or the custom files?"
				+ "\nNOTE: loading a file will replace the current file held on the system.");
		String [] options = {"Load custom files", "Load default files"};
		int option = getComboBox(options,msg);
		
		if(option == 0)
		{
			
			if(!bandMap.loadCustomFile())
			{;}
			else
			{tent_a.loadCustomFile("CustomTimeSlotSetA.dat");
			tent_b.loadCustomFile("CustomTimeSlotSetB.dat");
			tent_c.loadCustomFile("CustomTimeSlotSetC.dat");
			}
		}
		
		else if(option == 1)
		{
			bandMap.loadDefaultFile();
			tent_a.loadDefaultFile("DefaultTimeSlotSetA.dat");
			tent_b.loadDefaultFile("DefaultTimeSlotSetB.dat");
			tent_c.loadDefaultFile("DefaultTimeSlotSetC.dat");
		}
	}
	
	/*This method calls on methods from the BandMap class and the TimeSlotSet class
	 *which will save to files from the BandMap object and the three TimeSlotSet objects.
	 *Objects can only be saved to the custom file.*/
	public void menuSaveFile() throws ClassNotFoundException, IOException
	{
		int response = JOptionPane.showConfirmDialog(null,"Pressing OK will result in the custom files "
				+ "being overwritten by the current files held on the system.",null, JOptionPane.OK_CANCEL_OPTION,JOptionPane.PLAIN_MESSAGE);
		
		
			if(response == JOptionPane.CANCEL_OPTION || response == JOptionPane.CLOSED_OPTION)
			{;}
			else
			{bandMap.saveCustomFileToDisk();
			tent_a.saveCustomFileToDisk("CustomTimeSlotSetA.dat");
			tent_b.saveCustomFileToDisk("CustomTimeSlotSetB.dat");
			tent_c.saveCustomFileToDisk("CustomTimeSlotSetC.dat");}
		
		
	}
	
	/*This method is called by both Binary Search type menu options. It essentially
	 * performs the Binary Search and returns the int value from the Binary Search.*/
	private int getBandIndexByPrice(ArrayList<Band> bandList,Double d)
	{
		Band b  = new Band(null,d,null);
		
		int index = Collections.binarySearch(bandList,b, (Band x, Band y)->{return (int) (Math.round(x.getPrice()) - Math.round(y.getPrice()));});
		return index;
	}
	
	/*This methid takes in a String,splits it by the spaces present in the String, 
	 *then re-formats each word to first letter: capital,rest of the word: lower case.
	 *It then puts the words back together and returns the String. */
	private String capitalise(String temp)
	{
			String[] words = temp.split("\\s");
		   	for(int i = 0;i < words.length;i++)
		   	{words[i] = words[i].substring(0, 1).toUpperCase() + words[i].substring(1).toLowerCase() + " ";}

		   	StringBuilder builder = new StringBuilder();
		   	for (String value : words) 
		   	{
		   	    builder.append(value);
		   	}
		   	temp = (builder.toString()).trim();
		   	
		   	return temp;
	}
	
	
	/*This method takes in the values required to create a Band object. It then performs validation
	 * ensuring the input is in the correct format and returns the values in the form of a String Array.*/
	private String[] takeInBandValues()
	{	
			String[] genre = {"Rock","Folk","Dance"};
			String[] temp = new String[3];
			
			String msg1 = new String("1. Please enter the name of the band :");
			String msg2 = new String("2. Please enter the price of the band :");
			String msg3 = new String("3. Please enter the genre of the band :");
			JTextField ans1 = new JTextField("");
			JTextField ans2 = new JTextField("");
			JComboBox<String> ans3 = new JComboBox<String>(genre);
			   
			Object message[] = new Object[6];

			message[0] = msg1;
			message[1] = ans1;
			message[2] = msg2;
			message[3] = ans2;
			message[4] = msg3;
			message[5] = ans3;

			boolean formComplete = true;
			do
			{
			formComplete = true;
			int response = JOptionPane.showConfirmDialog(null,message,"Festival Scheduling System",JOptionPane.OK_CANCEL_OPTION,
					                                        JOptionPane.PLAIN_MESSAGE);
			   
			if(response == JOptionPane.CANCEL_OPTION || response == JOptionPane.CLOSED_OPTION)
				   return null;
			else
			{ 		
				try {  		
					temp[0] = (ans1.getText());
					temp[1] = (ans2.getText());
					temp[2] = (genre[ans3.getSelectedIndex()]);
					if(temp[0].length() == 0 || temp[1].length() == 0)
					{throw new EmptyStringException();}
					Pattern p1 = Pattern.compile("  |.*\\d.*|[^a-zA-Z'\\s]");
					Matcher m1; 
					m1 = p1.matcher(temp[0]);
					if(temp[0].length() == 0 || m1.find())
					{throw new StringFormatException("Please leave ONE space only between words and ensure only alphabetic characters are entered.","'name of the band'");}
					temp[0] = capitalise(temp[0]);
						
					Pattern p2 = Pattern.compile("  |[a-zA-Z'\\s]|,");
					Matcher m2; 
					m2 = p2.matcher(temp[1]);
					if(m2.find())
					{throw new NumberFormatException();}
					if(Math.signum(Double.parseDouble(temp[1])) == -1)
					{throw new NegativeNumberException(temp[1]);}
					
					
				   	}
					catch (EmptyStringException e)
			  		{
						formComplete = false;
						JOptionPane.showMessageDialog(null,e.getMessage(),"Festival Scheduling System",JOptionPane.PLAIN_MESSAGE);
			  		}
				  	catch (StringFormatException e)
				  	{
				  		formComplete = false;
				  		JOptionPane.showMessageDialog(null,e.getMessage(),"Festival Scheduling System",JOptionPane.PLAIN_MESSAGE);
				    }
					catch (NumberFormatException e)
					{
						formComplete = false;
						JOptionPane.showMessageDialog(null,e.getMessage(),"Festival Scheduling System",JOptionPane.PLAIN_MESSAGE);
					}
					catch (NegativeNumberException e)
					{
						formComplete = false;
						JOptionPane.showMessageDialog(null,e.getMessage(),"Festival Scheduling System",JOptionPane.PLAIN_MESSAGE);
					}
			   	}
			   } while(!formComplete);
			   return temp;
	}

	/*This method unassigns a band from a TimeSlot.*/
	private void unassignBandFromTimeSlot(Band b,TimeSlot t)
	{
			t.setBandName("");
			b.setTimeSlot("Unassigned");	
	}
	
	/*This method assigns a band to a TimeSlot*/
	public static void assignBandToTimeSlot(Band b,TimeSlot t)
	{
			t.setBandName(b.getName());
			b.setTimeSlot(t.toString());	
	}
	
	/*This method creates a JoptionPane with a message and a ComboBox and displays it to the user. It populates
	 * the ComboBox with the String Array entered as a parameter. It then returns the 
	 * selected index.*/
	private static int getComboBox(String[] options, String msg)
			{
			JComboBox<String> choice = new JComboBox<String>(options);
			Object [] message = new Object[2];
			message[0] = msg;
			message[1] = choice;
			
			int response = JOptionPane.showConfirmDialog(null,message,"Festival Scheduling System",JOptionPane.OK_CANCEL_OPTION,
	                JOptionPane.PLAIN_MESSAGE);
			if(response == JOptionPane.CANCEL_OPTION || response == JOptionPane.CLOSED_OPTION)
			{return -666;}
		
					return choice.getSelectedIndex();
			}	
}
