package com.gym.gym;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;

public class Dates extends ListActivity {

	private ProgressDialog pDialog;

	JSONPaser jPaser = new JSONPaser();

	ArrayList<HashMap<String, String>> dateList;

	private String url_dates = "http://www.akampuriraadvocates.co.ug/gym_api/get_dates.php";

	private static final String TAG_SUCCESS = "success";
	private static final String TAG_REPORTS = "report";
	private static final String TAG_DATES = "date";
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
			List<NameValuePair> params = new ArrayList<NameValuePair>();

			JSONObject json = jPaser.makeHttpRequest(url_dates, "GET", params);
			Log.d("dates", json.toString());
			try {
				int success = json.getInt(TAG_SUCCESS);

				if (success == 1) {
					dates = json.getJSONArray(TAG_REPORTS);

					for (int i = 0; i < dates.length(); ++i) {
						JSONObject c = dates.getJSONObject(i);

						String date = c.getString(TAG_DATES);
						String total = c.getString(TAG_TOTAL);

						HashMap<String, String> map = new HashMap<String, String>();

						map.put(TAG_DATES, date);
						map.put(TAG_TOTAL, total);

						dateList.add(map);
					}
				}

				if (success == 0) {
					Intent i = new Intent(getApplicationContext(),
							MainActivity.class);
					i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivity(i);
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			pDialog.dismiss();

			runOnUiThread(new Runnable() {

				@Override
				public void run() {
					ListAdapter adapter = new SimpleAdapter(Dates.this,
							dateList, R.layout.list_dates, new String[] {
									TAG_DATES, TAG_TOTAL }, new int[] {
									R.id.dates, R.id.total });
					setListAdapter(adapter);

				}
			});
		}

	}

}
