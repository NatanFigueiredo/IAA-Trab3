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

//Classe da Fila de Prioridade em Heap com um array de Elementos, a quantidade maxima e a atual
class FilaPrioridade { 

  private Elemento[] A;
  private int maxElementos;
  private int m;  

  //Construtor setando os valores iniciais
  FilaPrioridade()
  {
    this.A = new Elemento[4001];
    this.maxElementos = 4001;
    this.m = 1;
  }

  //Metodo para retornar a maior prioridade
  Elemento HeapMax()
  {
    //Elemento dummy de retorno
    Elemento vazio = new Elemento(-1,-1.0);

    //Caso não haja elementos na lista, retorna vazio
    //Senão, retorna o primeiro elemento
    if (this.m <= 1) return vazio;
    else if (this.m > 0) return (A[1]); 
    else return vazio;
    
  }

  //Metodo para extrair a maior prioridade
  Elemento HeapExtractMax()
  {
    //Caso a lista tenha elementos, extrai e retorna o primeiro, reordenando o heap em função dos restantes
    if(this.m > 0)
    {
      Elemento maxm = A[1];
      this.m--;
      A[1] = A[this.m];
      maxHeapify(A,1);
      return maxm;
    }
    //Caso contrario, retorna o elemento dummy vazio
    else 
    {
      Elemento vazio = new Elemento(-1,-1.0);
      return vazio;
    }
  }

  //Meotodo do maxHeapify
  void maxHeapify(Elemento A[], int index) {

    //Posições do filho direito e esquerdo do index
    int filhoEsquerdo = getFilhoEsquerdo(A, index);
    int filhoDireito = getFilhoDireito(A, index);
    //Setando o index como possivel maior prioridade
    int maiorPrioridade = index;

    //Caso a prioridade de um dos filhos seja maior que a do pai, esse filho será setado em maiorPrioridade
    if ((filhoEsquerdo <= this.m) && (filhoEsquerdo>0)) {
      if (A[filhoEsquerdo].getPrior() > A[maiorPrioridade].getPrior()) {
        maiorPrioridade = filhoEsquerdo;
      }
    }
    if ((filhoDireito <= this.m) && (filhoDireito>0)) {
      if (A[filhoDireito].getPrior() > A[maiorPrioridade].getPrior()) {
        maiorPrioridade = filhoDireito;
      }
    }

    //Ordenando o elemento de maior prioridade para a posição correta no heap
    if (maiorPrioridade != index) {
      Elemento temp;
      temp = A[maiorPrioridade];
      A[maiorPrioridade] = A[index];
      A[index] = temp;

      //Ordenando o subheap restante
      maxHeapify(A, maiorPrioridade);
    }
  }

  //Inserção de um elemento com prioridade no heap
  boolean HeapInsert(Elemento el)
  {
    boolean ok = false;

    //Caso o heap ainda possua espaço para um novo elemento, este será incluindo
    if (this.m < maxElementos)
    {
      //Inclusão do elemento e incrementação da quantidade atual
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

  //Funções para obter os filhos de um elemento no heap, retornando -1 caso não seja encontrado
  public int getFilhoDireito(Elemento A[], int index) {
    if((((2*index)+1) < A.length && (index >= 1)))
      return (2*index)+1;
    return -1;
  }
  public int getFilhoEsquerdo(Elemento A[], int index) {
      if(((2*index) < A.length && (index >= 1))){       
        return 2*index;
      }
      return -1;
  }

  //Função para retornar o pai de um elemento, retornando -1 caso seja a raiz
  public int getParent(Elemento A[], int index) {
    if ((index > 1) && (index < A.length)) {
      return index/2;
    }
    return -1;
  }

  //Metodo de incremento do heap com novos elementos
  public void increaseKey(Elemento A[], int index, Elemento el) {
    //Alocando o novo elemento na primeira posição livre disponivel
    A[index] = el;
    
    //Trocando e reordenando o heap em função do novo elemento inserido para aloca-lo na posição de prioridade correta
    while((index>1) && (A[getParent(A, index)].getPrior() < A[index].getPrior())) {
      Elemento temp;
      temp = A[getParent(A, index)];
      A[getParent(A, index)] = A[index];  
      A[index] = temp;

      //Obtendo o index do pai da posição onde o elemento foi realocado
      index = getParent(A, index);
    }
  }
}

// Classe Elemento onde cada objeto vai armazenar o id e a prioridade
class Elemento {
  private int id;
  private double prior;

  //Construtor setando os valores inicais
  public Elemento(int id, double prior){
    setId(id);
    setPrior(prior);
  }

  //Getters e Setters
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