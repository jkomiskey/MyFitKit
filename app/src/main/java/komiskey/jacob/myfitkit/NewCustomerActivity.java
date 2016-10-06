package komiskey.jacob.myfitkit;

import android.support.v4.app.Fragment;

import java.util.UUID;


public class NewCustomerActivity extends SingleFragmentActivity {

    private static final String EXTRA_CUSTOMER_ID =
            "komiskey.jacob.myfitkit.customer_id";

    @Override
    protected Fragment createFragment() {
        UUID customerId = (UUID) getIntent()
                .getSerializableExtra(EXTRA_CUSTOMER_ID);
        return CustomerFragment.newInstance(customerId);
    }
}

