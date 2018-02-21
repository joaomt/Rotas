package com.pharmamobi.joao.rotas;

import android.app.ProgressDialog;
import android.content.ClipboardManager;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.transition.ChangeBounds;
import android.transition.Explode;
import android.transition.Fade;
import android.transition.Slide;

import android.transition.Transition;
import android.transition.TransitionInflater;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.pharmamobi.joao.rotas.Domain.WebApiUrl;
import com.pharmamobi.joao.rotas.Interfaces.ClientesApi;
import com.pharmamobi.joao.rotas.Interfaces.GetEnderecoApi;
import com.pharmamobi.joao.rotas.activity.Activity_buscar_end_cep;
import com.pharmamobi.joao.rotas.activity.Activity_home_motoboy;
import com.pharmamobi.joao.rotas.model.Clientes;
import com.pharmamobi.joao.rotas.model.Endereco;
import com.facebook.Profile;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.util.Pair.create;

public class MenuPrincipal extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    //Cliente cliente = new Cliente();
    private ImageView setImgPerfil;
    private TextView nav_user;
    private TextView nav_email;
    private View VimgPerfil;
    private WebApiUrl webApi = new WebApiUrl();
    private String URL_BASE = webApi.GetUrlBase();
    private Toolbar toolbar;
    private Uri uri;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String menuFragment = getIntent().getStringExtra("fragment");
        String loginMotoBoy = getIntent().getStringExtra("id_login");
        // If menuFragment is defined, then this activity was launched with a fragment selection
        if (menuFragment != null) {

            // Here we can decide what do to -- perhaps load other parameters from the intent extras such as IDs, etc
            if (menuFragment.equals("entregas")) {
                FragmentMinhasEntregas minhasEntregas = new FragmentMinhasEntregas();
                getSupportFragmentManager().beginTransaction().replace(R.id.conteudo_fragment, minhasEntregas).commit();
            }else if(menuFragment.equals("orcamentos"))
            {
                FragmentOrcamentos Orcamentos = new FragmentOrcamentos();
                getSupportFragmentManager().beginTransaction().replace(R.id.conteudo_fragment, Orcamentos).commit();
            }
        } else if(loginMotoBoy != null) {
            Intent it = new Intent(this, Activity_home_motoboy.class);
            it.putExtra("id_motoboy", loginMotoBoy);
        }else{
            FragmentHome home = new FragmentHome();
            getSupportFragmentManager().beginTransaction().replace(R.id.conteudo_fragment, home).commit();
            // Activity was not launched with a menuFragment selected -- continue as if this activity was opened from a launcher (for pharmamobi)
        }
        Profile profile = Profile.getCurrentProfile();

        setContentView(R.layout.activity_menu_principal);//xml principal do menu
        toolbar = (Toolbar) findViewById(R.id.toolbar);//xml app_bar_... que está incluso no xml principal
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout); // id do drawer do xml principal
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer,toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);//xml do header do menu
        navigationView.setNavigationItemSelectedListener(this);
        View hView =  navigationView.getHeaderView(0);

        nav_user = (TextView)hView.findViewById(R.id.txt_nomePerfil);
        nav_email = (TextView)hView.findViewById(R.id.txt_emailPerfil);
        setImgPerfil = (ImageView)hView.findViewById(R.id.img_perfil);

        String nome = profile.getName();
        nav_user.setText(nome);



        final Bundle inBundle = getIntent().getExtras();
        int idUser = 2; //inBundle.getInt("id");
        final String getNome = "s";//inBundle.get("nome").toString();//Trazendo nome de perfil do facebook ou google
        final String getEmail = "s";//inBundle.get("email").toString();
        //uri = (Uri) inBundle.get("imageUrl");
       // final String url = inBundle.get("imageUrl").toString();

        Uri uri = profile.getProfilePictureUri(200, 200);
        //img_Perfil = (CircleImageView) v.findViewById(R.id.img_perfil_edit);
        if (uri != null) {
            Glide.with(this).load(uri).into(setImgPerfil);
            //new DownloadImage((setImgPerfil)).execute(uri.toString());
        } else {
            setImgPerfil.setBackgroundResource(R.drawable.ic_user);
        }
    }

    private void getUser(int id) {

    }

    public void Edit_perfil(View v){
        Bundle bundle = getIntent().getExtras();
        Uri url = (Uri) bundle.get("imageUrl");
        Intent i = new Intent(MenuPrincipal.this, PerfilUser.class);
        i.putExtra("image_url", url);

        View imgPerfil = v.findViewById(R.id.img_perfil);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(this, null);
            startActivity(i, options.toBundle());
        } else {
            startActivity(i);
        }

        /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(this,
                    Pair.create(VimgPerfil,"transition1"));
            startActivity(i, options.toBundle());
        } else {
            startActivity(i);
        }*/

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.nav_minhasEntregas) {
            toolbar.setTitle("Minhas Entregas");
                FragmentMinhasEntregas minhasEntregas = new FragmentMinhasEntregas();
                getSupportFragmentManager().beginTransaction().replace(R.id.conteudo_fragment, minhasEntregas).commit();
        }else if (id == R.id.nav_home) {
            toolbar.setTitle("Home");
            FragmentHome home = new FragmentHome();
            getSupportFragmentManager().beginTransaction().replace(R.id.conteudo_fragment, home).commit();
        } else if (id == R.id.nav_meusOrcamentos) {
            toolbar.setTitle("Orçamentos");
            FragmentOrcamentos Orcamentos = new FragmentOrcamentos();
            getSupportFragmentManager().beginTransaction().replace(R.id.conteudo_fragment, Orcamentos).commit();
        } else if (id == R.id.nav_sobre) {
            toolbar.setTitle("Sobre");
            FragmentSobre Sobre = new FragmentSobre();
            getSupportFragmentManager().beginTransaction().replace(R.id.conteudo_fragment, Sobre).commit();
        } else if (id == R.id.nav_ajuda) {
            toolbar.setTitle("Ajuda");
            FragmentAjuda Ajuda = new FragmentAjuda();
            getSupportFragmentManager().beginTransaction().replace(R.id.conteudo_fragment, Ajuda).commit();
        } else if (id == R.id.nav_configuracoes) {
            toolbar.setTitle("Configurações");
            FragmentConfiguracao Configuracao = new FragmentConfiguracao();
            getSupportFragmentManager().beginTransaction().replace(R.id.conteudo_fragment, Configuracao).commit();
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


}