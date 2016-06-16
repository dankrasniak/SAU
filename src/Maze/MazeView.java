package Maze;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import javax.swing.JPanel;






class MazeView extends JPanel{

	private static final long serialVersionUID = 1L;

	public double getcSectors() {
		return cSectors;
	}

	
    
    private final double cSectors=3.5;
    private final double ballSector=1.2;
    private final MazeModel mazeModel;

    public MazeView(MazeModel _mazeModel) {
    	mazeModel=_mazeModel;

        
        setSize(50, 30);
    }

   
    


    private void doDrawing(Graphics g) {
    	
    
        
        Graphics2D g2d;
        
        //DRAW SEGMENT X BORDERS

    	g2d = (Graphics2D) g.create();
        

	    	g2d.setPaint(Color.blue);
	    	
	    	
	
	        
	        
	        
	        
	        int linesCountL=(int)((ballSector*mazeModel.getSegmentSizeX()-mazeModel.getX())/mazeModel.getSegmentSizeX()+2);
	        int linesCountR=(int)(((cSectors-ballSector)*mazeModel.getSegmentSizeX()+mazeModel.getX())/mazeModel.getSegmentSizeX()+2);
	        
	        for (int i = 0; i < linesCountL; i++) {
	        	
	            int x = scaleSizeX(ballSector*mazeModel.getSegmentSizeX()-mazeModel.getX()-i*mazeModel.getSegmentSizeX());
	            int y=scaleSizeY(mazeModel.getSegmentSizeY()/2.0-mazeModel.getSegmentType()*Math.pow(-1,(double)(i))*(mazeModel.getSegmentOpeningSize()/2.0));
	            int wx=scaleSizeX(mazeModel.getSegmentBorderWidth());
	            int wy=scaleSizeY(mazeModel.getSegmentSizeY()/2.0-mazeModel.getSegmentOpeningSize()/2.0);
	            
	            g2d.fill(new Rectangle2D.Double(
	            		x-wx,
	            		y-wy,
                        wx*2,
                        wy*2
                        ));
	        }
	        
	        for (int i = 1; i < linesCountR; i++) {
	        	 int x = scaleSizeX(ballSector*mazeModel.getSegmentSizeX()-mazeModel.getX()+i*mazeModel.getSegmentSizeX());
		            int y=scaleSizeY(mazeModel.getSegmentSizeY()/2.0-mazeModel.getSegmentType()*Math.pow(-1,(double)(i))*(mazeModel.getSegmentOpeningSize()/2.0));
		            int wx=scaleSizeX(mazeModel.getSegmentBorderWidth());
		            int wy=scaleSizeY(mazeModel.getSegmentSizeY()/2.0-mazeModel.getSegmentOpeningSize()/2.0);
		            
		            g2d.fill(new Rectangle2D.Double(
		            		x-wx,
		            		y-wy,
	                        wx*2,
	                        wy*2
	                        ));
	        }
	
	
        g2d.dispose();
        
        
        //DRAW BALL

    	g2d = (Graphics2D) g.create();
        
	    	
	    	
	    	
	    	g2d.setPaint(Color.RED);

	        
	        
	        
	        
	        g2d.fill (new Ellipse2D.Double(
	        		scaleSizeX(ballSector*mazeModel.getSegmentSizeX())-scaleSizeX(mazeModel.getBallRadius()),
	        		flipY(scaleSizeY(mazeModel.getY()))-scaleSizeY(mazeModel.getBallRadius()),
	        		2*scaleSizeX(mazeModel.getBallRadius()),
	        		2*scaleSizeY(mazeModel.getBallRadius())
	        		));
	        
	        drawArrow(g2d, 
	        		scaleSizeX(ballSector*mazeModel.getSegmentSizeX()),
	        		flipY(scaleSizeY(mazeModel.getY())),
	        		scaleSizeX(ballSector*mazeModel.getSegmentSizeX())+scaleSizeX(mazeModel.getAX()),
	        		flipY(scaleSizeY(mazeModel.getY()))+(int)(-mazeModel.getSegmentType())*(scaleSizeY(mazeModel.getAY()))
	        		);
	        
	       
	        
	    g2d.dispose();       
        
	    g2d = (Graphics2D) g.create();
	    	
	    	g2d.setPaint(Color.GRAY);
	    
	    	g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
	    	      RenderingHints.VALUE_ANTIALIAS_ON);
	    	Font font = new Font("Serif", Font.PLAIN, 16);
	    	g2d.setFont(font);
	    	
