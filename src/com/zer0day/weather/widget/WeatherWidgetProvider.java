package com.zer0day.weather.widget;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.RemoteViews;
import android.widget.Toast;

public class WeatherWidgetProvider extends AppWidgetProvider {
	
	static int hour = 0;
	static int hour1 = 0;
	static int hour2 = 0;
	static int min = 0;
	static int min1 = 0;
	static int min2 = 0;
	static int month = 0;
	static int day = 0;
	static int day_number = 0;
	static String day_str;
	static String month_str;
	static String day_num_str;
	static String date_str;
	 
	private static SimpleDateFormat formatter = new SimpleDateFormat("dd MMM yyyy  hh:mm:ss a");
	static String strWidgetText = "";
	
	public static String MY_WIDGET_UPDATE = "MY_OWN_WIDGET_UPDATE";

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		super.onReceive(context, intent);
	  
		if(MY_WIDGET_UPDATE.equals(intent.getAction())){
			
			Bundle extras = intent.getExtras();
			if(extras!=null) {
				AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
				ComponentName thisAppWidget = new ComponentName(context.getPackageName(), WeatherWidgetProvider.class.getName());
                int[] appWidgetIds = appWidgetManager.getAppWidgetIds(thisAppWidget);

                updateClock(context, appWidgetManager);
				onUpdate(context, appWidgetManager, appWidgetIds);
			}
			
			Toast.makeText(context, "onReceiver()", Toast.LENGTH_LONG).show();
		}
	}
	
	@Override
	public void onDeleted(Context context, int[] appWidgetIds) {
		// TODO Auto-generated method stub
		//super.onDeleted(context, appWidgetIds);
		Toast.makeText(context, "onDeleted()", Toast.LENGTH_LONG).show();
	}

	static AlarmManager myAlarmManager;
	static PendingIntent myPendingIntent;
	
	@Override
	public void onDisabled(Context context) {
		// TODO Auto-generated method stub
		//super.onDisabled(context);
		myAlarmManager.cancel(myPendingIntent);
		Toast.makeText(context, "onDisabled()", Toast.LENGTH_LONG).show();
	}

	static void SaveAlarmManager(AlarmManager tAlarmManager, PendingIntent tPendingIntent)
	{
	 myAlarmManager = tAlarmManager;
	 myPendingIntent = tPendingIntent;
	}
	
	@Override
	public void onEnabled(Context context) {
		// TODO Auto-generated method stub
		//super.onEnabled(context);
		Toast.makeText(context, "onEnabled()", Toast.LENGTH_LONG).show();
	}
	
	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager,
			int[] appWidgetIds) {
		// TODO Auto-generated method stub
		super.onUpdate(context, appWidgetManager, appWidgetIds);

		final int N = appWidgetIds.length;
		for (int i=0; i<N; i++) {
			int appWidgetId = appWidgetIds[i];
			
			updateClock(context, appWidgetManager);
			updateAppWidget(context, appWidgetManager, appWidgetId);

			Toast.makeText(context, "onUpdate(): " + String.valueOf(i) + " : " +  String.valueOf(appWidgetId), Toast.LENGTH_LONG).show();
		}

	}
	
	public static void updateClock(Context context, AppWidgetManager appWidgetManager2)
	{
		RemoteViews remoteViews;
		AppWidgetManager appWidgetManager;
		ComponentName thisWidget;
		
		appWidgetManager = appWidgetManager2;
		remoteViews = new RemoteViews(context.getPackageName(), R.layout.weatherwidget_layout);
		thisWidget = new ComponentName(context, WeatherWidgetProvider.class);
		
		// Create Date object to get date
		Date date = new Date();
		// Get current hour
		hour = (date.getHours());
		// Get current minutes
		min = date.getMinutes();
		month = date.getMonth();
		day = date.getDay();
		day_number = date.getDate();
		
		if(hour < 19)
			remoteViews.setImageViewResource(R.id.wall, R.drawable.partly_cloudy);
		else
			remoteViews.setImageViewResource(R.id.wall, R.drawable.partly_cloudy_night);
		
		switch(day)
		{
		case 0:
			date_str = "Sun, ";
			break;
		case 1:
			date_str = "Mon, ";
			break;
		case 2:
			date_str = "Tue, ";
			break;
		case 3:
			date_str = "Wed, ";
			break;
		case 4:
			date_str = "Thu, ";
			break;
		case 5:
			date_str = "Fri, ";
			break;
		case 6:
			date_str = "Sat, ";
		}
		
		switch(month)
		{
		case 0:
			date_str += "Jan ";
			break;
		case 1:
			date_str += "Feb ";
			break;
		case 2:
			date_str += "Mar ";
			break;
		case 3:
			date_str += "Apr ";
			break;
		case 4:
			date_str += "May ";
			break;
		case 5:
			date_str += "Jun ";
			break;
		case 6:
			date_str += "Jul ";
			break;
		case 7:
			date_str += "Aug ";
			break;
		case 8:
			date_str += "Sep ";
			break;
		case 9:
			date_str += "Oct ";
			break;
		case 10:
			date_str += "Nov ";
			break;
		case 11:
			date_str += "Dec ";
		}
		
		date_str += day_number;
		
		remoteViews.setTextViewText(R.id.widget_date, date_str);
		
		// Calculate numbers for first and second digit of hour
		if(hour < 10)
		{
			hour1 = 0;
			hour2 = hour;
		}
		else if(hour < 20)
		{
			hour1 = 1;
			hour2 = hour - 10;
		}
		else
		{
			hour1 = 2;
			hour2 = hour - 20;
		}
		
		// Calculates first and second digits of minutes
		if(min < 10)
		{
			min1 = 0;
			min2 = min;
		}
		else if(min < 20)
		{
			min1 = 1;
			min2 = min - 10;
		}
		else if(min < 30)
		{
			min1 = 2;
			min2 = min - 20;
		}
		else if(min < 40)
		{
			min1 = 3;
			min2 = min - 30;
		}
		else if(min < 50)
		{
			min1 = 4;
			min2 = min - 40;
		}
		else
		{
			min1 = 5;
			min2 = min - 50;
		}
				
		// First digit of hour
		switch(hour1)
		{
		case 0:
			remoteViews.setImageViewResource(R.id.widget_hour1, R.drawable.font1_0);
			break;
		case 1:
			remoteViews.setImageViewResource(R.id.widget_hour1, R.drawable.font1_1);
			break;
		case 2:
			remoteViews.setImageViewResource(R.id.widget_hour1, R.drawable.font1_2);
			break;
		}
		
/*		remoteViews.setImageViewResource(R.id.widget_hour2, R.drawable.font1_9);
*/			
		// Second digit of hour
		switch(hour2)
		{
		case 0:
			remoteViews.setImageViewResource(R.id.widget_hour2, R.drawable.font1_0);
			break;
		case 1:
			remoteViews.setImageViewResource(R.id.widget_hour2, R.drawable.font1_1);
			break;
		case 2:
			remoteViews.setImageViewResource(R.id.widget_hour2, R.drawable.font1_2);
			break;
		case 3:
			remoteViews.setImageViewResource(R.id.widget_hour2, R.drawable.font1_3);
			break;
		case 4:
			remoteViews.setImageViewResource(R.id.widget_hour2, R.drawable.font1_4);
			break;
		case 5:
			remoteViews.setImageViewResource(R.id.widget_hour2, R.drawable.font1_5);
			break;
		case 6:
			remoteViews.setImageViewResource(R.id.widget_hour2, R.drawable.font1_6);
			break;
		case 7:
			remoteViews.setImageViewResource(R.id.widget_hour2, R.drawable.font1_7);
			break;
		case 8:
			remoteViews.setImageViewResource(R.id.widget_hour2, R.drawable.font1_8);
			break;
		case 9:
			remoteViews.setImageViewResource(R.id.widget_hour2, R.drawable.font1_9);
			break;		
		}
		
		// First digit of minute
		switch(min1)
		{
		case 0:
			remoteViews.setImageViewResource(R.id.widget_min1, R.drawable.font1_0);
			break;
		case 1:
			remoteViews.setImageViewResource(R.id.widget_min1, R.drawable.font1_1);
			break;
		case 2:
			remoteViews.setImageViewResource(R.id.widget_min1, R.drawable.font1_2);
			break;
		case 3:
			remoteViews.setImageViewResource(R.id.widget_min1, R.drawable.font1_3);
			break;
		case 4:
			remoteViews.setImageViewResource(R.id.widget_min1, R.drawable.font1_4);
			break;
		case 5:
			remoteViews.setImageViewResource(R.id.widget_min1, R.drawable.font1_5);
			break;
		}
		
		// Second Digit of minutes
		switch(min2)
		{
		case 0:
			remoteViews.setImageViewResource(R.id.widget_min2, R.drawable.font1_0);
			break;
		case 1:
			remoteViews.setImageViewResource(R.id.widget_min2, R.drawable.font1_1);
			break;
		case 2:
			remoteViews.setImageViewResource(R.id.widget_min2, R.drawable.font1_2);
			break;
		case 3:
			remoteViews.setImageViewResource(R.id.widget_min2, R.drawable.font1_3);
			break;
		case 4:
			remoteViews.setImageViewResource(R.id.widget_min2, R.drawable.font1_4);
			break;
		case 5:
			remoteViews.setImageViewResource(R.id.widget_min2, R.drawable.font1_5);
			break;
		case 6:
			remoteViews.setImageViewResource(R.id.widget_min2, R.drawable.font1_6);
			break;
		case 7:
			remoteViews.setImageViewResource(R.id.widget_min2, R.drawable.font1_7);
			break;
		case 8:
			remoteViews.setImageViewResource(R.id.widget_min2, R.drawable.font1_8);
			break;
		case 9:
			remoteViews.setImageViewResource(R.id.widget_min2, R.drawable.font1_9);
			break;		
		}
		
		//remoteViews.setTextViewText(R.id.widget_textview,
		//		"Time = " + format.format(new Date()));
		appWidgetManager.updateAppWidget(thisWidget, remoteViews);
	}
	
	public static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
			int appWidgetId){
		String currentTime = formatter.format(new Date());
		strWidgetText = currentTime;

		RemoteViews updateViews = new RemoteViews(context.getPackageName(), R.layout.weatherwidget_layout);
		updateViews.setTextViewText(R.id.widget_date, "[" + String.valueOf(appWidgetId) + "]" + strWidgetText);
		appWidgetManager.updateAppWidget(appWidgetId, updateViews);

		updateClock(context,appWidgetManager);
		Toast.makeText(context, "updateAppWidget(): " + String.valueOf(appWidgetId) + "\n" + strWidgetText, Toast.LENGTH_LONG).show();

	}
	
}