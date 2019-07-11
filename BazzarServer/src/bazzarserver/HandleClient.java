/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bazzarserver;

import java.io.*;
import java.net.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author Dell
 */
public class HandleClient implements Runnable {
    //for socketprogrammin
    Socket socket;
    ObjectOutputStream objectOutputStream;
    ObjectInputStream objectInputStream;
    
    //for addcart and addwish buttons
    List<addCart> cartItem;
    List<addWish> wishItem;
    
    //variables needed
    Date d;
    String localProductName,localDate,localTime,localSeller;
    int localCustId,localProductId,localAmount,localQuantity;
    int CustId,switchCase,result,discount,pId;
    String Username,Password, Address,Email,DOB,PhoneNo;
    String query2;
    String query4;
    
    //sql variables
    PreparedStatement ps;
    ResultSet rs;
    String query;
    
    public HandleClient(Socket socket) {
        this.socket = socket;
        try {
            System.out.println("reached 10091");
            objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            objectInputStream = new ObjectInputStream(socket.getInputStream());
          
            System.out.println("reached 1111");
            
        } catch (Exception e) {
            
            JOptionPane.showMessageDialog(null,"unable to connect to Socket"+e);            
        }
    }
    public static Connection getConnection(){
      Connection c=null;
      try{
          c=DriverManager.getConnection("jdbc:mysql://localhost/kuku_bazaar","root","");
          }
    
     catch(SQLException e){
       JOptionPane.showMessageDialog(null,"no connection"+ e);
          }
      return c;
    }
    public void run( )
    {
        try
        {
            System.out.println("running run");
//            objectInputStream = new ObjectInputStream(socket.getInputStream());
//            objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            switchCase= (int)objectInputStream.readObject();
            System.out.println(switchCase);
            query = (String)objectInputStream.readObject();
            System.out.println(query);
            
            switch (switchCase)
            {
                case 1:
                    ps = HandleClient.getConnection().prepareStatement(query);
                    Username = (String)objectInputStream.readObject();
                    Password = (String)objectInputStream.readObject();
                    System.out.println(Username+"\n");
                    System.out.println(Password+"\n");
                    ps.setString(1,Username);
                    ps.setString(2,Password);
                    System.out.println("query is pending \n");
                    rs = ps.executeQuery();
                    System.out.println(" query runned \n");
                    if(rs.next())
                    {  
                        result=1;
                        System.out.println(result+"\n");
                        objectOutputStream.writeObject(result);
                        CustId = rs.getInt("CustId");
                        objectOutputStream.writeObject(CustId);
                    }  
                    else{
                        result=0;
                        System.out.println(result+"\n");
                        objectOutputStream.writeObject(result);
                    }
                    break;
                    
               case 2:
                    System.out.println("enterend in case 2");
                    Username = (String)objectInputStream.readObject();
                    Password = (String)objectInputStream.readObject();
                    Address = (String)objectInputStream.readObject();
                    PhoneNo = (String)objectInputStream.readObject();
                    Email = (String)objectInputStream.readObject();
                    DOB = (String)objectInputStream.readObject();
                    //query2=  "select * from register where Username=?";
                    ps = HandleClient.getConnection().prepareStatement(query);
                    ps.setString(1,Username);
                    rs = ps.executeQuery();
                    result = -1;
                    
                    if(rs.next())
                    {  
                        result=0;
                        System.out.println(result+"\n");
                        objectOutputStream.writeObject(result);
                    }  
                    
                    else{
                        ps = HandleClient.getConnection().prepareStatement(query);
                        ps.setString(1, Username);
                        ps.setString(2, Password);
                        ps.setString(3, Address);
                        ps.setString(4, PhoneNo);
                        ps.setString(5, Email);
                        ps.setString(6, DOB);
                        
                        if(ps.executeUpdate()>0)
                        {
                            result=1;
                            System.out.println(result+"new user added");
                        }
      
                        System.out.println(result+"\n");
                        objectOutputStream.writeObject(result);
                        
                    }
                    
                    break;
               case 3:
                    System.out.println("enterend in case 3");
                    CustId = (int)objectInputStream.readObject();
                    System.out.println(CustId); 
                    //query4="select * from cart where CustID=?";
                    ps = HandleClient.getConnection().prepareStatement(query);
                    ps.setInt(1,CustId);
                    rs = ps.executeQuery();
                    result = -1;
                    System.out.println("cge");
                    if(!rs.isBeforeFirst()&& rs.getRow() == 0)
                    {
                      result=0;
                      System.out.println(result+"\n");
                      objectOutputStream.writeObject(result);
                    }
                    while(rs.next())
                    {
                       if(result==-1)
                       {
                         result=1;
                         System.out.println(result+"\n");
                       }
                      //getting
                      localCustId=rs.getInt("CustId");
                      localQuantity=rs.getInt("Quantity");
                      localAmount=rs.getInt("Cost");
                      localSeller=rs.getString("Seller");
                      localProductName=rs.getString("ProdName");
                     
                     //sending
                      objectOutputStream.writeObject(result);
                      objectOutputStream.writeObject(localCustId);
                      objectOutputStream.writeObject(localQuantity);
                      objectOutputStream.writeObject(localAmount);
                      objectOutputStream.writeObject(localSeller);
                      objectOutputStream.writeObject(localProductName);
                      result=-1;
                       }
                    System.out.println("end");
                    objectOutputStream.writeObject(result);
                       break;
               case 4:
                    System.out.println("enterend in case 4");
                    CustId = (int)objectInputStream.readObject();
                    System.out.println(CustId);
                    
                   // query4="select * from sales where CustID=?";
                    ps = HandleClient.getConnection().prepareStatement(query);
                    ps.setInt(1,CustId);
                    rs = ps.executeQuery();
                    result = -1;
                    System.out.println("cge");
                    if(!rs.isBeforeFirst()&& rs.getRow() == 0)
                    {
                      result=0;
                      System.out.println(result+"\n");
                      objectOutputStream.writeObject(result);
                    }
                    while(rs.next())
                    {
                       if(result==-1)
                       {
                         result=1;
                         System.out.println(result+"\n");
                       }
                      //getting
                      localProductName=rs.getString("ProName");
                      localCustId=rs.getInt("CustId");
                      localAmount=rs.getInt("Amt");
                      localProductId=rs.getInt("BillId");
                      d=rs.getDate("Date");
                      localDate=d.toString();
                      localTime=rs.getString("Time");
                     //sending
                      objectOutputStream.writeObject(result);
                      objectOutputStream.writeObject(localCustId);
                      objectOutputStream.writeObject(localProductId);
                      objectOutputStream.writeObject(localAmount);
                      objectOutputStream.writeObject(localDate);
                      objectOutputStream.writeObject(localTime);
                      objectOutputStream.writeObject(localProductName);
                      result=-1;
                       }
                    System.out.println("end");
                    objectOutputStream.writeObject(result);
                       break;
               case 5:
                    System.out.println("enterend in case 5");
                    CustId = (int)objectInputStream.readObject();
                    System.out.println(CustId);
                    
                    //query4="select * from wish where CustID=?";
                    ps = HandleClient.getConnection().prepareStatement(query);
                    ps.setInt(1,CustId);
                    rs = ps.executeQuery();
                    result = -1;
                    System.out.println("cge");
                    if(!rs.isBeforeFirst()&& rs.getRow() == 0)
                    {
                      result=0;
                      System.out.println(result+"\n");
                      objectOutputStream.writeObject(result);
                    }
                    while(rs.next())
                    {
                       if(result==-1)
                       {
                         result=1;
                         System.out.println(result+"\n");
                       }
                      //getting
                      localProductName=rs.getString("ProdName");
                      localCustId=rs.getInt("CustId");
                      localAmount=rs.getInt("Cost");
                    
                     //sending
                      objectOutputStream.writeObject(result);
                      objectOutputStream.writeObject(localCustId);
                      objectOutputStream.writeObject(localAmount);
                      objectOutputStream.writeObject(localProductName);
                      result=-1;
                       }
                    System.out.println("end");
                    objectOutputStream.writeObject(result);
                       break;
               case 6:        
                    System.out.println("enterend in case 6");
                    CustId = (int)objectInputStream.readObject();
                    System.out.println(CustId);
                    
                    //query4="SELECT SUM(`Quantity`*`Cost`) AS sum FROM cart where `CustId`=?";
                    ps = HandleClient.getConnection().prepareStatement(query);
                    ps.setInt(1,CustId);
                    rs = ps.executeQuery();
                    result = 0;
                    System.out.println("cge");
                    if(rs.next())
                    {
                       
                      result=1;
                      System.out.println(result+"\n");
                  
                      //getting
                      localAmount=rs.getInt("sum");
                      System.out.println(localAmount);
                    
                     //sending
                      objectOutputStream.writeObject(result);
                      objectOutputStream.writeObject(localAmount);
                    }
                    System.out.println("end");
                    break;
               case 7:        
                    System.out.println("enterend in case 7");
                    CustId = (int)objectInputStream.readObject();
                    System.out.println(CustId);
                    ps = HandleClient.getConnection().prepareStatement(query);
                    ps.setInt(1,CustId);
                    int p=ps.executeUpdate();
                    result = 0;
                    System.out.println("cge");
                    if(p>0)
                    {
                       
                      result=1;
                      System.out.println(result+"\n");
                  
                    
                     //sending
                      objectOutputStream.writeObject(result);
                    }
                    System.out.println("end");
                    break;
                
               case 8:   
                    cartItem=new ArrayList<>();
                    System.out.println("enterend in case 8");
                    CustId = (int)objectInputStream.readObject();
                    System.out.println(CustId);
                    query2="select * from cart where CustID=?";
                    ps = HandleClient.getConnection().prepareStatement(query2);
                    ps.setInt(1,CustId);
                    rs = ps.executeQuery();
                    result = -1;
                    System.out.println("cge");
                    
                    if(!rs.isBeforeFirst()&& rs.getRow() == 0)
                    {
                      result=0;
                      System.out.println(result+"\n");
                      objectOutputStream.writeObject(result);
                      break;
                    }
                    //aquiring cart table
                    while(rs.next())
                    {
                       if(result==-1)
                       {
                         result=1;
                         System.out.println(result+"\n");
                       }
                      //getting
                      localCustId=rs.getInt("CustId");
                      localAmount=rs.getInt("Cost");
                      localProductName=rs.getString("ProdName");
                      System.out.println(localCustId);
                      System.out.println(localAmount);
                      System.out.println(localProductName);
                     //adding
                     cartItem.add(new addCart(localCustId,localAmount,java.time.LocalDate.now().toString(),java.time.LocalTime.now().toString(),localProductName)); 
                    }
                    System.out.println("ending the list initialisation");
                    //entering aquired value in sales table
                    System.out.println("enterend in case 8b");
                    for(int c=0;c<cartItem.size();c++)
                    {
                    System.out.println("ander");
                    addCart item=cartItem.get(c);
                    localCustId=item.getCustId();
                    localAmount = item.getAmount();
                    localProductName =item.getProductName();
                    localDate = item.getDate();
                    localTime = item.getTime();
                    System.out.println("ander2");
                    query="INSERT INTO `sales`(`CustId`,`Amt`, `ProName`, `Date`, `Time`) VALUES (?,?,?,?,?)";
                    ps = HandleClient.getConnection().prepareStatement(query);
                    ps.setInt(1,localCustId);
                    ps.setInt(2,localAmount);
                    ps.setString(3,localProductName);
                    ps.setString(4,localDate);
                    ps.setString(5,localTime);
                    
                    System.out.println("ander3");
                    if(ps.executeUpdate()>0)
                    {
                      result=1;
                      System.out.println(result+"\n");
                    }
                    
                    System.out.println("cge");
                    }
                    
                    objectOutputStream.writeObject(result);
                    System.out.println("end");
                    break;
               case 9:   
                    wishItem=new ArrayList<>();
                    System.out.println("enterend in case 9");
                    CustId = (int)objectInputStream.readObject();
                    System.out.println(CustId);
                    query4="select * from wish where CustID=?";
                    ps = HandleClient.getConnection().prepareStatement(query4);
                    ps.setInt(1,CustId);
                    rs = ps.executeQuery();
                    result = -1;
                    System.out.println("cge");
                    //aquiring from wish
                    if(!rs.isBeforeFirst()&& rs.getRow() == 0)
                    {
                      result=0;
                      System.out.println(result+"\n");
                      objectOutputStream.writeObject(result);
                      break;
                    }
                    while(rs.next())
                    {
                       if(result==-1)
                       {
                         result=1;
                         System.out.println(result+"\n");
                       }
                      //getting
                      localCustId=rs.getInt("CustId");
                      localQuantity=rs.getInt("Quantity");
                      localAmount=rs.getInt("Cost");
                      localSeller=rs.getString("Seller");
                      localProductName=rs.getString("ProdName");
                     //adding
                     wishItem.add(new addWish(localCustId,localAmount,localQuantity,localSeller,localProductName)); 
                    }
                    System.out.println("ending the list initialisation");
                    //adding aquired product to cart
                    System.out.println("enterend in case 8b");
                    System.out.println(wishItem.size());
                    for(int c=0;c<wishItem.size();c++)
                    {
                    System.out.println("ander");
                    addWish item=wishItem.get(c);
                    localCustId=item.getCustId();
                    localAmount = item.getAmount();
                    localProductName =item.getProductName();
                    localQuantity = item.getQuantity();
                    localSeller = item.getSeller();
                    System.out.println("ander2");
                    query="INSERT INTO `cart`(`CustId`, `ProdName`, `Quantity`, `Cost`, `Seller`) VALUES (?,?,?,?,?)";
                    ps = HandleClient.getConnection().prepareStatement(query);
                    ps.setInt(1,localCustId);
                    ps.setString(2,localProductName);
                    ps.setInt(3,localQuantity);
                    ps.setInt(4,localAmount);
                    ps.setString(5,localSeller);
                    
                    System.out.println("ander3");
                    if(ps.executeUpdate()>0)
                    {
                      result=1;
                      System.out.println(result+"\n");
                    }
                    
                    System.out.println("cge");
                    }
                    
                    objectOutputStream.writeObject(result);
                    System.out.println("end");
                    break;
               case 10:
                    System.out.println("enterend in case 10");
                    localDate = (String)objectInputStream.readObject();
                    CustId = (int)objectInputStream.readObject();
                    System.out.println(localDate);
                    System.out.println(CustId);
                    ps = HandleClient.getConnection().prepareStatement(query);
                    ps.setString(1,localDate);
                    ps.setString(2,localDate);
                    ps.setString(3,localDate);
                    System.out.println(ps);
                    rs = ps.executeQuery();
                    result = 0;
                    System.out.println("cge");
                    while(rs.next())
                    {
                      localCustId=rs.getInt("CustId");
                      System.out.println(localCustId);
                      //discount=rs.getInt("SpecialDiscount");
                      if(localCustId==CustId)
                      {
                          result=1;
                          System.out.println("ans: "+result);
                         // System.out.println(discount+"\n");
                         
                      }
                    }
                    objectOutputStream.writeObject(result);
                    //objectOutputStream.writeObject(discount);
                    System.out.println(result);
                    System.out.println("end");
                    break;
                
               case 14:
                    ps = HandleClient.getConnection().prepareStatement(query);
                    rs = ps.executeQuery();
                    result = -1;
                    
                    System.out.println("cge");
                    if(!rs.isBeforeFirst()&& rs.getRow() == 0)
                    {
                      result=0;
                      System.out.println(result+"\n");
                      objectOutputStream.writeObject(result);
                    }
                    
                    while(rs.next())
                    {
                       if(result==-1)
                       {
                         result=1;
                         System.out.println(result+"\n");
                       }
                     
                     //sending
                      objectOutputStream.writeObject(result);
                      objectOutputStream.writeObject(rs.getString("Name"));
                      objectOutputStream.writeObject(rs.getInt("SP"));
                      objectOutputStream.writeObject(rs.getInt("Disc"));
                      objectOutputStream.writeObject(rs.getString("Spec"));
                      objectOutputStream.writeObject(rs.getInt("ProId"));
                      
                      Blob b1,b2,b3,b4;
                      b1=rs.getBlob("Image1");
                      byte data1[] =null,data2[] =null,data3[] =null,data4[] =null;
                      data1=b1.getBytes(1,(int)b1.length());
                      objectOutputStream.writeObject(data1);
                      b2=rs.getBlob("Image2");
            
                      data2=b2.getBytes(1,(int)b2.length());
                      objectOutputStream.writeObject(data2);
                      b3=rs.getBlob("Image3");
           
                      data3=b3.getBytes(1,(int)b3.length());
                      objectOutputStream.writeObject(data3);
//                      b4=rs.getBlob("Image4");
//                      data4=b4.getBytes(1,(intb4.length());
//                      objectOutputStream.writeObject(data4);
                      result=-1;
                    }
                    objectOutputStream.writeObject(result);
                    System.out.println("End");
                   break;
               case 15:
                    String category=(String)objectInputStream.readObject();
                    ps = HandleClient.getConnection().prepareStatement(query);
                    System.out.println("ander");
                    ps.setString(1,category);
                    rs = ps.executeQuery();
                    System.out.println("ander1");
                    result = -1;
                    
                    System.out.println("cge");
                    if(!rs.isBeforeFirst()&& rs.getRow() == 0)
                    {
                      result=0;
                      System.out.println(result+"\n");
                      objectOutputStream.writeObject(result);
                    }
                    
                    while(rs.next())
                    {
                       if(result==-1)
                       {
                         result=1;
                         System.out.println(result+"\n");
                       }
                     
                     //sending
                      objectOutputStream.writeObject(result);
                      objectOutputStream.writeObject(rs.getString("Name"));
                      objectOutputStream.writeObject(rs.getInt("SP"));
                      objectOutputStream.writeObject(rs.getInt("Disc"));
                      objectOutputStream.writeObject(rs.getString("Spec"));
                      Blob b1,b2,b3,b4;
                      b1=rs.getBlob("Image1");
                      byte data1[] =null,data2[] =null,data3[] =null,data4[] =null;
                      data1=b1.getBytes(1,(int)b1.length());
                      objectOutputStream.writeObject(data1);
                      b2=rs.getBlob("Image2");
            
                      data2=b2.getBytes(1,(int)b2.length());
                      objectOutputStream.writeObject(data2);
                      b3=rs.getBlob("Image3");
           
                      data3=b3.getBytes(1,(int)b3.length());
                      objectOutputStream.writeObject(data3);
//                      b4=rs.getBlob("Image4");
//                      data4=b4.getBytes(1,(intb4.length());
//                      objectOutputStream.writeObject(data4);
                      result=-1;
                    }
                    objectOutputStream.writeObject(result);
                    System.out.println("End");
                   
                   break;
                   
               case 16:
                    
                    pId = (int)objectInputStream.readObject();
                    ps = HandleClient.getConnection().prepareStatement(query);
                    ps.setInt(1,pId);
                    rs = ps.executeQuery();
                    
                    System.out.println("cge");
                   
                    if(rs.next())
                    {
                     
                     //sending
                      
                      objectOutputStream.writeObject(rs.getString("Name"));
                      objectOutputStream.writeObject(rs.getString("ManfDate"));
                      objectOutputStream.writeObject(rs.getString("ExpDate"));
                      objectOutputStream.writeObject(rs.getString("Seller"));
                      objectOutputStream.writeObject(rs.getString("Category"));
                      objectOutputStream.writeObject(rs.getInt("SP"));
                      objectOutputStream.writeObject(rs.getInt("Disc"));
                      objectOutputStream.writeObject(rs.getString("Spec"));
                      objectOutputStream.writeObject(rs.getString("Desc"));
                      
                      Blob b1,b2,b3,b4;
                      byte data1[] =null,data2[] =null,data3[] =null,data4[] =null;
                      
                      b1=rs.getBlob("Image1");
                      data1=b1.getBytes(1,(int)b1.length());
                      objectOutputStream.writeObject(data1);
                      
                      b2=rs.getBlob("Image2");
                      data2=b2.getBytes(1,(int)b2.length());
                      objectOutputStream.writeObject(data2);
                      
                      b3=rs.getBlob("Image3");
                      data3=b3.getBytes(1,(int)b3.length());
                      objectOutputStream.writeObject(data3);
                      
                      b4=rs.getBlob("Image4");
                      data4=b4.getBytes(1,(int)b4.length());
                      objectOutputStream.writeObject(data4);
                    }
                    System.out.println("End");
                    
                   break;
               case 17:   
                    try
                    {
                    
                    result = -1;
                    System.out.println("enterend in case 17");
                    CustId = (int)objectInputStream.readObject();
                    pId=(int)objectInputStream.readObject();
                    localProductName = (String)objectInputStream.readObject();
                    localAmount = (int)objectInputStream.readObject();
                    localSeller = (String)objectInputStream.readObject();
                    System.out.println(CustId);
                    String temp_query="SELECT * FROM cart WHERE ProId=?";
                    
                    ps = HandleClient.getConnection().prepareStatement(temp_query);
                    ps.setInt(1, pId);
                    rs=ps.executeQuery();
                    if(rs.next())
                    {
                        String temp_query1="UPDATE cart SET Quantity = Quantity+1 WHERE ProId=?";

                        ps = HandleClient.getConnection().prepareStatement(temp_query1);
                        ps.setInt(1, pId);
                        if(ps.executeUpdate()>0)
                          {
                             result=1;
                             System.out.println(result+"\n");
                          }
                        
                        
                    }
                    else
                    {
                    ps = HandleClient.getConnection().prepareStatement(query);
                    ps.setInt(1,CustId);
                    ps.setInt(2, pId);
                    ps.setString(3,localProductName);
                    ps.setInt(4, 1);
                    ps.setInt(5,localAmount);
                    ps.setString(6,localSeller);
                    System.out.println(ps);
                
                    
                    if(ps.executeUpdate()>0)
                    {
                      result=1;
                      System.out.println(result+"\n");
                    }
                    }
                    
                    System.out.println("pge");
                    objectOutputStream.writeObject(result);
                    System.out.println("end");
                    
                    }
                    catch(Exception ex)
                    {
                        System.out.println("error in case 17 "+ ex);
                    }
                    break;
                    
               default:
                    System.out.println("default run \n");
            }
         
            objectInputStream.close();
            objectOutputStream.close();
            socket.close();
            
        }catch(Exception e)
        {
            JOptionPane.showMessageDialog(null, "couldn't proceed to answer query");
            JOptionPane.showMessageDialog(null,e);
        }
    }
   //class to add cart items to sales
   public  class addCart{
       int custId,amount;
       String date,time,productName;
       addCart(int custId,int amount,String date,String time,String productName)
       {
           this.custId=custId;
           this.amount=amount;
           this.date=date;
           this.time=time;
           this.productName=productName;
       }
       int getCustId()
       {
           return custId;
       }
       int getAmount()
       {
           return amount;
       }
       String getDate()
       {
           return date;
       }
       String getTime()
       {
           return time;
       }
       String getProductName()
       {
           return productName;
       }
   }
   //class to add wishlist item to cart
    public  class addWish{
       int custId,amount,quantity;
       String seller,productName;
       addWish(int custId,int amount,int quantity,String seller,String productName)
       {
           this.custId=custId;
           this.amount=amount;
           this.quantity=quantity;
           this.seller=seller;
           this.productName=productName;
       }
       int getCustId()
       {
           return custId;
       }
       int getAmount()
       {
           return amount;
       }
       int getQuantity()
       {
           return quantity;
       }
       String getSeller()
       {
           return seller;
       }
       String getProductName()
       {
           return productName;
       }
   }
    
}
