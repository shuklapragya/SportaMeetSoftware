package assignment;

//import java.io.*;
//import java.lang.Integer;
import java.util.ArrayList;
//import java.util.List;

class EventNode  {
  public int eventKey;//This denotes the eventKey of the entry stored at the node
  public String eventID;//This denotes the eventID of the entry stored at the node
  public String eventName;
  public String eventDescription;
  public EventNode leftChildEvent;//This is a reference to the left child in the heap
  public EventNode rightChildEvent;//This is a reference to the right child in the heap
  public EventNode parentEvent;//This is a reference to the parentEvent of the node in the heap
  public ParticipantHeap refToPHeap;
  
  public EventNode(){
      parentEvent = leftChildEvent = rightChildEvent = null;
      refToPHeap = new ParticipantHeap();
  }
}

public class EventHeap {
  public EventNode rootEvent;//This is the rootEvent node
  public int eventSize;//This is the number of entries in the heap
  public EventNode lastEventNode;//This indicates the node where the next insert will take place
  
  //This is the initialization method for Binary Tree. This reads the tree description
  //from a file named tree.txt and initializes the tree.
  public EventHeap(){
      eventSize = 0;
      rootEvent = lastEventNode = null;
  }
 
  public void swapValsOfEvent(EventNode p, EventNode q){
	  int temp1 = p.eventKey;
      String temp2 = p.eventID;
      String temp3 = p.eventName;
      String temp4 = p.eventDescription;
      ParticipantHeap tempRef = p.refToPHeap;
      
      p.eventKey = q.eventKey;
      p.eventID = q.eventID;
      p.eventName = q.eventName;
      p.eventDescription = q.eventDescription;
      p.refToPHeap = q.refToPHeap;
      
      q.eventKey = temp1;
      q.eventID = temp2;
      q.eventName = temp3;
      q.eventDescription = temp4;
      q.refToPHeap = tempRef;
  }
  
  public String maxEvent(){
      return(rootEvent.eventID);
  }
 
  public void incrementLastEvent(){
      if(lastEventNode == null){lastEventNode = rootEvent; return;}
      
      //If the right child of the last node is null, then there is nothing to be done.
      if(lastEventNode.rightChildEvent == null)return;
      
      EventNode p = lastEventNode;
      while(p.parentEvent!=null && p.parentEvent.rightChildEvent == p)p = p.parentEvent;
      if(p.parentEvent != null)p = p.parentEvent.rightChildEvent;
      while(p.leftChildEvent != null)p = p.leftChildEvent;
      lastEventNode = p;
  }
  
  public void decrementLastEvent(){
      
      if(lastEventNode.leftChildEvent != null || lastEventNode.rightChildEvent != null)return;
      
      EventNode p = lastEventNode;
      while(p.parentEvent!=null && p.parentEvent.leftChildEvent == p)p = p.parentEvent;
      if(p.parentEvent != null)p = p.parentEvent.leftChildEvent;
      while(p.rightChildEvent != null)p = p.rightChildEvent;
      lastEventNode = p.parentEvent;
  }
 
  public boolean isEmptyEvent(){
      if(eventSize == 0)return(true);
      else return(false);
  }
  
  public void upHeapBubbleEvent(EventNode v){
  	if(v==null){
  		System.out.println("Null Object Passed To UpHeap Bubble");
  		return;
  	}
  	if(v!= null && v.parentEvent!=null){
  		if(v.eventKey>v.parentEvent.eventKey){
  		swapValsOfEvent(v,v.parentEvent);
  		upHeapBubbleEvent(v.parentEvent);
  		}
  	}
  	return;
  } 
  
