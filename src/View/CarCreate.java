import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JLabel;

import Controller.Board;
import Controller.Car;
import Controller.Car.Direction;


public class CarCreate {

    private ArrayList<JLabel> carList = new ArrayList<JLabel>();
    private int matrix[][];
    private static final int Car_width = 150;
    private ArrayList<MoveComponent> moveList = new ArrayList<MoveComponent>();
    private HashMap<Integer, Car> listCar;
    private Board newBoard;

    public CarCreate(Board newBoard) {

        this.newBoard = newBoard;

    }

    public ArrayList<JLabel> getCarList() {
        return carList;
    }

    public ArrayList<MoveComponent> getMoveList() {
        return moveList;
    }


    public void createCarList() {
        listCar = newBoard.getCars();
        for (int key : listCar.keySet()) {
            Car currentCar = listCar.get(key);
            JLabel a = new JLabel();
            if (currentCar.getDirection() == Direction.HORIZONTAL) {

                a.setBounds(currentCar.getPosCol() * Car_width, currentCar.getPosRow() * Car_width, Car_width * currentCar.getLength(), Car_width);
                if (key == 1) {
                    a.setBackground(Color.RED);
                } else {
                    a.setBackground(new Color((float) Math.random(), (float) Math.random(), (float) Math.random()));
                    if (a.getBackground().equals(Color.RED)) {
                        a.setBackground(new Color((float) Math.random(), (float) Math.random(), (float) Math.random()));
                    }
                }

                a.setOpaque(true);
                carList.add(a);

                MoveComponent mc = new MoveComponent();
                mc.registerComponent(a);
                mc.setDirection(0);
                mc.setSnapSize(new Dimension(150, 150));
                mc.setCarId(key);
                mc.setLabel(a);
                mc.setBoard(newBoard);

                moveList.add(mc);
            } else {

                a.setBounds(currentCar.getPosCol() * Car_width, (currentCar.getPosRow() - currentCar.getLength() + 1) * Car_width, Car_width, Car_width * currentCar.getLength());
                a.setBackground(new Color((float) Math.random(), (float) Math.random(), (float) Math.random()));
                if (a.getBackground().equals(Color.RED)) {
                    a.setBackground(new Color((float) Math.random(), (float) Math.random(), (float) Math.random()));
                }
                a.setOpaque(true);
                carList.add(a);

                MoveComponent mc = new MoveComponent();
                mc.registerComponent(a);
                mc.setDirection(1);
                mc.setSnapSize(new Dimension(150, 150));
                mc.setCarId(key);
                mc.setLabel(a);

                mc.setBoard(newBoard);
                moveList.add(mc);

            }
        }
    }
}