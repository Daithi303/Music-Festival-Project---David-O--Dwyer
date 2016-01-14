package Main_Package;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;



public class BandMap implements Serializable{
	

	private static final long serialVersionUID = 1L;
	private static Map<String, Band> bandMap;
	private File customBandMapFile = new File("CustomBandMap.dat");
	private File defaultBandMapFile = new File("DefaultBandMap.dat");
	
	/*This constructor initializes the HashMap bandMap.*/
	public BandMap()
	
	{
		bandMap = new HashMap< String, Band>();
		/*Band a = new Band("The National",5000.00,"Rock");
		Band b = new Band("The Who",10000.00,"Rock");
		Band c = new Band("The Music",7000.00,"Rock");
		Band d = new Band("Foster And Allen",1300.00,"Folk");
		Band e = new Band("Adam Clay",2300.00,"Folk");
		Band f = new Band("Orbital",6300.00,"Dance");
		Band g = new Band("Groove Armada",4100.00,"Dance");
		
		Band h = new Band("The ational",5000.00,"Rock");
		Band i = new Band("The ho",10000.00,"Rock");
		Band j = new Band("The usic",7000.00,"Rock");
		Band k = new Band("Foster nd Allen",1300.00,"Rock");
		Band l = new Band("Adam lay",2300.00,"Rock");
		Band m = new Band("rbital",6300.00,"Rock");
		Band n = new Band("roove Armada",4100.00,"Rock");
		
		bandMap.put(a.getName().toLowerCase(),a);
		bandMap.put(b.getName().toLowerCase(),b);
		bandMap.put(c.getName().toLowerCase(),c);
		bandMap.put(d.getName().toLowerCase(),d);
		bandMap.put(e.getName().toLowerCase(),e);
		bandMap.put(f.getName().toLowerCase(),f);
		bandMap.put(g.getName().toLowerCase(),g);
		
		bandMap.put(h.getName().toLowerCase(),h);
		bandMap.put(i.getName().toLowerCase(),i);
		bandMap.put(j.getName().toLowerCase(),j);
		bandMap.put(k.getName().toLowerCase(),k);
		bandMap.put(l.getName().toLowerCase(),l);
		bandMap.put(m.getName().toLowerCase(),m);
		bandMap.put(n.getName().toLowerCase(),n);*/
		}
	
	/*This method takes in a Band,checks if a band already exists with a key the same as the bands name
	 * (The Band's name is used as the key),if not then it adds it to the bandMap*/
	public void addBandToSet(Band b)
	{
		if(bandMap.containsKey(b.getName().toLowerCase()))
		JOptionPane.showMessageDialog(null,"Band cannot be added as a band with that name already exists within the system.","Festival Scheduling System",JOptionPane.PLAIN_MESSAGE);
		else
		{bandMap.put(b.getName().toLowerCase(), b);
		JOptionPane.showMessageDialog(null,"The band is added to the system","Festival Scheduling System",JOptionPane.PLAIN_MESSAGE);
		}
	}
	
	
	/*This method removes a Band (by key) from the bandMap*/
	public void removeBandFromSet(String s)
	{
	
			bandMap.remove(s);
		JOptionPane.showMessageDialog(null,"The band has been removed.","Festival Scheduling System",JOptionPane.PLAIN_MESSAGE);
	}
	