  public void downHeapBubbleEvent(EventNode v){
  	if(isEmptyEvent()) return;
  	
  	else if(v==null){
  		return;
  	}
  	else{
  		if(v.rightChildEvent!=null && v.leftChildEvent==null)
  		{
  		if(v.rightChildEvent.eventKey>v.eventKey){
  			swapValsOfEvent(v.rightChildEvent,v);
  			downHeapBubbleEvent(v.rightChildEvent);
  		}
  	} 
  		if(v.leftChildEvent!=null && v.rightChildEvent==null)
  		{
  		if(v.leftChildEvent.eventKey>v.eventKey){
  			swapValsOfEvent(v.leftChildEvent,v);
  			downHeapBubbleEvent(v.leftChildEvent);
  		}
  	} 
  		else if(v.rightChildEvent!=null && v.leftChildEvent!=null){
  			
  			if(v.leftChildEvent.eventKey>=v.rightChildEvent.eventKey){
  			if(v.leftChildEvent.eventKey>v.eventKey){
  				swapValsOfEvent(v.leftChildEvent,v);
  				downHeapBubbleEvent(v.leftChildEvent);
  			}
  		}
  		else if(v.rightChildEvent.eventKey>v.leftChildEvent.eventKey){
  			if(v.rightChildEvent.eventKey>v.eventKey){
  				swapValsOfEvent(v.rightChildEvent,v);
  				downHeapBubbleEvent(v.rightChildEvent);
  			}
  		}
  	}
  	return;
  	}
  }
   
  public void insertEvent(int key, String id, String name, String des){
      EventNode N = new EventNode();
      N.eventKey = key;
      N.eventID = id;
      N.eventName = name;
      N.eventDescription = des;
      
      if(eventSize == 0){
          rootEvent = N;
          lastEventNode = N;
          eventSize++;
          return;
      }
      if(lastEventNode.leftChildEvent == null)lastEventNode.leftChildEvent = N;
      else lastEventNode.rightChildEvent = N;
      N.parentEvent = lastEventNode;
      eventSize++;
      upHeapBubbleEvent(N);
      incrementLastEvent();
  }
 
  public String removeMaxEvent(){
      
      String result = rootEvent.eventKey +" "+ rootEvent.eventID + " " + rootEvent.eventName + " " + rootEvent.eventDescription;
      if(eventSize == 1){rootEvent = lastEventNode = null; eventSize--; return(result);}
      
      decrementLastEvent();
      EventNode N;
      if(lastEventNode.rightChildEvent != null){N = lastEventNode.rightChildEvent; lastEventNode.rightChildEvent = null;}
      else {N = lastEventNode.leftChildEvent; lastEventNode.leftChildEvent = null;}
      
      //Copy the entry in N to the rootEvent
      rootEvent.eventKey = N.eventKey; 
      rootEvent.eventID = N.eventID;
      rootEvent.eventName = N.eventName;
      rootEvent.eventDescription = N.eventDescription;
      eventSize--;
      downHeapBubbleEvent(rootEvent);
      return(result);
  }
 
  public void deletEventWithID(String e_id){
      EventNode dn = new EventNode();
      dn = getEvent(rootEvent,e_id,dn);
      if(dn == null){
    	  System.out.println("Exception");
    	  return;
      }
      else{
    	  deleteEvent(dn);
      }
  }
  public void deleteEvent(EventNode v){
  	if(v==null){
  		System.out.println("Null Object Passed To deleteNode");
  		return;
  	}
  	
      if(eventSize == 1){v = rootEvent = lastEventNode = null; eventSize--; return;}
      
      decrementLastEvent();
      EventNode N;
      if(lastEventNode.rightChildEvent != null){N = lastEventNode.rightChildEvent; lastEventNode.rightChildEvent = null;}
      else {N = lastEventNode.leftChildEvent; lastEventNode.leftChildEvent = null;}
      
      v.eventKey = N.eventKey; v.eventID = N.eventID;
      eventSize--;
      downHeapBubbleEvent(v);
      return;      
  } 
  
  public void modifyKeyOfEvent(EventNode eve, int k){
      if(eve.eventKey==k){return;}
      else{
      	int oldKey = eve.eventKey;
      	eve.eventKey=k;
      	if(k>oldKey){
      		upHeapBubbleEvent(eve);  
      	}if(k<oldKey){
      		downHeapBubbleEvent(eve);
      	}
      }
  }
  
