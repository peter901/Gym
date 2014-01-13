package com.gym.gym;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;

public class Dates extends ListActivity {

	private ProgressDialog pDialog;

	JSONPaser jPaser = new JSONPaser();

	ArrayList<HashMap<String, String>> dateList;

	private String url_dates = "http://www.akampuriraadvocates.co.ug/gym_api/get_dates.php";

	private static final String TAG_SUCCESS = "success";
	private static final String TAG_DATES = "dates";
	private static final String TAG_TOTAL = "total";

	JSONArray dates = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.all_dates);

		dateList = new ArrayList<HashMap<String, String>>();

		new Load_all_dates().execute();

	}

	class Load_all_dates extends AsyncTask<String, String, String> {

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			pDialog = new ProgressDialog(Dates.this);
			pDialog.setMessage("Loading. Please wait....");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(false);
			pDialog.show();
		}

		@Override
		protected String doInBackground(String... arg0) {

			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
		}

	}

}
