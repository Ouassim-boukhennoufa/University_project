package com.example.medpharm;



import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class PatientCtrl {
    static boolean check;
    String  sur;
    int ch;
   static private DatabaseReference ref;
    Patient pat;



    public  PatientCtrl(DatabaseReference ref) {
        this.ref = ref;
    }


    public void ajoutpat(String nomPatient, String prenPatient, int nmrPatient, double taille, double surfcorp, double poids){
        pat=new Patient(nomPatient,prenPatient,nmrPatient,taille,surfcorp,poids);
        String n=String.valueOf(nmrPatient);
        ref.child(n).setValue(pat);
    }
}
