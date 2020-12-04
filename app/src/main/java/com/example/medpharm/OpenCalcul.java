package com.example.medpharm;

import android.content.Intent;
import android.graphics.Path;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DecimalFormat;
import java.util.HashMap;

public class OpenCalcul extends AppCompatActivity {


    TextView te,tex,texte,t,textee,text;
    DatabaseReference ref;
    String c,d,vol,nm,n,cm,cx,pr,r,quant;
    int q,poche[]=new int[2];
    double reliquat,result,nouv;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.opencalcul);
        te=(TextView)findViewById(R.id.text);
        tex=(TextView)findViewById(R.id.text2);
        texte=(TextView)findViewById(R.id.text3);
        t=(TextView)findViewById(R.id.text4);
        textee=(TextView)findViewById(R.id.text5);
        text=(TextView)findViewById(R.id.textView4);
      c=getIntent().getStringExtra("Ci");
      quant=getIntent().getStringExtra("quant");
       d=getIntent().getStringExtra("Dose");
       vol=getIntent().getStringExtra("Vol");
       nm=getIntent().getStringExtra("Nom");
       n=getIntent().getStringExtra("Numero");
       cm=getIntent().getStringExtra("cmin");
        cx=getIntent().getStringExtra("cmax");
        pr= getIntent().getStringExtra("pres");
        r=getIntent().getStringExtra("reliquat");
        poche[0]=250;poche[1]=500;
       ref= FirebaseDatabase.getInstance().getReference("Medicament/"+nm);

        te.setText("La dose à adminitrer est : " + new DecimalFormat("0.000").format(Double.parseDouble(d)));
        tex.setText("Le volume est : " + new DecimalFormat("0.000").format(Double.parseDouble(vol)));


        if(Double.parseDouble(vol)<=Double.parseDouble(c)) {
            if(Double.parseDouble(vol)<=Double.parseDouble(r)){
                texte.setText("Vous n'avez pas eu besoin de flacon de  "+nm+" pour cette préparation");
                reliquat=Double.parseDouble(r)-Double.parseDouble(vol);
                textee.setText("Le reliquat de "+ nm+" est de : "+reliquat);
                t.setText("Pas de nouveau reliquat pour cette préparation");
            }
            else{
                texte.setText("Vous avez besoin d'un seul flacon de "+nm);
                reliquat=Double.parseDouble(c)-(Double.parseDouble(vol)-Double.parseDouble(r));
                textee.setText("Le reliquat total de "+ nm+" est de : "+new DecimalFormat("0.000").format(reliquat));
                q=Integer.parseInt(quant)+1;
                if(reliquat>=Double.parseDouble(r)) {
                    nouv=reliquat-Double.parseDouble(r);
                    t.setText("Le reliquat de cette préparation est : "+new DecimalFormat("0.000").format(nouv));
                }
                else
                 t.setText("Pas de reliquat dans cette préparation,l'ancien a été utilisé");
            }

        }
        else{
            if(Double.parseDouble(vol)<=Double.parseDouble(r)){
                texte.setText("Vous n'avez pas eu besoin de flacon de  "+nm+" pour cette préparation");
                reliquat=Double.parseDouble(r)-Double.parseDouble(vol);
                t.setText("Pas de reliquat dans cette préparation,l'ancien a été utilisé");
                textee.setText("Le reliquat total de "+ nm+" est de : "+new DecimalFormat("0.000").format(reliquat));
            }
            else{
                result=(Double.parseDouble(vol)-Double.parseDouble(r))/Double.parseDouble(c);
                if(result%1==0){
                    texte.setText("Vous avez eu besoin de : "+(int)result+" flacons de "+nm);
                    q=Integer.parseInt(quant)+(int) result;
                    reliquat=0;
                    textee.setText("Tout l'ancien reliquat a été utilisé pour cette préparation");
                    t.setText("Pas de reliquat dans cette préparation,l'ancien a été utilisé");
                }
                else{
                    texte.setText("Vous avez eu besoin de : "+((int)result+1)+" flacons de "+nm);
                   q=Integer.parseInt(quant)+(int)(result)+1;
                   reliquat=((int)result+1)*Double.parseDouble(c)-(Double.parseDouble(vol)-Double.parseDouble(r));
                    textee.setText("Le reliquat total de "+ nm+" est de : "+new DecimalFormat("0.000").format(reliquat));
                    if(reliquat>=Double.parseDouble(r)) {
                        nouv=reliquat-Double.parseDouble(r);
                        t.setText("Le reliquat de cette préparation est : "+new DecimalFormat("0.000").format(nouv));
                    }
                    else
                        t.setText("Pas de reliquat dans cette préparation,l'ancien a été utilisé");

                }
            }
        }
        HashMap has=new HashMap();
        has.put("reliquat",reliquat);
        has.put("quantité consommée",String.valueOf(q));
        ref.updateChildren(has);

    if(Double.parseDouble(d)>(poche[0]*Double.parseDouble(cm))&&Double.parseDouble(d)<(poche[0]*Double.parseDouble(cx))){
       if(Double.parseDouble(d)>(poche[1]*Double.parseDouble(cm))&&Double.parseDouble(d)<(poche[1]*Double.parseDouble(cx))){
           text.setText("Les poches de 250 et 500 peuvent étre utilisés");
       }
       else{
           text.setText("La poche de 250 est la plus approprié");
       }
    }
    else {
        textee.setText("La poche de 500 est la plus approprié");
    }

    }

}
