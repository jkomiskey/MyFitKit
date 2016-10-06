package komiskey.jacob.myfitkit;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import komiskey.jacob.myfitkit.database.CustomerBaseHelper;
import komiskey.jacob.myfitkit.database.CustomerCursorWrapper;
import komiskey.jacob.myfitkit.database.CustomerDbSchema;

public class CustomerList {
    private static CustomerList sCustomerList;

    private Context mContext;
    private SQLiteDatabase mDatabase;

    public static CustomerList get(Context context) {
        if (sCustomerList == null) {
            sCustomerList = new CustomerList(context);
        }
        return sCustomerList;
    }
    private CustomerList(Context context) {
        mContext = context.getApplicationContext();
        mDatabase = new CustomerBaseHelper(mContext)
                .getWritableDatabase();
    }

    public void addCustomer(Customer c) {
        ContentValues values = getContentValues(c);

        mDatabase.insert(CustomerDbSchema.CustomerTable.NAME, null, values);
    }

    public List<Customer> getCustomers() {
        List<Customer> customers = new ArrayList<>();

        CustomerCursorWrapper cursor = queryCustomers(null, null);

        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                customers.add(cursor.getCustomer());
                cursor.moveToNext();
            }
        }finally {
                cursor.close();
            }

        return customers;
        }

    public Customer getCustomer(UUID id) {
        CustomerCursorWrapper cursor = queryCustomers(
                CustomerDbSchema.CustomerTable.Cols.UUID + " = ?",
                new String[] {id.toString() }
        );

        try {
            if (cursor.getCount() == 0) {
                return null;
            }

            cursor.moveToFirst();
            return cursor.getCustomer();
        } finally {
            cursor.close();
        }
    }

    public File getPhotoFile(Customer customer) {
        File externalFilesDir = mContext
                .getExternalFilesDir(Environment.DIRECTORY_PICTURES);

        if (externalFilesDir == null) {
            return null;
        }
        return new File(externalFilesDir, customer.getPhotoFilename());
    }

    public void updateCustomer(Customer customer) {
        String uuidString = customer.getId().toString();
        ContentValues values = getContentValues(customer);

        mDatabase.update(CustomerDbSchema.CustomerTable.NAME, values,
                CustomerDbSchema.CustomerTable.Cols.UUID + " = ?",
                new String[] {uuidString});
    }

    private static ContentValues getContentValues(Customer customer) {
        ContentValues values = new ContentValues();
        values.put(CustomerDbSchema.CustomerTable.Cols.UUID, customer.getId().toString());
        values.put(CustomerDbSchema.CustomerTable.Cols.CUSTOMER_NAME, customer.getCustomerName());
        values.put(CustomerDbSchema.CustomerTable.Cols.PHONE, customer.getPhoneNum());
        values.put(CustomerDbSchema.CustomerTable.Cols.EMAIL, customer.getEmail());

        return values;
    }

    private CustomerCursorWrapper queryCustomers (String whereClause, String[] whereArgs) {
        Cursor cursor = mDatabase.query(
                CustomerDbSchema.CustomerTable.NAME,
                null,
                whereClause,
                whereArgs,
                null,
                null,
                null
        );

        return new CustomerCursorWrapper(cursor);
    }
}
