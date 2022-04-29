package com.example.jogodavelhaapp.fragment;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.jogodavelhaapp.R;
import com.example.jogodavelhaapp.databinding.FragmentInicioBinding;
import com.example.jogodavelhaapp.databinding.FragmentJogoBinding;

public class InicioFragment extends Fragment {
private  FragmentInicioBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentInicioBinding.inflate(inflater, container, false);
        binding.btInicio.setOnClickListener(v ->{
            NavHostFragment.findNavController(InicioFragment.this).navigate(R.id.action_inicioFragment_to_jogoFragment);
        });
        return binding.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();
        //pega a referencia para a activity
        AppCompatActivity minhaActivity = (AppCompatActivity) getActivity();
        //oculta a action bar
        minhaActivity.getSupportActionBar().hide();
    }
}