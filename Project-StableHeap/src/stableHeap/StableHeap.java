package stableHeap;

import java.util.LinkedList;

class MinHeap<Type extends Comparable<? super Type>> implements Comparable<MinHeap<Type>>{
	private Type data;
	private long time;
	private static long insertTime;
	public MinHeap(Type data) {
		this.data=data;
		this.time=insertTime;
		insertTime++;
	}
	public Type getData() {
		return data;
	}
	public void setData(Type data) {
		this.data = data;
	}
	public long getTime() {
		return time;
	}
	public void setTime(int time) {
		this.time = time;
	}
	@Override
	public int compareTo(MinHeap<Type> o) {
		if(this.time < o.time)
			return -1;
		else if(this.time == o.time)
			return 0;
		else
			return 1;
	}
	@Override
	public String toString() {
		
		return data.toString();
	}
	
}
public class StableHeap<Type extends Comparable<? super Type>> implements Heap<Type>{
	/*private LinkedList<Type> list;
	private int size;*/
	private Object[] heapArray;
	private int size;
	private static final int DEFAULT_CAPACITY = 17;
	
	public StableHeap() {
		/*list=new LinkedList<>();
		size=0;*/
		heapArray=new Object[DEFAULT_CAPACITY];
		size=0;
	}
	private void expandCapacity() {
		Object[] newArray = new Object[heapArray.length*2];
		for(int i = 1; i <= size; i++) { 
			newArray[i] = heapArray[i];
		}
		heapArray = newArray;
	}
	@Override
	public void insert(Type data) {
		MinHeap heap=new MinHeap(data);
		if (size == heapArray.length-1)
			expandCapacity();
		int hole = ++size;
		heapArray[0] = heap;
		while(data.compareTo((Type)((MinHeap)heapArray[hole/2]).getData()) < 0){
			heapArray[hole] = heapArray[hole/2];
			hole = hole/2;
		}
		heapArray[hole] = heap;
	}
	@Override
	public Type findMin() {
		return (Type) ((MinHeap)heapArray[1]).getData();
	}
	@Override
	public Type deleteMin() {
		if(isEmpty())
			return null;
		Type returnValue= (Type) ((MinHeap)heapArray[1]).getData();
		heapArray[1] = heapArray[size]; //last item becomes the first item 
		size--; 
		percolateDown(1); 
		return returnValue;
	}
	private void percolateDown(int hole) {
		MinHeap heap =((MinHeap) heapArray[hole]);

		int child;
		while(hole*2 <= size) {
			child = hole*2;
			//if left and right child has same priority, check which is has smaller insert time.
			if(child != size&&((Type) ((MinHeap)heapArray[child]).getData()).compareTo((Type)( ((MinHeap) heapArray[child+1]).getData())) == 0) {
				if(((MinHeap) heapArray[child+1]).compareTo((MinHeap)heapArray[child])<0){
					child++;
				}
			}
			//is there any right child which is smaller than left child
			else if(child !=size&&((Type) ((MinHeap)heapArray[child+1]).getData()).compareTo((Type)( ((MinHeap) heapArray[child]).getData()))<0)
				child++;
			if(((Type) ((MinHeap)heapArray[child]).getData()).compareTo((Type) heap.getData())==0) {
				//if insert time of child is smaller than item which is percolating down
				if( ((MinHeap)heapArray[child]).compareTo(heap)<0)
						heapArray[hole]=heapArray[child];
			}
			else if(((Type) ((MinHeap)heapArray[child]).getData()).compareTo((Type) heap.getData())<0)
					heapArray[hole]=heapArray[child];
			else 
				break;
			hole = child;
		}
		if( heap.compareTo(((MinHeap)heapArray[hole]))<0 && ((Type) ((MinHeap)heapArray[hole]).getData()).compareTo((Type) heap.getData())==0) 
			heapArray[hole/2] = heap;
		else
			heapArray[hole] = heap;
	}
	@Override
	public boolean isEmpty() {
		return size==0;
	}
	@Override
	public void makeEmpty() {
		heapArray=new Object[DEFAULT_CAPACITY];
		size=0;
	}
}
