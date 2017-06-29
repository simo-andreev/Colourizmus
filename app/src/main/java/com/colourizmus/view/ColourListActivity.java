package com.colourizmus.view;

import android.arch.lifecycle.LifecycleActivity;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.support.annotation.Nullable;
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

import com.colourizmus.R;
import com.colourizmus.model.ColourRepository;
import com.colourizmus.model.CustomColour;
import com.colourizmus.utils.Util;

import java.util.List;


public class ColourListActivity extends LifecycleActivity implements CheckBox.OnCheckedChangeListener{

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_colour_list);

        recyclerView = (RecyclerView) findViewById(R.id.list_recycler);

        recyclerView.setHasFixedSize(true);
        recyclerView.setVerticalScrollBarEnabled(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new RecyclerAdapter(this, this));
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        CustomColour c = (CustomColour) buttonView.getTag();
        if (c == null) {
            Log.e(Util.LOG_TAG_ERR, "onCheckedChanged: com/colourizmus/view/ColourListActivity.java:49 : button tag was nill!!!");
            return;
        }
        ColourRepository.setColourFavorite(c, isChecked);
    }

}


class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ColourViewHolder>{

    private CompoundButton.OnCheckedChangeListener checkListener;

    protected RecyclerAdapter(LifecycleOwner lifecycleOwner, CheckBox.OnCheckedChangeListener checkListener) {
        ColourRepository.getCachedColours().observe(lifecycleOwner, new Observer<List<CustomColour>>() {
            @Override
            public void onChanged(@Nullable List<CustomColour> customColours) {
                RecyclerAdapter.this.notifyDataSetChanged();
            }
        });
        this.checkListener = checkListener;
    }

    @Override
    public RecyclerAdapter.ColourViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CardView v = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.card_colour_row, parent, false);
        ColourViewHolder vh = new ColourViewHolder(v, checkListener);
        return vh;
    }

    @Override
    public void onBindViewHolder(ColourViewHolder holder, int position) {
        CustomColour c = ColourRepository.getCachedColours().getValue().get(position);

        holder.favouriteCheckbox.setTag(c);

        holder.colourPreview.setBackgroundColor(c.getValue());
        holder.colourName.setText(c.getName());
        holder.favouriteCheckbox.setChecked(c.getIsFavourite());
    }

    @Override
    public int getItemCount() {
        if (ColourRepository.getCachedColours().getValue() != null)
            return ColourRepository.getCachedColours().getValue().size();
        else
            return 0;
    }

    protected static class ColourViewHolder extends RecyclerView.ViewHolder {
        private ImageView colourPreview;
        private TextView colourName;
        private CheckBox favouriteCheckbox;

        public ColourViewHolder(CardView v, CheckBox.OnCheckedChangeListener checkListener) {
            super(v);
            colourPreview = (ImageView) v.findViewById(R.id.cardview_colour_preview);
            colourName = (TextView) v.findViewById(R.id.cardview_colour_name);
            favouriteCheckbox = (CheckBox) v.findViewById(R.id.cardview_colour_favourite);

            favouriteCheckbox.setOnCheckedChangeListener(checkListener);
        }
    }
}
