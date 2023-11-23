package com.example.xyz_final;

import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainAdapter extends FirebaseRecyclerAdapter<MainModel, MainAdapter.myViewHolder> {
    public MainAdapter(@NonNull FirebaseRecyclerOptions<MainModel> options) {
        super(options);
    }
    @Override
    protected void onBindViewHolder(@NonNull myViewHolder holder, final int position, @NonNull MainModel model) {
        holder.nombre.setText(model.getNombre());
        holder.descripcion.setText(model.getDescripcion());
        holder.existencias.setText(model.getExistencias());
        holder.precio.setText(model.getPrecio());

        Glide.with(holder.img.getContext())
                .load(model.getImgURL())
                .placeholder(com.firebase.ui.database.R.drawable.common_google_signin_btn_icon_dark)
                .circleCrop()
                .error(com.google.firebase.database.R.drawable.common_google_signin_btn_icon_dark_normal)
                .into(holder.img);
        holder.editar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                final DialogPlus dialogPlus = DialogPlus.newDialog(holder.img.getContext())
                        .setContentHolder(new ViewHolder(R.layout.ventana_emergente))
                        .setExpanded(true, 1200)
                        .create();

                View view = dialogPlus.getHolderView();
                EditText nombre = view.findViewById(R.id.nombreText);
                EditText descripcion = view.findViewById(R.id.descripcioText);
                EditText existencias = view.findViewById(R.id.existeciasText);
                EditText precio = view.findViewById(R.id.precioText);
                EditText imgURL = view.findViewById(R.id.img1);

                Button actualizar = view.findViewById(R.id.btn_actualizar);

                nombre.setText(model.getNombre());
                descripcion.setText(model.getDescripcion());
                existencias.setText(model.getExistencias());
                precio.setText(model.getPrecio());
                imgURL.setText(model.getImgURL());

                dialogPlus.show();

                actualizar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Map<String,Object> map = new HashMap<>();
                        map.put("Nombre",nombre.getText().toString());
                        map.put("Descripcion",descripcion.getText().toString());
                        map.put("Existencias",existencias.getText().toString());
                        map.put("Precio",precio.getText().toString());
                        map.put("imgURL",imgURL.getText().toString());

                        FirebaseDatabase.getInstance().getReference().child("Inventario Empresa")
                                .child(getRef(position).getKey()).updateChildren(map)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Toast.makeText(holder.nombre.getContext(), "Actualizacion Correcta", Toast.LENGTH_SHORT).show();
                                        dialogPlus.dismiss();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(holder.nombre.getContext(), "Error en la Actualizacion", Toast.LENGTH_SHORT).show();
                                        dialogPlus.dismiss();
                                    }
                                });
                    }
                });
            }

        });
        holder.eliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(holder.nombre.getContext());
                builder.setTitle("estas seguro de ELIMINARLO");
                builder.setMessage("Eliminado");

                builder.setPositiveButton("Eliminado", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        FirebaseDatabase.getInstance().getReference().child("Inventario Empresa")
                                .child(getRef(position).getKey()).removeValue();
                    }
                });
                builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(holder.nombre.getContext(),"Cancelar",Toast.LENGTH_SHORT).show();
                    }
                });
                builder.show();
            }
        });

    }
    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_item, parent, false);
        return new myViewHolder(view);
    }
    class myViewHolder extends RecyclerView.ViewHolder {

        CircleImageView img;
        TextView nombre, descripcion, existencias, precio;
        Button editar, eliminar;
        public myViewHolder(@NonNull View itemView){
            super(itemView);

            img = itemView.findViewById(R.id.img1);
            nombre = itemView.findViewById(R.id.nombreText);
            descripcion = itemView.findViewById(R.id.descripcioText);
            existencias = itemView.findViewById(R.id.existeciasText);
            precio = itemView.findViewById(R.id.precioText);

            editar = itemView.findViewById(R.id.btn_edit);
            eliminar = itemView.findViewById(R.id.btn_eliminar);
        }
    }

}