  public void preOrderTraversalEvent(EventNode v){
      System.out.println("(" + v.eventKey + ", " + v.eventID + ")");
      if(v.leftChildEvent != null)preOrderTraversalEvent(v.leftChildEvent);
      if(v.rightChildEvent != null)preOrderTraversalEvent(v.rightChildEvent);
  }
  public void eventValue(String e_id){
	  EventNode v = new EventNode();
	  v = getEvent(rootEvent,e_id,v);
	  System.out.println(v.eventKey+" "+v.eventID+ " "+v.eventName+" "+v.eventDescription+" "+v.refToPHeap.pRoot.pID+" "+v.refToPHeap.pRoot.pKey);
  }
  
  public EventNode getEvent(EventNode revent,String val,EventNode n){
      if(revent==null){
          return null;
      }
      if(revent.eventID.equals(val)){
          n=revent;
          return n;
      }
          if(revent.leftChildEvent != null) n = getEvent(revent.leftChildEvent,val,n);
          if(n==revent.leftChildEvent) return n;
          if(revent.rightChildEvent != null) n = getEvent(revent.rightChildEvent,val,n);
          if(n==revent.rightChildEvent) return n;
          return n;
  }
  
  
   ArrayList<PNode> nodelist = new ArrayList<>();
   public void arrangeTopInEvent(String e_id){
  			EventNode en = new EventNode();
  			en = getEvent(rootEvent, e_id , en);
  			if(en.eventID == null){
  				return;
  			}
  			PNode pr = new PNode();
  			pr = en.refToPHeap.pRoot;
  			
  			if(pr ==null){

  				return;
  				
  			}
  			
  			if(en.refToPHeap.pSize == 1){
  				//System.out.println(pr.pID+", "+pr.pName+", "+pr.pUniversity+", "+pr.pKey);
  				nodelist.add(pr);
  			}
  			if(en.refToPHeap.pSize == 2){
  				//System.out.println(pr.pID+", "+pr.pName+", "+pr.pUniversity+", "+pr.pKey);
  				//System.out.println(pr.pLeftChild.pID+", "+pr.pLeftChild.pName+", "+pr.pLeftChild.pUniversity+", "+pr.pLeftChild.pKey);
  				nodelist.add(pr);
  				nodelist.add(pr.pLeftChild);
  				
  			}
  			if(en.refToPHeap.pSize == 3){
  					nodelist.add(pr);
//  				System.out.println(pr.pID+", "+pr.pName+", "+pr.pUniversity+", "+pr.pKey);
  				if(pr.pLeftChild.pKey >= pr.pRightChild.pKey){
//  					System.out.println(pr.pLeftChild.pID+", "+pr.pLeftChild.pName+", "+pr.pLeftChild.pUniversity+", "+pr.pLeftChild.pKey);
//  					System.out.println(pr.pRightChild.pID+", "+pr.pRightChild.pName+", "+pr.pRightChild.pUniversity+", "+pr.pRightChild.pKey);
  					nodelist.add(pr.pLeftChild);
  					nodelist.add(pr.pRightChild);
  					
  				}else{
//  					System.out.println(pr.pRightChild.pID+", "+pr.pRightChild.pName+", "+pr.pRightChild.pUniversity+", "+pr.pRightChild.pKey);
//  					System.out.println(pr.pLeftChild.pID+", "+pr.pLeftChild.pName+", "+pr.pLeftChild.pUniversity+", "+pr.pLeftChild.pKey);
  					nodelist.add(pr.pRightChild);
  					nodelist.add(pr.pLeftChild);
  				}
  			}
  			if(en.refToPHeap.pSize == 4){
  				//System.out.println(pr.pID+", "+pr.pName+", "+pr.pUniversity+", "+pr.pKey);
  				nodelist.add(pr);
  				PNode temp;
  				PNode tempSib;
  				if(pr.pLeftChild.pKey >= pr.pRightChild.pKey){
  					  temp = pr.pLeftChild;
  					  tempSib = pr.pRightChild; 
  				}else{
  					  temp = pr.pRightChild;
  					  tempSib = pr.pLeftChild;
  			}
  				nodelist.add(temp);
  				//System.out.println(temp.pID+", "+temp.pName+", "+temp.pUniversity+", "+temp.pKey);
  				
  				if(tempSib.pKey>= temp.pLeftChild.pKey){
  					nodelist.add(tempSib);
  					//System.out.println(tempSib.pID+", "+tempSib.pName+", "+tempSib.pUniversity+", "+tempSib.pKey);
  				}else{
  					nodelist.add(temp.pLeftChild);
  					//System.out.println(temp.pLeftChild.pID+", "+temp.pLeftChild.pName+", "+temp.pLeftChild.pUniversity+", "+temp.pLeftChild.pKey);
  				}
  			}
  			if(en.refToPHeap.pSize == 5){
  				nodelist.add(pr);
  				//System.out.println(pr.pID+", "+pr.pName+", "+pr.pUniversity+", "+pr.pKey);
  				PNode temp;
  				PNode tempSib;
  				PNode tempMax;
  				if(pr.pLeftChild.pKey >= pr.pRightChild.pKey){
  					  temp = pr.pLeftChild;
  					  tempSib = pr.pRightChild; 
  				}else{
  					  temp = pr.pRightChild;
  					  tempSib = pr.pLeftChild;
  			}
  				nodelist.add(temp);
  				//System.out.println(temp.pID+", "+temp.pName+", "+temp.pUniversity+", "+temp.pKey);
  				if(temp.equals(pr.pLeftChild)){
  				if(temp.pLeftChild.pKey >= temp.pRightChild.pKey){
  					tempMax = temp.pLeftChild;
  				}else{
  					tempMax = temp.pRightChild;
  				}
  				
  				if(tempSib.pKey>= tempMax.pKey){
  					nodelist.add(tempSib);
  					//System.out.println(tempSib.pID+", "+tempSib.pName+", "+tempSib.pUniversity+", "+tempSib.pKey);
  				}else{
  					nodelist.add(tempMax);
  					//System.out.println(tempMax.pID+", "+tempMax.pName+", "+tempMax.pUniversity+", "+tempMax.pKey);
  				}
  				}
  				else{
  						nodelist.add(tempSib);
  				}
  			}
  			if(en.refToPHeap.pSize == 6){
  				nodelist.add(pr);
  				//System.out.println(pr.pID+", "+pr.pName+", "+pr.pUniversity+", "+pr.pKey);
  				PNode temp;
  				PNode tempSib;
  				PNode tempMax;
  				if(pr.pLeftChild.pKey >= pr.pRightChild.pKey){
  					  temp = pr.pLeftChild;
  					  tempSib = pr.pRightChild; 
  				}else{
  					  temp = pr.pRightChild;
  					  tempSib = pr.pLeftChild;
  			}
  				nodelist.add(temp);
  				//System.out.println(temp.pID+", "+temp.pName+", "+temp.pUniversity+", "+temp.pKey);
  				if(temp.equals(pr.pLeftChild)){
  				if(temp.pLeftChild.pKey >= temp.pRightChild.pKey){
  					tempMax = temp.pLeftChild;
  				}else{
  					tempMax = temp.pRightChild;
  				}
  				
  				if(tempSib.pKey>= tempMax.pKey){
  					nodelist.add(tempSib);
  					//System.out.println(tempSib.pID+", "+tempSib.pName+", "+tempSib.pUniversity+", "+tempSib.pKey);
  				}else{
  					nodelist.add(tempMax);
  					//System.out.println(tempMax.pID+", "+tempMax.pName+", "+tempMax.pUniversity+", "+tempMax.pKey);
  				}
  				}
  				else{
  					if(temp.pLeftChild.pKey >= tempSib.pKey){
  						nodelist.add(temp.pLeftChild);
  					}else{
  						nodelist.add(tempSib);
  					}  					
  				}
  				
  			}
  			if(en.refToPHeap.pSize >=7){
  				nodelist.add(pr);
  				//System.out.println(pr.pID+", "+pr.pName+", "+pr.pUniversity+", "+pr.pKey);
  				PNode temp;
  				PNode tempSib;
  				PNode tempMax;
  				if(pr.pLeftChild.pKey >= pr.pRightChild.pKey){
  					  temp = pr.pLeftChild;
  					  tempSib = pr.pRightChild; 
  				}else{
  					  temp = pr.pRightChild;
  					  tempSib = pr.pLeftChild;
  			}
  				nodelist.add(temp);
  				 
  				if(temp.pLeftChild.pKey >= temp.pRightChild.pKey){
  					tempMax = temp.pLeftChild;
  				}else{
  					tempMax = temp.pRightChild;
  				}
  				
  				if(tempSib.pKey >= tempMax.pKey){
  					nodelist.add(tempSib);
  				}else{
  					nodelist.add(tempMax);
  				}
  				
  			}
  		}
   
