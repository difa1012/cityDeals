package com.citydeals.service;

import java.text.DecimalFormat;
import java.util.List;

import android.content.Context;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;

public class LocationService {

	private Location location;

	@SuppressWarnings("static-access")
	public LocationService(Context context) {
		LocationManager locationManager = (LocationManager) context
				.getSystemService(context.LOCATION_SERVICE);

		Criteria criteria = new Criteria();
		String provider = locationManager.getBestProvider(criteria, false);
		location = locationManager.getLastKnownLocation(provider);
		if(location == null){
			location = new Location(provider);
		}
	}

	public Location getLocation() {
		return location;
	}

	public Address getAdress(Context context) {
		Geocoder gc = new Geocoder(context);
		List<Address> addressList = null;

		try {
			addressList = gc.getFromLocation(location.getLatitude(),
					location.getLongitude(), 1);
		} catch (Exception e) {
			return null;
		}

		if (addressList != null) {
			return addressList.get(0);
		} else {
			return null;
		}
	}

	public Float calculateDistance(Location dest) {

		if (dest != null) {
			return this.location.distanceTo(dest);
		} else {
			return null;
		}
	}
	
	public static String formattedDistance(Float distance)
	{
		if (distance != null) {

			if (distance < 1000) {
				DecimalFormat df = new DecimalFormat("####");
				return df.format(distance) + " m";
			} else {
				distance = distance / 1000;
				DecimalFormat df = null;
				if (distance < 99) {
					df = new DecimalFormat("###.##");
				} else {
					df = new DecimalFormat("###");
				}
				return df.format(distance) + "\n" + "km";
			}
		}
		return "";
	}
}
