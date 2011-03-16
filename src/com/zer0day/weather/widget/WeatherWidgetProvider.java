package com.zer0day.weather.widget;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.Toast;

public class WeatherWidgetProvider extends AppWidgetProvider {
	 
	private static SimpleDateFormat formatter = new SimpleDateFormat("dd MMM yyyy  hh:mm:ss a");
	static String strWidgetText = "";
	
	public static String MY_WIDGET_UPDATE = "MY_OWN_WIDGET_UPDATE";

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		super.onReceive(context, intent);
	  
		if(MY_WIDGET_UPDATE.equals(intent.getAction())){
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
			updateAppWidget(context, appWidgetManager, appWidgetId);

			Toast.makeText(context, "onUpdate(): " + String.valueOf(i) + " : " +  String.valueOf(appWidgetId), Toast.LENGTH_LONG).show();
		}

	}
	
	public static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
			int appWidgetId){
		String currentTime = formatter.format(new Date());
		strWidgetText = currentTime;

		RemoteViews updateViews = new RemoteViews(context.getPackageName(), R.layout.weatherwidget_layout);
		updateViews.setTextViewText(R.id.widget_date, "[" + String.valueOf(appWidgetId) + "]" + strWidgetText);
		appWidgetManager.updateAppWidget(appWidgetId, updateViews);

		Toast.makeText(context, "updateAppWidget(): " + String.valueOf(appWidgetId) + "\n" + strWidgetText, Toast.LENGTH_LONG).show();

	}
	
}