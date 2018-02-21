package com.pharmamobi.joao.rotas;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SlidingPaneLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.transition.ChangeBounds;
import android.transition.Explode;
import android.transition.Fade;
import android.transition.Slide;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.transition.TransitionManager;
import android.view.MenuItem;
import android.view.View;

import com.bumptech.glide.Glide;

import de.hdodenhof.circleimageview.CircleImageView;


public class PerfilUser extends AppCompatActivity {
    private ViewPager mViewPager;
    private SlidingTabLayout mSlidingTabLayout;
    private Toolbar tb_perfil;
    @Override
    public void onCreate(Bundle save){
        //transitions
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            /*Explode trans1 = new Explode();
            trans1.setDuration(500);
            Fade trans2 = new Fade();
            trans2.setDuration(500);

            getWindow().setEnterTransition(trans1);
            getWindow().setEnterTransition(trans2);*/
        }
        super.onCreate(save);
        setContentView(R.layout.activity_perfil_user);

        tb_perfil = (Toolbar)findViewById(R.id.tb_cadastro);
        tb_perfil.setTitle("Minha conta");
        setSupportActionBar(tb_perfil);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mViewPager = (ViewPager)findViewById(R.id.vp_tabs);
        mViewPager.setAdapter(new TabsAdapter(getSupportFragmentManager(), this));

        mSlidingTabLayout = (SlidingTabLayout) findViewById(R.id.stl_tabs);
        mSlidingTabLayout.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        mSlidingTabLayout.setSelectedIndicatorColors(getResources().getColor(R.color.branco));
        //mSlidingTabLayout.setDistributeEvenly(true);
        mSlidingTabLayout.setViewPager(mViewPager);

        /*CircleImageView img_Perfil = (CircleImageView) findViewById(R.id.img_perfil_edit);
        if(uri != null) {
            Glide.with(this).load(uri).into(img_Perfil);
            //new DownloadImage((setImgPerfil)).execute(uri.toString());
        }else{
            img_Perfil.setBackgroundResource(R.drawable.ic_user_menu);
        }*/
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
        }
        return true;
    }
}
