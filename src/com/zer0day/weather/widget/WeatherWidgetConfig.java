package com.zer0day.weather.widget;

import java.util.Calendar;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class WeatherWidgetConfig extends Activity {

	Button configOkButton;
	int mAppWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	
		setResult(RESULT_CANCELED);
	
		setContentView(R.layout.config);
		
		configOkButton = (Button)findViewById(R.id.saveconfig);
		configOkButton.setOnClickListener(configOkButtonOnClickListener);
		
		Intent intent = getIntent();
		Bundle extras = intent.getExtras();
		if (extras != null) {
		    mAppWidgetId = extras.getInt(
		            AppWidgetManager.EXTRA_APPWIDGET_ID,
		            AppWidgetManager.INVALID_APPWIDGET_ID);
		}

	 // If they gave us an intent without the widget id, just bail.
	 if (mAppWidgetId == AppWidgetManager.INVALID_APPWIDGET_ID) {
	     	finish();
	 	}
	}

private Button.OnClickListener configOkButtonOnClickListener = new Button.OnClickListener(){

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			
			final Context context = WeatherWidgetConfig.this;
			
			AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
			
			//RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.hellowidget_layout);
			//appWidgetManager.updateAppWidget(mAppWidgetId, views);
			WeatherWidgetProvider.updateAppWidget(context, appWidgetManager, mAppWidgetId);
			
			Toast.makeText(context, "HelloWidgetConfig.onClick(): " + String.valueOf(mAppWidgetId) , Toast.LENGTH_LONG).show();
			
			//prepare Alarm Service to trigger Widget
			Intent intent = new Intent(WeatherWidgetProvider.MY_WIDGET_UPDATE);
			PendingIntent pendingIntent = PendingIntent.getBroadcast(WeatherWidgetConfig.this, 0, intent, 0);
			AlarmManager alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);
			Calendar calendar = Calendar.getInstance();
			calendar.setTimeInMillis(System.currentTimeMillis());
			calendar.add(Calendar.SECOND, 10);
			alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), 20*1000, pendingIntent);
			
			Intent resultValue = new Intent();
			resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, mAppWidgetId);
			setResult(RESULT_OK, resultValue);
			finish();
		}};
}
