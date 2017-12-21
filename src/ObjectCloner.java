import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;


public class ObjectCloner
{  
   private ObjectCloner(){}
   // returns a deep copy of an object
   static public Object deepCopy(Object oldObj) throws Exception
   {
      ObjectOutputStream oostream = null;
      ObjectInputStream oistream = null;
      try
      {
         ByteArrayOutputStream bos = new ByteArrayOutputStream();
         oostream = new ObjectOutputStream(bos);
         oostream.writeObject(oldObj); 
         oostream.flush(); 
         ByteArrayInputStream bin = new ByteArrayInputStream(bos.toByteArray());
         oistream = new ObjectInputStream(bin);
         return oistream.readObject();
      }
      catch(Exception e)
      {
         System.out.println("Exception in ObjectCloner = " + e);
         throw(e);
      }
      finally
      {
    	  oostream.close();
         oistream.close();
      }
   }
   
}
