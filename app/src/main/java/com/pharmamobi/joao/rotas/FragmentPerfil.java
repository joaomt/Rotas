package com.pharmamobi.joao.rotas;


import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.facebook.Profile;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.R.attr.windowSoftInputMode;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentPerfil extends Fragment {
    private static final int request_galeria = 101;
    private static final int request_camera = 102;
    private int posicaoSexo = 0;
    private CharSequence[] itens = new CharSequence[]{"Masculino", "Feminino", "Não Informar"};
    private String[] array = new String[]{"Tirar foto", "Escolher na galeria", "Remover Foto"};
    private EditText edt_cpf;
    private TextView txt_nascimento, txt_info_nascimento, txt_sexo, txt_info_sexo;
    private Button btn_atualizar;
    private ImageButton btn_camera;
    private CircleImageView img_Perfil;

    public FragmentPerfil() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_perfil_basico, container, false);
        txt_info_nascimento = (TextView) v.findViewById(R.id.txt_info_nascimento);
        txt_info_sexo = (TextView) v.findViewById(R.id.txt_info_sexo);
        btn_camera = (ImageButton) v.findViewById(R.id.btn_camera);
        edt_cpf = (EditText)v.findViewById(R.id.edt_cpf);

        Profile profile = Profile.getCurrentProfile();
        String nome = profile.getName();
        EditText edt_nome = (EditText) v.findViewById(R.id.edt_nome);
        TextView txt_nome = (TextView) v.findViewById(R.id.txt_nome);

        edt_cpf.addTextChangedListener(Mask.insert(Mask.MaskType.CPF,edt_cpf));

        edt_nome.setText(nome);
        txt_nome.setVisibility(View.VISIBLE);

        Uri uri = profile.getProfilePictureUri(200, 200);
        img_Perfil = (CircleImageView) v.findViewById(R.id.img_perfil_edit);
        if (uri != null) {
            Glide.with(this).load(uri).into(img_Perfil);
            //new DownloadImage((setImgPerfil)).execute(uri.toString());
        } else {
            img_Perfil.setBackgroundResource(R.drawable.ic_user);
        }
        btn_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogCamera();
            }
        });

        txt_nascimento = (TextView) v.findViewById(R.id.txt_nascimento);
        txt_nascimento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment nFrag = new DateFragment();
                nFrag.show(getFragmentManager(), "Nascimento");
            }
        });

        txt_sexo = (TextView) v.findViewById(R.id.txt_sexo);
        txt_sexo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogSexo();
            }
        });
        return v;
    }

    public void DialogSexo() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Sexo");

        builder.setSingleChoiceItems(itens, posicaoSexo, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                posicaoSexo = i;
            }
        });
        builder.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
                txt_sexo.setText(itens[posicaoSexo]);
                txt_sexo.setTextColor(getResources().getColor(R.color.preto));
                txt_info_sexo.setVisibility(View.VISIBLE);
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                txt_sexo.setText("Sexo:");
                txt_info_sexo.setVisibility(View.INVISIBLE);
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void DialogCamera() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Alterar foto");
        builder.setItems(array, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                if (item == 0) {
                    TirarFoto();
                } else if (item == 1) {
                    AbrirGaleria();
                } else if (item == 2) {
                    RemoverFoto();
                }
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();

    }

    private void RemoverFoto() {
        img_Perfil.setImageBitmap(null);
        Glide.with(this).load(R.drawable.ic_user).into(img_Perfil);
        //img_Perfil.setBackgroundResource(R.drawable.ic_user);
    }

    private void AbrirGaleria() {
        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(Intent.createChooser(intent, "Selecione uma imagem"), request_galeria);
    }

    public void TirarFoto() {
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        this.startActivityForResult(intent, request_camera);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == request_camera) {
                if (data != null) {
                    Bundle bundle = data.getExtras();
                    if (bundle != null) {
                        Bitmap img_camera = (Bitmap) bundle.get("data");
                        img_Perfil.setImageBitmap(img_camera);
                    }
                }
            } else if (requestCode == request_galeria) {
                Uri imagemSelecionada = data.getData();
                Glide.with(this).load(imagemSelecionada).into(img_Perfil);
            }
        }
    }
}
