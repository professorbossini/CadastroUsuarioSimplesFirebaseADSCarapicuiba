package br.com.bossini.cadastrousuariosimplesfirebaseadscarapicuiba;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    private FirebaseDatabase database;
    private DatabaseReference userReference;
    private TextView nomeTextView, foneTextView, emailTextView;
    private ImageView fotoImageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        nomeTextView = (TextView) findViewById(R.id.nomeTextView);
        foneTextView = (TextView) findViewById(R.id.foneTextView);
        emailTextView = (TextView)  findViewById(R.id.emailTextView);
        fotoImageView = (ImageView) findViewById(R.id.fotoImageView);
        database = FirebaseDatabase.getInstance();
        userReference = database.getReference("usuario");
        userReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Usuario usuario = dataSnapshot.getValue(Usuario.class);
                if (usuario != null){
                    Usuario.getInstance().setNome(usuario.getNome());
                    Usuario.getInstance().setFone(usuario.getFone());
                    Usuario.getInstance().setEmail(usuario.getEmail());
                    atualizaComponentesVisuais();
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, EditarUsuarioActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    private void atualizaComponentesVisuais (){
        nomeTextView.setText(Usuario.getInstance().getNome());
        foneTextView.setText(Usuario.getInstance().getFone());
        emailTextView.setText(Usuario.getInstance().getEmail());
        if (Usuario.getInstance().getFoto() != null)
            fotoImageView.setImageBitmap(Usuario.getInstance().getFoto());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        atualizaComponentesVisuais();
    }
}
