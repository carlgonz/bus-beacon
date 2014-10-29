package org.altbeacon.beaconreference;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.altbeacon.beacon.Beacon;

import java.util.ArrayList;

/**
 * Created by VAIO on 29-10-2014.
 */
public class Adapter  extends ArrayAdapter<Beacon>{
    ArrayList<Beacon> beacon;
    Activity context;

    Adapter(Activity context,ArrayList<Beacon> datos) {
        super(context, R.layout.item, datos);
        this.context = context;
        this.beacon = datos;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View item = inflater.inflate(R.layout.item, null);

        TextView lblTitulo = (TextView)item.findViewById(R.id.bus_id);
        lblTitulo.setText(beacon.get(position).toString());

        TextView lblSubtitulo = (TextView)item.findViewById(R.id.bus_distance);
        lblSubtitulo.setText(beacon.get(position).getDistance()+"");

        return(item);
    }

}
