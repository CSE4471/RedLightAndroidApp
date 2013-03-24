package ThrowAway;
import java.awt.print.PrinterException;

import javax.imageio.IIOException;

import com.github.kevinsawicki.http.HttpRequest;
 


public class HTTPCaller {
 
  /**
   * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String body = HttpRequest.get("http://redlights.herokuapp.com", true, "latitude", -40, "longitude", +83,"distance_in_miles",.0001).body();
		body = body.substring(69); //may need to change this index if the header changes, and perhaps define it somewhere
		String longitude;
		String latitude;//= t.substring(0, 10);
		double lat = 0, lon=0;
		String[] tokens = body.split("latitude\":");		
		
		for (String t : tokens){		

			if (t.charAt(0)=='-'){
				latitude = t.substring(0,11);
			}
			else{
				latitude = t.substring(0,10);
			}
			
			String[] token2 = t.split("longitude\":");	
			String s = token2[1];
				if (s.charAt(0)=='-'){
					if (s.charAt(4)=='.')
						{longitude = s.substring(0,12);}
					else longitude = s.substring(0,11);
				}
				else{
					if (s.charAt(3)=='.')
						{longitude = s.substring(0,11);}
					else longitude = s.substring(0,10);
				} 
				latitude = latitude.replace(",", "");
				longitude = longitude.replace(",", "");

				
				/*Here, i am going to make an OOP major sin, but it doesnt seem worth the trouble to make another class
				 * for this.  If anyone else would like to do so, feel free
				 */

				  try{
					  lat = Double.valueOf(latitude.trim()).doubleValue();
					  lon = Double.valueOf(longitude.trim()).doubleValue();
				  }
					    catch (NumberFormatException e){
					    System.out.println("NumberFormatException: " + e.getMessage());
				  }
					  
				  //NEED TO CONSTRUCT latlng objects, place into an array, and make this array available to other classes
				  //this is inside of the loop where the values are found and made into doubles.  each iteration, at this point
				  //the doubles need to be placed into the latlng object and this needs to be added to the array (or other similar structure)
				  
				  
				/*try{	
				System.out.println("latitude: " + lat + "longitude: " + lon);
				}
					finally{
						lat=0;
						lon=0;
					}*/
	}
}}
