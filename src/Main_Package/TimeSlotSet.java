package Main_Package;
import java.awt.Dimension;
import java.awt.Window;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;

public class TimeSlotSet implements Serializable{
	
	private static final long serialVersionUID = 1L;
	String file = "";
	Set<TimeSlot> timeslot_collection;
	Set<TimeSlot> default_timeslot_collection;
	private File customTimeSlotSetFile;
	private File defaultTimeSlotSetFile;
	
	/*This method initializes the tree set timeslot_collection then uses a nested for loop to populate it TimeSlot objects.*/
	public TimeSlotSet()
	{
	
	final Integer[] timeSlotId = {new Integer(101), new Integer(102),new Integer(103),new Integer(104),new Integer(105),new Integer(106),
			new Integer(201),new Integer(202),new Integer(203),new Integer(204),new Integer(205),new Integer(206)};
	int i = 0;
	timeslot_collection = new TreeSet<TimeSlot>();
	
	for(TimeSlot.Day day : TimeSlot.Day.values())
		{
		for(TimeSlot.Time time : TimeSlot.Time.values())
			{
				timeslot_collection.add(new TimeSlot(day, time,timeSlotId[i]));
				++i;
			}
		}
		
	}
	
	/*This method returns a TimeSlotSet object*/
	public Set<TimeSlot> getTimeslot_collection() {
		return timeslot_collection;
	}

	/*this method uses an iterator of type TimeSlot to check if the objects
	 * in the iterator have a BandName variable equal to the String entered as a parameter.*/
	public ArrayList<TimeSlot> SubListByBandName(String bandName)
	{
		
		Iterator<TimeSlot> it = timeslot_collection.iterator();
		ArrayList<TimeSlot> subList = new ArrayList<TimeSlot>();
		TimeSlot a;
		while(it.hasNext() ) 
		{
			a = it.next();		
		if((a.getBandName().toLowerCase()).equals(bandName.toLowerCase()))
		{
			subList.add(a);
			}
		}

		return subList;
	}
	
	
	/*This method passes on the choice variable into the getTable method along with
	 * an ArrayList containing an object of TimeSlotSet.*/
	public JScrollPane getTimeTable(int choice)
	{
		ArrayList <TimeSlot> list = new ArrayList <TimeSlot>();
		list.addAll(timeslot_collection);
		return getJTable(list,choice);
	
	}
	
	/*This method sets the BandName of a TimeSlot object with the appropriate index to
	 * the String value entered in as a parameter.*/
	public void setBand(int i, String s)
	{
		ArrayList <TimeSlot> list = new ArrayList <TimeSlot>();
		list.addAll(timeslot_collection);
		list.get(i).setBandName(s);
	}
	
	
	/*This method creates a JTable and populates it with data from the appropriate TimeSlotSet object
	 * (This is defined by the choice variable). It also uses a mouse listener to identify which cell has
	 * been double clicked. Depending on the value stored in the clicked cell, certain functions are performed.*/
	private JScrollPane getJTable(ArrayList<TimeSlot> list,int choice)
	{
		String [][] tempArray = new String[2][7];
		for(int i = 0;i < 2;i++)
		{	String[] a = {list.get(i*6).getDay().toString(),list.get(i*6).getBandName(),
				list.get((i*6)+1).getBandName(),list.get((i*6)+2).getBandName(),
				list.get((i*6)+3).getBandName(),list.get((i*6)+4).getBandName(),
				list.get((i*6)+5).getBandName()};
			tempArray [i] = a;
		}
		
		Object rowData[][] = tempArray;;
		Object columnNames[] = { "Day","Two","Four","Six","Eight","Ten","Twelve"};
		JTable table = new JTable(rowData, columnNames);
		table.addMouseListener(new MouseAdapter() {
			  public void mousePressed(MouseEvent e) {
			    if (e.getClickCount() == 2) {
			      JTable target = (JTable)e.getSource();
			      if(table.getModel().getValueAt(target.getSelectedRow(),target.getSelectedColumn()).equals(""))
			      {
			    	  int r = target.getSelectedRow(),c = target.getSelectedColumn();

			    	  if(r==0)
			    	  {c--;}
			    	 
			    	  String [] genre = {"Rock","Folk","Dance"};
			    	  ArrayList<TimeSlot> a;
			    	  Band b = Manager.getBandChoice("Unassigned",null,genre[choice]);
			    	  if(b == null)
			    		  ;
			    	  else
			    	  { a = new ArrayList<TimeSlot>(Manager.getTentTimeSlots(choice));
			    	  TimeSlot t = a.get(r*5+c);
			    	  Manager.assignBandToTimeSlot( b,t);
			    	  JOptionPane.showMessageDialog(null,"The band has been allocated to that timeslot","Festival Scheduling System",JOptionPane.PLAIN_MESSAGE);
			    	  Window win = SwingUtilities.getWindowAncestor(table);
			    	  win.setVisible(false);}
			    	 
			    	 
			      }
			      else
			      {
			    	  JScrollPane j = BandMap.getJTable(BandMap.getSubList(null,((String) table.getModel().getValueAt(target.getSelectedRow(),target.getSelectedColumn())).toLowerCase(),null));
			    	  j.setPreferredSize(new Dimension(500,40));
			    	  JOptionPane.showMessageDialog(null,j,"Festival Scheduling System - Rock Tent",JOptionPane.PLAIN_MESSAGE);

			      }
			     
			    }
			  }
			});
		JScrollPane tableScroll = new JScrollPane(table);
		tableScroll.setPreferredSize(new Dimension(1100,55));
		table.getColumnModel().getColumn(0).setPreferredWidth(70);
		table.getColumnModel().getColumn(1).setPreferredWidth(150);
		table.getColumnModel().getColumn(2).setPreferredWidth(150);
		table.getColumnModel().getColumn(3).setPreferredWidth(150);
		table.getColumnModel().getColumn(4).setPreferredWidth(150);
		table.getColumnModel().getColumn(5).setPreferredWidth(150);
		table.getColumnModel().getColumn(6).setPreferredWidth(150);
		
		return tableScroll;
	}
	
