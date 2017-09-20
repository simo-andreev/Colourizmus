package bg.o.sim.colourizmus.view;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import bg.o.sim.colourizmus.R;
import bg.o.sim.colourizmus.model.CR;
import bg.o.sim.colourizmus.model.CustomColour;
import bg.o.sim.colourizmus.utils.Util;

public class ColourListActivity extends AppCompatActivity implements CheckBox.OnCheckedChangeListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_colour_list);

        RecyclerView mRecyclerView = findViewById(R.id.list_recycler);

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setVerticalScrollBarEnabled(true);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(new RecyclerAdapter(this, this));
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        CustomColour c = (CustomColour) buttonView.getTag();
        if (c == null)
            Log.e(Util.LOG_TAG_ERR, "onCheckedChanged: com/colourizmus/view/ColourListActivity.java:49 : button tag was nill!!!");

        CR.setColourFavorite(c);
    }

}


class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ColourViewHolder>{

    private CompoundButton.OnCheckedChangeListener checkListener;

    RecyclerAdapter(LifecycleOwner lifecycleOwner, CheckBox.OnCheckedChangeListener checkListener) {
        CR.getCachedColours().observe(lifecycleOwner, cacheObserver -> RecyclerAdapter.this.notifyDataSetChanged());
        this.checkListener = checkListener;
    }

    @Override
    public RecyclerAdapter.ColourViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CardView v = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.card_colour_row, parent, false);
        return new ColourViewHolder(v, checkListener);
    }

    @Override
    public void onBindViewHolder(ColourViewHolder holder, int position) {
        CustomColour c = CR.getCachedColours().getValue().get(position);

        holder.favouriteCheckbox.setTag(c);

        holder.colourPreview.setBackgroundColor(c.getValue());
        holder.colourName.setText(c.getName());
        holder.favouriteCheckbox.setChecked(c.getIsFavourite());
    }

    @Override
    public int getItemCount() {
        return CR.getCachedColours().getValue() == null ? 0 : CR.getCachedColours().getValue().size();
    }

    static class ColourViewHolder extends RecyclerView.ViewHolder {
        private ImageView colourPreview;
        private TextView colourName;
        private CheckBox favouriteCheckbox;

        ColourViewHolder(CardView v, CheckBox.OnCheckedChangeListener checkListener) {
            super(v);
            colourPreview = v.findViewById(R.id.cardview_colour_preview);
            colourName = v.findViewById(R.id.cardview_colour_name);
            favouriteCheckbox = v.findViewById(R.id.cardview_colour_favourite);

            favouriteCheckbox.setOnCheckedChangeListener(checkListener);
        }
    }
}
