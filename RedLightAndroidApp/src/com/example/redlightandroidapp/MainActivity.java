package com.example.redlightandroidapp;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;

public class MainActivity extends FragmentActivity {
	
	//Google Map
	private GoogleMap map;
	public static ArrayList<ArrayList<String>> points;
	/*Points is ArrayList of ArrayLists.  Points is populated by calling getPoints(double myLat, double myLon, int myDist)
	The members of points are ArrayLists containing the state, city, name of intersection, latitude, and longitude,
	respectively, for each red light within myDistance miles of the provided lat and lon points.*/
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		map = ((SupportMapFragment)  getSupportFragmentManager().findFragmentById(R.id.map))
	               .getMap();
	               
	        //BELOW IS CODE FOR CALLING THE HTTPREQUEST FUNCTION AND FILLING IN MARKERS
		/*
			getPoints(LAT, LON, DIST);
	        ArrayList<String> set;
	        double lat, lon;
	        for (int i=0; i< points.size(); i++){
	        	set = points.get(i);
				 try{
				  lat = Double.valueOf(set.get(3).trim()).doubleValue();
				  lon = Double.valueOf(set.get(4).trim()).doubleValue();
				 	}
				    catch (NumberFormatException e){
				    System.out.println("NumberFormatException: " + e.getMessage());
				    }
	        	Marker marker = map.addMarker(new MarkerOptions()
     				.position(new LatLng(lat, lon))
     				.title(set.get(2))
     				.snippet(set.get(1) + ", " + set.get(0)));
	        }
	        */
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
		
	public static void getPoints(double myLat, double myLon, int myDist) {
			// Creating HTTP client
		HttpClient httpClient = new DefaultHttpClient();
		 
		// Creating HTTP Post
		HttpPost httpPost = new HttpPost("http://redlights.herokuapp.com/in_proximity_of");
		
		List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>(3);
		nameValuePair.add(new BasicNameValuePair("latitude", String.valueOf(myLat)));
		nameValuePair.add(new BasicNameValuePair("longitude", String.valueOf(myLon)));
		nameValuePair.add(new BasicNameValuePair("distance_in_miles", String.valueOf(myDist)));
		String body="";
		
		
		// Url Encoding the POST parameters
		try {
		    httpPost.setEntity(new UrlEncodedFormEntity(nameValuePair));
		}
		catch (UnsupportedEncodingException e) {
		    // writing error to Log
		    e.printStackTrace();
		}
		
		// Making HTTP Request
		try {
		    HttpResponse response = httpClient.execute(httpPost);
		 
		    // writing response to log
		    body = response.toString();
		 
		} catch (ClientProtocolException e) {
		    // writing exception to log
		    e.printStackTrace();
		 
		} catch (IOException e) {
		    // writing exception to log
		    e.printStackTrace();
		}
		// TODO Auto-generated method stub
		//NOT USED String body = HttpRequest.get("http://redlights.herokuapp.com/in_proximity_of", true, "latitude", myLat, "longitude", myLon,"distance_in_miles",myDist).body();
		//System.out.println(body);
		//body = body.substring(69); //may need to change this index if the header changes, and perhaps define it somewhere
		String longitude="", latitude="", city="", state="", name="", entry = "", trash = "";
		//double lat = 0, lon=0;
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
