package br.com.bossini.cadastrousuariosimplesfirebaseadscarapicuiba;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class EditarUsuarioActivity extends AppCompatActivity {

    private static final int REQUISICAO_CAMERA = 5436;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference usuarioReference;
    private EditText editarNomeEditText, editarFoneEditText, editarEmailEditText;
    private ImageView fotoEditadaImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_usuario);
        editarNomeEditText = (EditText) findViewById(R.id.editarNomeEditText);
        editarFoneEditText = (EditText) findViewById(R.id.editarFoneEditText);
        editarEmailEditText = (EditText) findViewById(R.id.editarEmailEditText);
        fotoEditadaImageView = (ImageView) findViewById(R.id.fotoEditadaImageView);
        atualizarComponentesVisuais();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Usuario.getInstance().setNome(editarNomeEditText.getEditableText().toString());
                Usuario.getInstance().setFone(editarFoneEditText.getEditableText().toString());
                Usuario.getInstance().setEmail(editarEmailEditText.getEditableText().toString());
                usuarioReference.setValue(Usuario.getInstance());
                /*Snackbar.make(view, getString(R.string.usuario_salvo), Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
                Toast.makeText(EditarUsuarioActivity.this, getString(R.string.usuario_salvo), Toast.LENGTH_SHORT).show();
                finish();
            }
        });
        firebaseDatabase = FirebaseDatabase.getInstance();
        usuarioReference = firebaseDatabase.getReference("usuario");
    }

    private void atualizarComponentesVisuais (){
        editarNomeEditText.setText(Usuario.getInstance().getNome() != null ? Usuario.getInstance().getNome() : "");
        editarFoneEditText.setText(Usuario.getInstance().getFone() != null ? Usuario.getInstance().getFone() : "");
        editarEmailEditText.setText(Usuario.getInstance().getEmail() != null ? Usuario.getInstance().getEmail() : "");
        if (Usuario.getInstance().getFoto() != null)
            fotoEditadaImageView.setImageBitmap(Usuario.getInstance().getFoto());
    }


    public void tirarFoto (View view){
        Intent intent = new Intent (MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUISICAO_CAMERA);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUISICAO_CAMERA){
            if (resultCode == Activity.RESULT_OK){
                Bundle bundle = data.getExtras();
                Bitmap foto = (Bitmap) bundle.get("data");
                fotoEditadaImageView.setImageBitmap(foto);
                Usuario.getInstance().setFoto(foto);
            }
        }
    }
}
