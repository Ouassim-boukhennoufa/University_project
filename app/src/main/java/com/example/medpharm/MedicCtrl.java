package com.example.medpharm;


import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.List;



public class MedicCtrl {
    String q,r;
    private DatabaseReference ref;
    Medicament med;
public MedicCtrl(DatabaseReference ref){
    this.ref=ref;
}

public void ajoutMedic(String name,String lab,int prese,double ci,double cm,double cx,double p){

    med=new Medicament(name,lab,prese,ci,cm,cx,p);
    ref.child(name).setValue(med);
    HashMap hash=new HashMap();
    hash.put("quantité consommée",0);
    hash.put("reliquat",0);
    ref.child(name).updateChildren(hash);
}
public void modifMedic(String n1,String name,String lab,int prese,double ci,double cm,double cx,double p){
   /* DatabaseReference db=ref.child(n1);
       med = new Medicament(name, lab, prese, ci, cm, cx, p);

       db.removeValue();
       ref.child(name).setValue(med);
    HashMap hash=new HashMap();
    hash.put("quantité consommée",0);
    hash.put("reliquat",0);
    ref.child(name).updateChildren(hash);*/
    DatabaseReference db=ref.child(n1);
    if(name!=null)
        db.setValue(name);
    DatabaseReference d=ref.child(name);
    if(lab!=null)
    d.child("nomLabo").setValue(lab);
    d.child("nomMedicament").setValue(name);

   }
   public List<Medicament> show(final String newText,final List<Medicament> medlst){
       ref.addValueEventListener(new ValueEventListener() {
           @Override
           public void onDataChange( DataSnapshot dataSnapshot) {
               medlst.clear();
               for(DataSnapshot ds:dataSnapshot.getChildren()){
                   Medicament medi=ds.getValue(Medicament.class);
                   if(medi.getNomMedicament().toLowerCase().contains(newText.toLowerCase()))
                       medlst.add(medi);
               }

           }

           @Override
           public void onCancelled( DatabaseError databaseError) {

           }
       });
    return  medlst;
   }

   public void deletemed(String nmed){
       DatabaseReference db=ref.child(nmed);
       db.removeValue();
   }
   public void addCalcul(Double ad){
    ref.child("dose administrer").setValue(ad);
   }

}
