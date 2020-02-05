package com.example.dostava.ui.tools;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.core.view.MenuItemCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.dostava.DatabaseHelper;
import com.example.dostava.ItemInfo;
import com.example.dostava.R;

import java.util.ArrayList;

public class ToolsFragment extends Fragment {

    private ToolsViewModel toolsViewModel;
    DatabaseHelper mDatabaseHelper;
    ArrayList<String> users=new ArrayList<>();
    ListView lwItems;
    SearchView searchView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        toolsViewModel =
                ViewModelProviders.of(this).get(ToolsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_tools, container, false);
        toolsViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
            }
        });
        return root;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        searchView=view.findViewById(R.id.item_search);
        lwItems = view.findViewById(R.id.lvItems3);
        mDatabaseHelper=new DatabaseHelper(getContext());

        Cursor data=mDatabaseHelper.getListContents();
        if(data.getCount()==0){
            Toast.makeText(getContext(),"Trenutno nema korisnika",Toast.LENGTH_SHORT).show();
        }else{
            while(data.moveToNext()){
                users.add(data.getString(0)+" "+data.getString(1)+" "+data.getString(2)+" | "+
                        data.getString(4)+" | "+data.getString(5));
                ListAdapter adapter=new ArrayAdapter<>(getContext(),android.R.layout.simple_list_item_1,users);
                lwItems.setAdapter(adapter);
            }
        }
        lwItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(getContext(), ItemInfo.class);
                i.putExtra("position", position);
                startActivity(i);
            }
        });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                ArrayList<String> users2 = new ArrayList<>();

                for(String user: users){
                    if(user.toLowerCase().contains(newText.toLowerCase())){
                        users2.add(user);
                    }
                }
                ArrayAdapter<String> adapter=new ArrayAdapter(getContext(),android.R.layout.simple_list_item_1,users2);
                lwItems.setAdapter(adapter);
                return true;
            }
        });
    }


}