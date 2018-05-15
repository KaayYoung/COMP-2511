package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Map;

import javax.swing.JLabel;

import gridlock.Board;
import gridlock.Car;
import gridlock.Car.Direction;
import settings.Settings;


public class CarCreate {

    private ArrayList<JLabel> carList = new ArrayList<JLabel>();
    private ArrayList<MoveComponent> moveList = new ArrayList<MoveComponent>();
    private Map<Integer, Car> listCar;
    private Color[] carColours = {null, Color.RED, Color.BLUE, Color.GREEN, Color.YELLOW, Color.ORANGE, Color.PINK, Color.WHITE, Color.CYAN, Color.DARK_GRAY, Color.MAGENTA, Color.GRAY};
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

                a.setBounds(currentCar.getPosCol() * Settings.UI_BLOCK_SIZE, currentCar.getPosRow() * Settings.UI_BLOCK_SIZE, Settings.UI_BLOCK_SIZE * currentCar.getLength(), Settings.UI_BLOCK_SIZE);
                a.setBackground(carColours[key]);
                a.setOpaque(true);
                carList.add(a);

                MoveComponent mc = new MoveComponent(currentCar);
                mc.registerComponent(a);
                mc.setDirection(0);
                mc.setSnapSize(new Dimension(Settings.UI_BLOCK_SIZE, Settings.UI_BLOCK_SIZE));
                mc.setCarId(key);
                mc.setLabel(a);
                mc.setBoard(newBoard);
                moveList.add(mc);
            } else {

                a.setBounds(currentCar.getPosCol() * Settings.UI_BLOCK_SIZE, (currentCar.getPosRow() - currentCar.getLength() + 1) * Settings.UI_BLOCK_SIZE, Settings.UI_BLOCK_SIZE, Settings.UI_BLOCK_SIZE * currentCar.getLength());
                a.setBackground(carColours[key]);
                a.setOpaque(true);
                carList.add(a);

                MoveComponent mc = new MoveComponent(currentCar);
                mc.registerComponent(a);
                mc.setDirection(1);
                mc.setSnapSize(new Dimension(Settings.UI_BLOCK_SIZE, Settings.UI_BLOCK_SIZE));
                mc.setCarId(key);
                mc.setLabel(a);
                mc.setBoard(newBoard);
                moveList.add(mc);

            }
        }
    }
}