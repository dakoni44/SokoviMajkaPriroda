package com.example.dostava.ui.izbrisi;

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.dostava.DatabaseHelper;
import com.example.dostava.R;

public class IzbrisiFragment extends Fragment {

    private IzbrisiViewModel mViewModel;
    private EditText editId;
    private Button button3;
    DatabaseHelper mDatabaseHelper;

    public static IzbrisiFragment newInstance() {
        return new IzbrisiFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.izbrisi_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(IzbrisiViewModel.class);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        editId=view.findViewById(R.id.edit_id3);
        button3=view.findViewById(R.id.button3);
        mDatabaseHelper=new DatabaseHelper(getContext());

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer deleteRow=mDatabaseHelper.deleteData(editId.getText().toString());
                if(deleteRow>0){
                    Toast.makeText(getActivity(),"Uspesno obrisan korisnik",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getActivity(),"Nije uspelo brisanje korisnika",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
