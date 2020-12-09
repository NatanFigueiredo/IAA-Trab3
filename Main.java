import java.util.Scanner;

public class Main{

  public static void main(String[] args) {

    FilaPrioridade fp = new FilaPrioridade();
    Scanner sc = new Scanner(System.in);
    int operations = sc.nextInt();
    for(int i=0; i<=operations; i++)
    { 
      String op = sc.nextLine();
      String comp[] = op.split(" ");
      
      if(comp.length == 1)
      {
        switch (comp[0]) {
          case "1":
            (fp.HeapMax()).printa();
            break;
            
          case "2":
            (fp.HeapExtractMax()).printa();
            break; 
            
          case "4":
          fp.HeapPrint();
            break;
        
          default:
            break;
        }
      }
      else if(comp[0].equals("3"))
      {
          int id = Integer.parseInt(comp[1]);
          double prior = Double.parseDouble(comp[2]);
          Elemento novo = new Elemento(id, prior);
          if(fp.HeapInsert(novo))
            System.out.println("T");
          else 
             System.out.println("F");
      }
    }
  }
}

class FilaPrioridade { 

  /*
    TO DO

    Heap-Max -> Done
    HeapExtractMax -> Done
    maxHeapify -> Done
    HeapInsert -> Done
    HeapPrint -> Done

   */

  private Elemento[] A;
  private int maxElementos;
  private int m;  

  FilaPrioridade()
  {
    this.A = new Elemento[4000];
    this.maxElementos = 4000;
    this.m = 0;
  }

  Elemento HeapMax()
  {
    if(this.m > 0) return (A[0]); 

    Elemento vazio = new Elemento(-1,-1.0);
    return vazio;
    
  }

  Elemento HeapExtractMax()
  {
    if(this.m > 0)
    {
      Elemento maxm = A[0];
      this.m--;
      A[0] = A[this.m];
      maxHeapify(A,0);
      return maxm;
    }
    else 
    {
      Elemento vazio = new Elemento(-1,-1.0);
      return vazio;
    }
  }

  void maxHeapify(Elemento A[], int index) {
    int leftChildIndex = getLeftChild(A, index);
    int rightChildIndex = getRightChild(A, index);

    // finding largest among index, left child and right child
    int largest = index;

    if ((leftChildIndex <= this.m) && (leftChildIndex>0)) {
      if (A[leftChildIndex].getPrior() > A[largest].getPrior()) {
        largest = leftChildIndex;
      }
    }

    if ((rightChildIndex <= this.m && (rightChildIndex>0))) {
      if (A[rightChildIndex].getPrior() > A[largest].getPrior()) {
        largest = rightChildIndex;
      }
    }

    // largest is not the node, node is not a heap
    if (largest != index) {
      Elemento temp;
      //swapping
      temp = A[largest];
      A[largest] = A[index];
      A[index] = temp;
      maxHeapify(A, largest);
    }
  }

  boolean HeapInsert(Elemento el)
  {
    boolean ok = false;
    if (this.m < maxElementos)
    {
      increaseKey(this.A, this.m, el);
      this.m++;
      ok = true;     
    }
    return ok;
  }

  void HeapPrint()
  {
    for(int i = 0; i < m ; i++)
    {
      System.out.print(A[i].getId()+" "+A[i].getPrior()+" posicao("+ i + ") ");
    }
    System.out.println();
  }

  /*METODOS AUXILIARES*/

  public int getRightChild(Elemento A[], int index) {
    if((((2*index)+1) < A.length && (index >= 1)))
      return (2*index)+1;
    return -1;
  }

  //function to get left child of a node of a tree
  public int getLeftChild(Elemento A[], int index) {
      if(((2*index) < A.length && (index >= 1))){       
        return 2*index;
      }
      return -1;
  }

  //function to get the parent of a node of a tree
  public int getParent(Elemento A[], int index) {
    if ((index > 0) && (index < A.length)) {
      return index/2;
    }
    return -1;
  }

  public void increaseKey(Elemento A[], int index, Elemento el) {

    A[index] = el;
    siftUp(index);
    /*while((index>0) && (A[getParent(A, index)].getPrior() < A[index].getPrior())) {
      Elemento temp;
      temp = A[getParent(A, index)];
      A[getParent(A, index)] = A[index];
      A[index] = temp;
      index = getParent(A, index);
    }
    maxHeapify(A, index);*/
  }

  private void siftUp(int nodeIndex) {

    int parentIndex;
    Elemento tmp;

    if (nodeIndex != 0) {

          parentIndex = getParent(this.A, nodeIndex);

          if (this.A[parentIndex].getPrior() < this.A[nodeIndex].getPrior()) {

                tmp = this.A[parentIndex];

                this.A[parentIndex] = this.A[nodeIndex];

                this.A[nodeIndex] = tmp;

                siftUp(parentIndex);

          }

    }

  }
}

