package org.altbeacon.beaconreference;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Locale;

import android.app.Activity;

import android.os.Bundle;
import android.os.RemoteException;
import android.speech.tts.TextToSpeech;
import android.widget.ListView;

import org.altbeacon.beacon.Beacon;
import org.altbeacon.beacon.BeaconConsumer;
import org.altbeacon.beacon.BeaconManager;
import org.altbeacon.beacon.RangeNotifier;
import org.altbeacon.beacon.Region;

public class RangingActivity extends Activity implements BeaconConsumer {
    protected static final String TAG = "RangingActivity";
    private BeaconManager beaconManager = BeaconManager.getInstanceForApplication(this);
    private ListView list;
    ArrayList<Beacon> beacons_l;
    Adapter adapter;
    private TextToSpeech busTTS;
    private String appTitle;
    Integer ticks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
        list = (ListView)findViewById(R.id.listView);
        beaconManager.bind(this);
        beaconManager.debug = true;
        beacons_l = new ArrayList<Beacon>();
        adapter = new Adapter(RangingActivity.this, beacons_l);
        busTTS = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {

            }
        });
        busTTS.setLanguage(Locale.getDefault());
        appTitle = findViewById(R.id.appTitle).toString();
        ticks = 0;
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
                    beacons_l.clear();
                    beacons_l.addAll(beacons);

                    ticks ++;
                    logToDisplay(beacons_l);
                    speech(beacons_l);
                }
            }
        });

        try {
            beaconManager.startRangingBeaconsInRegion(new Region("myRangingUniqueId", null, null, null));
        } catch (RemoteException e) {   }
    }

    private void speech(final ArrayList<Beacon> beacons) {
        Integer current = ticks%beacons.size();
        if(current%2 == 0) {
            Beacon current_beacon = beacons.get(current);
            String report = "Recorrido " + current_beacon.getId3() + ", a " + String.format("%.1f", current_beacon.getDistance()) + "metros.";
            busTTS.speak(report, TextToSpeech.QUEUE_FLUSH, null);
        }
    }

    private void logToDisplay(final ArrayList<Beacon> l) {
    	runOnUiThread(new Runnable() {
    	    public void run() {
                adapter = new Adapter(RangingActivity.this,l);
                list.setAdapter(adapter);
    	    }
    	});
    }


}