	/*This method checks to see if the bandMap is empty.*/
	public boolean isEmpty()
	{
			return bandMap.isEmpty();
	}
	
	
	/*This method uses the containsKey method to check if there is a Band in the bandMap
	 * with a key the same as the String entered as a parameter.*/
	public String searchSetByName(String s)
	{
		
		if(!bandMap.containsKey(s.toLowerCase()))
		{
			   
			   return null;
		}
		
		else
		{
			return s.toLowerCase();
			
		}
	}
	
	
	/*This method creates an ArrayList from the bandMap,
	 * sorts it by price, then reverses it. Then displays the results in a JTable(Which is in a JScrollPane,in a JOptionPane)*/
	public void listBandsByPrice()
	{
		ArrayList <Band> list = new ArrayList <Band> (bandMap.values());
		
		Collections.sort(list, (Band a, Band b)->{return (int) (Math.round(a.getPrice()) - Math.round(b.getPrice()));});
		Collections.reverse(list);
		JOptionPane.showMessageDialog(null,getJTable(list),"Festival Scheduling System - List bands in order of ascending price",JOptionPane.PLAIN_MESSAGE);
}
	/*This method creates an ArrayList from the bandMap,
	 * sorts it by name, then displays the results in a JTable(Which is in a JScrollPane,in a JOptionPane)*/
	public void listBandsInAlphabeticalOrder()
	{
		ArrayList <Band> list = new ArrayList <Band> (bandMap.values());
		Collections.sort(list, (Band a, Band b)->{return a.getName().compareTo(b.getName());});
		JOptionPane.showMessageDialog(null,getJTable(list),"Festival Scheduling System - List bands in alphabetical order",JOptionPane.PLAIN_MESSAGE);
}

	/*This method creates a String Array from the values of the objects in the ArrayList entered in as a parameter.
	 * It then uses that String Array to create a JTable. It puts the JTable in a JScrollPane and returns it.*/
	public static JScrollPane getJTable(ArrayList<Band> list)
	{
		String [][] tempArray = new String[list.size()][4];
		int i = 0;
		for(Band x : list)
		{	String[] a = {x.getName(),Double.toString(x.getPrice()),x.getGenre(),x.getTimeSlot()};
			tempArray [i] = a;
		i++;
		}
		
		Object rowData[][] = tempArray;;
		Object columnNames[] = { "Band Name", "Price", "Genre","Time Slot"};
		JTable table = new JTable(rowData, columnNames);
		JScrollPane tableScroll = new JScrollPane(table);
		
		
		return tableScroll;
	}
	
	
	/*This method creates and returns a sub list of bands based on the values of the three String parameters.*/
	public static ArrayList<Band> getSubList(String s,String s1,String s2)
	{	
		ArrayList <Band> subList = new ArrayList<Band>();
		ArrayList <Band> list = new ArrayList <Band> (bandMap.values());
		Collections.sort(list, (Band a, Band b)->{return a.getName().compareTo(b.getName());});
		
		if(s != null && s1 == null && s2 == null)
		{
		for(Band x : list)
		{
			if((x.getTimeSlot()).equals(s))
			{
				subList.add(x);
			}
		}
		
		return subList;
		}
		
		if(s == null && s1 != null && s2 == null)
		{
			for(Band x : list)
			{
				if((x.getName().toLowerCase()).equals(s1))
				{
					subList.add(x);
				}
			}
			
			return subList;	
			
		}
		if(s != null && s1 == null && (!s2.equals("Not Null")))
		{
			for(Band x : list)
			{
				if((x.getTimeSlot()).equals(s) && (x.getGenre()).equals(s2))
				{
					subList.add(x);
				}
			}
			
			return subList;	
		}
		
		if(s != null && s1 == null && s2.equals("Not Null"))
		{
			for(Band x : list)
			{
				if(!(x.getTimeSlot().equals(s)))
				{
					subList.add(x);
				}
			}
			return subList;	
		}
		if(s == null && s1 == null && s2 == null)
		{
			return list;	
		}
		
		return null;
	}
	
	/*This method clears the bandMap.*/
	public void clearBandMap()
	{
		bandMap.clear();
	}
	
