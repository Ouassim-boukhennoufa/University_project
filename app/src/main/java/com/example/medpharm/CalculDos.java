package com.example.medpharm;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class CalculDos extends AppCompatActivity {
    EditText chek;
    static String surf, n;
    String ci,r,q,poso;
   Double vol;
    AlertDialog.Builder ajout,rech;
     String adm,nm,cm,cx,pr;;
    EditText nom, nmr, pre, tai, poi, sur, medi, s, c;
    EditText d,M,P;
    TextView text1,text2,text3;
    Button verf, ajo, anu, cal, ann, va,aj,V,b1;
    static DatabaseReference ref,re;
    PatientCtrl patientCtrl;
    public static int numero;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calcul_dos);
        ajout=new AlertDialog.Builder(CalculDos.this);
        rech=new AlertDialog.Builder(CalculDos.this);
        chek = (EditText) findViewById(R.id.check);
        verf = (Button) findViewById(R.id.verif);
        b1=(Button)findViewById(R.id.rub);
        aj=(Button)findViewById(R.id.ajou);
        M=(EditText) findViewById(R.id.editText);
        P=(EditText)findViewById(R.id.editText2);
        V=(Button)findViewById(R.id.button);
        text1=(TextView)findViewById(R.id.textView);
        text2=(TextView)findViewById(R.id.textView2);
        text3=(TextView)findViewById(R.id.textView3);
        text3.setVisibility(View.GONE);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(CalculDos.this,Maj.class);
                startActivity(intent);
            }
        });

        ref = FirebaseDatabase.getInstance().getReference("Patient");
        re=FirebaseDatabase.getInstance().getReference("Medicament");
        patientCtrl = new PatientCtrl(ref);
        verf.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (!chek.getText().toString().trim().isEmpty()){
                    numero = Integer.parseInt(chek.getText().toString());
                ref.addListenerForSingleValueEvent(new ValueEventListener() {

                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        final boolean d = dataSnapshot.child(String.valueOf(numero)).exists();
                        if (d == true) {
                            dialogu(numero);
                        } else {
                            Toast.makeText(CalculDos.this, "Patient n'existe pas veuillez l'ajouter", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }
                else
                    chek.setError("Champs obligatoire");
            }
            });
        aj.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                dialogue();
            }
        });
    V.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            final String p,m;
            if(!P.getText().toString().trim().isEmpty()&&!M.getText().toString().trim().isEmpty()) {
                p = P.getText().toString();
                m = M.getText().toString();
                check(m, p);
            }
            else{
                if(P.getText().toString().trim().isEmpty())
                    P.setError("Champs obligatoire");
                if(M.getText().toString().trim().isEmpty())
                    M.setError("Champs obligatoire");
            }
        }
    });
    }

    public void dialogue() {
       View dialog=getLayoutInflater().inflate(R.layout.addpat,null);
       final AlertDialog dialogue;
        nmr = (EditText) dialog.findViewById(R.id.nmrpat);
        nom = (EditText) dialog.findViewById(R.id.nompat);
        pre = (EditText) dialog.findViewById(R.id.prepat);
        tai = (EditText) dialog.findViewById(R.id.taillepat);
        poi = (EditText) dialog.findViewById(R.id.poipat);
        sur = (EditText) dialog.findViewById(R.id.surpat);
        ajo = (Button) dialog.findViewById(R.id.aj);
        anu = (Button) dialog.findViewById(R.id.an);
        ajout.setCancelable(false);
        ajout.setView(dialog);
        dialogue=ajout.create();
        dialogue.show();
        dialogue.setTitle("Ajouter patient");
        anu.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                dialogue.cancel();
            }
        });
        ajo.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (!nmr.getText().toString().trim().isEmpty() && !nom.getText().toString().trim().isEmpty() && !pre.getText().toString().trim().isEmpty() && !tai.getText().toString().trim().isEmpty() && !poi.getText().toString().trim().isEmpty() && !sur.getText().toString().trim().isEmpty()){
                    final String n = nom.getText().toString();
                final String p = pre.getText().toString();
                final int nm = Integer.parseInt(nmr.getText().toString());
                final double ta = Double.parseDouble(tai.getText().toString());
                final double po = Double.parseDouble(poi.getText().toString());
                final double su = Double.parseDouble(sur.getText().toString());
                ref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        final boolean d = dataSnapshot.child(String.valueOf(nm)).exists();
                        if (d == false) {
                            patientCtrl.ajoutpat(n, p, nm, ta, su, po);
                            Toast.makeText(CalculDos.this, n + "Ajouté avec succés", Toast.LENGTH_LONG).show();
                            dialogue.cancel();
                        } else {
                            Toast.makeText(CalculDos.this, n + "Existe déja", Toast.LENGTH_LONG).show();
                            dialogue.cancel();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
                else{

                     if(nmr.getText().toString().trim().isEmpty())
                         nmr.setError("Champs obligatoire");
                     if(nom.getText().toString().trim().isEmpty())
                         nom.setError("Champs obligatoire");
                     if(pre.getText().toString().trim().isEmpty())
                         pre.setError("Champs obligatoire");
                     if(tai.getText().toString().trim().isEmpty())
                         tai.setError("Champs obligatoire");
                     if(poi.getText().toString().trim().isEmpty())
                         poi.setError("Champs obligatoire");
                     if(sur.getText().toString().trim().isEmpty())
                         sur.setError("Champs obligatoire");
                    }
            }
        });

    }
   public void dialogu(final int numeroo){
        View dialo = getLayoutInflater().inflate(R.layout.calcul,null);
                final  AlertDialog dialog;
                va = (Button) dialo.findViewById(R.id.val);
                medi = (EditText) dialo.findViewById(R.id.nm);
                s = (EditText) dialo.findViewById(R.id.sc);
                d = (EditText) dialo.findViewById(R.id.da);
                c = (EditText) dialo.findViewById(R.id.coinitiale);
                ann = (Button) dialo.findViewById(R.id.a);
                cal = (Button) dialo.findViewById(R.id.c);
                rech.setView(dialo);
                dialog=rech.create();
                dialog.setTitle("Calculer dosage");
                dialog.show();
                ann.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.cancel();
                    }
                });
                va.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        if (!medi.getText().toString().trim().isEmpty()) {
                            n = medi.getText().toString();
                            re.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    final boolean d = dataSnapshot.child(n).exists();
                                    if (d == true) {
                                        ci = String.valueOf(dataSnapshot.child(n).child("concentrationinit").getValue());
                                        cm = String.valueOf(dataSnapshot.child(n).child("concentrationmin").getValue());
                                        cx = String.valueOf(dataSnapshot.child(n).child("concentrationmax").getValue());
                                        pr = String.valueOf(dataSnapshot.child(n).child("presentation").getValue());
                                        r = String.valueOf(dataSnapshot.child(n).child("reliquat").getValue());
                                        q = String.valueOf(dataSnapshot.child(n).child("quantité consommée").getValue());
                                        ref.child(String.valueOf(numeroo)).addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                surf = String.valueOf(dataSnapshot.child("surfcorp").getValue());
                                                c.setText(ci);
                                                s.setText(surf);
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError databaseError) {

                                            }
                                        });
                                    } else {
                                        Toast.makeText(CalculDos.this, "Médicament inéxistant", Toast.LENGTH_LONG).show();
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });

                        }
                        else
                            medi.setError("Champs obligatoire");
                    }
                });
                cal.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!d.getText().toString().trim().isEmpty()){
                            adm = String.valueOf(Double.parseDouble(surf) * Double.parseDouble(d.getText().toString()));
                        vol = Double.parseDouble(adm) / Double.parseDouble(ci);
                        DatabaseReference re = FirebaseDatabase.getInstance().getReference("Medicament/" + n + "/" + numeroo);
                        nm = String.valueOf(numeroo);
                        MedicCtrl medicCtr = new MedicCtrl(re);
                        medicCtr.addCalcul(Double.parseDouble(d.getText().toString()));
                        Intent intent = new Intent(CalculDos.this, OpenCalcul.class);
                        intent.putExtra("Dose", adm);
                        intent.putExtra("Ci", c.getText().toString());
                        intent.putExtra("Vol", String.valueOf(vol));
                        intent.putExtra("reliquat", r);
                        intent.putExtra("Nom", n);
                        intent.putExtra("Numero", nm);
                        intent.putExtra("cmin", cm);
                        intent.putExtra("cmax", cx);
                        intent.putExtra("pres", pr);
                        intent.putExtra("quant", q);
                        startActivity(intent);
                    }
                        else
                            d.setError("Champs obligatoire");
                    }
                });

    }
    public  void check(final String m, final String p){

        DatabaseReference data=FirebaseDatabase.getInstance().getReference("Medicament");
        data.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                boolean d=dataSnapshot.child(m).child(p).exists();
                if(d==false){
                    text3.setVisibility(View.GONE);
                    Toast.makeText(CalculDos.this,"Calcul jamais effectué  ",Toast.LENGTH_LONG).show();
                }
                else{
                    poso=String.valueOf(dataSnapshot.child(m).child(p).child("dose administrer").getValue());
                    text3.setVisibility(View.VISIBLE);
                    text3.setText("La dose à administrer de ce calcul est : "+poso);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }


}