class Elemento {
  private int id;
  private double prior;

  public Elemento(int id, double prior){
    setId(id);
    setPrior(prior);
  }

  public void setId (int id){
    this.id = id;
  }

  public int getId(){
    return this.id;
  }

  public void setPrior(double prior){
    if(prior >= 0 && prior <= 900000){
      this.prior = prior;
    }
    else if(prior == -1.0){
      this.prior = prior;
    }
  }

  public double getPrior(){
    return this.prior;
  }

  public void printa() {
    System.out.println(Integer.toString(this.id)+" "+Double.toString(this.prior));
  }

}

/*class MaximumPriorityQueue {
  public static int heapSize = 0;
  public static int treeArraySize = 20;
  public static int INF = 100000;

  //function to get right child of a node of a tree
  public static int getRightChild(Elemento A[], int index) {
    if((((2*index)+1) < A.length && (index >= 1)))
      return (2*index)+1;
    return -1;
  }

  //function to get left child of a node of a tree
  public static int getLeftChild(Elemento A[], int index) {
      if(((2*index) < A.length && (index >= 1))){       
        return 2*index;
      }
      return -1;
  }

  //function to get the parent of a node of a tree
  public static int getParent(Elemento A[], int index) {
    if ((index > 1) && (index < A.length)) {
      return index/2;
    }
    return -1;
  }

  public static void maxHeapify(Elemento A[], int index) {
    int leftChildIndex = getLeftChild(A, index);
    int rightChildIndex = getRightChild(A, index);

    // finding largest among index, left child and right child
    int largest = index;

    if ((leftChildIndex <= heapSize) && (leftChildIndex>0)) {
      if (A[leftChildIndex].getPrior() > A[largest].getPrior()) {
        largest = leftChildIndex;
      }
    }

    if ((rightChildIndex <= heapSize && (rightChildIndex>0))) {
      if (A[rightChildIndex].getPrior() > A[largest].getPrior()) {
        largest = rightChildIndex;
      }
    }

    // largest is not the node, node is not a heap
    if (largest != index) {
      Elemento temp;
      //swapping
      temp = A[largest];
      A[largest] = A[index];
      A[index] = temp;
      maxHeapify(A, largest);
    }
  }

  public static void buildMaxHeap(Elemento A[]) {
    for(int i=heapSize/2; i>=1; i--) {
      maxHeapify(A, i);
    }
  }

  public static Elemento maximum(Elemento A[]) {
    return A[0];
  }

  public static Elemento extractMax(Elemento A[]) {
    Elemento maxm = A[0];
    A[1] = A[heapSize];
    heapSize--;
    maxHeapify(A, 1);
    return maxm;
  }

  public static void increaseKey(Elemento A[], int index, Elemento el) {
    A[index] = el;
    while((index>0) && (A[getParent(A, index)].getPrior() < A[index].getPrior())) {
      Elemento temp;
      temp = A[getParent(A, index)];
      A[getParent(A, index)] = A[index];
      A[index] = temp;
      index = getParent(A, index);
    }
  }

  public static void decreaseKey(Elemento A[], int index, int key) {
    A[index].setId(key);
    maxHeapify(A, index);
  }

  /*public static void insert(Elemento A[], int key) {
    heapSize++;
    A[heapSize] = -1*INF;
    increaseKey(A, heapSize, key);
  }

  public static void printHeap(int A[]) {
    for(int i=1; i<=heapSize; i++) {
      System.out.println(A[i]);
    }
    System.out.println("");
  }

  
    
    
    
    
    /*
    
    int A[] = new int[treeArraySize];
    buildMaxHeap(A);

    insert(A, 20);
    insert(A, 15);
    insert(A, 8);
    insert(A, 10);
    insert(A, 5);
    insert(A, 7);
    insert(A, 6);
    insert(A, 2);
    insert(A, 9);
    insert(A, 1);

    printHeap(A);

    increaseKey(A, 5, 22);
    printHeap(A);

    decreaseKey(A, 1, 13);
    printHeap(A);

    System.out.println(maximum(A));
    System.out.println("");
    System.out.println(extractMax(A));
    System.out.println("");

    printHeap(A);

    System.out.println(extractMax(A));
    System.out.println(extractMax(A));
    System.out.println(extractMax(A));
    System.out.println(extractMax(A));
    System.out.println(extractMax(A));
    System.out.println(extractMax(A));
    System.out.println(extractMax(A));
    System.out.println(extractMax(A));
    System.out.println(extractMax(A));
    */