	/*This method loads a bandMap Object to the system's bandMap from the custom bandMap file.*/
	@SuppressWarnings({ "unchecked" })
	public boolean loadCustomFile() throws ClassNotFoundException, IOException
	{
		try
		{
			if(!customBandMapFile.exists())
			{throw new FileNotFoundException();}
		
		FileInputStream fis = new FileInputStream (customBandMapFile);
		ObjectInputStream ois = new ObjectInputStream(fis);
		bandMap = (Map<String, Band>)ois.readObject();
		ois.close();
		fis.close();
		
		
		JOptionPane.showMessageDialog(null,"Custom band map has been loaded.","Festival Scheduling System",JOptionPane.PLAIN_MESSAGE);
		return true;
		}catch(FileNotFoundException e)
		{JOptionPane.showMessageDialog(null,"There is currently no custom band map file saved to disk.","Festival Scheduling System",JOptionPane.PLAIN_MESSAGE);
			return false;
		}
	}
	
	/*This method saves the current state of the system's bandMap to the custom bandMap file.*/
	public void saveCustomFileToDisk() throws IOException
	{

		
		
		ObjectOutputStream oos = null;
		FileOutputStream fos = new FileOutputStream (customBandMapFile);

		oos = new ObjectOutputStream(fos);
		oos.flush();
		oos.writeObject(bandMap);
		oos.close();
		fos.close();	
		JOptionPane.showMessageDialog(null,"Custom band map has been saved to the custom band map file.","",JOptionPane.PLAIN_MESSAGE);
	}
	
	
	/*This method creates a HashMap of type band, creates Band objects,
	 * populates the HashMap with the Bands, then saves it to the custom bandMap file.*/

	public void saveDefaultFileToDisk() throws IOException
	{
		Map<String, Band> defaultBandMap;
		defaultBandMap = new HashMap< String, Band>();
		Band a = new Band("The National",5000.00,"Rock");
		Band b = new Band("The Who",10000.00,"Rock");
		Band c = new Band("The Music",7000.00,"Rock");
		Band d = new Band("Foster And Allen",1300.00,"Folk");
		Band e = new Band("Adam Clay",2300.00,"Folk");
		Band f = new Band("Orbital",6300.00,"Dance");
		Band g = new Band("Groove Armada",4100.00,"Dance");
		g.setTimeSlot("Friday at Eight");
		c.setTimeSlot("Saturday at Ten");
		defaultBandMap.put(a.getName().toLowerCase(),a);
		defaultBandMap.put(b.getName().toLowerCase(),b);
		defaultBandMap.put(c.getName().toLowerCase(),c);
		defaultBandMap.put(d.getName().toLowerCase(),d);
		defaultBandMap.put(e.getName().toLowerCase(),e);
		defaultBandMap.put(f.getName().toLowerCase(),f);
		defaultBandMap.put(g.getName().toLowerCase(),g);
		
		ObjectOutputStream oos = null;
		
		FileOutputStream fos = new FileOutputStream (defaultBandMapFile);

		oos = new ObjectOutputStream(fos);
		oos.flush();
		oos.writeObject(defaultBandMap);
		oos.close();
		fos.close();	
	}
	
	
	/*This method loads a bandMap Object to the system's bandMap from the default bandMap file.*/

	@SuppressWarnings("unchecked")
	public void loadDefaultFile() throws ClassNotFoundException, IOException, FileNotFoundException
	{
		try
		{
		if(!defaultBandMapFile.exists())
		{throw new FileNotFoundException();}
		
		}catch(FileNotFoundException e)
		{JOptionPane.showMessageDialog(null,"File doesn't exist, writing file to disk.","Festival Scheduling System",JOptionPane.PLAIN_MESSAGE);
		saveDefaultFileToDisk();}
		
		FileInputStream fis = new FileInputStream (defaultBandMapFile);
		ObjectInputStream ois = new ObjectInputStream(fis);
		//bandMap.clear();
		bandMap = (Map<String,Band>)ois.readObject();
		ois.close();
		fis.close();
		JOptionPane.showMessageDialog(null,"Default band map has been loaded.","Festival Scheduling System",JOptionPane.PLAIN_MESSAGE);
	}
}
