package komiskey.jacob.myfitkit.database;

public class CustomerDbSchema {
    public static final class CustomerTable {
        public static final String NAME = "customers";

        public static final class Cols {
            public static final String UUID = "uuid";
            public static final String CUSTOMER_NAME = "customer";
            public static final String PHONE = "phone";
            public static final String EMAIL = "email";
        }
    }
}
