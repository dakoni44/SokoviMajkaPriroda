package com.example.dostava.ui.slideshow;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.dostava.DatabaseHelper;
import com.example.dostava.ItemInfo;
import com.example.dostava.IzmenaActivity;
import com.example.dostava.R;
import com.example.dostava.ui.home.HomeFragment;

import java.util.ArrayList;

public class SlideshowFragment extends Fragment {

    private SlideshowViewModel slideshowViewModel;
    DatabaseHelper mDatabaseHelper;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        slideshowViewModel =
                ViewModelProviders.of(this).get(SlideshowViewModel.class);
        View root = inflater.inflate(R.layout.fragment_slideshow, container, false);
        slideshowViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
            }
        });
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ListView lwItems = view.findViewById(R.id.lvItems2);
        mDatabaseHelper=new DatabaseHelper(getContext());
        ArrayList<String> users=new ArrayList<>();
        Cursor data=mDatabaseHelper.getListContents();
        if(data.getCount()==0){
            Toast.makeText(getContext(),"The data is empty",Toast.LENGTH_SHORT).show();
        }else{
            while(data.moveToNext()){
                users.add(data.getString(0)+" "+data.getString(1)+" "+data.getString(2)+" | "+
                        data.getString(3));
                ListAdapter adapter=new ArrayAdapter<>(getContext(),android.R.layout.simple_list_item_1,users);
                lwItems.setAdapter(adapter);
            }
        }
        lwItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(getContext(), IzmenaActivity.class);
                i.putExtra("position", position);
                startActivity(i);
            }
        });
    }
}