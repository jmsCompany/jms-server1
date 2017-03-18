package com.jms;


import java.io.FileWriter;
import java.io.IOException;

public class CRandom {
	

//	public static void main(String[] args) throws IOException {
//
//	//File f = new File("/Users/renhongtao/1.csv");
//	 FileWriter fw = new FileWriter("/Users/renhongtao/1.csv",false);  
//	 int sum_in=0;
//	 int sum_out=0;
//	  for(int i=1; i<5;i++)
//	  {
//		  
//		int vfi=  (int)((Math.random())*600);
//		int vfo = (int)((Math.random())*10);
//		sum_in =sum_in +vfi;
//		sum_out =sum_out+vfo;
//		fw.write(i+","+vfi+","+vfo+"\r\n");
//	  }
//	  for(int i=5; i<50;i++)
//	  {
//		  
//		int vfi=  (int)((Math.random())*400);
//		int vfo = (int)((Math.random())*30);
//		sum_in =sum_in +vfi;
//		sum_out =sum_out+vfo;
//		fw.write(i+","+vfi+","+vfo+"\r\n");
//	  }
//	  for(int i=50; i<100;i++)
//	  {
//		  
//		int vfi=  (int)((Math.random())*400);
//		int vfo = (int)((Math.random())*50);
//		sum_in =sum_in +vfi;
//		sum_out =sum_out+vfo;
//		fw.write(i+","+vfi+","+vfo+"\r\n");
//	  }
//	  for(int i=100; i<500;i++)
//	  {
//		  
//		int vfi=  (int)((Math.random())*100);
//		int vfo = (int)((Math.random())*55);
//		sum_in =sum_in +vfi;
//		sum_out =sum_out+vfo;
//		fw.write(i+","+vfi+","+vfo+"\r\n");
//	  }
//	  for(int i=500; i<550;i++)
//	  {
//		  
//		int vfi=  (int)((Math.random())*80);
//		int vfo = (int)((Math.random())*280);
//		sum_in =sum_in +vfi;
//		sum_out =sum_out+vfo;
//		fw.write(i+","+vfi+","+vfo+"\r\n");
//	  }
//	  for(int i=550; i<650;i++)
//	  {
//		  
//		int vfi=  (int)((Math.random())*100);
//		int vfo = (int)((Math.random())*200);
//		sum_in =sum_in +vfi;
//		sum_out =sum_out+vfo;
//		fw.write(i+","+vfi+","+vfo+"\r\n");
//	  }
//	  for(int i=650; i<690;i++)
//	  {
//		  
//		int vfi=  (int)((Math.random())*80);
//		int vfo = (int)((Math.random())*260);
//		sum_in =sum_in +vfi;
//		sum_out =sum_out+vfo;
//		fw.write(i+","+vfi+","+vfo+"\r\n");
//	  }
//	  for(int i=690; i<705;i++)
//	  {
//		  
//		int vfi=  (int)((Math.random())*20);
//		int vfo = (int)((Math.random())*200);
//		sum_in =sum_in +vfi;
//		sum_out =sum_out+vfo;
//		fw.write(i+","+vfi+","+vfo+"\r\n");
//	  }
////	  for(int i=800; i<911;i++)
////	  {
////		  
////		int vfi=  (int)((Math.random())*20);
////		int vfo = (int)((Math.random())*70);
////		sum_in =sum_in +vfi;
////		sum_out =sum_out+vfo;
////		fw.write(i+","+vfi+","+vfo+"\r\n");
////	  }
////	  
////	  for(int i=911; i<943;i++)
////	  {
////		  
////		int vfi=  (int)((Math.random())*50);
////		int vfo = (int)((Math.random())*10);
////		sum_in =sum_in +vfi;
////		sum_out =sum_out+vfo;
////		fw.write(i+","+vfi+","+vfo+"\r\n");
////	  }
//	  
////	  for(int i=943; i<1261;i++)
////	  {
////		  
////		int vfi=  (int)((Math.random())*50);
////		int vfo = (int)((Math.random())*50);
////		sum_in =sum_in +vfi;
////		sum_out =sum_out+vfo;
////		fw.write(i+","+vfi+","+vfo+"\r\n");
////	  }
////	  for(int i=1261; i<1471;i++)
////	  {
////		  
////		int vfi=  (int)((Math.random())*50);
////		int vfo = (int)((Math.random())*50);
////		sum_in =sum_in +vfi;
////		sum_out =sum_out+vfo;
////		fw.write(i+","+vfi+","+vfo+"\r\n");
////	  }
////	  
////	  for(int i=1471; i<1480;i++)
////	  {
////		  
////		int vfi=  (int)((Math.random())*550);
////		int vfo = (int)((Math.random())*50);
////		sum_in =sum_in +vfi;
////		sum_out =sum_out+vfo;
////		fw.write(i+","+vfi+","+vfo+"\r\n");
////	  }
////	  
////	  for(int i=1480; i<1526;i++)
////	  {
////		  
////		int vfi=  (int)((Math.random())*50);
////		int vfo = (int)((Math.random())*150);
////		sum_in =sum_in +vfi;
////		sum_out =sum_out+vfo;
////		fw.write(i+","+vfi+","+vfo+"\r\n");
////	  }
////	  for(int i=1526; i<1694;i++)
////	  {
////		  
////		int vfi=  (int)((Math.random())*50);
////		int vfo = (int)((Math.random())*45);
////		sum_in =sum_in +vfi;
////		sum_out =sum_out+vfo;
////		fw.write(i+","+vfi+","+vfo+"\r\n");
////	  }
////	  
////	  for(int i=1694; i<1861;i++)
////	  {
////		  
////		int vfi=  (int)((Math.random())*50);
////		int vfo = (int)((Math.random())*45);
////		sum_in =sum_in +vfi;
////		sum_out =sum_out+vfo;
////		fw.write(i+","+vfi+","+vfo+"\r\n");
////	  }
//	  System.out.println("in: " + sum_in +", out: " +sum_out);
//	  fw.close();
//	}

}
  
