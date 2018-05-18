package gui;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;
import settings.Settings;

public class Grid extends JPanel {
//	private static final long serialVersionUID = 1L;
//
//	public Grid() {
//		setLayout(new GridBagLayout());
//
//        GridBagConstraints gbc = new GridBagConstraints();
//        for (int row = 0; row < 5; row++) {
//            for (int col = 0; col < 5; col++) {
//                gbc.gridx = col;
//                gbc.gridy = row;
//
//                CellPane cellPane = new CellPane();
//                Border border = null;
//                if (row < 4) {
//                    if (col < 4) {
//                        border = new MatteBorder(1, 1, 0, 0, Color.GRAY);
//                    } else {
//                        border = new MatteBorder(1, 1, 0, 1, Color.GRAY);
//                    }
//                } else {
//                    if (col < 4) {
//                        border = new MatteBorder(1, 1, 1, 0, Color.GRAY);
//                    } else {
//                        border = new MatteBorder(1, 1, 1, 1, Color.GRAY);
//                    }
//                }
//                cellPane.setBorder(border);
//                add(cellPane, gbc);
//            }
//        }
//    }
//	
//	@Override
//	public Dimension getPreferredSize() {
//	    return new Dimension(300, 300);
//	}
//	
//	public class CellPane extends JPanel {
//		private static final long serialVersionUID = 1L;
//		private Color defaultBackground;
//	
//	    public CellPane() {
//	    }
//	
//	    @Override
//	    public Dimension getPreferredSize() {
//	        return new Dimension(Settings.UI_BLOCK_SIZE, Settings.UI_BLOCK_SIZE);
//	    }
//	}
	
	private static final long serialVersionUID = 1L;

	public Grid() {
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(Settings.UI_BLOCK_SIZE * Settings.BOARD_SIZE, Settings.UI_BLOCK_SIZE * Settings.BOARD_SIZE);
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();
        for ( int x = 0; x <= Settings.UI_BLOCK_SIZE * Settings.BOARD_SIZE; x += Settings.UI_BLOCK_SIZE) {
        	for ( int y = 0; y <= Settings.UI_BLOCK_SIZE * Settings.BOARD_SIZE; y += Settings.UI_BLOCK_SIZE) {
        		g.drawRect( x, y, Settings.UI_BLOCK_SIZE, Settings.UI_BLOCK_SIZE);
        	}
        }
        g2d.dispose();
    }

}



