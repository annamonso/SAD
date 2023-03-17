
import java.io.*;


/*
		ESC parser:
		RIGHT:	ESC [ C
		LEFT:	ESC [ D
		HOME:	ESC [ H
		END:	ESC [ F
		INS:	ESC [ 2 ~
		DEL:	ESC [ 3 ~
	*/

class EditableBufferedReader extends BufferedReader {
    private int [] pos_cursor= new int[2];
    public EditableBufferedReader(Reader in) {
        super(in);
        this.pos_cursor[0]=0;
        this.pos_cursor[1]=0;
    }

    public void setRaw(){
        String[] cmd = {"/bin/sh", "-c", "stty raw -echo </dev/tty"};
        try {
			Runtime.getRuntime().exec(cmd).waitFor();
		} catch (InterruptedException e) {

			e.printStackTrace();
		} catch (IOException e) {
	
			e.printStackTrace();
		}
        
    }

    public void unsetRaw() {
        String[] cmd = {"/bin/sh", "-c", "stty cooked echo </dev/tty"};
        try {
			Runtime.getRuntime().exec(cmd).waitFor();
		} catch (InterruptedException e) {

			e.printStackTrace();
		} catch (IOException e) {
	
			e.printStackTrace();
		}

    }


    private String readAll() throws IOException{
        StringBuffer str = new StringBuffer();
        int charac,i=0;
        int[] mouse=new int[4];
        
        do{
            charac = super.read();
            if(charac == Key.DEL){
                return "DEL";
            }
            
            str.append((char) charac);
           
        }while(super.ready());
        return str.toString();
    }

    public void get_position(String str){
        char[] car = str.toCharArray();
        char [] pos = new char[2];
        pos[0] = car[4];
        pos[1]= car[5];
        pos_cursor[0] = ((int) pos[0])-33;
        pos_cursor[1]= (int) pos[1];
    }
    
    public int read() throws IOException {
        String str = readAll();
        if(str.contains("\033[M ")){
            get_position(str);
            str = "Mouse";
        }
        int character;
        switch(str){
            case "\033[C":
                return Key.RIGHT_ARROW;
            case "\033[D":
                return Key.LEFT_ARROW;
            case "\033[H":
                return Key.INI_BUTTON;
            case "\033[F":
                return Key.FIN_BUTTON;
            case "\033[2~":
                return Key.INS_BUTTON;
            case "\033[3~":
                return Key.SUPR_BUTTON;
            case "Mouse":
                return Key.CLICK_DRET;
            case "DEL":
                return Key.DEL_BUTTON;
        }
        character = str.toCharArray()[0];
        return character;

    }

    private void activate_mouse(){
        System.out.print("\033[?9h");
    }
    private void desactivate_mouse(){
        System.out.print("\033[?9l");
    } 

    /* (non-Javadoc)
     * @see java.io.BufferedReader#readLine()
     */
    @Override
    public String readLine() throws IOException{
        Line ret = new Line();
        Console console = new Console(ret);
        this.activate_mouse();
        ret.addObserver(console);
        int ch;
        try{       
            setRaw();
            while((ch = read()) != Key.EXIT){    
                switch(ch){
                    case Key.RIGHT_ARROW:
                        ret.moveR();  
                        break;
                    case Key.LEFT_ARROW:                      
                        ret.moveL();
                          break;
                    case Key.INI_BUTTON:
                        ret.gotoINI();                 
                         break;
                    case Key.FIN_BUTTON:
                        ret.gotoFIN();
                         break;
                    case Key.INS_BUTTON:
                        ret.modeINS();
                         break;
                    case Key.SUPR_BUTTON:  
                        ret.supr();      
                        break;
                    case Key.DEL_BUTTON:
                        ret.dele();
                        break;
                    case Key.CLICK_DRET:
                        ret.move_Cursor(pos_cursor);
                        break;
                    default:
                        char c = (char) ch; 
                        ret.insert(c);
                        System.out.print(c);
                }
               
        }
        unsetRaw();
        desactivate_mouse();
        return ret.toString();
        
        }finally{
            unsetRaw();
        }


    }

}