   public void printTopInEvent(String e_id){
	   nodelist.clear();
	   arrangeTopInEvent(e_id);
	   for(int i =0;i<3;i++){
		   System.out.println(nodelist.get(i).pID+", "+nodelist.get(i).pName+", "+nodelist.get(i).pUniversity+", "+nodelist.get(i).pKey);
	   }
	   
   }
//   public void printTopInEvent(){
//	   for(int i =0;i<3;i++){
//		   System.out.println(nodelist.get(i).pID+", "+nodelist.get(i).pName+", "+nodelist.get(i).pUniversity+", "+nodelist.get(i).pKey);
//	   }
//   }
   
   public void TopInAll (){
	   nodelist.clear();
	   if(isEmptyEvent()){
		   System.out.println("Exception");
	   }
	   if(eventSize == 1){
		   printTopInEvent(rootEvent.eventID);
	   }
	   if(eventSize == 2){
		   arrangeTopInEvent(rootEvent.eventID);
		   arrangeTopInEvent(rootEvent.leftChildEvent.eventID);
	   }
	   if(eventSize == 3){
		   arrangeTopInEvent(rootEvent.eventID);
		   arrangeTopInEvent(rootEvent.leftChildEvent.eventID);
		   arrangeTopInEvent(rootEvent.rightChildEvent.eventID);
	   }
	   if(eventSize == 4){
		   arrangeTopInEvent(rootEvent.eventID);
		   EventNode max2;
		   EventNode max2Sib;
		   EventNode max3;
		   if(rootEvent.leftChildEvent.eventKey >= rootEvent.rightChildEvent.eventKey){
			   max2 = rootEvent.leftChildEvent;
			   max2Sib = rootEvent.rightChildEvent;
		   }else{
			   max2 = rootEvent.rightChildEvent;
			   max2Sib = rootEvent.leftChildEvent;
		   }
		   arrangeTopInEvent(max2.eventID);
		   if(max2.leftChildEvent.eventKey >= max2Sib.eventKey){
			   max3 = max2.leftChildEvent;
		   }else{
			   max3 = max2Sib;
		   }
		   arrangeTopInEvent(max3.eventID);
	   }
	   if(eventSize ==5){
		   arrangeTopInEvent(rootEvent.eventID);
		   EventNode max2;
		   EventNode max2Sib;
		   EventNode max3;
		   EventNode max2MaxChild;
		   if(rootEvent.leftChildEvent.eventKey >= rootEvent.rightChildEvent.eventKey){
			   max2 = rootEvent.leftChildEvent;
			   max2Sib = rootEvent.rightChildEvent;
		   }else{
			   max2 = rootEvent.rightChildEvent;
			   max2Sib = rootEvent.leftChildEvent;
		   }
		   arrangeTopInEvent(max2.eventID);
		   if(max2.equals(rootEvent.leftChildEvent)){
		   if(max2.leftChildEvent.eventKey>max2.rightChildEvent.eventKey){
			   max2MaxChild = max2.leftChildEvent;
		   }else{
			   max2MaxChild = max2.rightChildEvent;
		   }
		   if(max2MaxChild.eventKey >= max2Sib.eventKey){
			   max3 = max2MaxChild;
		   }else{
			   max3 = max2Sib;
		   }
		   }
		   else{
			   max3 = max2Sib;
		   }
		   arrangeTopInEvent(max3.eventID);
		   
	   }
	   if(eventSize == 6){
		   arrangeTopInEvent(rootEvent.eventID);
		   EventNode max2;
		   EventNode max2Sib;
		   EventNode max3;
		   EventNode max2MaxChild;
		   if(rootEvent.leftChildEvent.eventKey >= rootEvent.rightChildEvent.eventKey){
			   max2 = rootEvent.leftChildEvent;
			   max2Sib = rootEvent.rightChildEvent;
		   }else{
			   max2 = rootEvent.rightChildEvent;
			   max2Sib = rootEvent.leftChildEvent;
		   }
		   arrangeTopInEvent(max2.eventID);
		   
		   if(max2.equals(rootEvent.leftChildEvent))
		   {
		   if(max2.leftChildEvent.eventKey >= max2.rightChildEvent.eventKey){
			   max2MaxChild = max2.leftChildEvent;
		   }else{
			   max2MaxChild = max2.rightChildEvent;
		   }
		   if(max2MaxChild.eventKey >= max2Sib.eventKey){
			   max3 = max2MaxChild;
		   }else{
			   max3 = max2Sib;
		   }
		   }
		   else{
			   if(max2Sib.eventKey>max2.leftChildEvent.eventKey){
				   max3 = max2Sib;
			   }else{
				   max3 = max2.leftChildEvent;
			   }
			 
		   }
		   arrangeTopInEvent(max3.eventID); 
	   }
	   if(eventSize >=7){
		   arrangeTopInEvent(rootEvent.eventID);
		   EventNode max2;
		   EventNode max2Sib;
		   EventNode max3;
		   EventNode max2MaxChild;
		   if(rootEvent.leftChildEvent.eventKey >= rootEvent.rightChildEvent.eventKey){
			   max2 = rootEvent.leftChildEvent;
			   max2Sib = rootEvent.rightChildEvent;
		   }else{
			   max2 = rootEvent.rightChildEvent;
			   max2Sib = rootEvent.leftChildEvent;
		   }
		   arrangeTopInEvent(max2.eventID);
		    
		   if(max2.leftChildEvent.eventKey>max2.rightChildEvent.eventKey){
			   max2MaxChild = max2.leftChildEvent;
		   }else{
			   max2MaxChild = max2.rightChildEvent;
		   }
		   if(max2MaxChild.eventKey >= max2Sib.eventKey){
			   max3 = max2MaxChild;
		   }else{
			   max3 = max2Sib;
		   }	 
		   arrangeTopInEvent(max3.eventID);
	   }
//	   for(int i =0;i<nodelist.size();i++){
//	   System.out.println(nodelist.get(i).pID+", "+nodelist.get(i).pName+", "+nodelist.get(i).pUniversity+", "+nodelist.get(i).eventInfo+", "+nodelist.get(i).pKey);
//	   }
	   //System.out.println(nodelist.size());
	   PNode max1Node = nodelist.get(0) ;
	   int maxKey = nodelist.get(0).pKey;
	   int count = 0;
	   for(int i =0;i<nodelist.size();i++){
		   if(nodelist.get(i).pKey > maxKey){
			   maxKey = nodelist.get(i).pKey;
			   max1Node = nodelist.get(i);
			   count = i;
		   }
	   }
	   System.out.println(max1Node.pID+", "+max1Node.pName+", "+max1Node.pUniversity+", "+max1Node.eventInfo+", "+max1Node.pKey);
	   nodelist.remove(count);
	   
	   PNode max2Node = nodelist.get(0) ;
	   maxKey = nodelist.get(0).pKey;
	   count = 0;
	   for(int i =0;i<nodelist.size();i++){
		   if(nodelist.get(i).pKey > maxKey){
			   maxKey = nodelist.get(i).pKey;
			   max2Node = nodelist.get(i);
			   count = i;
		   }
	   }
	   System.out.println(max2Node.pID+", "+max2Node.pName+", "+max2Node.pUniversity+", "+max2Node.eventInfo+", "+max2Node.pKey);
	   nodelist.remove(count);
	   
	   PNode max3Node = nodelist.get(0) ;
	   maxKey = nodelist.get(0).pKey;
	   count = 0;
	   for(int i =0;i<nodelist.size();i++){
		   if(nodelist.get(i).pKey > maxKey){
			   maxKey = nodelist.get(i).pKey;
			   max3Node = nodelist.get(i);
			   count = i;
		   }
	   }
	   System.out.println(max3Node.pID+", "+max3Node.pName+", "+max3Node.pUniversity+", "+max3Node.eventInfo+", "+max3Node.pKey);
	   nodelist.remove(count);
	   
   }

  
  ParticipantHeap defaultHeap = new ParticipantHeap();
  
