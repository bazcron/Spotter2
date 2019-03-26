package apps.my.spotter.activities.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import apps.my.spotter.activities.activities.Base;
import apps.my.spotter.R;

import apps.my.spotter.activities.activities.EditIssues;
import apps.my.spotter.activities.activities.Home;
import apps.my.spotter.activities.adapters.IssueListAdapter;
import apps.my.spotter.activities.models.Issue;

import static apps.my.spotter.activities.activities.Base.ALLissueList;
import static apps.my.spotter.activities.activities.Base.totalNumberOfIssues;

public class IssueFragment extends ListFragment implements View.OnClickListener,
        AbsListView.MultiChoiceModeListener
{
    public Base activity;
    public static IssueListAdapter listAdapter;
    public ListView listView;

    public IssueFragment() {
        // Required empty public constructor
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        Bundle activityInfo = new Bundle(); // Creates a new Bundle object
        activityInfo.putString("issueId", (String) v.getTag());
        Intent goEdit = new Intent(getActivity(), EditIssues.class); // Creates a new Intent
        /* Add the bundle to the intent here */
        goEdit.putExtras(activityInfo);
        Log.v("issue1", "EDIT : " + goEdit);
        getActivity().startActivity(goEdit); // Launch the Intent
    }

    public static IssueFragment newInstance() {
        IssueFragment fragment = new IssueFragment();
        return fragment;
    }

    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
        this.activity = (Base) context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        listAdapter = new IssueListAdapter(activity, this, Base.IssueList);
        setListAdapter (listAdapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View v = super.onCreateView(inflater, parent, savedInstanceState);

        listView = v.findViewById(android.R.id.list);
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
        listView.setMultiChoiceModeListener(this);

        return v;
    }

    @Override
    public void onStart()
    {
        super.onStart();
    }

    @Override
    public void onClick(View view)
    {
        if (view.getTag() instanceof Issue)
        {
            onIssueDelete ((Issue) view.getTag());
        }
    }

    public void onIssueDelete(final Issue issue)
    {
        String stringName = issue.street;
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setMessage("Are you sure you want to Delete the \'issue\' " + stringName + "?");
        builder.setCancelable(false);

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int id)
            {

                DatabaseReference ToDelete = FirebaseDatabase.getInstance().getReference().child("issue");
DatabaseReference issueToDelete = ToDelete.child(issue.issueId);
                issueToDelete.removeValue();

                totalNumberOfIssues--;        //decrement the number of issues by 1
                Base.IssueList.remove(issue); // remove from our list
                Base.ALLissueList.remove(issue);  //remove from All Issues List ..........
                listAdapter.IssueList.remove(issue); // update adapters data
                listAdapter.notifyDataSetChanged(); // refresh adapter
            }
        }).setNegativeButton("No", new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int id)
            {
                dialog.cancel();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    /* ************ MultiChoiceModeListener methods (begin) *********** */
    @Override
    public boolean onCreateActionMode(ActionMode actionMode, Menu menu)
    {
        MenuInflater inflater = actionMode.getMenuInflater();
        inflater.inflate(R.menu.delete_list_context, menu);
        return true;
    }

    @Override
    public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
        return false;
    }

    @Override
    public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem)
    {
        switch (menuItem.getItemId())
        {
            case R.id.menu_item_delete_issue:
                deleteissues(actionMode);
                return true;
            default:
                return false;
        }
    }

    private void deleteissues(ActionMode actionMode)
    {
        for (int i = listAdapter.getCount() - 1; i >= 0; i--)
        {
            if (listView.isItemChecked(i))
            {
                Base.IssueList.remove(listAdapter.getItem(i));
            }
        }
        actionMode.finish();
        listAdapter.notifyDataSetChanged();
    }

    @Override
    public void onDestroyActionMode(ActionMode actionMode)
    {}

    @Override
    public void onItemCheckedStateChanged(ActionMode actionMode, int position, long id, boolean checked)
    {}
    /* ************ MultiChoiceModeListener methods (end) *********** */
}
