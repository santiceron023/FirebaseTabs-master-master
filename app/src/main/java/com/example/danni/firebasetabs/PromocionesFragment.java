package com.example.danni.firebasetabs;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


/**
 * A simple {@link Fragment} subclass.
 */
public class PromocionesFragment extends Fragment implements View.OnClickListener {

    private Button bPromociones;
    private TextView tPromociones;
    public PromocionesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_promociones, container, false);
        bPromociones=(Button)view.findViewById(R.id.bPromociones);
        bPromociones.setOnClickListener((View.OnClickListener) this);
        tPromociones=(TextView)view.findViewById(R.id.tPromociones);


        return view;
    }
    public void onClick(View view){
        switch (view.getId()){
            case R.id.bPromociones:
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference("message");
                DatabaseReference myRef2 = database.getReference("message2");
                myRef2.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String value = dataSnapshot.getValue(String.class);
                        tPromociones.setText(value);
                    }

                    @Override
                    public void onCancelled(DatabaseError error) {

                    }
                });
                myRef.setValue("Promociones");
                break;


        }
    }

}
