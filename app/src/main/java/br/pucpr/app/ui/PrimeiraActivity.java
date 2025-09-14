package br.pucpr.app.ui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import adapter.PessoaAdapter;
import br.pucpr.app.R;
import db.PessoaDao;
import model.Pessoa;


public class PrimeiraActivity extends AppCompatActivity {

    private static final int REQ_NOVA = 1;
    private static final int REQ_EDITAR = 2;

    private PessoaDao dao;
    private PessoaAdapter adapter;
    private List<Pessoa> dados;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_primeira);

        setTitle("Pessoas");

        dao = new PessoaDao(this);
        dados = new ArrayList<Pessoa>(dao.listarTudo());
        adapter = new PessoaAdapter(this, (ArrayList<Pessoa>) dados);

        ListView list = findViewById(R.id.listPessoas);
        list.setAdapter(adapter);

        Button btnAdicionar = findViewById(R.id.btnAdicionar);
        btnAdicionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(PrimeiraActivity.this, SegundaActivity.class);
                // Sem id => inclusão
                startActivityForResult(it, REQ_NOVA);
            }
        });

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Pessoa p = adapter.getItem(position);
                if (p != null) {
                    Intent it = new Intent(PrimeiraActivity.this, SegundaActivity.class);
                    it.putExtra("pessoaId", p.getId());
                    startActivityForResult(it, REQ_EDITAR);
                }
            }
        });

        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                final Pessoa p = adapter.getItem(position);
                if (p == null) return true;

                new AlertDialog.Builder(PrimeiraActivity.this)
                        .setTitle("Excluir")
                        .setMessage("Deseja excluir " + p.getNome() + "?")
                        .setPositiveButton("Excluir", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                int linhas = dao.excluir(p.getId());
                                if (linhas > 0) {
                                    Toast.makeText(PrimeiraActivity.this, "Excluído", Toast.LENGTH_SHORT).show();
                                    recarregar();
                                } else {
                                    Toast.makeText(PrimeiraActivity.this, "Falha ao excluir", Toast.LENGTH_SHORT).show();
                                }
                            }
                        })
                        .setNegativeButton("Cancelar", null)
                        .show();
                return true; // consumiu o clique longo
            }
        });
    }

    private void recarregar() {
        List<Pessoa> lista = dao.listarTudo();
        adapter.clear();
        adapter.addAll(lista);
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && (requestCode == REQ_NOVA || requestCode == REQ_EDITAR)) {
            recarregar();
        }
    }
}