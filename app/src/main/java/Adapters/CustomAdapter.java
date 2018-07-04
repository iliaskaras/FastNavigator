package Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import Model.Dustbin;
import gr.onetouchaway.findeverything.fast_navigator.R;

/**
 * Created by ILIAS on 4/7/2018.
 */

public class CustomAdapter extends BaseAdapter {
    private Context mContext;
    private List<Dustbin> dustbinsList;

    public CustomAdapter(Context mContext, List<Dustbin> dustbinsList) {
        this.mContext = mContext;
        this.dustbinsList = dustbinsList;
    }

    @Override
    public int getCount() {
        return this.dustbinsList.size();
    }

    @Override
    public Object getItem(int position) {
        return this.dustbinsList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.raw,null);

        TextView txtUser = (TextView)view.findViewById(R.id.txtUser);
        txtUser.setText(dustbinsList.get(position).getLocation());

        return view;
    }
}
