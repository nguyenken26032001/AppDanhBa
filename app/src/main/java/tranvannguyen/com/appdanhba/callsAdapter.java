package tranvannguyen.com.appdanhba;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;


public class callsAdapter extends RecyclerView.Adapter<callsAdapter.ViewHolder> {

    private List<callsInformation> listCalls;
    private Context context;

    public callsAdapter(List<callsInformation> listCalls, Context context) {
        this.listCalls = listCalls;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_calls, parent, false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        callsInformation calls = listCalls.get(position);
        TextView  name,phone,date,duration;
        name = holder.name;
        phone = holder.phone;
        date = holder.date;
        duration = holder.duration;
        name.setText(listCalls.get(position).getName());
        phone.setText(listCalls.get(position).getPhone());
        date.setText(listCalls.get(position).getDate());
        duration.setText(listCalls.get(position).getDuration());
        holder.goiNgay.setOnClickListener(new View.OnClickListener() {
           @Override
            public void onClick(View v) {
               Intent intent = new Intent(Intent.ACTION_CALL);
            //   intent.setData(Uri.parse("tel:"+String.valueOf(listCalls.get(position).getPhone())));
               intent.setData(Uri.parse("tel:"+String.valueOf(phone)));
                context.startActivity(intent);
           }
        });
    }

    @Override
    public int getItemCount() {
        return listCalls.size() ;
    }

    public  class ViewHolder extends  RecyclerView.ViewHolder{

        TextView name,phone,date,duration;
        Button goiNgay;
        LinearLayout item_calls;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.contact_name);
            phone = itemView.findViewById(R.id.contact_phone);
            date = itemView.findViewById(R.id.date);
            duration = itemView.findViewById(R.id.duration);
            item_calls = itemView.findViewById(R.id.callss);
            goiNgay = itemView.findViewById(R.id.callsNow);

        }
    }



}
