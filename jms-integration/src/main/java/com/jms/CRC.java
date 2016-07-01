package com.jms;

import java.io.UnsupportedEncodingException;

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
          System.out.println(por);
         // return por;
          return String.format("{0:X4}", por);
      }
	
	public static void main(String[] args) {

		  String data = "ST=32;CN=2051;PW=123456;MN=32060032020014;CP=&&DataTime=20160620000000;B01-Min=40.2999,B01-Avg=43.0200,B01-Max=43.7999,B01-Cou=7.0000;001-Min=7.0000,001-Avg=7.0000,001-Max=7.0000,001-Cou=0.0490;011-Min=20.0000,011-Avg=20.0000,011-Max=20.0000,011-Cou=0.1400;060-Min=20.0700,060-Avg=20.0700,060-Max=20.0700,060-Cou=0.1405&&";
		  try {
			System.out.println(CRC16(data));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	
	}

}
  
