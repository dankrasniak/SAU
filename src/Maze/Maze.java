package Maze;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;
import javax.swing.Timer;




public class Maze extends JFrame  implements ActionListener {

	private static final long serialVersionUID = 1L;
	private final double scale=100;
	private MazeModel mazeModel;
	private MazeKeyboardController mazeKeyboardController;
	private MazeView mazeView;
    private Timer timer;

    public Maze() {

        initUI();
    }

    private void initUI() {
    	
    	mazeModel=new MazeModel();
    	mazeKeyboardController=new MazeKeyboardController(mazeModel);
    	addKeyListener(mazeKeyboardController);
        mazeView = new MazeView(mazeModel);
        add(mazeView);
        
        initTimer();

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                timer.stop();
            }
        });

        setTitle("Maze");
        setSize((int)(mazeView.getcSectors()*mazeModel.getSegmentSizeX()*this.scale), (int)(mazeModel.getSegmentSizeY()*this.scale));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {

        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {

                Maze ex = new Maze();
                ex.setVisible(true);
            }
        });
    }
    
    public void actionPerformed(ActionEvent e) {
    	this.mazeModel.update((double)(MazeModel.getTimerdelay())/(double)(1000));
        repaint();
    }
    
    private void initTimer() {

        timer = new Timer(MazeModel.getTimerdelay(), this);
        timer.start();
    }
}