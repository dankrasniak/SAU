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
        final int INPUT_SIZE = 5;
        final int OUTPUT_SIZE = 1;
        final double BetaV = 0.01;
        final int HORIZON_LENGTH = 10;
        final int TIMES_TO_REWRITE_HISTORY = 40;
        final int TIMES_TO_PREPARE_BETTER_SOLUTION = 40;
        final double GAMMA = 0.98;
        TeachingPolicy teachingPolicy = new ClassicalMomentumTP(BetaV);

        // Build the neural network
        int[] sizes = new int[] {INPUT_SIZE, 20, 20, OUTPUT_SIZE};
        CellType[] cellTypes = new CellType[] {ARCTANGENT, ARCTANGENT, ARCTANGENT};
        qLearning = new QLearning(
                sizes,
                cellTypes,
                teachingPolicy,
                HORIZON_LENGTH,
                TIMES_TO_REWRITE_HISTORY,
                TIMES_TO_PREPARE_BETTER_SOLUTION,
                GAMMA,
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
        mazeModel.setAAngleMultiplier45(qLearning.AdviseAction(mazeModel.getCurrentState()));

    	this.mazeModel.update((double)(MazeModel.getTimerdelay())/(double)(1000));

        qLearning.ThisHappened(mazeModel.getCurrentState());

        repaint();
    }
    
    private void initTimer() {
        timer = new Timer(MazeModel.getTimerdelay(), this);
        timer.start();
    }
}