	public void addDefaultParticipant(int particiant_key, String partcipant_id, String participant_name, String participant_university){
		String event_info = null;
  	defaultHeap.insert( particiant_key, partcipant_id, participant_name, participant_university,event_info);
	}
	public void addPToEvent (String p_id , String e_id){
		PNode pn = new PNode();
		pn = defaultHeap.getNode(defaultHeap.pRoot, p_id, pn);
		if(pn == null){
			System.out.println("Exception");
			return;
		}
		if(pn.pID == null){
			System.out.println("Exception");
			return;
		}
		EventNode en = new EventNode();
		en = getEvent(rootEvent, e_id ,en);
		if(en.eventID == null){
			System.out.println("Exception : Event ID Doesnt Exist");
			return;
		}
		pn.eventInfo = en.eventID+", "+en.eventName;
		en.refToPHeap.insert(pn.pKey, pn.pID, pn.pName, pn.pUniversity, pn.eventInfo);
		modifyKeyOfEvent(en,en.refToPHeap.pRoot.pKey);
	}
	
	public void updateScoreOfParticipant (String p_id , String e_id ,int key){
		
		EventNode ende = new EventNode();
		ende = getEvent(rootEvent, e_id ,ende);
		if(ende.eventID == null){
			System.out.println("Exception");
			return;
		}
		PNode pnde = new PNode();
		pnde = ende.refToPHeap.getNode(ende.refToPHeap.pRoot, p_id, pnde);
		if(pnde.pID == null){
			System.out.println("Exception");
			return;
		}
		ende.refToPHeap.modifyKeyOfNode(pnde, key);
		//int heapkey = ende.refToPHeap.pRoot.pKey;
		ende = getEvent(rootEvent, e_id ,ende);
		modifyKeyOfEvent(ende,ende.refToPHeap.pRoot.pKey);
	}
	public void removeMaxParticipant(String e_id){
	
		EventNode evn = new EventNode();
		evn = getEvent(rootEvent,e_id,evn);
		if(evn.eventID ==null){
			System.out.println("Exception");
			return;
		}	
		while(!evn.refToPHeap.isEmpty()){
			System.out.println(evn.refToPHeap.removeMax());		
		}
	}
	
