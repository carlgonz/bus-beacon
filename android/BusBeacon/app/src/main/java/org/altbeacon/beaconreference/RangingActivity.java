package org.altbeacon.beaconreference;

import java.util.ArrayList;
import java.util.Collection;

import android.app.Activity;

import android.os.Bundle;
import android.os.RemoteException;
import android.util.Log;
import android.widget.EditText;
import android.widget.ListView;

import org.altbeacon.beacon.AltBeacon;
import org.altbeacon.beacon.Beacon;
import org.altbeacon.beacon.BeaconConsumer;
import org.altbeacon.beacon.BeaconManager;
import org.altbeacon.beacon.BeaconParser;
import org.altbeacon.beacon.RangeNotifier;
import org.altbeacon.beacon.Region;

public class RangingActivity extends Activity implements BeaconConsumer {
    protected static final String TAG = "RangingActivity";
    private BeaconManager beaconManager = BeaconManager.getInstanceForApplication(this);
    private ListView list;
    ArrayList<Beacon> l;
    Adapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
        list = (ListView)findViewById(R.id.listView);
        beaconManager.bind(this);
        beaconManager.debug = true;
        l = new ArrayList<Beacon>();
        adapter = new Adapter(RangingActivity.this,l);

    }
    @Override 
    protected void onDestroy() {
        super.onDestroy();
        beaconManager.unbind(this);
    }
    @Override 
    protected void onPause() {
    	super.onPause();
    	if (beaconManager.isBound(this)) beaconManager.setBackgroundMode(true);
    }
    @Override 
    protected void onResume() {
    	super.onResume();
    	if (beaconManager.isBound(this)) beaconManager.setBackgroundMode(false);
    }

    @Override
    public void onBeaconServiceConnect() {
        beaconManager.setRangeNotifier(new RangeNotifier() {
        @Override 
        public void didRangeBeaconsInRegion(Collection<Beacon> beacons, Region region) {
            if (beacons.size() > 0) {
                l.clear();
                l.addAll(beacons);

            	//EditText editText = (EditText)RangingActivity.this
				//		.findViewById(R.id.rangingText);

            	logToDisplay(l);
            }
        }

        });

        try {
            beaconManager.startRangingBeaconsInRegion(new Region("myRangingUniqueId", null, null, null));
        } catch (RemoteException e) {   }
    }
    private void logToDisplay(final ArrayList<Beacon> l) {
    	runOnUiThread(new Runnable() {
    	    public void run() {
    	    	//EditText editText = (EditText)RangingActivity.this
    			//		.findViewById(R.id.rangingText);
    	    	//editText.append(line+"\n");
                adapter = new Adapter(RangingActivity.this,l);
                list.setAdapter(adapter);

    	    }
    	});
    }
}
