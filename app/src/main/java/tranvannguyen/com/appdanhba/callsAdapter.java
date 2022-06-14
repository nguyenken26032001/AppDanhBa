package tranvannguyen.com.appdanhba;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


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
        View view = LayoutInflater.from(context).inflate(R.layout.call_log_view, parent, false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        callsInformation calls = listCalls.get(position);
        String phoneNumber=listCalls.get(position).getLogTime();
        TextView  name,time,date,duration;
        CircleImageView imageView;
        ImageButton goiNgay;
        name = holder.name;
        date = holder.date;
        duration = holder.duration;
        time=holder.time;
        goiNgay = holder.goiNgay;
        imageView= holder.imageView;
        name.setText(listCalls.get(position).getLogName());
        date.setText(listCalls.get(position).getLogDate());
        duration.setText(listCalls.get(position).getLogDuration());
        time.setText(listCalls.get(position).getLogTime());
        imageView.setImageBitmap(listCalls.get(position).getLogImage());
        holder.goiNgay.setOnClickListener(new View.OnClickListener() {
           @Override
            public void onClick(View v) {
               Intent intent = new Intent(Intent.ACTION_CALL);
            //  intent.setData(Uri.parse("tel:"+String.valueOf(listCalls.get(position).getLogTime())));
               intent.setData(Uri.parse("tel:"+String.valueOf(phoneNumber)));
                context.startActivity(intent);
           }
        });
    }

    @Override
    public int getItemCount() {
        return listCalls.size() ;
    }

    public  class ViewHolder extends  RecyclerView.ViewHolder{
        TextView name,date,duration,time;
        ImageButton goiNgay;
        LinearLayout item_calls;
        CircleImageView imageView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.log_name);
            date = (TextView) itemView.findViewById(R.id.log_date);
            duration = (TextView) itemView.findViewById(R.id.log_duration);
            time =  (TextView)itemView.findViewById(R.id.log_time);
            item_calls = (LinearLayout) itemView.findViewById(R.id.Log_calls);
            goiNgay =  (ImageButton) itemView.findViewById(R.id.log_callNow);
            imageView = (CircleImageView) itemView.findViewById(R.id.log_image);
        }
    }



}
