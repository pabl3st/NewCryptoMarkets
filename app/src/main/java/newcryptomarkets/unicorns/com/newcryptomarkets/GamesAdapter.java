package newcryptomarkets.unicorns.com.newcryptomarkets;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.util.Log;
import android.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;

import java.util.List;

public class GamesAdapter extends BaseAdapter {
    Class newclass = null;
    final GamesAdapter thisActivity = this;
    private Context context;
    private List<Games> gamesList;
    private LayoutInflater inflater = null;

    public static final String IMAGE_BASEURL = "https://chasing-coins.com/api/v1/std/logo/";

    private LruCache<Integer,Bitmap> imageCache;
    private RequestQueue queue;

    public GamesAdapter(Context context, List<Games> list) {

        this.context = context;
        this.gamesList = list;
        inflater = LayoutInflater.from(context);

        final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
        final int cacheSize = maxMemory / 8;
        imageCache = new LruCache<>(cacheSize);
        queue = Volley.newRequestQueue(context);
    }

    public class ViewHolder {

        TextView _title;
        TextView _title2;
        TextView _usdprice;
        TextView _change24h;
        ImageView _coinImage;
        Button _button;

    }

    @Override
    public int getCount() {
        return gamesList.size();
    }

    @Override
    public Object getItem(int position) {

        return gamesList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint({"SetTextI18n", "ResourceAsColor", "CutPasteId", "InflateParams"})
    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {

        final Games games = gamesList.get(position);
        final ViewHolder holder;
        if(convertView == null) {
            convertView = inflater.inflate(R.layout.list_item_templates,null);
            holder = new ViewHolder();
            //holder._title = convertView.findViewById(R.id.tvtitle);
            //holder._title.setOnClickListener(new View.OnClickListener() {
                //@Override
                //public void onClick(View v) {
                    //String activityToStart = games.getTitle();
                    //try {
                        //newclass = Class.forName(activityToStart);
                    //} catch (ClassNotFoundException c) {
                        //c.printStackTrace();
                    //}

                    //if( newclass != null) {
                        //Intent nextIntent = new Intent(context, newclass);
                        //context.startActivity(nextIntent);
                    //} else {
                        //Toast.makeText(context, "error", Toast.LENGTH_LONG);
                    //}


                    //Intent intent = new Intent().setClassName(String.valueOf(GamesAdapter.class),games.getTitle());
                    //context.startActivity(intent);

                //}
            //});
            holder._title2 = convertView.findViewById(R.id.tvdesc);
            holder._usdprice = convertView.findViewById(R.id.tvplateform);
            holder._change24h = convertView.findViewById(R.id.tvdesc);
            holder._button = convertView.findViewById((R.id.button));
            holder._button.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    String url = "http://partners.etoro.com/B9214_A70838_TClick.aspx";

                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(url));
                    context.startActivity(i);
                }
            });

            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder._title.setText(games.getTitle());
        holder._usdprice.setText("1 "+ games.getTitle2() + " = $" + games.getUsdPrice());
        holder._title2.setText(games.getTitle2());
        holder._change24h.setText(games.getChange24H() + "%");

        Double number = Double.valueOf(games.getChange24H());
        if(number<0) {
            holder._change24h.setTextColor(Color.RED);
        } else{
            holder._change24h.setTextColor(Color.parseColor("#32af2b"));
        }

        Bitmap bitmap = imageCache.get(Integer.parseInt(games.getId()));
        holder._coinImage = convertView.findViewById(R.id.gameImage);

        if (bitmap != null) {

            holder._coinImage.setImageBitmap(bitmap);

        }else {

            String imageURL = IMAGE_BASEURL + games.getTitle2();
            ImageRequest request = new ImageRequest(imageURL,
                    new Response.Listener<Bitmap>() {

                        @Override
                        public void onResponse(Bitmap response) {

                            holder._coinImage.setImageBitmap(response);
                            imageCache.put(Integer.parseInt(games.getId()), response);

                        }
                    },
                    90, 90,
                    Bitmap.Config.ARGB_8888,
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.d("error", error.getMessage());
                        }
                    });
            queue.add(request);

        }
        return convertView;

    }

}
