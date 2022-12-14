package ca.cmpt276.a2.model;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import ca.cmpt276.a2.GameInfoActivity;
import ca.cmpt276.a2.MainActivity;
import ca.cmpt276.a2.R;

// https://www.youtube.com/watch?v=Mc0XT58A1Z4
public class GameInfoRecyclerViewAdapter extends RecyclerView.Adapter<GameInfoRecyclerViewAdapter.MyViewHolder>{

    Context context;
    ArrayList<GameInfoCardModel> gameInfoCardModels;
    //private RecyclerViewClickListener listener;

    public GameInfoRecyclerViewAdapter(Context context, ArrayList<GameInfoCardModel> gameInfoCardModels){
        this.context = context;
        this.gameInfoCardModels = gameInfoCardModels;
        //this.listener = listener;
    }

    @NonNull
    @Override
    public GameInfoRecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // This is where we inflate the layout (giving the look to our rows)
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recyclerview_cardview_layout, parent, false);

        return new GameInfoRecyclerViewAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GameInfoRecyclerViewAdapter.MyViewHolder holder, int position) {
        // Assigning values to the views we created in the recyclerview_cardview_layout.xml file
        // based on the position of the recycler view

        holder.tvWinners.setText("Winning players: " + gameInfoCardModels.get(position).getGameWinners());
        holder.tvScores.setText("Scores: " + gameInfoCardModels.get(position).getGameScores());
        holder.tvDatePlayed.setText(gameInfoCardModels.get(position).getGameTimePlayed());
        holder.ivWinner.setImageResource(gameInfoCardModels.get(position).getGameWinnersIcon());

    }

    @Override
    public int getItemCount() {
        // To tell the recycler view how many items we want displayed on the screen
        return gameInfoCardModels.size();
    }

    public ArrayList<GameInfoCardModel> getGameInfoCardModels(){
        return gameInfoCardModels;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        // Grabbing the views from our recyclerview layout file
        // Can think of this as an onCreate method

        ImageView ivWinner;
        TextView tvWinners;
        TextView tvScores;
        TextView tvDatePlayed;
        Button btnEditGame;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            ivWinner = itemView.findViewById(R.id.ivWinnerIcon);
            tvWinners = itemView.findViewById(R.id.tvWinners);
            tvScores = itemView.findViewById(R.id.tvScores);
            tvDatePlayed = itemView.findViewById(R.id.tvDatePlayed);
            btnEditGame = itemView.findViewById(R.id.btnEditGame);

            btnEditGame.setOnClickListener( view -> {
                Intent editGameIntent = GameInfoActivity.makeIntent(context, getAdapterPosition());
                context.startActivity(editGameIntent);
                notifyItemChanged(getAdapterPosition());
            });

        }

    }

}
