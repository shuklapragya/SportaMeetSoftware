package assignment;

//import java.io.*;
//import java.lang.Integer;
import java.util.ArrayList;
//import java.util.List;

class PNode  {
  public int pKey;//This denotes the pKey of the entry stored at the node
  public String pID;//This denotes the pID of the entry stored at the node
  public String pName;
  public String pUniversity;
  public PNode pLeftChild;//This is a reference to the left child in the heap
  public PNode pRightChild;//This is a reference to the right child in the heap
  public PNode pParent;//This is a reference to the pParent of the node in the heap
  public String eventInfo;
  public PNode(){
      pParent = pLeftChild = pRightChild = null;
     
  }
}

public class ParticipantHeap {
  public PNode pRoot;//This is the pRoot node
  public int pSize;//This is the number of entries in the heap
  public PNode pLastNode;//This indicates the node where the next insert will take place
  
  //This is the initialization method for Binary Tree. This reads the tree description
  //from a file named tree.txt and initializes the tree.
  public ParticipantHeap(){
      pSize = 0;
      pRoot = pLastNode = null;
  }
 
  public void swapVals(PNode p, PNode q){
	  int temp1 = p.pKey;
	  String temp2 = p.pID;
      String temp3 = p.pName;
      String temp4 = p.pUniversity;
      
      p.pKey = q.pKey;
      p.pID = q.pID;
      p.pName = q.pName;
      p.pUniversity = q.pUniversity;
      
      q.pKey = temp1;
      q.pID = temp2;
      q.pName = temp3;
      q.pUniversity = temp4;
  }
  
  public String min(){
      return(pRoot.pID);
  }
 
  public void incrementLastNode(){
      if(pLastNode == null){pLastNode = pRoot; return;}
      
      //If the right child of the last node is null, then there is nothing to be done.
      if(pLastNode.pRightChild == null)return;
      
      PNode p = pLastNode;
      while(p.pParent!=null && p.pParent.pRightChild == p)p = p.pParent;
      if(p.pParent != null)p = p.pParent.pRightChild;
      while(p.pLeftChild != null)p = p.pLeftChild;
      pLastNode = p;
  }
  
  public void decrementLastNode(){
      //If there is a left child or a right child of the current node, then there is
      //nothing to be done.
      if(pLastNode.pLeftChild != null || pLastNode.pRightChild != null)return;
      
      PNode p = pLastNode;
      while(p.pParent!=null && p.pParent.pLeftChild == p)p = p.pParent;
      if(p.pParent != null)p = p.pParent.pLeftChild;
      while(p.pRightChild != null)p = p.pRightChild;
      pLastNode = p.pParent;
  }
 
  public boolean isEmpty(){
      if(pSize == 0)return(true);
      else return(false);
  }
  
  public void upHeapBubble(PNode v){
  	if(v==null){
  		System.out.println("Null Object Passed To UpHeap Bubble");
  		return;
  	}
  	if(v!= null && v.pParent!=null){
  		if(v.pKey>v.pParent.pKey){
  		swapVals(v,v.pParent);
  		upHeapBubble(v.pParent);
  		}
  	}
  	return;
  } 
  
  public void downHeapBubble(PNode v){
  	if(isEmpty()) return;
  	
  	else if(v==null){
  		System.out.println("Null Object Passed To DownHeap Bubble");
  		return;
  	}
  	else{
  		if(v.pRightChild!=null && v.pLeftChild==null)
  		{
  		if(v.pRightChild.pKey>v.pKey){
  			swapVals(v.pRightChild,v);
  			downHeapBubble(v.pRightChild);
  		}
  	} 
  		if(v.pLeftChild!=null && v.pRightChild==null)
  		{
  		if(v.pLeftChild.pKey>v.pKey){
  			swapVals(v.pLeftChild,v);
  			downHeapBubble(v.pLeftChild);
  		}
  	} 
  		else if(v.pRightChild!=null && v.pLeftChild!=null){
  			
  			if(v.pLeftChild.pKey>v.pRightChild.pKey){
  			if(v.pLeftChild.pKey>v.pKey){
  				swapVals(v.pLeftChild,v);
  				downHeapBubble(v.pLeftChild);
  			}
  		}
  		else if(v.pRightChild.pKey>v.pLeftChild.pKey){
  			if(v.pRightChild.pKey>v.pKey){
  				swapVals(v.pRightChild,v);
  				downHeapBubble(v.pRightChild);
  			}
  		}
  	}
  	return;
  	}
  }
   
