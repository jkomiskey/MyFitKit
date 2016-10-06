package komiskey.jacob.myfitkit.database;

import android.database.Cursor;
import android.database.CursorWrapper;

import java.util.UUID;

import komiskey.jacob.myfitkit.Customer;

public class CustomerCursorWrapper extends CursorWrapper {
    public CustomerCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public Customer getCustomer() {
        String uuidString = getString(getColumnIndex(CustomerDbSchema.CustomerTable.Cols.UUID));
        String customerName = getString(getColumnIndex(CustomerDbSchema.CustomerTable.Cols.CUSTOMER_NAME));
        String customerPhone = getString(getColumnIndex(CustomerDbSchema.CustomerTable.Cols.PHONE));
        String customerEmail = getString(getColumnIndex(CustomerDbSchema.CustomerTable.Cols.EMAIL));

       Customer customer = new Customer (UUID.fromString(uuidString));

       customer.setCustomerName(customerName);
       customer.setPhoneNum(customerPhone);
       customer.setEmail(customerEmail);

       return customer;
    }
}
