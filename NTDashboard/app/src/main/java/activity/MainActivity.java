package activity;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.pc6.ntdashboard.R;
import com.google.gson.Gson;

import controller.APIClient;
import controller.IUpdateDataCallback;
import controller.NgoaiTeAPI;
import model.NgoaiTe;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "MainActivityLog";

    private RecyclerView recyclerView;
    private DashboardAdapter adapter;
    private Button btnUpdate;
    private NgoaiTe ngoaiTe;
    private boolean isAutoUpdate = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recycle_dashboard);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        btnUpdate = findViewById(R.id.button_update);
        btnUpdate.setTag("ButtonUpdate");
        btnUpdate.setOnClickListener(this);

        call();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    private void call() {
        Retrofit callApi = APIClient.create(getResources().getString(R.string.base_url));
        NgoaiTeAPI ngoaiTeAPI = callApi.create(NgoaiTeAPI.class);

        Call<String> call = ngoaiTeAPI.getGiaNgoaiTe();

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                String res = response.body();

                res = res.replace("(", "");
                res = res.replace(")", "");

                Gson gson = new Gson();
                ngoaiTe = gson.fromJson(res, NgoaiTe.class);

                if (isAutoUpdate) {
                    adapter = new DashboardAdapter(getBaseContext(), ngoaiTe);
                    recyclerView.setAdapter(adapter);
                    isAutoUpdate = false;
                } else {
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                new AlertDialog.Builder(getBaseContext())
                        .setTitle("Thông báo")
                        .setMessage("Cập nhật dữ liệu thất bại")
                        .setCancelable(true)
                        .show();
            }
        });
    }

    @Override
    public void onClick(View v) {
        String t = v.getTag().toString();

        if (t != null && t.equals("ButtonUpdate")) {
            call();
        }
    }

    public class DashboardAdapter extends RecyclerView.Adapter<DashboardAdapter.ViewHolder> {

        private Context context;
        private NgoaiTe data;

        public DashboardAdapter(Context context, NgoaiTe data) {
            this.context = context;
            this.data = data;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);

            assert inflater != null;
            View itemView = inflater.inflate(R.layout.dashboard_row, viewGroup, false);

            return new ViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

            NgoaiTe.Item item = data.getItems().get(i);

            Glide.with(context)
                    .asGif()
                    .load(item.getImageurl())
                    .into(viewHolder.icon);

            viewHolder.name.setText(item.getType());

            String buy1 = context.getResources().getString(R.string.buy_1_text);
            buy1 = buy1.replace("xxx", item.getMuatienmat());
            buy1 = buy1.replace("yyy", item.getBantienmat());
            viewHolder.buy1.setText(buy1);

            String buy2 = context.getResources().getString(R.string.buy_2_text);
            buy2 = buy2.replace("xxx", item.getMuack());
            buy2 = buy2.replace("yyy", item.getBantienmat());
            viewHolder.buy2.setText(buy2);
        }

        @Override
        public int getItemCount() {
            return data.getItems().size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            private ImageView icon;
            private TextView name;
            private TextView buy1;
            private TextView buy2;


            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                icon = itemView.findViewById(R.id.image_icon);
                name = itemView.findViewById(R.id.text_name);
                buy1 = itemView.findViewById(R.id.text_1);
                buy2 = itemView.findViewById(R.id.text_2);
            }
        }
    }
}
