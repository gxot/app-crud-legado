package br.pucpr.app.ui;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import br.pucpr.app.R;
import db.PessoaDao;
import model.Pessoa;

public class SegundaActivity extends AppCompatActivity {

    private EditText etNome, etEmail;
    private Button btnSalvar, btnCancelar;

    private PessoaDao dao;
    private long pessoaId = -1L; // -1 => inclusão

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_segunda);

        setTitle("Cadastro de Pessoa");

        dao = new PessoaDao(this);

        etNome = findViewById(R.id.etNome);
        etEmail = findViewById(R.id.etEmail);
        btnSalvar = findViewById(R.id.btnSalvar);
        btnCancelar = findViewById(R.id.btnCancelar);

        if (getIntent() != null && getIntent().hasExtra("pessoaId")) {
            pessoaId = getIntent().getLongExtra("pessoaId", -1L);
            if (pessoaId > 0) {
                Pessoa p = dao.buscarPorId(pessoaId);
                if (p != null) {
                    etNome.setText(p.getNome());
                    etEmail.setText(p.getEmail());
                }
            }
        }

        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                salvar();
            }
        });

        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_CANCELED);
                finish();
            }
        });
    }

    private void salvar() {
        String nome = etNome.getText().toString().trim();
        String email = etEmail.getText().toString().trim();

        if (TextUtils.isEmpty(nome)) {
            etNome.setError("Informe o nome");
            etNome.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(email)) {
            etEmail.setError("Informe o email");
            etEmail.requestFocus();
            return;
        }

        if (pessoaId <= 0) {
            // inclusão
            Pessoa p = new Pessoa(0, nome, email);
            long idNovo = dao.inserir(p);
            if (idNovo > 0) {
                Toast.makeText(this, "Incluído com sucesso!", Toast.LENGTH_SHORT).show();
                setResult(RESULT_OK);
                finish();
            } else {
                Toast.makeText(this, "Falha na inclusão.", Toast.LENGTH_SHORT).show();
            }
        } else {
            // edição
            Pessoa p = new Pessoa(pessoaId, nome, email);
            int linhas = dao.atualizar(p);
            if (linhas > 0) {
                Toast.makeText(this, "Atualizado com sucesso!", Toast.LENGTH_SHORT).show();
                setResult(RESULT_OK);
                finish();
            } else {
                Toast.makeText(this, "Falha na atualização.", Toast.LENGTH_SHORT).show();
            }
        }
    }
}