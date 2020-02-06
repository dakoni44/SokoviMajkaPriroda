package com.example.dostava.ui.pazar;

import androidx.lifecycle.ViewModelProviders;

import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dostava.DatabaseHelper;
import com.example.dostava.ItemInfo;
import com.example.dostava.R;
import com.example.dostava.model.User;

import java.util.ArrayList;
import java.util.List;

public class PazarFragment extends Fragment {

    private PazarViewModel mViewModel;
    DatabaseHelper mDatabaseHelper;
    TextView textView;
    String ime,prezime,adresa,grad,datum,telefon,sokovi,cena,isporuceno;
    EditText editText;
    List<User> users=new ArrayList<>();
    double sum;
    Button button,reset,detalji;
    List<User> usersProvera=new ArrayList<>();
    ListView listView;
    List<String> zaIspis=new ArrayList<>();
    List<String> praznaLista=new ArrayList<>();

    public static PazarFragment newInstance() {
        return new PazarFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.pazar_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mDatabaseHelper=new DatabaseHelper(getContext());
        textView=view.findViewById(R.id.message);
        Cursor cursor=mDatabaseHelper.getListContents();
        if(cursor.getCount()==0){
            Toast.makeText(getContext(),"Trenutno nema korisnika",Toast.LENGTH_SHORT).show();
        }else{
            if (cursor.moveToNext()){
                do {
                    ime = cursor.getString(1);
                    prezime = cursor.getString(2);
                    adresa = cursor.getString(3);
                    grad = cursor.getString(4);
                    datum = cursor.getString(5);
                    telefon = cursor.getString(6);
                    sokovi = cursor.getString(7);
                    cena = cursor.getString(8);
                    isporuceno = cursor.getString(9);
                    User user = new User(ime, prezime, adresa, grad, datum, telefon, sokovi, cena, isporuceno);
                    users.add(user);
                }while(cursor.moveToNext());
            }
        }
        editText=view.findViewById(R.id.editText);

        button=view.findViewById(R.id.button4);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textView.setText(String.valueOf(suma()));
            }
        });
        reset=view.findViewById(R.id.button5);
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textView.setText("0.0");
                sum=0;
                ArrayAdapter<String> adapter2=new ArrayAdapter<>(getActivity(),android.R.layout.simple_list_item_1,praznaLista);
                listView.setAdapter(adapter2);
                zaIspis=new ArrayList<>();
                usersProvera=new ArrayList<>();
            }
        });
        detalji=view.findViewById(R.id.button6);
        listView=view.findViewById(R.id.lvItems4);
        detalji.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dodaj();
                dodaj2();
                for(int i=0;i<usersProvera.size();i++) {
                    ArrayAdapter<String> adapter=new ArrayAdapter<>(getActivity(),android.R.layout.simple_list_item_1,zaIspis);
                    listView.setAdapter(adapter);
                }
            }
        });

    }
    public double suma(){
        for(int i=0;i<users.size();i++){

            String datum=editText.getText().toString();
            if(datum.contains(users.get(i).getDatum())){
                sum +=Double.parseDouble(users.get(i).getCena())*Double.parseDouble(users.get(i).getSokovi());
            }
        }
        return sum;
    }
    public void dodaj(){
        for(int i=0;i<users.size();i++){

            String datum=editText.getText().toString();
            if(datum.contains(users.get(i).getDatum())){
                usersProvera.add(users.get(i));
            }
        }
    }
    public void dodaj2(){
        for(int i=0;i<usersProvera.size();i++){
            zaIspis.add(usersProvera.get(i).getIme()+
                    " "+ usersProvera.get(i).getPrezime()+" | "+
                    usersProvera.get(i).getGrad()+
                    " | "+usersProvera.get(i).getDatum()+
                    " : "+Double.parseDouble(usersProvera.get(i).getSokovi())*Double.parseDouble(usersProvera.get(i).getCena()));
        }
    }




}
