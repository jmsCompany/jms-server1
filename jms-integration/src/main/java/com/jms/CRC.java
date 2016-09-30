package com.jms;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CRC {
	
	
	  public static String CRC16(String data) throws UnsupportedEncodingException
      {
		 
          byte[] temp =  data.getBytes("ascii");
          int por = 0xffff;
          for (int j = 0; j < temp.length; j++)
          {
              por >>= 8;
              por ^= temp[j];

              for (int i = 0; i < 8; i++)
              {
                  if ((por & 0x01) == 1)
                  {
                      por = por >> 1;
                      por = por ^ 0xa001;
                  }
                  else
                      por = por >> 1;
              }
          }
          return ""+Integer.toHexString(por);
      }
	
	 public static void main(String[] args) {
		
		
		
//		Date d = new Date();
//		d.setTime(1469808000000l);
//		
//		System.out.println(d);
//
//		  String data = "ST=32;CN=2031;PW=123456;MN=32060032020014;CP=&&DataTime=20160612000000;B01-Min=0.0000,B01-Avg=13.0090,B01-Max=46.5999,B01-Cou=311.0000;001-Min=7.0000,001-Avg=7.0000,001-Max=7.0000,001-Cou=2.1770;011-Min=41.5000,011-Avg=51.4222,011-Max=60.1000,011-Cou=17.2828;060-Min=28.5100,060-Avg=29.4431,060-Max=32.3600,060-Cou=9.3910&&";
//		  try {
//			System.out.println(CRC16(data));
//		} catch (UnsupportedEncodingException e) {
//			e.printStackTrace();
//		}
	
		  
		  
			Date d = new Date();
			SimpleDateFormat formatter = new SimpleDateFormat ("MMdd"); 
			System.out.println(formatter.format(d));
	}

}
  
