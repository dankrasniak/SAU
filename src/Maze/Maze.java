package Maze;

import MLPerceptron.CellType;
import MLPerceptron.TeachingPolicies.ClassicalMomentumTP;
import MLPerceptron.TeachingPolicies.TeachingPolicy;
import QLearinging.QLearning;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;
import javax.swing.Timer;

import static MLPerceptron.CellType.ARCTANGENT;
import static MLPerceptron.CellType.LINEAR;


public class Maze extends JFrame  implements ActionListener {

	private static final long serialVersionUID = 1L;
	private final double scale=100;
	private MazeModel mazeModel;
	private MazeKeyboardController mazeKeyboardController;
	private MazeView mazeView;
    private Timer timer;
    private QLearning qLearning;

    public Maze() {

        initUI();
    }

    private void initUI() {

        mazeModel=new MazeModel();

        // QLearning

        // Prepare parameters
        final int INPUT_SIZE = 6;
        final int OUTPUT_SIZE = 1;
        final double BetaV = -0.0001;
        final int TIMES_TO_REWRITE_HISTORY = 30;
        final double GAMMA = 0.98;
        final int MEMORY_LIMIT = 100;
        TeachingPolicy teachingPolicy = new ClassicalMomentumTP(BetaV);

        // Build the neural network
        int[] sizes = new int[] {INPUT_SIZE, 60, 60, OUTPUT_SIZE};
        CellType[] cellTypes = new CellType[] {ARCTANGENT, ARCTANGENT, LINEAR};
        qLearning = new QLearning(
                sizes,
                cellTypes,
                teachingPolicy,
                TIMES_TO_REWRITE_HISTORY,
                GAMMA,
                MEMORY_LIMIT,
                this.mazeModel);

        // -- QLearning
    	

//    	mazeKeyboardController=new MazeKeyboardController(mazeModel);
//    	addKeyListener(mazeKeyboardController);
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
        // Prepare and apply decisions
        mazeModel.setAAngleMultiplier45(qLearning.AdviseAction(mazeModel.getCurrentState()));
        double reward = mazeModel.getReward(mazeModel.getCurrentState());

    	this.mazeModel.update((double)(MazeModel.getTimerdelay())/(double)(1000));

        qLearning.ThisHappened(mazeModel.getCurrentState(), reward);

        repaint();
    }
    
    private void initTimer() {
        timer = new Timer(MazeModel.getTimerdelay(), this);
        timer.start();
    }
}