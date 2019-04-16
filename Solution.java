package com.javarush.task.task19.task1905;

import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;

/* 
Закрепляем адаптер
*/

public class Solution {
    public static Map<String,String> countries = new HashMap<String,String>();

    static {
        countries.put("UA", "Ukraine");
        countries.put("CA", "Canada");
        countries.put("RU", "Russia");
    }
    public static void main(String[] args) {

    }

    public static class DataAdapter implements RowItem {
        private Customer customer;
        private Contact contact;
        public DataAdapter(Customer customer, Contact contact) {
            this.customer = customer;
            this.contact = contact;
        }

        @Override
        public String getCountryCode() {
            for (Map.Entry<String, String> entry : countries.entrySet())
                if (entry.getValue().equals(customer.getCountryName())) return entry.getKey();
            return "Неизвестная страна";
        }

        @Override
        public String getCompany() {
            return customer.getCompanyName();
        }

        @Override
        public String getContactFirstName() {
            String str = contact.getName();
            return str.substring(str.indexOf(' ') + 1);
        }

        @Override
        public String getContactLastName() {
            String str = contact.getName();
            return str.substring(0, str.indexOf(','));
        }

        @Override
        public String getDialString() {
            StringBuffer sb = new StringBuffer("callto://" + contact.getPhoneNumber());
            sb.deleteCharAt(sb.indexOf("("));
            sb.deleteCharAt(sb.indexOf(")"));
            sb.deleteCharAt(sb.indexOf("-"));
            sb.deleteCharAt(sb.indexOf("-"));
            return sb.toString();
//            return "callto://" + contact.getPhoneNumber().replaceAll("[()-]","");
        }
    }

    public static interface RowItem {
        String getCountryCode();        //example UA
        String getCompany();            //example JavaRush Ltd.
        String getContactFirstName();   //example Ivan
        String getContactLastName();    //example Ivanov
        String getDialString();         //example callto://+380501234567
    }

    public static interface Customer {
        String getCompanyName();        //example JavaRush Ltd.
        String getCountryName();        //example Ukraine
    }

    public static interface Contact {
        String getName();               //example Ivanov, Ivan
        String getPhoneNumber();        //example +38(050)123-45-67 or +3(805)0123-4567 or +380(50)123-4567 or ...
    }

}