	/*This method loads a timeSlot Object to the system's timeslot_collection from the custom timeslot_collection file.
	 * Which object is loaded from which file is determined by the String variable sent in as a parameter.*/
	@SuppressWarnings({ "unchecked" })
	public void loadCustomFile(String file) throws ClassNotFoundException, IOException
	{
		this.file = file;
		customTimeSlotSetFile = new File(file);
		try
		{
			if(!customTimeSlotSetFile.exists())
			{throw new FileNotFoundException();}
		
		FileInputStream fis = new FileInputStream (customTimeSlotSetFile);
		ObjectInputStream ois = new ObjectInputStream(fis);
		timeslot_collection = (Set<TimeSlot>)ois.readObject();
		ois.close();
		fis.close();
	
		
		JOptionPane.showMessageDialog(null,"Custom time slot set '"+file+"' has been loaded.","Festival Scheduling System",JOptionPane.PLAIN_MESSAGE);
		}catch(FileNotFoundException e) {JOptionPane.showMessageDialog(null,"There is currently no custom band map file saved to disk.","Festival Scheduling System",JOptionPane.PLAIN_MESSAGE);}
	}
	
	/*This method saves a TimeSlotSet file to a custom TimeSlotSet file.*/
	public void saveCustomFileToDisk(String file) throws IOException
	{
		this.file = file;
		customTimeSlotSetFile = new File(file);
		ObjectOutputStream oos = null;
		FileOutputStream fos = new FileOutputStream (customTimeSlotSetFile);

		oos = new ObjectOutputStream(fos);
		oos.flush();
		oos.writeObject(timeslot_collection);
		oos.close();
		fos.close();	
		JOptionPane.showMessageDialog(null,"The appropriate time slot set has been saved to '"+file+"'.","Festival Scheduling System",JOptionPane.PLAIN_MESSAGE);
	}
	
	
	/*This method creates and object of type TimeSlotSet then saves it to the default TimeslotSet file.*/
	public void saveDefaultFileToDisk(String file) throws IOException
	{
		this.file = file;
		defaultTimeSlotSetFile = new File(file);
		
		final Integer[] timeSlotId = {new Integer(101), new Integer(102),new Integer(103),new Integer(104),new Integer(105),new Integer(106),
				new Integer(201),new Integer(202),new Integer(203),new Integer(204),new Integer(205),new Integer(206)};
		int i = 0;
		default_timeslot_collection = new TreeSet<TimeSlot>();
		
		for(TimeSlot.Day day : TimeSlot.Day.values())
			{
			for(TimeSlot.Time time : TimeSlot.Time.values())
				{	TimeSlot t = new TimeSlot(day, time,timeSlotId[i]);
					default_timeslot_collection.add(t);
					++i;
					if(file.equals("DefaultTimeSlotSetC.dat"))
					{ if(t.toString().equals("Friday at Eight"))
						{t.setBandName("Groove Armada");}}
					if(file.equals("DefaultTimeSlotSetA.dat"))
					{ if(t.toString().equals("Saturday at Ten"))
						{t.setBandName("The Music");}}
				}
			}
		
		ObjectOutputStream oos = null;
		
		FileOutputStream fos = new FileOutputStream (defaultTimeSlotSetFile);

		oos = new ObjectOutputStream(fos);
		oos.flush();
		oos.writeObject(default_timeslot_collection);
		oos.close();
		fos.close();	
	}
	
	
	/*This method loads a TimeSlotSet object from a default TimeSlotSet file to the appropriate TimeSlotSet object.*/
	@SuppressWarnings("unchecked")
	public void loadDefaultFile(String file) throws ClassNotFoundException, IOException, FileNotFoundException
	{
		this.file = file;
		defaultTimeSlotSetFile = new File(file);
		try
		{
		if(!defaultTimeSlotSetFile.exists())
		{throw new FileNotFoundException(file);}
		
		}catch(FileNotFoundException e)
		{JOptionPane.showMessageDialog(null,"Default time slot file '"+e.getMessage()+"' doesn't exist, writing file to disk.","Festival Scheduling System",JOptionPane.PLAIN_MESSAGE);
		saveDefaultFileToDisk(file);}
		
		FileInputStream fis = new FileInputStream (defaultTimeSlotSetFile);
		ObjectInputStream ois = new ObjectInputStream(fis);
		timeslot_collection.clear();
		timeslot_collection = (Set<TimeSlot>)ois.readObject();
		ois.close();
		fis.close();
		JOptionPane.showMessageDialog(null,"Default time slot file '"+file+"' has now been loaded.","Festival Scheduling System",JOptionPane.PLAIN_MESSAGE);
	}
	
}
