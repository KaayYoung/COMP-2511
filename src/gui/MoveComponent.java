package gui;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;

import gridlock.Board;
import settings.Settings;

/*Code from internet, needs to be modify*/
public class MoveComponent extends MouseAdapter
{
    private Insets dragInsets = new Insets(0, 0, 0, 0);
    private Dimension snapSize = new Dimension(1, 1);
    private Insets edgeInsets = new Insets(0, 0, 0, 0);
    private boolean changeCursor = true;
    private boolean autoLayout = false;

    private Class<?> destinationClass;
    private Component destinationComponent;
    private Component destination;
    private Component source;

    private Point pressed;
    private Point location;

    private Cursor originalCursor;
    private boolean autoscrolls;
    private boolean potentialDrag;

    private int direction = 3;

    private ArrayList<JLabel> carList;
    private JLabel label;
    private int carId;
    private Board board;

    public MoveComponent()
    {
    }

    public MoveComponent(Class<?> destinationClass, Component... components)
    {
        this.destinationClass = destinationClass;
        registerComponent( components );
    }

    public MoveComponent(Component destinationComponent, Component... components)
    {
        this.destinationComponent = destinationComponent;
        registerComponent( components );
    }

    public void deregisterComponent(Component... components)
    {
        for (Component component : components)
            component.removeMouseListener( this );
    }


    public void registerComponent(Component... components)
    {
        for (Component component : components)
            component.addMouseListener( this );
    }


    public void setSnapSize(Dimension snapSize)
    {
        if (snapSize.width < 1
                ||  snapSize.height < 1)
            throw new IllegalArgumentException("Snap sizes must be greater than 0");

        this.snapSize = snapSize;
    }


    @Override
    public void mousePressed(MouseEvent e)
    {
        source = e.getComponent();
        int width  = source.getSize().width  - dragInsets.left - dragInsets.right;
        int height = source.getSize().height - dragInsets.top - dragInsets.bottom;
        Rectangle r = new Rectangle(dragInsets.left, dragInsets.top, width, height);

        if (r.contains(e.getPoint()))
            setupForDragging(e);
    }

    private void setupForDragging(MouseEvent e)
    {
        source.addMouseMotionListener( this );
        potentialDrag = true;

        if (destinationComponent != null)
        {
            destination = destinationComponent;
        }
        else if (destinationClass == null)
        {
            destination = source;
        }
        else
        {
            destination = SwingUtilities.getAncestorOfClass(destinationClass, source);
        }

        pressed = e.getLocationOnScreen();
        location = destination.getLocation();

        if (changeCursor)
        {
            originalCursor = source.getCursor();
            source.setCursor( Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR) );
        }


