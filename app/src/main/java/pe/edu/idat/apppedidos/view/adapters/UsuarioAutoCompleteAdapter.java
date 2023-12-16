package pe.edu.idat.apppedidos.view.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

import pe.edu.idat.apppedidos.retrofit.response.ListusuResponse;

public class UsuarioAutoCompleteAdapter extends ArrayAdapter<ListusuResponse> {

    public UsuarioAutoCompleteAdapter(Context context, List<ListusuResponse> listusuResponses) {
        super(context, android.R.layout.simple_dropdown_item_1line, listusuResponses);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        TextView textView = (TextView) super.getView(position, convertView, parent);
        ListusuResponse item = getItem(position);
        if (item != null) {
            textView.setText(item.getNombre());
        }
        return textView;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        TextView textView = (TextView) super.getDropDownView(position, convertView, parent);
        ListusuResponse item = getItem(position);
        if (item != null) {
            textView.setText(item.getNombre());
        }
        return textView;
    }

}
