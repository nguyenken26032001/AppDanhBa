package tranvannguyen.com.appdanhba;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class adapter  extends RecyclerView.Adapter<adapter.viewHolder>{
    private ArrayList<user> listUuser;
    private Context context;

    public adapter(ArrayList<user> listUuser, Context context) {
        this.listUuser = listUuser;
        this.context = context;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_user, parent, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
      final   user user = listUuser.get(position);
        holder.txtuserName.setText(listUuser.get(position).getName());
        holder.layout_item.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            goToPageDetail(user);
        }
    });
    }

    public void goToPageDetail(user user) {
        Intent intent = new Intent(context, danhBaDetail.class);
          Bundle bundle = new Bundle();
         bundle.putSerializable("object", user);
         intent.putExtras(bundle);
            context.startActivity(intent);
    }
    @Override
    public int getItemCount() {
        return listUuser.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
         RelativeLayout layout_item;
          TextView txtuserName;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            txtuserName = (TextView) itemView.findViewById(R.id.user);
            layout_item =itemView.findViewById(R.id.layout_item);
        }
    }
    public interface ItemClickListener {
        void onClick(View view, int position,boolean isLongClick);
    }
}
