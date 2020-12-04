package com.example.medpharm;



import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class Maj<pubic> extends AppCompatActivity {

    Medicament med;
    Button b, b1;
    MedicCtrl medicCtrl;
    ListView lis;
    SearchView sear;
    DatabaseReference ref;
    List<Medicament> medlst;
    String n;
    AlertDialog.Builder modif, ajouti;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maj);
        b = (Button) findViewById(R.id.buttondos);
        b1 = (Button) findViewById(R.id.button1ret);
        modif = new AlertDialog.Builder(Maj.this);
        ajouti = new AlertDialog.Builder(Maj.this);
        sear = (SearchView) findViewById(R.id.search);
        lis = (ListView) findViewById(R.id.lst);
        sear.setQueryHint("Tapez le nom du médicament");
        med = new Medicament();
        medlst = new ArrayList<>();
        ref = FirebaseDatabase.getInstance().getReference("Medicament");
        medicCtrl = new MedicCtrl(ref);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Maj.this, CalculDos.class);
                startActivity(intent);
            }
        });
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openajou();
            }
        });

        lis.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Medicament m = medlst.get(position);
                n = m.getNomMedicament();
                openlist(n);

                return true;
            }
        });
        lis.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Medicament me = medlst.get(position);
                final String medm = me.getNomMedicament();
                AlertDialog.Builder alert = new AlertDialog.Builder(Maj.this);
                alert.setMessage("Voulez vous supprimer le médicament ?")
                        .setCancelable(false)
                        .setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                medicCtrl.deletemed(medm);
                                Toast.makeText(Maj.this, "Médicament supprimé", Toast.LENGTH_LONG).show();
                                dialog.cancel();
                            }
                        })
                        .setNegativeButton("Non", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                AlertDialog aler = alert.create();
                aler.setTitle("Supression de: " + medm);
                aler.show();
            }
        });
        sear.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(final String newText) {

                medicCtrl.show(newText, medlst);
                ShowMedic adap = new ShowMedic(Maj.this, medlst);
                lis.setAdapter(adap);
                return true;
            }
        });
    }

    protected void onStart() {
        super.onStart();
        ref.addValueEventListener(new ValueEventListener() {

            public void onDataChange(DataSnapshot dataSnapshot) {
                medlst.clear();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    Medicament medi = ds.getValue(Medicament.class);
                    medlst.add(medi);
                }
                ShowMedic adap = new ShowMedic(Maj.this, medlst);
                lis.setAdapter(adap);
            }

            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void openajou() {
        View view = getLayoutInflater().inflate(R.layout.dialogajou, null);
        final AlertDialog dialog;
        final EditText nomm, labb, pree, cinit, cminim, cma, prx;
        Button but, bu;
        nomm = (EditText) view.findViewById(R.id.nom);
        labb = (EditText) view.findViewById(R.id.labo);
        pree = (EditText) view.findViewById(R.id.pres);
        cinit = (EditText) view.findViewById(R.id.cini);
        cminim = (EditText) view.findViewById(R.id.cmi);
        cma = (EditText) view.findViewById(R.id.cmax);
        prx = (EditText) view.findViewById(R.id.pri);
        but = (Button) view.findViewById(R.id.b);
        bu = (Button) view.findViewById(R.id.buu);
        ajouti.setView(view);
        dialog=ajouti.create();
        dialog.show();
        dialog.setTitle("Ajouter Médicament");
        bu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
        but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!nomm.getText().toString().trim().isEmpty() && !labb.getText().toString().trim().isEmpty() && !pree.getText().toString().trim().isEmpty() && !cinit.getText().toString().trim().isEmpty() && !cminim.getText().toString().trim().isEmpty() && !cma.getText().toString().trim().isEmpty() && !prx.getText().toString().trim().isEmpty()) {
                    final String na = nomm.getText().toString();
                    final String l = labb.getText().toString();
                    final int pr = Integer.parseInt(pree.getText().toString());
                    final double c = Double.parseDouble(cinit.getText().toString());
                    final double ci = Double.parseDouble(cminim.getText().toString());
                    final double cm = Double.parseDouble(cma.getText().toString());
                    final double pi = Double.parseDouble(prx.getText().toString());
                    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Medicament");
                    reference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.child(na).exists()) {
                                Toast.makeText(Maj.this, na + " existe déja ", Toast.LENGTH_LONG).show();
                            } else {
                                medicCtrl.ajoutMedic(na, l, pr, c, ci, cm, pi);
                                Toast.makeText(Maj.this, na + " ajouté avec succées ", Toast.LENGTH_LONG).show();
                                dialog.cancel();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                } else {

                    if (nomm.getText().toString().trim().isEmpty())
                        nomm.setError("Champs obligatoire");
                    if (labb.getText().toString().trim().isEmpty())
                        labb.setError("Champs obligatoitre");
                    if (pree.getText().toString().trim().isEmpty())
                        pree.setError("Champs obligatoire");
                    if (cinit.getText().toString().trim().isEmpty())
                        cinit.setError("Champs obligatoire");
                    if (cminim.getText().toString().trim().isEmpty())
                        cminim.setError("Champs obligatoire");
                    if (cma.getText().toString().trim().isEmpty())
                        cma.setError("Champs obligatoire");
                    if (prx.getText().toString().trim().isEmpty())
                        prx.setError("Champs obligatoire");
                }
            }
        });

    }


    public void openlist(final String md) {
        View view = getLayoutInflater().inflate(R.layout.dialogajou, null);
        final AlertDialog dialog;
        final EditText nomm, labb, pree, cinit, cminim, cma, prx;
        Button but, bu;
        nomm = (EditText) view.findViewById(R.id.nom);
        labb = (EditText) view.findViewById(R.id.labo);
        pree = (EditText) view.findViewById(R.id.pres);
        cinit = (EditText) view.findViewById(R.id.cini);
        cminim = (EditText) view.findViewById(R.id.cmi);
        cma = (EditText) view.findViewById(R.id.cmax);
        prx = (EditText) view.findViewById(R.id.pri);
        but = (Button) view.findViewById(R.id.b);
        bu = (Button) view.findViewById(R.id.buu);
        modif.setView(view);
        dialog = modif.create();
        dialog.setTitle("Modification de " + n);
        dialog.show();
        bu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
        but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!nomm.getText().toString().trim().isEmpty()||!labb.getText().toString().trim().isEmpty() || !pree.getText().toString().trim().isEmpty() || !cinit.getText().toString().trim().isEmpty() || !cminim.getText().toString().trim().isEmpty() ||! cma.getText().toString().trim().isEmpty() || !prx.getText().toString().trim().isEmpty()) {
                    if (!nomm.getText().toString().trim().isEmpty()) {
                        if (!labb.getText().toString().trim().isEmpty() && !pree.getText().toString().trim().isEmpty() && !cinit.getText().toString().trim().isEmpty() && !cminim.getText().toString().trim().isEmpty() && !cma.getText().toString().trim().isEmpty() && !prx.getText().toString().trim().isEmpty()) {
                            final String na = nomm.getText().toString();
                            final String l = labb.getText().toString();
                            final int pr = Integer.parseInt(pree.getText().toString());
                            final double c = Double.parseDouble(cinit.getText().toString());
                            final double ci = Double.parseDouble(cminim.getText().toString());
                            final double cm = Double.parseDouble(cma.getText().toString());
                            final double pi = Double.parseDouble(prx.getText().toString());
                            medicCtrl.modifMedic(md, na, l, pr, c, ci, cm, pi);
                            Toast.makeText(Maj.this, md + " modifié", Toast.LENGTH_LONG).show();
                        } else {
                            if (labb.getText().toString().trim().isEmpty())
                                labb.setError("Champs obligatoitre");
                            if (pree.getText().toString().trim().isEmpty())
                                pree.setError("Champs obligatoire");
                            if (cinit.getText().toString().trim().isEmpty())
                                cinit.setError("Champs obligatoire");
                            if (cminim.getText().toString().trim().isEmpty())
                                cminim.setError("Champs obligatoire");
                            if (cma.getText().toString().trim().isEmpty())
                                cma.setError("Champs obligatoire");
                            if (prx.getText().toString().trim().isEmpty())
                                prx.setError("Champs obligatoire");
                        }

                    } else {
                        final String l;
                        final int pr;
                        final double c, ci, cm, pi;
                        if (!labb.getText().toString().trim().isEmpty())
                            l = labb.getText().toString();
                        else
                            l = null;
                        if (!pree.getText().toString().trim().isEmpty())
                            pr = Integer.parseInt(pree.getText().toString());
                        else
                            pr = 0;
                        if (!cinit.getText().toString().trim().isEmpty())
                            c = Double.parseDouble(cinit.getText().toString());
                        else
                            c = 0;
                        if (!cminim.getText().toString().trim().isEmpty())
                            ci = Double.parseDouble(cminim.getText().toString());
                        else
                            ci = 0;
                        if (!cma.getText().toString().trim().isEmpty())
                            cm = 0;
                        else
                            cm = 0;
                        if (!prx.getText().toString().trim().isEmpty())
                            pi = Double.parseDouble(prx.getText().toString());
                        else
                            pi = 0;
                        String na = null;
                        medicCtrl.modifMedic(md, na, l, pr, c, ci, cm, pi);
                        Toast.makeText(Maj.this, md + "modifié", Toast.LENGTH_LONG).show();
                    }
                }
                else
                    Toast.makeText(Maj.this, "Remplissez au moin un champs !", Toast.LENGTH_LONG).show();
            }
        });

    }
}

