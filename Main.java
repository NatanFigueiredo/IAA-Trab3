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

  private Elemento[] A;
  private int maxElementos;
  private int m;  

  FilaPrioridade()
  {
    this.A = new Elemento[4001];
    this.maxElementos = 4001;
    this.m = 1;
  }

  Elemento HeapMax()
  {
    Elemento vazio = new Elemento(-1,-1.0);

    if (this.m <= 1) return vazio;

    else if (this.m > 0) return (A[1]); 

    else return vazio;
    
  }

  Elemento HeapExtractMax()
  {
    if(this.m > 0)
    {
      Elemento maxm = A[1];
      this.m--;
      A[1] = A[this.m];
      maxHeapify(A,1);
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

    if ((rightChildIndex <= this.m) && (rightChildIndex>0)) {
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
    for(int i = 1; i < m ; i++)
    {
      System.out.print(A[i].getId()+" "+A[i].getPrior()+" ");
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
    if ((index > 1) && (index < A.length)) {
      return index/2;
    }
    //else if ((index > 0) )
    return -1;
  }

  public void increaseKey(Elemento A[], int index, Elemento el) {

    A[index] = el;
    while((index>1) && (A[getParent(A, index)].getPrior() < A[index].getPrior())) {
      Elemento temp;
      temp = A[getParent(A, index)];
      A[getParent(A, index)] = A[index];
      A[index] = temp;
      index = getParent(A, index);
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