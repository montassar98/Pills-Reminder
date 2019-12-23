package com.montassar.distributeurdespliles.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.montassar.distributeurdespliles.AddActivity;
import com.montassar.distributeurdespliles.EditActivity;
import com.montassar.distributeurdespliles.MainActivity;
import com.montassar.distributeurdespliles.MyAdapter;
import com.montassar.distributeurdespliles.R;
import com.montassar.distributeurdespliles.Time;

import java.util.ArrayList;

public class HomeFragment extends Fragment implements View.OnClickListener, MyAdapter.ItemClickListener {

    View view;
    private ImageView imgSignOut;
    private FirebaseAuth mAuth;
    public static ArrayList<Time> arrayList;
    private  RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private MyAdapter adapter;
    private FirebaseDatabase database;
    private DatabaseReference myRefTime;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home,container,false);

        imgSignOut = (ImageView) view.findViewById(R.id.img_sign_out);
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        myRefTime = database.getReference("MyTimes/"+mAuth.getCurrentUser().getUid());
        arrayList = new ArrayList<>();


        setupTime();
        myRefTime.addValueEventListener(timeListener);

        imgSignOut.setOnClickListener(this);
        return view;
    }

    private ValueEventListener timeListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            Time time;
            for (DataSnapshot np : dataSnapshot.getChildren())
            {
                time= np.getValue(Time.class);
                arrayList.add(time);
            }
            adapter.notifyDataSetChanged();
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    };

    private void setupTime() {
        recyclerView =(RecyclerView) view.findViewById(R.id.recycler_view);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL));
        adapter = new MyAdapter(getContext(),arrayList);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id)
        {
            case R.id.img_sign_out :
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void onItemClickListener(View view, int position) {
        Toast.makeText(getContext(),position+"",Toast.LENGTH_SHORT).show();
        onSelect(arrayList.get(position));
    }
    public  void  onSelect(Time time)
    {

        Intent intent = new Intent(getActivity(), EditActivity.class);
        intent.putExtra("ID", time.getId());
        intent.putExtra("HOUR",time.getHour());
        intent.putExtra("MSG",time.getMsg());
        startActivity(intent);
    }
}