	    	int w=getWidth();
	    	int xTextPosition=w-140;
	    	int yTextHeight=16;
	    	
	    	g2d.setPaint(Color.GRAY);
	    	g2d.fill(new Rectangle2D.Double(
	    			xTextPosition-10,
            		0,
                    w,
                    300
                    ));
	    	
	    	
	    	
	    	
	    	
	    	g2d.setPaint(Color.BLACK);
	    
	    	g2d.drawString("x="+(Math.round(mazeModel.getX()*100.0)/100.0), xTextPosition, 1*yTextHeight);
	    	g2d.drawString("y="+(Math.round(mazeModel.getY()*100.0)/100.0), xTextPosition, 2*yTextHeight);
	    	
	    	g2d.drawString("vx="+(Math.round(mazeModel.getVX()*100.0)/100.0), xTextPosition, 4*yTextHeight);
	    	g2d.drawString("vy="+(Math.round(mazeModel.getVY()*100.0)/100.0), xTextPosition, 5*yTextHeight);
	    	
	    	g2d.drawString("ax="+(Math.round(mazeModel.getAX()*100.0)/100.0), xTextPosition, 7*yTextHeight);
	    	g2d.drawString("ay="+(Math.round(mazeModel.getAY()*100.0)/100.0), xTextPosition, 8*yTextHeight);
	    	g2d.drawString("AngleMultiplier="+(Math.round(mazeModel.getAngleMultiplier()*100.0)/100.0), xTextPosition, 9*yTextHeight);
	    	g2d.drawString("Angle="+(Math.round(mazeModel.getAngle()*100.0)/100.0), xTextPosition, 10*yTextHeight);
	    	
	    	g2d.drawString("colisions="+mazeModel.getCollisionCount(), xTextPosition, 12*yTextHeight);
	    	g2d.drawString("segmentType="+mazeModel.getSegmentType(), xTextPosition, 13*yTextHeight);	    	
	    	g2d.drawString("time="+(Math.round(mazeModel.getTimeInSegment()*100.0)/100.0), xTextPosition, 14*yTextHeight);
	    	g2d.drawString("lastTime="+(Math.round(mazeModel.getLastTimeInSegment()*100.0)/100.0), xTextPosition, 15*yTextHeight);
	    	g2d.drawString("averageTime="+(Math.round(mazeModel.getAverageTimeInSegment()*100.0)/100.0), xTextPosition, 16*yTextHeight);
			if (avgt != (Math.round(mazeModel.getAverageTimeInSegment()*100.0)/100.0)) {
				avgt = (Math.round(mazeModel.getAverageTimeInSegment()*100.0)/100.0);
				MyLogger.MyLogger.Log("AverageTime", avgt + "");
			}
	    	
	    g2d.dispose();
        
        
    }
	private static double avgt = -2;

    @Override
    public void paintComponent(Graphics g) {

        super.paintComponent(g);
        doDrawing(g);
    }

   int scaleSizeX(double _x){
	   int w = getWidth();
	   return (int)( Math.ceil(_x/(cSectors*mazeModel.getSegmentSizeX())*(double)(w)));
   }
   
   int scaleSizeY(double _y){
	   int h = getHeight();
	   return  (int)(Math.ceil(_y/(mazeModel.getSegmentSizeY())*(double)(h)));
   }
   
   int flipY(int _y){
	   int h = getHeight();
	   return (int)(mazeModel.getSegmentType())<0?_y:h-_y;
   }
   
   void drawArrow(Graphics g1, int x1, int y1, int x2, int y2) {
       Graphics2D g = (Graphics2D) g1.create();
       final int ARR_SIZE = 4;
       double dx = x2 - x1, dy = y2 - y1;
       double angle = Math.atan2(dy, dx);
       int len = (int) Math.sqrt(dx*dx + dy*dy);
       AffineTransform at = AffineTransform.getTranslateInstance(x1, y1);
       at.concatenate(AffineTransform.getRotateInstance(angle));
       g.transform(at);

       // Draw horizontal arrow starting in (0, 0)
       g.drawLine(0, 0, len, 0);
       g.fillPolygon(new int[] {len, len-ARR_SIZE, len-ARR_SIZE, len},
                     new int[] {0, -ARR_SIZE, ARR_SIZE, 0}, 4);
   }
    
}
