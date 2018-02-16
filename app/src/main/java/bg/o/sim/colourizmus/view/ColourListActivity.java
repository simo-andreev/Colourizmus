package bg.o.sim.colourizmus.view;

import android.arch.lifecycle.LifecycleOwner;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.Serializable;
import java.util.Random;

import bg.o.sim.colourizmus.R;
import bg.o.sim.colourizmus.model.CR;
import bg.o.sim.colourizmus.model.CustomColour;
import bg.o.sim.colourizmus.utils.UtilKt;

public class ColourListActivity extends AppCompatActivity implements CheckBox.OnCheckedChangeListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_colour_list);

        RecyclerView mRecyclerView = findViewById(R.id.list_recycler);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(new RecyclerAdapter(this, getLayoutInflater(), this));

        findViewById(R.id.TEST_DATA_GEN_BUTTON).setOnClickListener(view -> {
            Random r = new Random();

            for (int i = 0; i < 100; i++)
                CR.saveColour(new CustomColour(Color.rgb(r.nextInt(256), r.nextInt(256), r.nextInt(256)), "Test " + r.nextGaussian()));

            UtilKt.toastLong(ColourListActivity.this, "DONE!");
        });

        findViewById(R.id.TEST_DATA_REM_BUTTON).setOnClickListener(view -> {
            CR.deleteAllColours();
            UtilKt.toastLong(ColourListActivity.this, "DONE!");
        });
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        CustomColour c = (CustomColour) buttonView.getTag();
        if (c == null)
            Log.e(UtilKt.LOG_TAG_ERR, "onCheckedChanged: com/colourizmus/view/ColourListActivity.java:49 : button tag was nill!!!");

        CR.setColourFavorite(c, isChecked);
    }

    public static void start(Context context) {
        Intent starter = new Intent(context, ColourListActivity.class);
        context.startActivity(starter);
    }
}


class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ColourViewHolder> {

    private final LayoutInflater mInflater;
    private final Context mContext;

    RecyclerAdapter(LifecycleOwner lifecycleOwner, LayoutInflater inflater, Context context) {
        this.mInflater = inflater;
        this.mContext = context;
        CR.sCachedColours.observe(lifecycleOwner, cacheObserver -> RecyclerAdapter.this.notifyDataSetChanged());
        setHasStableIds(true);
    }

    @Override
    public RecyclerAdapter.ColourViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ColourViewHolder(mInflater.inflate(R.layout.card_colour_row, parent, false), mContext);
    }

    @Override
    public long getItemId(int position) {
        return CR.sCachedColours.getValue().get(position).getValue();
    }

    @Override
    public void onBindViewHolder(ColourViewHolder holder, int position) {
        CustomColour c = CR.sCachedColours.getValue().get(position);

        holder.rootView.setTag(c);

        holder.mPreview.setBackgroundColor(c.getValue());
        holder.mName.setText(c.getName());
        holder.mIsFavourite.setChecked(c.isFavourite());
    }

    @Override
    public int getItemCount() {
        return CR.sCachedColours.getValue() != null ? CR.sCachedColours.getValue().size() : 0;
    }

    static class ColourViewHolder extends RecyclerView.ViewHolder {
        private final View rootView;
        private final ImageView mPreview;
        private final TextView mName;
        private final CheckBox mIsFavourite;

        ColourViewHolder(View holderView, Context c) {
            super(holderView);

            rootView = holderView;
            mPreview = holderView.findViewById(R.id.cardview_colour_preview);
            mName = holderView.findViewById(R.id.cardview_colour_name);
            mIsFavourite = holderView.findViewById(R.id.cardview_colour_favourite);

            holderView.setOnClickListener(view -> {
                Intent i = new Intent(c, ColourDetailsActivity.class);
                i.putExtra(UtilKt.EXTRA_COLOUR, ((Serializable)rootView.getTag()));
                c.startActivity(i);
            });
        }
    }
}
