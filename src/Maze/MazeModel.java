package Maze;

import MLPerceptron.Utils.Vector;
import QLearinging.Model;
import java.util.LinkedList;


class MazeModel implements Model {
	
	private final static double segmentSizeX=2;
	private final static double segmentSizeY=3;
	private final static double segmentOpeningSize=1;
	
	private final static double segmentBorderWidth=0.025;
	private final static double ballRadius=0.05;
	
	private final static int angleBase=45;
	private final static int timerDelay = 100;

	
	public double getReward(final Vector state){
        double penalty = 0.0;
        if (state.Get(4) == 1)
            penalty = .0;

//        double reward = Math.signum(state.Get(2)) * Math.min(Math.abs(state.Get(2)), 0.35) - penalty;
//        double reward = state.Get(2)/4 - penalty;
		double reward = (2 / (Math.exp(-5 * state.Get(2)) + 1) ) - 1 - penalty;
        return reward;
	}

	public Vector[] stateFunction(final Vector state, final int[] actions){
		MazeModel testModel=new MazeModel(state);
		Vector [] nextStates=new Vector[actions.length];
		
		for(int i=0;i<actions.length;i++){
			testModel.setAAngleMultiplier45(actions[i]);
			testModel.update((double)(timerDelay)/(double)(1000));
			nextStates[i]=testModel.getCurrentState();
		}
		
		
		return nextStates;
	}

	

	private double segmentType=1;
	private double x=0.3;
	private double y=0.3;
	private double vx=0.0;
	private double vy=0.0;
	
	private int angleMultiplier=1;
	private double ax=0.70710678;
	private double ay=0.70710678;
	
	
	private boolean collisionDetectedX=false;
	private boolean collisionDetectedY=false;
	private int collisionCount=0;
	
	private double timeInSegment=0;
	private LinkedList<Double> timeInSegment_list=new LinkedList<Double>();
	private static int timeInSegment_list_lenght=10;
	
	
	
	
	
	public MazeModel() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	private MazeModel(Vector state) {
		super();
		this.x=state.Get(0);
		this.y=state.Get(1);
		this.vx=state.Get(2);
		this.vy=state.Get(3);
		
	}
	
	public Vector getCurrentState(){
		return new Vector(new double[]{this.x,this.y,this.vx,this.vy,(collisionDetectedX||collisionDetectedY)?1:0});
	}
	
	
	
	
	
	public double getTimeInSegment() {
		return timeInSegment;
	}
	public double getLastTimeInSegment() {
		return timeInSegment_list.size()>0?timeInSegment_list.peekLast():-1;
	}
	public double getAverageTimeInSegment() {
		double _sum=0;
		for(double _i:timeInSegment_list){
			_sum+=_i;
		}
		return timeInSegment_list.size()>0?_sum/timeInSegment_list.size():-1;
	}
	
	public boolean isCollisionDetectedX() {
		return collisionDetectedX;
	}
	public boolean isCollisionDetectedY() {
		return collisionDetectedY;
	}
	public int getCollisionCount() {
		return collisionCount;
	}
	public double getAX() {
		return ax;
	}

	public double getAY() {
		return ay;
	}
	
	
	
	public void setAAngleMultiplier45(int multiplier){
		
		//angleMultiplier=multiplier modulo 8
		this.angleMultiplier=multiplier < 0 ? 8 + multiplier : multiplier % 8;
		
		//angle=multiplier*angleBase
		//ax=cos(angle)
		//ay=sin(angle)
		
		
		switch (this.angleMultiplier) {
        case 0:  ax=1;ay=0;
                 break;
        case 1:  ax=0.70710678;ay=0.70710678;
                 break;
        case 2:  ax=0;ay=1;
                 break;
        case 3:  ax=-0.70710678;ay=0.70710678;
                 break;
        case 4:  ax=-1;ay=0;
                 break;
        case 5:  ax=-0.70710678;ay=-0.70710678;
                 break;
        case 6:  ax=0;ay=-1;
                 break;
        case 7:  ax=0.70710678;ay=-0.70710678;
                 break;
        default: ax=0;ay=0;
                 break;
    }
		
		
	}
	
	
	public int getAngleMultiplier() {
		return angleMultiplier;
	}
	public int getAngle() {
		return angleMultiplier*angleBase;
	}
	public double getVX() {
		return vx;
	}
	public double getVY() {
		return vy;
	}

	public double getSegmentSizeX() {
		return segmentSizeX;
	}
	public double getSegmentSizeY() {
		return segmentSizeY;
	}
	public double getSegmentOpeningSize() {
		return segmentOpeningSize;
	}
	public double getSegmentType() {
		return segmentType;
	}
	public double getX() {
		return x;
	}
	public double getY() {
		return y;
	}
	
	public double getSegmentBorderWidth() {
		return segmentBorderWidth;
	}
	
	public double getBallRadius() {
		return ballRadius;
	}
	
	void update(double seconds){
		seconds=seconds>0?seconds:0;
		
		timeInSegment=timeInSegment+seconds;
		
		
		x=x+seconds*vx+seconds*seconds*ax/2;
		y=y+seconds*vy+seconds*seconds*ay/2;
		
		vx=vx+seconds*ax;
		vy=vy+seconds*ay;
		
		
		//collision
		collisionDetectedX=false;
		if(
			(x<(segmentBorderWidth+ballRadius))  && (y>(segmentOpeningSize-ballRadius))
			){
			collisionDetectedX=true;
			collisionCount++;
			
			x=(segmentBorderWidth+ballRadius);
			vx=0;
//            vy=0;//TODO
		}
		else 
		if(
			((x>(segmentSizeX-(segmentBorderWidth+ballRadius)))&&(y<(segmentSizeY-(segmentOpeningSize-ballRadius))))
			){
			collisionDetectedX=true;
			collisionCount++;
			
			x=(segmentSizeX-(segmentBorderWidth+ballRadius));
			vx=0;
//            vy=0;//TODO
		}

		
		collisionDetectedY=false;
		if(y<(0+ballRadius)){
			collisionDetectedY=true;
			collisionCount++;
			
			y=(0+ballRadius);
			vy=0;
//            vx=0;//TODO
		}
		else 
		if(y>(segmentSizeY-ballRadius)){
			collisionDetectedY=true;
			collisionCount++;
			
			y=(segmentSizeY-ballRadius);
			vy=0;
//            vx=0;//TODO
		}
		
		
		
		// changing segment
		if( x>=segmentSizeX){
			
			
			timeInSegment_list.addLast(timeInSegment);
			while (timeInSegment_list.size()>timeInSegment_list_lenght)
			{
				timeInSegment_list.removeFirst();
			}
			
			timeInSegment=0;
			
			segmentType=segmentType*(-1);
			
			x=x-segmentSizeX;
			y=segmentSizeY-y;
			
			//vx=vx;
			vy=-vy;
			
			
		}
		
		if( x<0){
			
			timeInSegment=0;
			
			segmentType=segmentType*(-1);
			
			x=-(x-segmentSizeX);
			y=segmentSizeY-y;
			
			//vx=vx;
			vy=-vy;
			
			
		}
	
		
	}
	public static int getTimerdelay() {
		return timerDelay;
	}

}
