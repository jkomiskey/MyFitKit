package komiskey.jacob.myfitkit;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.List;

public class CustomerListFragment extends Fragment {

    private static final String SAVED_SUBTITLE_VISIBLE = "subtitle";

    private RecyclerView mCrimeRecyclerView;
    private CustomerAdapter mAdapter;
    private boolean mSubtitleVisible;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_customer_list, container, false);

        mCrimeRecyclerView = (RecyclerView) view
                .findViewById(R.id.customer_recycler_view);
        mCrimeRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        if (savedInstanceState != null) {
            mSubtitleVisible = savedInstanceState.getBoolean(SAVED_SUBTITLE_VISIBLE);
        }

        updateUI();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(SAVED_SUBTITLE_VISIBLE, mSubtitleVisible);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.add_customer_menu, menu);

        MenuItem subtitleItem = menu.findItem(R.id.menu_item_show_subtitle);
        if (mSubtitleVisible) {
            subtitleItem.setTitle(R.string.hide_subtitle);
        } else {
            subtitleItem.setTitle(R.string.show_subtitle);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_new_customer:
                Customer customer = new Customer();
                CustomerList.get(getActivity()).addCustomer(customer);
                Intent intent = CustomerPagerActivity
                        .newIntent(getActivity(), customer.getId());
                startActivity(intent);
                return true;
            case R.id.menu_item_show_subtitle:
                mSubtitleVisible = !mSubtitleVisible;
                getActivity().invalidateOptionsMenu();
                updateSubtitle();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void updateSubtitle() {
        CustomerList customerList = CustomerList.get(getActivity());
        int customerCount = customerList.getCustomers().size();
        String subtitle = getString(R.string.subtitle_format, customerCount);

        if (!mSubtitleVisible) {
            subtitle = null;
        }

        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.getSupportActionBar().setSubtitle(subtitle);
    }

    private void updateUI() {
        CustomerList customerList = CustomerList.get(getActivity());
        List<Customer> customers = customerList.getCustomers();

        if (mAdapter == null) {
            mAdapter = new CustomerAdapter(customers);
            mCrimeRecyclerView.setAdapter(mAdapter);
        } else {
            mAdapter.setCustomers(customers);
            mAdapter.notifyDataSetChanged();
        }

        updateSubtitle();
    }

    private class CustomerHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        private Customer mCustomer;

        private TextView mCustomerNameTextView;
        private TextView mPhoneTextView;
        private TextView mEmailTextView;

        public void bindCustomer(Customer customer) {
            mCustomer = customer;
            mCustomerNameTextView.setText(mCustomer.getCustomerName());
            mPhoneTextView.setText(mCustomer.getPhoneNum());
            mEmailTextView.setText(mCustomer.getEmail());
        }

        public CustomerHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);

            mCustomerNameTextView = (TextView)
                    itemView.findViewById(R.id.Name);
            mPhoneTextView = (TextView)
                    itemView.findViewById(R.id.Phone);
            mEmailTextView = (TextView)
                    itemView.findViewById(R.id.Email);
        }

        @Override
        public void onClick(View v) {
            Intent intent = CustomerPagerActivity.newIntent(getActivity(), mCustomer.getId());
            startActivity(intent);
        }
    }

    private class CustomerAdapter extends RecyclerView.Adapter<CustomerHolder> {

        private List<Customer> mCustomers;

        public CustomerAdapter(List<Customer> customers) {
            mCustomers = customers;
        }

        @Override
        public CustomerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater
                    .inflate(R.layout.activity_list_item_customer, parent, false);
            return new CustomerHolder(view);
        }

        @Override
        public void onBindViewHolder(CustomerHolder holder, int postion) {
            Customer customer = mCustomers.get(postion);
            holder.bindCustomer(customer);
        }

        @Override
        public int getItemCount() {
            return mCustomers.size();
        }

        public void setCustomers(List<Customer> customers) {
            mCustomers = customers;
        }
    }
}
