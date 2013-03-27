package ThrowAway;

import java.util.ArrayList;
import java.util.Scanner;

import com.github.kevinsawicki.http.HttpRequest;


//import com.google.android.gms.maps.model.LatLng;



public class HTTPCaller {

  /**
	 * @param args
	 */
	public static ArrayList< ArrayList<String> > points;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String body = HttpRequest.get("http://redlights.herokuapp.com", true, "latitude", -40, "longitude", +83,"distance_in_miles",.0001).body();
		//System.out.println(body);
		//body = body.substring(69); //may need to change this index if the header changes, and perhaps define it somewhere
		String longitude="", latitude="", city="", state="", name="", entry = "", trash = "";
		double lat = 0, lon=0;
		points = new ArrayList< ArrayList <String> >();
		
		
		Scanner scanner = new Scanner(body);
		scanner.useDelimiter("}");
		while (body.length()>0 && !scanner.hasNext("]") ){


			if (scanner.hasNext()){
				entry = scanner.next();
			}
			Scanner scan2 = new Scanner(entry);
			scan2.useDelimiter("\":");

			if (scan2.hasNext()){
				trash = scan2.next();
			}
			if (scan2.hasNext()){
				state = scan2.next();
				state = (String) state.subSequence(1, state.indexOf("\","));
			}

			if (scan2.hasNext()){
				city = scan2.next();
				city = (String) city.subSequence(1, city.indexOf("\","));
			}

			if (scan2.hasNext()){
				name = scan2.next();
				name = (String) name.subSequence(1, name.indexOf("\","));				
			}

			if (scan2.hasNext()){
				longitude = scan2.next();
				longitude = (String) longitude.subSequence(0, longitude.indexOf(",\""));
			}
	
			if (scan2.hasNext()){
				latitude = scan2.next();
				latitude = (String) latitude.subSequence(0, latitude.indexOf(",\""));				
			}
			
			/*  try{
				  lat = Double.valueOf(latitude.trim()).doubleValue();
				  lon = Double.valueOf(longitude.trim()).doubleValue();
			  }
				    catch (NumberFormatException e){
				    System.out.println("NumberFormatException: " + e.getMessage());
			  }*/
			  
			ArrayList<String> vals = new ArrayList <String> ();
			vals.add(state);
			vals.add(city);
			vals.add(name);
			vals.add(latitude);
			vals.add(longitude);
			points.add(vals);
			//System.out.println(state + " " + city + " " + name + " " + lon + " " + lat +"\n");
		}

	}
}
