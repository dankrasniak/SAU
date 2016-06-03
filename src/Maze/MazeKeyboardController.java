package Maze;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class MazeKeyboardController implements KeyListener {

	MazeModel mazeModel;
	
	public MazeKeyboardController(MazeModel mazeModel) {
		super();
		this.mazeModel = mazeModel;
	}

	@Override
	public void keyPressed(KeyEvent e) {

		if(e.getKeyCode()==KeyEvent.VK_UP){
			mazeModel.setAAngleMultiplier45(mazeModel.getAngleMultiplier()+1);;
		}
		else
		if(e.getKeyCode()==KeyEvent.VK_DOWN){
			mazeModel.setAAngleMultiplier45(mazeModel.getAngleMultiplier()-1);;
		}

	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

}
