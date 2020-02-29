package com.elgindy.expandablelistviewlibrary;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.diegodobelo.expandingview.ExpandingItem;
import com.diegodobelo.expandingview.ExpandingList;

public class MainActivity extends AppCompatActivity {
    private ExpandingList mExpandingList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mExpandingList = findViewById(R.id.expanding_list_main);
        createItems();
    }

    private void createItems() {
        addItem("Web", new String[]{"JavaScript", "Java", "Python", "css", "PHP", "Ruby", "c++","C"}, R.color.pink, R.drawable.web);
        addItem("Android", new String[]{"Java", "Kotlin", "flutter"}, R.color.blue, R.drawable.android);
        addItem("Security", new String[]{"c" , "c++" , "python" , "javaScript" , "PHP" , "SQL"}, R.color.purple, R.drawable.security);
        addItem("Machine learning", new String[]{"Python", "JAVA", "R", "JavaScript","Scala"}, R.color.yellow, R.drawable.ml);
    }

    private void addItem(String title, String[] subItems, int colorRes, int iconRes) {
        //Let's create an item with R.layout.expanding_layout
        final ExpandingItem item = mExpandingList.createNewItem(R.layout.expanding_layout);

        //If item creation is successful, let's configure it
        if (item != null) {
            item.setIndicatorColorRes(colorRes);
            item.setIndicatorIconRes(iconRes);
            //It is possible to get any view inside the inflated layout. Let's set the text in the item
            ((TextView) item.findViewById(R.id.title)).setText(title);

            //We can create items in batch.
            item.createSubItems(subItems.length);
            for (int i = 0; i < item.getSubItemsCount(); i++) {
                //Let's get the created sub item by its index
                final View view = item.getSubItemView(i);

                //Let's set some values in
                configureSubItem(item, view, subItems[i]);
            }
            item.findViewById(R.id.add_more_sub_items).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showInsertDialog(new OnItemCreated() {
                        @Override
                        public void itemCreated(String title) {
                            View newSubItem = item.createSubItem();
                            configureSubItem(item, newSubItem, title);
                        }
                    });
                }
            });

//            item.findViewById(R.id.remove_item).setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    mExpandingList.removeItem(item);
//                }
//            });
        }
    }

    private void configureSubItem(final ExpandingItem item, final View view, final String subTitle) {
        Log.d("TAG", "configureSubItem: " + subTitle);
        ((TextView) view.findViewById(R.id.sub_title)).setText(subTitle);
        view.findViewById(R.id.sub_title).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "" + subTitle, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, ItemClick.class);
                intent.putExtra("subItem", subTitle);
                startActivity(intent);
            }
        });
//        view.findViewById(R.id.remove_sub_item).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                item.removeSubItem(view);
//            }
//        });
    }

    private void showInsertDialog(final OnItemCreated positive) {
        final EditText text = new EditText(this);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(text);
        builder.setTitle(R.string.enter_title);
        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                positive.itemCreated(text.getText().toString());
            }
        });
        builder.setNegativeButton(android.R.string.cancel, null);
        builder.show();
    }

    interface OnItemCreated {
        void itemCreated(String title);
    }
}
