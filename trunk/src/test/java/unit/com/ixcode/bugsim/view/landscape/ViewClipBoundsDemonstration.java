package com.ixcode.bugsim.view.landscape;

import org.apache.log4j.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import static java.awt.BorderLayout.CENTER;
import static java.awt.BorderLayout.SOUTH;

public class ViewClipBoundsDemonstration extends JPanel {
    private StatusBar statusBar;
    private MovingBlock movingBlock;

    public static void main(String[] args) {
        JFrame f = new JFrame("View Clip Bounds Demo");
        f.setSize(new Dimension(500, 500));
        f.getRootPane().setLayout(new BorderLayout());

        JPanel container = new JPanel(new BorderLayout());
        final int borderWidth = 20;
        container.setBorder(BorderFactory.createEmptyBorder(borderWidth, borderWidth, borderWidth, borderWidth));

        StatusBar statusBar = new StatusBar();
        MovingBlock movingBlock = new MovingBlock(new Point(150, 150), new Dimension(100, 100));

        ViewClipBoundsDemonstration boundsDemonstration = new ViewClipBoundsDemonstration(statusBar, movingBlock);

        MovingBlockMouseHandler movingBlockMouseHandler = new MovingBlockMouseHandler(statusBar, movingBlock);
        boundsDemonstration.addMouseListener(movingBlockMouseHandler);
        boundsDemonstration.addMouseMotionListener(movingBlockMouseHandler);
        boundsDemonstration.addComponentListener(new MovingBlockComponentListener(movingBlock));

        container.add(boundsDemonstration, CENTER);
        f.getRootPane().add(container, CENTER);
        f.getRootPane().add(statusBar, SOUTH);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        f.setVisible(true);
    }

    public ViewClipBoundsDemonstration(StatusBar statusBar, MovingBlock movingBlock) {
        this.statusBar = statusBar;
        this.movingBlock = movingBlock;
        this.movingBlock.setParent(this);
    }

    @Override
    protected void paintComponent(Graphics g) {
        paintComponent2D((Graphics2D) g);
    }

    private void paintComponent2D(Graphics2D g2D) {
        Rectangle clipBounds = g2D.getClipBounds();

        g2D.setColor(Color.lightGray);
        g2D.fill(clipBounds);

        movingBlock.paint2D(g2D);
    }


    private static class StatusBar extends JPanel {
        private final JLabel textLabel;

        private StatusBar() {
            setLayout(new BorderLayout());
            textLabel = new JLabel("Status");
            add(textLabel, CENTER);
        }

        public void setStatusText(String text) {
            textLabel.setText(text);
            SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    invalidate();
                    repaint();
                }
            });

        }

    }

    private static class MovingBlock {
        private static final Logger log = Logger.getLogger(ViewClipBoundsDemonstration.class);
        private Point topLeft;
        private Dimension size;
        private JComponent parent;
        private Point startDragTopLeft;

        public MovingBlock(Point topLeft, Dimension size) {
            this.topLeft = topLeft;
            this.size = size;
        }

        public void setParent(JComponent parent) {
            this.parent = parent;
        }

        public void paint2D(Graphics2D g2D) {
            Rectangle blockBounds = new Rectangle(topLeft, size);
            g2D.setColor(Color.blue);
            g2D.fill(blockBounds);
        }

        public boolean containsPoint(Point screenPoint) {
            return new Rectangle(topLeft, size).contains(screenPoint);
        }

        public Point topLeft() {
            return topLeft;
        }

        public void beginDrag() {
            startDragTopLeft = new Point(topLeft);
        }

        public void endDrag() {
            startDragTopLeft = null;
        }

        public void dragBy(int dx, int dy, Rectangle containingBounds) {
            if (startDragTopLeft == null) {
                return;
            }
            Point newTopLeftPosition = new Point(startDragTopLeft);
            newTopLeftPosition.translate(dx, dy);

            topLeft = keepWithinBounds(newTopLeftPosition, containingBounds);

            if (parent != null) {
                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        parent.invalidate();
                        parent.repaint();
                    }
                });
            }
        }

        public void keepWithinBounds(Rectangle containingBounds) {
            this.topLeft = keepWithinBounds(this.topLeft, containingBounds);                
        }

        private Point keepWithinBounds(Point newTopLeftPosition, Rectangle containingBounds) {
            int x = limitToRange(newTopLeftPosition.x, 0, (containingBounds.width) - size.width);
            int y = limitToRange(newTopLeftPosition.y, 0, (containingBounds.height) - size.height);
            return new Point(x, y);
        }

        private int limitToRange(int variable, int min, int max) {
            int x = Math.min(variable, max);
            return Math.max(x, min);
        }

        public String toString() {
            return "MovingBlock: " + new Rectangle(topLeft, size);
        }

    }

    private static class MovingBlockMouseHandler extends MouseAdapter {
        private static final Logger log = Logger.getLogger(ViewClipBoundsDemonstration.class);
        private final StatusBar statusBar;
        private final MovingBlock movingBlock;

        private Point startOfDrag;

        public MovingBlockMouseHandler(StatusBar statusBar, MovingBlock movingBlock) {
            this.statusBar = statusBar;
            this.movingBlock = movingBlock;
        }

        @Override
        public void mousePressed(MouseEvent e) {
            startOfDrag = e.getPoint();
            if (movingBlock.containsPoint(startOfDrag)) {
                movingBlock.beginDrag();
            }
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            movingBlock.endDrag();
        }

        @Override
        public void mouseDragged(MouseEvent e) {
            Point currentLocation = e.getPoint();
            int dx = currentLocation.x - startOfDrag.x;
            int dy = currentLocation.y - startOfDrag.y;

            movingBlock.dragBy(dx, dy, e.getComponent().getBounds());

            updateStatusBar(e);
        }

        @Override
        public void mouseMoved(MouseEvent e) {
            Cursor cursor = (movingBlock.containsPoint(e.getPoint())) ? Cursor.getPredefinedCursor(Cursor.HAND_CURSOR) : Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR);
            e.getComponent().setCursor(cursor);            
            updateStatusBar(e);
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            updateStatusBar(e);
        }

        @Override
        public void mouseExited(MouseEvent e) {
            updateStatusBar(e);
        }

        private void updateStatusBar(MouseEvent e) {
            statusBar.setStatusText("Current Location:" + e.getPoint() + ", ClipBounds:" + e.getComponent().getBounds() + ", " + movingBlock);
        }

    }

    private static class MovingBlockComponentListener extends ComponentAdapter {
        private final MovingBlock movingBlock;

        public MovingBlockComponentListener(MovingBlock movingBlock) {
            this.movingBlock = movingBlock;
        }

        @Override
        public void componentResized(ComponentEvent e) {
            movingBlock.keepWithinBounds(e.getComponent().getBounds());
        }
    }
}
