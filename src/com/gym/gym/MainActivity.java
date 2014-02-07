package com.gym.gym;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {
	Button btnViewClients;
	Button btnViewDates;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		btnViewClients = (Button) findViewById(R.id.btnViewClientsProducts);
		btnViewDates =(Button) findViewById(R.id.btnViewdates);
		
		btnViewClients.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(getApplicationContext(),AllClientsActivity.class);
				startActivity(i);
			}
		});
		
		btnViewDates.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent i = new Intent(getApplicationContext(),Dates.class);
				startActivity(i);
			}
		});
	}
	
	
}
