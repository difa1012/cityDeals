package com.citydeals.ui;

import java.text.DecimalFormat;
import java.util.ArrayList;

import android.content.Context;
import android.text.SpannableString;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.citydeals.Deal;
import com.citydeals.R;
import com.citydeals.service.LocationService;
import com.citydeals.util.StringUtil;

public class DealsArrayAdapter extends ArrayAdapter<String> {

	private final Context context;
	private final String[] values;
	private final ArrayList<Deal> deals;

	public static String[] getHeadlines(ArrayList<Deal> deals) {
		String[] headlines = new String[deals.size()];
		for (int i = 0; i < deals.size(); i++) {
			headlines[i] = deals.get(i).getHeadline();
		}
		return headlines;

	}

	public DealsArrayAdapter(Context context, ArrayList<Deal> deals) {
		super(context, R.layout.deals_list_item, getHeadlines(deals));
		this.deals = deals;
		this.context = context;
		this.values = getHeadlines(deals);

	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		View rowView = inflater
				.inflate(R.layout.deals_list_item, parent, false);
		TextView textViewHeadline = (TextView) rowView
				.findViewById(R.id.headline);
		TextView textViewDistance = (TextView) rowView
				.findViewById(R.id.distance);

		ImageView imageView = (ImageView) rowView.findViewById(R.id.logo);

		String[] splitteHeadline = StringUtil.splitLine(values[position]);

		String line = splitteHeadline[0] + "\n" + splitteHeadline[1];

		SpannableString spanHead = new SpannableString(line);
		spanHead.setSpan(new StyleSpan(android.graphics.Typeface.BOLD), 0,
				splitteHeadline[0].length(), 0);
		spanHead.setSpan(new RelativeSizeSpan(1.3f), 0,
				splitteHeadline[0].length(), 0);

		textViewHeadline.setText(spanHead);

		Deal d = deals.get(position);

		Float distance = d.getDistance();
		textViewDistance.setText(LocationService.formattedDistance(distance));

		setImageByCategory(d.getCategory().getCategory(), imageView);

		return rowView;
	}

	private void setImageByCategory(String category, ImageView i) {

		if (category.equals("FoodDrink")) {
			i.setImageResource(R.drawable.food_icon);
		} else if (category.equals("Shopping")) {

			i.setImageResource(R.drawable.shopping_icon);
		} else if (category.equals("CarTransport")) {

			i.setImageResource(R.drawable.transport_icon);
		} else if (category.equals("Service")) {

			i.setImageResource(R.drawable.service_icon);
		} else if (category.equals("Recreation")) {

			i.setImageResource(R.drawable.recreation_icon);
		}
	}

}
