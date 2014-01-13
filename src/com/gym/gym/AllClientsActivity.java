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
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class AllClientsActivity extends ListActivity {
	// Progress Dialog
	private ProgressDialog pDialog;

	// Creating JSON Parser object
	JSONPaser jPaser = new JSONPaser();

	ArrayList<HashMap<String, String>> clientList;

	// url to get all products list
	private static String url_all_clients = "http://www.akampuriraadvocates.co.ug/gym_api/get_all_clients.php";

	// JSON Node names
	private static final String TAG_SUCCESS = "success";
	private static final String TAG_CLIENTS = "clients";
	private static final String TAG_CID = "cid";
	private static final String TAG_NAME = "names";
	private static final String TAG_DATE = "date";
	private static final String TAG_AMOUNT = "amount";
	private static final String TAG_INSTRUCTOR = "instructor_name";

	JSONArray clients = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.all_clients);

		// Hashmap for Listview
		clientList = new ArrayList<HashMap<String, String>>();

		// loading clients in background thread
		new LoadAllClients().execute();

		ListView lv = getListView();

		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				String cid = ((TextView) view.findViewById(R.id.cid)).getText()
						.toString();
			}

		});

	}

	class LoadAllClients extends AsyncTask<String, String, String> {

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			pDialog = new ProgressDialog(AllClientsActivity.this);
			pDialog.setMessage("Loading Records. Please Wait.....");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(false);
			pDialog.show();
		}

		@Override
		protected String doInBackground(String... arg0) {
			// TODO Auto-generated method stub
			List<NameValuePair> params = new ArrayList<NameValuePair>();

			JSONObject json = jPaser.makeHttpRequest(url_all_clients, "GET",
					params);
			Log.d("All clients", json.toString());

			try {
				int success = json.getInt(TAG_SUCCESS);

				if (success == 1) {
					clients = json.getJSONArray(TAG_CLIENTS);

					for (int i = 0; i < clients.length(); ++i) {
						JSONObject c = clients.getJSONObject(i);

						String id = c.getString(TAG_CID);
						String name = c.getString(TAG_NAME);
						String date = c.getString(TAG_DATE);
						String amount = c.getString(TAG_AMOUNT);
						String instructor = c.getString(TAG_INSTRUCTOR);

						Log.d("clients", name);

						HashMap<String, String> map = new HashMap<String, String>();

						map.put(TAG_CID, id);
						map.put(TAG_NAME, name);
						map.put(TAG_DATE, date);
						map.put(TAG_AMOUNT, amount);
						map.put(TAG_INSTRUCTOR, instructor);

						clientList.add(map);
					}
				} else {
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
					// TODO Auto-generated method stub
					ListAdapter adapter = new SimpleAdapter(
							AllClientsActivity.this, clientList,
							R.layout.list_client, new String[] { TAG_CID,
									TAG_NAME, TAG_DATE, TAG_AMOUNT,
									TAG_INSTRUCTOR }, new int[] { R.id.cid,
									R.id.name, R.id.date, R.id.amount,
									R.id.instructor });

					setListAdapter(adapter);
				}
			});
		}

	}

}
