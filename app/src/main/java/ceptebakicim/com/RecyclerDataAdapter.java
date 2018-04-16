package ceptebakicim.com;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import ceptebakicim.com.Activity.ChatActivity;
import ceptebakicim.com.Activity.CvActivity;
import ceptebakicim.com.Activity.TeklifDetayActivity;

/**
 * Created by SD on 4.03.2018.
 * dilmacsedat@gmail.com
 * :)
 */

public class RecyclerDataAdapter extends RecyclerView.Adapter<RecyclerDataAdapter.MyViewHolder> {
    private ArrayList<DummyParentDataItem> dummyParentDataItems;
    private Context applicationContext;

    RecyclerDataAdapter(ArrayList<DummyParentDataItem> dummyParentDataItems, Context applicationContext) {
        this.dummyParentDataItems = dummyParentDataItems;
        this.applicationContext = applicationContext;
    }

    @Override
    public RecyclerDataAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_parent_child_listing, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public int getItemCount() {
        return dummyParentDataItems.size();
    }

    @Override
    public void onBindViewHolder(RecyclerDataAdapter.MyViewHolder holder, int position) {
        final DummyParentDataItem dummyParentDataItem = dummyParentDataItems.get(position);
        holder.textView_parentName.setText(dummyParentDataItem.getParentName());
        int noOfChildTextViews = holder.linearLayout_childItems.getChildCount();
        int noOfChild = dummyParentDataItem.getChildDataItems().size();
        if (noOfChild < noOfChildTextViews) {
            for (int index = noOfChild; index < noOfChildTextViews; index++) {
                LinearLayout linearLayout = (LinearLayout) holder.linearLayout_childItems.getChildAt(index);
                linearLayout.setVisibility(View.GONE);
            }
        }

        for (int textViewIndex = 0; textViewIndex < noOfChild; textViewIndex++) {
            LinearLayout linearLayout = (LinearLayout) holder.linearLayout_childItems.getChildAt(textViewIndex);
            if (textViewIndex % 2 == 0)
                linearLayout.setBackgroundColor(applicationContext.getResources().getColor(R.color.lightRed));
            TextView currentTextView = (TextView) linearLayout.getChildAt(0);
            ImageButton currentImageButton = (ImageButton) linearLayout.getChildAt(1);
            currentTextView.setText(dummyParentDataItem.getChildDataItems().get(textViewIndex).getChildName());

            if (dummyParentDataItem.getTypeId() == 3) {
                TextView currentRegDateTextView = (TextView) linearLayout.getChildAt(2);
                String[] separated = dummyParentDataItem.getChildDataItems().get(textViewIndex).getRegDate().split(" ");
                currentRegDateTextView.setText(separated[0]);

                currentRegDateTextView.setVisibility(View.VISIBLE);
                currentImageButton.setVisibility(View.GONE);
            }
            if (dummyParentDataItem.getTypeId() == 5) {
                currentImageButton.setVisibility(View.GONE);
            }
            if (dummyParentDataItem.getTypeId() == 6) {
                TextView currentRegDateTextView = (TextView) linearLayout.getChildAt(2);
                String[] separated = dummyParentDataItem.getChildDataItems().get(textViewIndex).getRegDate().split(" ");
                currentRegDateTextView.setText(separated[0]);

                currentRegDateTextView.setVisibility(View.GONE);
                currentImageButton.setVisibility(View.GONE);
            } else {
                if (dummyParentDataItem.getTypeId() == 4)
                    currentImageButton.setBackgroundResource(R.drawable.ic_message_black_24dp);

                final int finalTextViewIndex = textViewIndex;
                currentImageButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        switch (dummyParentDataItem.getTypeId()) {
                            case 1: //teklif detay
                                Log.wtf("Adapter", "case teklif : " + dummyParentDataItem.getTypeId() + " / id : " + dummyParentDataItem.getChildDataItems().get(finalTextViewIndex).getChildId());
                                Intent intent = new Intent(applicationContext, TeklifDetayActivity.class);
                                intent.putExtra("pos", dummyParentDataItem.getChildDataItems().get(finalTextViewIndex).getChildId());
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                applicationContext.startActivity(intent);
                                break;
                            case 2: //cv
                                Log.wtf("Adapter", "case bakıcı : " + dummyParentDataItem.getTypeId() + " / id : " + dummyParentDataItem.getChildDataItems().get(finalTextViewIndex).getChildId());
                                Intent intent2 = new Intent(applicationContext, CvActivity.class);
                                intent2.putExtra("id", dummyParentDataItem.getChildDataItems().get(finalTextViewIndex).getChildId());
                                intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                applicationContext.startActivity(intent2);
                                break;
                            case 4: //chat
                                Log.wtf("Adapter", "case chat : " + dummyParentDataItem.getTypeId() + " / id : " + dummyParentDataItem.getChildDataItems().get(finalTextViewIndex).getChildId());
                                Intent intent3 = new Intent(applicationContext, ChatActivity.class);
                                intent3.putExtra("chatId", dummyParentDataItem.getChildDataItems().get(finalTextViewIndex).getChildId());
                                intent3.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                applicationContext.startActivity(intent3);
                                break;
                        }
                        //Toast.makeText(applicationContext, "" + ((TextView) view).getText().toString(), Toast.LENGTH_SHORT).show();
                    }
                });
            }

        }
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private Context context;
        private TextView textView_parentName;
        private LinearLayout linearLayout_childItems;

        MyViewHolder(View itemView) {
            super(itemView);

            context = itemView.getContext();

            textView_parentName = itemView.findViewById(R.id.tv_parentName);
            textView_parentName.setOnClickListener(this);

            linearLayout_childItems = itemView.findViewById(R.id.ll_child_items);
            linearLayout_childItems.setVisibility(View.GONE);

            int intMaxNoOfChild = 0;
            for (int index = 0; index < dummyParentDataItems.size(); index++) {
                int intMaxSizeTemp = dummyParentDataItems.get(index).getChildDataItems().size();
                if (intMaxSizeTemp > intMaxNoOfChild)
                    intMaxNoOfChild = intMaxSizeTemp;
            }

            for (int indexView = 0; indexView < intMaxNoOfChild; indexView++) {
                LinearLayout.LayoutParams layoutParamsTextView = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                LinearLayout.LayoutParams layoutParamsButton = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                LinearLayout.LayoutParams layoutParamsLinear = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

                layoutParamsTextView.weight = 1;

                TextView textView = new TextView(context);
                textView.setPadding(16, 16, 0, 16);
                textView.setTextSize(17);
                //textView.setOnClickListener(this);

                TextView textViewRegDate = new TextView(context);
                textViewRegDate.setPadding(0, 16, 16, 16);
                textViewRegDate.setVisibility(View.GONE);

                ImageButton imageButton = new ImageButton(context);
                imageButton.setId(indexView);
                imageButton.setPadding(0, 16, 0, 16);
                imageButton.setBackgroundResource(android.R.drawable.ic_menu_search);

                LinearLayout linearLayout = new LinearLayout(context);
                linearLayout.setOrientation(LinearLayout.HORIZONTAL);
                linearLayout.setBackground(ContextCompat.getDrawable(context, R.drawable.background_sub_module_text));

                linearLayout.addView(textView, layoutParamsTextView);
                linearLayout.addView(imageButton, layoutParamsButton);
                linearLayout.addView(textViewRegDate, layoutParamsButton);

                linearLayout_childItems.addView(linearLayout, layoutParamsLinear);
            }

        }

        @Override
        public void onClick(View view) {
            if (view.getId() == R.id.tv_parentName) {
                if (linearLayout_childItems.getVisibility() == View.VISIBLE) {
                    linearLayout_childItems.setVisibility(View.GONE);
                } else {
                    linearLayout_childItems.setVisibility(View.VISIBLE);
                }
            }
            /*else {
                TextView textViewClicked = (TextView) view;
                Toast.makeText(context, "" + textViewClicked.getText().toString(), Toast.LENGTH_SHORT).show();
            }*/
        }

    }

}
