package com.example.jogodavelhaapp.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.jogodavelhaapp.R;
import com.example.jogodavelhaapp.databinding.FragmentJogoBinding;
import com.example.jogodavelhaapp.util.PrefsUtil;

import java.util.Arrays;
import java.util.Random;


public class JogoFragment extends Fragment {
//variavel para acessar os elementos da view

    private FragmentJogoBinding binding;
    // vetor de botões para referenciar os botoes
    private Button[] botoes;
    //matriz de string que representa o tabuleiro
    private String[][] tabuleiro;
    //variaveis para os simbolos
    private String simbJog1, simbJog2, simbolo;
    //variavel Random para sortear quem inicia
    private Random random;
    //variavel para controlar o numero de jogadas
    private int numJogadas = 0;


    private int placarJogador1 = 0;
    private int placarJogador2 = 0;
    private int placarVelha = 0;


    //variavel para contar o número de jogadas
    private int numeroJogadas;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);


        binding = FragmentJogoBinding.inflate(inflater, container, false);


        //instanciar o vetor
        botoes = new Button[9];

        //associar o vetor aos botoes
        botoes[0] = binding.bt00;
        botoes[1] = binding.bt01;
        botoes[2] = binding.bt02;
        botoes[3] = binding.bt10;
        botoes[4] = binding.bt11;
        botoes[5] = binding.bt12;
        botoes[6] = binding.bt20;
        botoes[7] = binding.bt21;
        botoes[8] = binding.bt22;


        // associa o listener ao botão

        for (Button bt : botoes) {
            bt.setOnClickListener(listenerBotoes);
        }


        //instanciar o tabuleiro

        tabuleiro = new String[3][3];

        //preenche a matriz ""
       /* for (int  i = 0; 1<3; i++){
            for (int j = 0; j < 3 ;j++){
                tabuleiro[1][1]= "";
            }

        }
        */
        //preeenche a matriz com ""
        for (String[] vetor : tabuleiro) {
            Arrays.fill(vetor, "");

        }

        //define os simbolos do jogador 1 e do jogador 2
        simbJog1 = PrefsUtil.getSimboloJog1(getContext());
        simbJog2 = PrefsUtil.getSimboloJog2(getContext());

        binding.jogador1.setText(getResources().getString(R.string.jogador_1, simbJog1));
        binding.jogador2.setText(getResources().getString(R.string.jogador_2, simbJog2));

        random = new Random();
        sorteia();

        atualizaVez();
        //atualiza a cor do placar


        return binding.getRoot();
    }

    private void sorteia() {

        if (random.nextBoolean()) {
            simbolo = simbJog1;


        } else {
            simbolo = simbJog2;

        }
    }

    private void atualizaVez() {
        if (simbolo.equals(simbJog1)) {
            binding.jogador1.setBackgroundColor(getResources().getColor(R.color.white));
            binding.jogador2.setBackgroundColor(getResources().getColor(R.color.Light_Blue_400));
        } else {
            binding.jogador2.setBackgroundColor(getResources().getColor(R.color.white));
            binding.jogador1.setBackgroundColor(getResources().getColor(R.color.Light_Blue_400));
        }

    }

    private boolean venceu() {
        //verifica se venceu nas linhas
        for (int li = 0; li < 3; li++) {
            if (tabuleiro[li][0].equals(simbolo) && tabuleiro[li][1].equals(simbolo) && tabuleiro[li][2].equals(simbolo)) {
                return true;
            }
        }

        for (int col = 0; col < 3; col++) {
            if (tabuleiro[0][col].equals(simbolo) && tabuleiro[1][col].equals(simbolo) && tabuleiro[2][col].equals(simbolo)) {
                return true;
            }
        }
        if (tabuleiro[0][0].equals(simbolo) && tabuleiro[1][1].equals(simbolo) && tabuleiro[2][2].equals(simbolo)) {
            return true;
        }
        if (tabuleiro[0][2].equals(simbolo) && tabuleiro[1][1].equals(simbolo) && tabuleiro[2][0].equals(simbolo)) {
            return true;
        }
        return false;

    }

    private void resetar() {
        //percorrer os botoes do vetor voltando o background inicial tornando os clicaveis novamente e limpando o texto
        for (Button botao : botoes) {
            botao.setClickable(true);
            botao.setText("");
            botao.setBackgroundColor(getResources().getColor(R.color.Light_Blue_700));
        }
        //limpar a matriz
        for (String[] vetor : tabuleiro) {
            Arrays.fill(vetor, "");
        }
        //zerar o numero de jogadas
        numJogadas = 0;

        sorteia();
        atualizaVez();
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        //verificar qual oitem domenu foi selecionado
        switch (item.getItemId()) {
            //caso seja a opção de resetar
            case R.id.menu_resetar:
                placarJogador1 = 0;
                placarJogador2 = 0;

               atualizaPlacar();
                resetar();
                break;

            //caso seja a opção de preferencias
            case R.id.menu_prefs:
                NavHostFragment.findNavController(JogoFragment.this).navigate(R.id.action_jogoFragment_to_prefFragment);
                break;

        }

        return true;
    }

    private void atualizaPlacar() {
        binding.placar1.setText(placarJogador1 + "");
        binding.placar2.setText(placarJogador2 + "");
    }

    private void atualizaVelha(){
        binding.placarVelha.setText(placarVelha +"");

    }





    @Override
    public void onStart() {
        super.onStart();
        //pega a referencia para a activity
        AppCompatActivity minhaActivity = (AppCompatActivity) getActivity();
        //oculta a action bar
        minhaActivity.getSupportActionBar().show();
        //desabilita a seta de retornar
        minhaActivity.getSupportActionBar().setDisplayHomeAsUpEnabled(false);
    }
    //listener para os botoes
    private View.OnClickListener listenerBotoes = btPress -> {
        //incrementa numero de jogadas
        numJogadas++;

        //obtem o nome do botãp
        String nomeBotao = getContext().getResources().getResourceName(btPress.getId());
        //extrai a posiçao atraves do nome do botao

        String posicao = nomeBotao.substring(nomeBotao.length() - 2);
        //extrai linha e coluna da string posição

        int linha = Character.getNumericValue(posicao.charAt(0));
        int coluna = Character.getNumericValue(posicao.charAt(1));
        // preencher a posiçao da matriz com o simbolo "da vez"
        tabuleiro[linha][coluna] = simbolo;
        //faz um casting de view para button
        Button botao = (Button) btPress;
        //seta o simbulo no botao precionado
        botao.setText(simbolo);
        //trocar o backgoround do botao
        botao.setBackgroundColor(Color.WHITE);
        //desabilitar o botao que foi pressionado
        botao.setClickable(false);
        Toast.makeText(getContext(), R.string.venceu, Toast.LENGTH_LONG).show();
        //verifica quem venceu
        if (numJogadas >= 5 && venceu()) {
            Toast.makeText(getContext(), R.string.venceu, Toast.LENGTH_LONG).show();
            //verifica quem venceu
            if (simbolo.equals(simbJog1)) {
                placarJogador1++;
            } else {
                placarJogador2++;
            }
            //atualiza placar
            atualizaPlacar();
            resetar();
        } else if (numJogadas == 9) {
            Toast.makeText(getContext(), R.string.deuvelha, Toast.LENGTH_LONG).show();
            placarVelha++;
            atualizaVelha();
            resetar();
        } else {
            //inverte o simbolo
            simbolo = simbolo.equals(simbJog1) ? simbJog2 : simbJog1;
            atualizaVez();
        }
    };


}
