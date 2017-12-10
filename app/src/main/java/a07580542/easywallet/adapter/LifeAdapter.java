package a07580542.easywallet.adapter;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import a07580542.easywallet.R;
import a07580542.easywallet.model.Life;

/**
 * Created by Dream on 10/12/2560.
 */

public class LifeAdapter extends ArrayAdapter<Life> {
    private Context context;
    private int layoutresid;
    private ArrayList<Life> lifelist;
    public LifeAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Life> objects) {
        super(context, resource, objects);
        this.context=context;
        this.layoutresid=resource;
        this.lifelist=objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View itemlayout = inflater.inflate(layoutresid,null);
        Life life = lifelist.get(position);
        ImageView iv = itemlayout.findViewById(R.id.imageView_item);
        TextView tv_detail = itemlayout.findViewById(R.id.detail_text_item);
        TextView tv_money = itemlayout.findViewById(R.id.money_text_item);

        AssetManager asset = context.getAssets();
        try {
            InputStream ip = asset.open(life.picture);
            Drawable drawble = Drawable.createFromStream(ip,null);
            iv.setImageDrawable(drawble);
        } catch (IOException e) {
            e.printStackTrace();
        }
//        Drawable drawable = Drawable.createFromPath(life.picture);
//        iv.setImageDrawable(drawable);
        DecimalFormat myFormatter = new DecimalFormat("#,###");
        String money = myFormatter.format(Integer.parseInt(life.money));
        tv_detail.setText(life.detail);
        tv_money.setText(money);
        return itemlayout;
    }
}
