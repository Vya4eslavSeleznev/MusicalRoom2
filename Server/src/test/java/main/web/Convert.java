package main.web;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class Convert {

  public java.util.Date convertToDate(String dateToParse) throws ParseException {
    SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");

    return formatter.parse(dateToParse);
  }
}