  public void insert(int key, String id, String name, String university, String event_info){
      PNode N = new PNode();
      N.pKey = key;
      N.pID = id;
      N.pName = name;
      N.pUniversity = university;
      N.eventInfo = event_info;
      if(pSize == 0){
          pRoot = N;
          pLastNode = N;
          pSize++;
          return;
      }
      if(pLastNode.pLeftChild == null)pLastNode.pLeftChild = N;
      else pLastNode.pRightChild = N;
      N.pParent = pLastNode;
      pSize++;
      upHeapBubble(N);
      incrementLastNode();
  }
 
  public String removeMax(){
      
      String result = pRoot.pKey +" "+ pRoot.pID +" " + pRoot.pName + " " + pRoot.pUniversity;
      if(pSize == 1){pRoot = pLastNode = null; pSize--; return(result);}
      
      decrementLastNode();
      PNode N;
      if(pLastNode.pRightChild != null){N = pLastNode.pRightChild; pLastNode.pRightChild = null;}
      else {N = pLastNode.pLeftChild; pLastNode.pLeftChild = null;}
      
       
      pRoot.pKey = N.pKey;
      pRoot.pID = N.pID;
      pRoot.pName = N.pName;
      pRoot.pUniversity = N.pUniversity;
      pSize--;
      downHeapBubble(pRoot);
      return(result);
  }
   
  public void deleteNode(PNode v){

  	  if(pSize == 1){v = pRoot = pLastNode = null; pSize--; return;}
      
      decrementLastNode();
      PNode N;
      if(pLastNode.pRightChild != null){N = pLastNode.pRightChild; pLastNode.pRightChild = null;}
      else {N = pLastNode.pLeftChild; pLastNode.pLeftChild = null;}
      
      v.pKey = N.pKey;
      v.pID = N.pID;
      v.pName = N.pName;
      v.pUniversity = N.pUniversity;
      pSize--;
      downHeapBubble(v);
      return;      
  } 
  
  public void searchForModify(String val,int k){
      PNode sn = new PNode();
      sn=getNode(pRoot,val,sn);
      if(sn==null){
          System.out.println("Exception : Participant Not Found ");
          return;
      }else{
          modifyKeyOfNode(sn,k);  
      }
  }
  
  public void modifyKeyOfNode(PNode N, int k){
      if(N.pKey==k){return;}
      else{
      	int oldKey = N.pKey;
      	N.pKey=k;
      	if(k>oldKey){
      		upHeapBubble(N);  
      	}else{
      		downHeapBubble(N);
      	}
      }
  }
  
  public void preOrderTraversal(PNode v){
      System.out.println("(" + v.pKey + ", " + v.pID + ")");
      if(v.pLeftChild != null)preOrderTraversal(v.pLeftChild);
      if(v.pRightChild != null)preOrderTraversal(v.pRightChild);
  }
  
  public PNode getNode(PNode rNode,String val,PNode n){
      if(isEmpty()){
          //System.out.println("Empty Participant Heap");
          return null;
      }
      if(rNode.pID.equals(val)){
          n=rNode;
          return n;
      }
          if(rNode.pLeftChild != null) n = getNode(rNode.pLeftChild,val,n);
          if(n == rNode.pLeftChild) return n;
          if(rNode.pRightChild != null) n = getNode(rNode.pRightChild,val,n);
          if(n == rNode.pRightChild) return n;
          return n;
  }
}
