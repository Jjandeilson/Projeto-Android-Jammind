package com.example.jammind.menuAluno.album;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.jammind.Configuracao;
import com.example.jammind.R;
import com.example.jammind.model.Foto;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;

public class AlbumFragment extends Fragment {

    public static AlbumFragment newInstance() {
        return new AlbumFragment();
    }

    private static int TIRAR_FOTO = 0;
    private Bitmap imagemBitmap;
    private ImageView imgTema1, imgTema2, imgTema3, imgTema4, imgTema5;
    Drawable icFotoAtual;
    Drawable icFoto;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.album_fragment, container, false);

        imgTema1 = view.findViewById(R.id.imgTema1);
        imgTema2 = view.findViewById(R.id.imgTema2);
        imgTema3 = view.findViewById(R.id.imgTema3);
        imgTema4 = view.findViewById(R.id.imgTema4);
        imgTema5 = view.findViewById(R.id.imgTema5);

        buscarFotos();

        imgTema1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                icFotoAtual = imgTema1.getDrawable();
                icFoto = getResources().getDrawable(R.drawable.polaroid_photograph);

                if(!icFotoAtual.getConstantState().equals(icFoto.getConstantState())){
                    imgTema1.setEnabled(false);
                }  else{
                    exibirFoto(imgTema1);
                }
            }
        });

        imgTema2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                icFotoAtual = imgTema2.getDrawable();
                icFoto = getResources().getDrawable(R.drawable.polaroid_photograph);

                if(!icFotoAtual.getConstantState().equals(icFoto.getConstantState())){
                    imgTema2.setEnabled(false);
                }else {
                    exibirFoto(imgTema2);
                }
            }
        });

        imgTema3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                icFotoAtual = imgTema3.getDrawable();
                icFoto = getResources().getDrawable(R.drawable.polaroid_photograph);

                if(!icFotoAtual.getConstantState().equals(icFoto.getConstantState())){
                    imgTema3.setEnabled(false);
                }else {
                    exibirFoto(imgTema3);
                }
            }
        });

        imgTema4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                icFotoAtual = imgTema4.getDrawable();
                icFoto = getResources().getDrawable(R.drawable.polaroid_photograph);

                if(!icFotoAtual.getConstantState().equals(icFoto.getConstantState())){
                    imgTema4.setEnabled(false);
                }else {
                    exibirFoto(imgTema4);
                }
            }
        });

        imgTema5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                icFotoAtual = imgTema5.getDrawable();
                icFoto = getResources().getDrawable(R.drawable.polaroid_photograph);

                if(!icFotoAtual.getConstantState().equals(icFoto.getConstantState())){
                    imgTema5.setEnabled(false);
                }else {
                    exibirFoto(imgTema5);
                }
            }
        });

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == TIRAR_FOTO && resultCode == getActivity().RESULT_OK){
            Bundle extras = data.getExtras();
            imagemBitmap = (Bitmap) extras.get("data");
            if(TIRAR_FOTO == 1){
                imgTema1.setImageBitmap(imagemBitmap);
                salvarFoto(TIRAR_FOTO);
            }else if(TIRAR_FOTO == 2){
                imgTema2.setImageBitmap(imagemBitmap);
                salvarFoto(TIRAR_FOTO);
            }else if(TIRAR_FOTO == 3){
                imgTema3.setImageBitmap(imagemBitmap);
                salvarFoto(TIRAR_FOTO);
            }else if(TIRAR_FOTO == 4){
                imgTema4.setImageBitmap(imagemBitmap);
                salvarFoto(TIRAR_FOTO);
            }else {
                imgTema5.setImageBitmap(imagemBitmap);
                salvarFoto(TIRAR_FOTO);
            }

        }
    }

    public void exibirFoto(ImageView img){

        if(img.equals(imgTema1)){
            TIRAR_FOTO = 1;
        }else if(img.equals(imgTema2)){
            TIRAR_FOTO = 2;
        }else if(img.equals(imgTema3)){
            TIRAR_FOTO = 3;
        }else if(img.equals(imgTema4)){
            TIRAR_FOTO = 4;
        }else {
            TIRAR_FOTO = 5;
        }

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if(intent.resolveActivity(getActivity().getPackageManager()) != null){
            startActivityForResult(intent, TIRAR_FOTO);
        }
    }

    public void salvarFoto(final int id){
        AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
        alert.setTitle("Deseja Salvar a foto?");
        alert.setCancelable(false);
        alert.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                ByteArrayOutputStream strem = new ByteArrayOutputStream();
                imagemBitmap.compress(Bitmap.CompressFormat.JPEG, 100, strem);
                byte[] imagemByte = strem.toByteArray();

                Realm.init(getActivity());
                Realm realm = Realm.getDefaultInstance();
                realm.beginTransaction();
                Foto foto = new Foto();
                foto.setIdFoto(id);
                foto.setFoto(imagemByte);
                realm.copyToRealm(foto);
                realm.commitTransaction();
                realm.close();
                Toast.makeText(getActivity(), "Imagem Salva", Toast.LENGTH_LONG).show();
            }
        });

        alert.setNegativeButton("NÃO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        alert.create();
        alert.show();
    }

    public void buscarFotos(){
        Realm.init(getActivity());
        Realm realm = Realm.getDefaultInstance();

        RealmQuery<Foto> query = realm.where(Foto.class);
        RealmResults<Foto> results =query.findAll();
        List<Foto> fotos = results;
        for(int i = 0; i < fotos.size(); i++){
            if(fotos.get(i).getIdFoto() == 1){
                byte[] outImage =fotos.get(i).getFoto();
                ByteArrayInputStream imageStream =new ByteArrayInputStream(outImage);
                Bitmap imagemBitmap =BitmapFactory.decodeStream(imageStream);
                imgTema1.setImageBitmap(imagemBitmap);
                imgTema1.setEnabled(false);
            }else if(fotos.get(i).getIdFoto() == 2){
                byte[] outImage =fotos.get(i).getFoto();
                ByteArrayInputStream imageStream =new ByteArrayInputStream(outImage);
                Bitmap imagemBitmap =BitmapFactory.decodeStream(imageStream);
                imgTema2.setImageBitmap(imagemBitmap);
            }else if(fotos.get(i).getIdFoto() == 3){
                byte[] outImage =fotos.get(i).getFoto();
                ByteArrayInputStream imageStream =new ByteArrayInputStream(outImage);
                Bitmap imagemBitmap =BitmapFactory.decodeStream(imageStream);
                imgTema3.setImageBitmap(imagemBitmap);
            }else if(fotos.get(i).getIdFoto() == 4){
                byte[] outImage =fotos.get(i).getFoto();
                ByteArrayInputStream imageStream =new ByteArrayInputStream(outImage);
                Bitmap imagemBitmap =BitmapFactory.decodeStream(imageStream);
                imgTema4.setImageBitmap(imagemBitmap);
            }else{
                byte[] outImage =fotos.get(i).getFoto();
                ByteArrayInputStream imageStream =new ByteArrayInputStream(outImage);
                Bitmap imagemBitmap =BitmapFactory.decodeStream(imageStream);
                imgTema5.setImageBitmap(imagemBitmap);
            }
        }
    }
}
