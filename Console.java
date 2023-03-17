import java.util.Observer;
import java.util.Observable;


public class Console implements Observer {
	private Line line;

	public Console(Line line){
		this.line = line;
	}
        @Override
	public void update(Observable o, Object arg){
		int seq = ((Integer)arg).intValue();
		switch (seq){
			case Key.SUPR_BUTTON:
				System.out.print("\033[P"); 
				break;
			case Key.LEFT_ARROW:
				//System.out.print("Esquerra");
				System.out.print("\033[1D");
				break;
			case Key.RIGHT_ARROW:
				//System.out.print("Dreta");
				System.out.print("\033[1C"); 
				break;
			case Key.INI_BUTTON:
				System.out.print("\033[G"); 
				break;
			case Key.CLICK_DRET:
				int pos[] = line.get_Cursor();
				System.out.print("\033["+pos[1]+";"+(pos[0]+1)+"H");
				break;
			case Key.FIN_BUTTON:
				System.out.print("\033["+(this.line.buff_length()+1)+"G");
				break;
			case Key.DEL_BUTTON:
				System.out.print("\033[1D");
				System.out.print("\033[P");  
				break;
			case Key.INS_BUTTON:
				System.out.print("\033[@");
				break;
		}
	}
}