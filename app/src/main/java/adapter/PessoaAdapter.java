package adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.List;
import br.pucpr.app.R;
import model.Pessoa;


public class PessoaAdapter extends ArrayAdapter<Pessoa> {
    private final LayoutInflater inflater;

    public PessoaAdapter(Context context, List<Pessoa> data) {
        super(context, 0, data);
        this.inflater = LayoutInflater.from(context);
    }

    static class ViewHolder {
        TextView txtNome;
        TextView txtEmail;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        ViewHolder h;

        if (v == null) {
            v = inflater.inflate(R.layout.item_pessoa, parent, false);
            h = new ViewHolder();
            h.txtNome = v.findViewById(R.id.txtNome);
            h.txtEmail = v.findViewById(R.id.txtEmail);
            v.setTag(h);
        } else {
            h = (ViewHolder) v.getTag();
        }

        Pessoa p = getItem(position);
        if (p != null) {
            h.txtNome.setText(p.getNome());
            h.txtEmail.setText(p.getEmail());
        }
        return v;
    }
}