        if (destination instanceof JComponent)
        {
            JComponent jc = (JComponent)destination;
            autoscrolls = jc.getAutoscrolls();
            jc.setAutoscrolls( false );
        }
    }

    @Override
    public void mouseDragged(MouseEvent e)
    {
        Point dragged = e.getLocationOnScreen();

        int dragX = getDragDistance(dragged.x, pressed.x, snapSize.width);

        int dragY = getDragDistance(dragged.y, pressed.y, snapSize.height);
        if (direction == 1) {
            dragX = 0;
        } else if (direction == 0){
            dragY = 0;
        }
        int locationX = location.x + dragX;
        int locationY = location.y + dragY;

        //*********************************************************************************
        // for the board edge
        while (locationX < edgeInsets.left)
            locationX += snapSize.width;

        while (locationY < edgeInsets.top)
            locationY += snapSize.height;

        Dimension d = getBoundingSize( destination );


        while (locationX + destination.getSize().width + edgeInsets.right > d.width)
            locationX -= snapSize.width;

        while (locationY + destination.getSize().height + edgeInsets.bottom > d.height)
            locationY -= snapSize.height;

        //*************************************************************************************


        //*********************************************************************************
        //check collision and solve it
        for(int i = 0; i < carList.size(); i++) {
            JLabel wall = carList.get(i);
            if(wall != label) {
                // vertical
                if(locationX >= wall.getBounds().getX() && locationX < wall.getBounds().getX() +  wall.getBounds().getWidth()){
                    // hit up
                    if(location.y >= wall.getBounds().getY() + wall.getBounds().getHeight()) {
                        while(locationY <= wall.getBounds().getY()) {
                            locationY += snapSize.height;
                        }
                    }
                    //hit down
                    if(location.y + label.getBounds().getHeight() <= wall.getBounds().getY()) {
                        while(locationY+label.getBounds().getHeight() >= wall.getBounds().getY() + wall.getBounds().getHeight()) {
                            locationY -= snapSize.height;
                        }
                    }
                }

                // horizontal
                if(locationY >= wall.getBounds().getY() && locationY < wall.getBounds().getY() + wall.getBounds().getHeight()) {
                    //hit left
                    if(location.x >= wall.getBounds().getX() + wall.getBounds().getWidth()) {
                        while(locationX <= wall.getBounds().getX()) {
                            locationX += snapSize.width;
                        }
                    }
                    //hit right
                    if(location.x + label.getBounds().getWidth() <= wall.getBounds().getX()) {
                        while(locationX+label.getBounds().getWidth() >= wall.getBounds().getX() + wall.getBounds().getWidth()) {
                            locationX -= snapSize.width;
                        }
                    }
                }
            }
        }

        //************************************************************************************



        destination.setLocation(locationX, locationY);


    }

    private int getDragDistance(int larger, int smaller, int snapSize)
    {
        int halfway = snapSize / 2;
        int drag = larger - smaller;
        drag += (drag < 0) ? -halfway : halfway;
        drag = (drag / snapSize) * snapSize;

        return drag;
    }

    private Dimension getBoundingSize(Component source)
    {
        if (source instanceof Window)
        {
            GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
            Rectangle bounds = env.getMaximumWindowBounds();
            return new Dimension(bounds.width, bounds.height);
        }
        else
        {
            return source.getParent().getSize();
        }
    }

    @Override
    public void mouseReleased(MouseEvent e)
    {
    	JLabel label = (JLabel)e.getSource();
    	GameFrame frame = (GameFrame)SwingUtilities.getRoot(label);

        int changeCol = 0;
        int changeRow = 0;
        int rowBefore = 0;
        int colBefore = 0;
        
        if(direction == 1) {
            changeCol = (int)label.getBounds().getX()/Settings.UI_BLOCK_SIZE;
            changeRow = ((int)label.getBounds().getY()+(int)label.getBounds().getHeight())/Settings.UI_BLOCK_SIZE;
            rowBefore = board.getCar(carId).getPosRow();
            board.changeCarPos(carId, changeRow-1, changeCol);
            frame.updateMoves(Math.abs(rowBefore - changeRow + 1));
        }else if(direction == 0) {
            changeCol = (int)label.getBounds().getX()/Settings.UI_BLOCK_SIZE;
            changeRow = (int)label.getBounds().getY()/Settings.UI_BLOCK_SIZE;
            colBefore = board.getCar(carId).getPosCol();
            board.changeCarPos(carId, (int)label.getBounds().getY()/Settings.UI_BLOCK_SIZE, (int)label.getBounds().getX()/Settings.UI_BLOCK_SIZE);
            frame.updateMoves(Math.abs(changeCol - colBefore));
        }


        if (!potentialDrag) return;

        source.removeMouseMotionListener( this );
        potentialDrag = false;

        if (changeCursor)
            source.setCursor( originalCursor );

        if (destination instanceof JComponent)
        {
            ((JComponent)destination).setAutoscrolls( autoscrolls );
        }

        //  Layout the components on the parent container

        if (autoLayout)
        {
            if (destination instanceof JComponent)
            {

                ((JComponent)destination).revalidate();
            }
            else
            {


                destination.validate();
            }

        }

    }
    // 0 -> horizontal
    // 1-> vertical
    public void setDirection (int direction) {

        this.direction = direction;

    }

    public void setLabel(JLabel l) {
        label = l;
    }

    public JLabel getLabel() {
        return label;
    }

    public void setCarList(ArrayList<JLabel> carList) {
        this.carList = carList;
    }

    public void setCarId(int key) {
        carId = key;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

}