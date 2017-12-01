package com.example.danni.firebasetabs;


import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;


/**
 * A simple {@link Fragment} subclass.
 */
public class TiendasFragment extends Fragment implements View.OnClickListener {

    private ListView listaTiendas;
    private ArrayList<Tiendas> tiendas;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();


    public TiendasFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tiendas, container, false);


        listaTiendas=(ListView)view.findViewById(R.id.listaTiendas);

        tiendas = new ArrayList<Tiendas>();
        final Adapter adapter = new Adapter(getActivity(),tiendas);
        listaTiendas.setAdapter(adapter);


        DatabaseReference myRef = database.getReference("Tiendas");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot tiendasSnapshot: dataSnapshot.getChildren()){
                    tiendas.add(tiendasSnapshot.getValue(Tiendas.class));
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });

        listaTiendas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                String nombre = tiendas.get(position).getNombre();
                String tiendId = tiendas.get(position).getCelular();
                Intent intent = new Intent(getActivity(),ProductosActivity.class);
                //pasar el celular
                intent.putExtra("TiendaId",tiendId);
                intent.putExtra("TiendaNombre",nombre);
                getActivity().startActivity(intent);
            }
        });





        return view;
    }



    public void onClick(View view){
        switch (view.getId()){
            case R.id.bTiendas:
                /*FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference("message");
                DatabaseReference myRef2 = database.getReference("message2");
                myRef2.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String value = dataSnapshot.getValue(String.class);

                    }

                    @Override
                    public void onCancelled(DatabaseError error) {

                    }
                });
                myRef.setValue("Tiendas");*/
                break;


        }
    }

    class Adapter extends ArrayAdapter<Tiendas>{
        public Adapter(Context context, ArrayList<Tiendas> tiendas) {
            super(context, R.layout.lista_tiendas,tiendas);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater inflater= LayoutInflater.from(getContext());
            View item =inflater.inflate(R.layout.lista_tiendas,null);

            Tiendas tiendas = getItem(position);

            TextView tNombre=(TextView)item.findViewById(R.id.tNombre);
            tNombre.setText(tiendas.getNombre());
            TextView tNegocio=(TextView)item.findViewById(R.id.tNegocio);
            tNegocio.setText(tiendas.getTipo());
            TextView tTiempoEnvio=(TextView)item.findViewById(R.id.tTiempoEnvio);
            tTiempoEnvio.setText(tiendas.getTiempoEnvio());
            TextView tPedidoMin=(TextView)item.findViewById(R.id.tPedidoMin);
            tPedidoMin.setText(tiendas.getPedidoMin());
            TextView tCostoEnvio=(TextView)item.findViewById(R.id.tCostoEnvio);
            tCostoEnvio.setText(tiendas.getCostoEnvio());

            return item;
        }

    }


}
