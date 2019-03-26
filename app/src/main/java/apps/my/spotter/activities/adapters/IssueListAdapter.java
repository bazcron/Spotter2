package apps.my.spotter.activities.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.List;

import apps.my.spotter.activities.models.Issue;
import apps.my.spotter.R;
import apps.my.spotter.activities.models.Issue;

public class IssueListAdapter extends ArrayAdapter<Issue>
{
    private Context context;
    private View.OnClickListener deleteListener;
    public List<Issue> IssueList;

    public IssueListAdapter(Context context, View.OnClickListener deleteListener, List<Issue> IssueList)
    {
        super(context, R.layout.issue_row, IssueList);

        this.context = context;
        this.deleteListener = deleteListener;
        this.IssueList = IssueList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        IssueItem item = new IssueItem(context, parent,
                                         deleteListener, IssueList.get(position));
        return item.view;
    }

    @Override
    public int getCount() {
        return IssueList.size();
    }

    @Override
    public Issue getItem(int position) {
        return IssueList.get(position);
    }
//    @Override
//    public Issue getItem(int position)
//    {
//        return null;
//    }
//
//    @Override
//    public long getItemId(int position)
//    {
//        return 0;
//    }
//
//    @Override
//    public int getPosition(Issue c)
//    {
//        return 0;
//    }
}
