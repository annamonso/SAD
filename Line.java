import java.util.Observable;

public class Line extends Observable {

    public static final int BUFFER_CAPACITY = 16;
    private StringBuffer buff;
    private int cursor;
    private boolean modo_ins;
    private int pos_cursor_VERTICAL;

    public Line(){
        this.buff= new StringBuffer(BUFFER_CAPACITY);
        this.cursor=0;
        this.modo_ins = false;
        this.pos_cursor_VERTICAL=0;
    }

    public int buff_length(){
        return buff.length();
    }

   
    public void moveR(){    
        setChanged();
        int posicio=this.cursor+1;
         if(posicio<=this.buff.length()){
            
            notifyObservers(Key.RIGHT_ARROW);
            this.cursor+=1;
         }
    }
     
    public void moveL(){
        setChanged();
        if(this.cursor!=0){
            //System.out.print("Esquerra");
            notifyObservers(Key.LEFT_ARROW);
            this.cursor-=1;
        }

    }
    
    public void gotoINI(){
        setChanged();
        this.cursor=0;
        notifyObservers(Key.INI_BUTTON);
    }

    public void gotoFIN(){
        setChanged();
        this.cursor = this.buff.length();
        notifyObservers(Key.FIN_BUTTON);
    }

    public int get_lenght(){
        return this.get_lenght();
    }

    public void dele(){
        setChanged();
        if(this.cursor!=0){
            buff.deleteCharAt(this.cursor-1);
            this.cursor-=1;
            notifyObservers(Key.DEL_BUTTON);
        }

    }

    public void supr(){
        setChanged();
        if(this.cursor<this.buff.length()){
            buff.deleteCharAt(this.cursor); 
            notifyObservers(Key.SUPR_BUTTON);  
        }
    }

    public void modeINS(){
        modo_ins = !modo_ins; 
        
    }

    public void insert(char c){
        setChanged();
        if(this.cursor==this.buff.capacity()){
            StringBuffer buff2 = new StringBuffer(BUFFER_CAPACITY);
           buff.append(buff2);
           
       }
        if(modo_ins && this.cursor < this.buff_length()){  
            buff.setCharAt(this.cursor, c);
            this.cursor+=1;
            
            
        }else{
          
            if(this.cursor  < this.buff_length()){
                notifyObservers(Key.INS_BUTTON);
            }
            buff.insert(this.cursor, c);
            this.cursor+=1;
        }

        
    }

    public String toString(){
        return this.buff.toString();
    }

    public void move_Cursor(int pos[]){
        setChanged();
        this.cursor=pos[0];
        if(pos[0]> this.buff_length()){
            this.cursor=this.buff_length();
        }
        this.pos_cursor_VERTICAL= pos[1];
        notifyObservers(Key.CLICK_DRET);
    }
       
    public int[] get_Cursor(){
         int[] pos = new int[2];
         pos[0]= this.cursor;
         pos[1]= this.pos_cursor_VERTICAL;
         return pos;
    }
}
