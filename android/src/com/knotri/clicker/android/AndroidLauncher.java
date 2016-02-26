package com.knotri.clicker.android;

import android.accounts.AccountManager;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import com.backendless.Backendless;
import com.backendless.BackendlessCollection;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.persistence.BackendlessDataQuery;
import com.backendless.persistence.QueryOptions;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.badlogic.gdx.utils.Array;
import com.knotri.clicker.MyGame;
import com.knotri.clicker.screen.TopScreen;

import java.util.LinkedList;
import java.util.List;

import static com.knotri.clicker.screen.TopScreen.*;

public class AndroidLauncher extends AndroidApplication implements MyGame.RequestHandler{
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		initialize(new MyGame(this), config);

		String appVersion = "v1";



		Backendless.initApp( this, "9F098758-0B92-8278-FF69-6A5DA0D0C900", "A17FBAB2-513A-2815-FF87-E43D4CEC5F00", appVersion );

	}

	public String getEMail() {
		AccountManager manager = AccountManager.get(this);
		android.accounts.Account[] accounts = manager.getAccountsByType("com.google");
		List<String> possibleEmails = new LinkedList<String>();

		for (android.accounts.Account account : accounts) {
			// TODO: Check possibleEmail against an email regex or treat
			// account.name as an email address only for certain account.type
			// values.
			possibleEmails.add(account.name);
		}

		if (!possibleEmails.isEmpty() && possibleEmails.get(0) != null) {
			String email = possibleEmails.get(0);
			return email;
		} else
			return null;
	}

	@Override
	public void saveRecord(int score){
		Record record = new Record();
		record.result = score;
		record.email = getEMail();

		final SharedPreferences settings = getSharedPreferences("UID_ball2048", 0);
		record.objectId = settings.getString("record_objectID", null);


		Backendless.Persistence.save(record, new AsyncCallback<Record>() {
			public void handleResponse( Record response )
			{
				Log.i("backendless" , "All OK");
				SharedPreferences.Editor editor = settings.edit();
				editor.putString("record_objectID", response.objectId);
				editor.commit();
				//response.objectId;
			}

			public void handleFault( BackendlessFault fault )
			{
				Log.i("backendless" , fault.getDetail() + fault.getCode() + fault.toString());
				// an error has occurred, the error code can be retrieved with fault.getCode()
			}
		});
	}

	@Override
	public String getTopRecord(final Array<TopScreen.ItemRecord> recordArray){
		String output = "";
		BackendlessDataQuery dataQuery = new BackendlessDataQuery();
		dataQuery.setPageSize(3);
		QueryOptions queryOptions = new QueryOptions();
		queryOptions.addSortByOption("result DESC");
		queryOptions.setPageSize(3);
		dataQuery.setQueryOptions(queryOptions);
		//dataQuery.setWhereClause( whereClause );
//		BackendlessCollection<Record> resultQuery = Backendless.Persistence.of( Record.class ).find( dataQuery );
//		for(Record record : resultQuery.getCurrentPage() ){
//			output += record.result + " , ";
//		}


		Backendless.Persistence.of( Record.class).find( dataQuery ,  new AsyncCallback<BackendlessCollection<Record>>(){
			@Override
			public void handleResponse( BackendlessCollection<Record> response )
			{
				for( Record record : response.getCurrentPage()){
					recordArray.add( new TopScreen.ItemRecord(record.name, record.result));
				}

				// a Contact instance has been found by ObjectId
			}
			@Override
			public void handleFault( BackendlessFault fault )
			{
				// an error has occurred, the error code can be retrieved with fault.getCode()
			}
		});
		return output;
	}
}
