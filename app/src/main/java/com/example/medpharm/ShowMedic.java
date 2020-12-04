package com.example.medpharm;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


import java.util.List;

public class ShowMedic extends ArrayAdapter<Medicament> {
    private Activity context;
    private List<Medicament> mediclist;
    public ShowMedic(Activity context,List<Medicament> mediclist){
     super(context,R.layout.listmedic,mediclist);
     this.context=context;
     this.mediclist=mediclist;
    }


    public View getView(int position,  View convertView,  ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View listview=inflater.inflate(R.layout.listmedic,null,true);
        TextView nameview=(TextView)listview.findViewById(R.id.medic);
        TextView laboview=(TextView)listview.findViewById(R.id.lab);
        TextView presview=(TextView)listview.findViewById(R.id.presen);
        TextView cinview=(TextView)listview.findViewById(R.id.coni);
        TextView cmiview=(TextView)listview.findViewById(R.id.conmi);
        TextView cmxview=(TextView)listview.findViewById(R.id.conmx);
        TextView prview=(TextView)listview.findViewById(R.id.mg);

        Medicament medicament=mediclist.get(position);
        nameview.setText("Nom: "+medicament.getNomMedicament());
        laboview.setText("Nom du labo: "+medicament.getNomLabo());
        presview.setText("Présentation: "+medicament.getPresentation());
        cinview.setText("Concentration initiale: "+medicament.getConcentrationinit());
        cmiview.setText("Concentration minimale: "+medicament.getConcentrationmin());
        cmxview.setText("Concentration maximale: "+medicament.getConcentrationmax());
        prview.setText("Prix: "+medicament.getPrix());

        return listview;
    }
}