	public void deleteParticipantInEvent(String p_id , String e_id){
		EventNode en = new EventNode();
		en = getEvent(rootEvent,e_id,en);
		if(en.eventID == null){
			System.out.println("Exception");
			return;
		}
		PNode pn = new PNode();
		
		pn = en.refToPHeap.getNode(en.refToPHeap.pRoot, p_id , pn);
		if(pn.pID == null){
			System.out.println("Exception");
			return;
		}
        en.refToPHeap.deleteNode(pn);
        
        int k = en.refToPHeap.pRoot.pKey;
        modifyKeyOfEvent(en,k);	
	}
	public void deleteParticipantFromAllEvent(String p_id){
		PNode pn = new PNode();
		checkWithTraversal(rootEvent, p_id ,pn);
	}
	 
	public void checkWithTraversal(EventNode v,String p_id ,PNode pn){
	      if(v.refToPHeap.getNode(v.refToPHeap.pRoot,p_id,pn)!=null){
	    	  deleteParticipantInEvent(p_id ,v.eventID);  
	      }
	      if(v.leftChildEvent != null)checkWithTraversal(v.leftChildEvent,p_id,pn);
	      if(v.rightChildEvent != null)checkWithTraversal(v.rightChildEvent,p_id,pn);
	  }
//  public void TopInEvent (int k){
//  ArrayList<String> list = new ArrayList<String>();
//   
//  for(int i=0;i<k;i++){    
//  String temp = removeMaxEvent();
//  System.out.println(temp);
//  list.add(temp);
//  
//}
//  for(int i=0;i<k;i++){
//  String[] word=list.get(i).split("\\s");
//  insertEvent(Integer.parseInt(word[0]),word[1],word[2],word[3]);
//  }
//}

}

