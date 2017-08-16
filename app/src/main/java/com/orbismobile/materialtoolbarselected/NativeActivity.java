package com.orbismobile.materialtoolbarselected;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class NativeActivity extends AppCompatActivity implements NativeAdapter.OnSelectItemListener{

    private NativeAdapter nativeAdapter;
    private List<UserEntity> userEntities = new ArrayList<>();
    private Toolbar toolbar;


    private ActionMode actionMode;
    private ActionModeCallback actionModeCallback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_native);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        for (int i = 0; i <= 50; i++) {
            userEntities.add(new UserEntity("CARLOS " + i, false));
        }

        nativeAdapter = new NativeAdapter(userEntities, this);
        RecyclerView rcvNative = (RecyclerView) findViewById(R.id.rcvNative);
        rcvNative.setLayoutManager(new LinearLayoutManager(this));
        rcvNative.setAdapter(nativeAdapter);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        actionModeCallback = new ActionModeCallback();
    }

    @Override
    public void onItemClicked(int index) {
        if (actionMode == null) {
            actionMode = startSupportActionMode(actionModeCallback);
        }

        int count = nativeAdapter.getSelectedItemCount();
        if (count == 0) {
            actionMode.finish();
        } else {
            actionMode.setTitle(String.valueOf(count));
            actionMode.invalidate();
        }
    }

    private class ActionModeCallback implements ActionMode.Callback {

        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            mode.getMenuInflater().inflate(R.menu.menu_native_mode, menu);
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            switch (item.getItemId()) {
                case R.id.action_delete:
                    // delete all the selected messages
                    //deleteMessages();
                    mode.finish();
                    return true;

                default:
                    return false;
            }
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
            nativeAdapter.clearAllSelections();
            actionMode = null;
        }
    }

}
