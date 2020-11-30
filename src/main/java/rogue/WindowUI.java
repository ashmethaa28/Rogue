package rogue;

import java.util.ArrayList;

import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.swing.SwingTerminal;
import com.googlecode.lanterna.terminal.Terminal;
import com.googlecode.lanterna.TerminalPosition;

import javax.swing.JFrame;
import java.awt.Container;
import javax.swing.WindowConstants;
import java.awt.BorderLayout;
import java.io.IOException;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.GridLayout;
import javax.swing.BoxLayout;
import javax.swing.JTextField;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JFileChooser;
import java.io.File;
import java.io.Serializable;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.FileInputStream;
import java.io.ObjectInputStream;

public class WindowUI extends JFrame implements Serializable {


    private SwingTerminal terminal;
    private TerminalScreen screen;
    private static String message = "Welcome to my Rogue game";
    public static final int WIDTH = 900;
    public static final int HEIGHT = 1000;
    // Screen buffer dimensions are different than terminal dimensions
    public static final int COLS = 80;
    public static final int ROWS = 24;
    private final char startCol = 0;
    private final char msgRow = 1;
    private final char roomRow = 3;
    private Container contentPane;
    private static Rogue theGame;
    private static WindowUI theGameUI;
    private static RogueParser parser;
    public static final int THREE = 3;
    private static String configurationFileLocation = "fileLocations.json";
    private static char userInput = 'h';

/**
Constructor.
**/
    public WindowUI() {
        super("my awesome game");
        contentPane = getContentPane();
        setWindowDefaults(getContentPane());
        setUpPanels(true);
        pack();
        start();
    }

/**
 * Saves game.
 */
    public void save() {
        JFileChooser chooser = new JFileChooser();
        if (chooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
            File select = chooser.getSelectedFile();
            String filename = select.getName();
            try {
                FileOutputStream outputStream = new FileOutputStream(filename);
                ObjectOutputStream outputDest = new ObjectOutputStream(outputStream);
                outputDest.writeObject(theGame);
                outputDest.close();
                outputStream.close();
            } catch (IOException e) {
                action("IOException is caught " + e);
                System.out.println("IOException is caught " + e);
            }
        }
    }

/**
 * Loads Game.
 * @param filename of the game
 */
    public void loadGame(String filename) {
        Rogue oldGame;
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(filename));) {
            oldGame = (Rogue) in.readObject();
            theGame = oldGame;
            theGameUI.draw(message, theGame.getNextDisplay());
            theGameUI.setUpPanels(false);
            theGameUI.setVisible(true);
        } catch (IOException e) {
            action("IOException is caught " + e);
        } catch (ClassNotFoundException e) {
            action("ClassNotFoundException is caught " + e);
        }
    }

    private void setWindowDefaults(Container contentPaneX) {
        setTitle("Rogue!");
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        contentPaneX.setLayout(new BorderLayout());
    }

    private void setTerminal() {
        JPanel terminalPanel = new JPanel();
        terminal = new SwingTerminal();
        terminalPanel.add(terminal);
        contentPane.add(terminalPanel, BorderLayout.CENTER);
    }

    private void setUpPanels(boolean validUpdate) {
        JPanel menuPanel = new JPanel();
        JPanel inventoryPanel = new JPanel();
        JPanel movePanel = new JPanel();
        setUpMenu(menuPanel);
        setUpInventory(inventoryPanel);
        setUpMove(movePanel);
        if (validUpdate) {
            setTerminal();
        }
    }

    private void setUpMove(JPanel thePanel) {
        thePanel.setLayout(new GridLayout(1, THREE));

        JLabel move = new JLabel(message);
        thePanel.add(move);

        JLabel inventoryChoiceLabel = new JLabel("Enter item name that you would like to use: ");
        thePanel.add(inventoryChoiceLabel);

        JTextField inventoryChoice = new JTextField(null, ROWS);
        thePanel.add(inventoryChoice);
        inventoryChoice.addActionListener(ev -> validItemChoice(inventoryChoice.getText()));

        contentPane.add(thePanel, BorderLayout.SOUTH);
    }

    private void validItemChoice(String choice) {
        boolean goodChoice = false;
        Inventory inventory = theGame.getInventory();
        ArrayList<Item> items = inventory.getList();
        Item validItem = null;

        for (Item item : items) {
            if (choice.equals(item.getName())) {
                goodChoice = true;
                validItem = item;
            }
        }

        if (goodChoice) {
            itemAction(validItem);
        } else {
            action("Invalid item choice!");
        }
    }

    private void itemAction(Item item) {
        String action = JOptionPane.showInputDialog("Action (e/w/t): ");
        if (action.equals("e") && item.getEat()) {
            theGame.getInventory().removeItem(item);
            eatMessage(item);
        } else if (action.equals("w") && item.getWear()) {
            theGame.getInventory().removeItem(item);
            wearMessage(item);
        } else if (action.equals("t") && item.getToss()) {
            theGame.getInventory().removeItem(item);
            Room room = theGame.getPlayer().getCurrentRoom();
            ArrayList<Room> rooms = theGame.getRooms();
            tossAction(item, room, rooms);
            tossMessage(item);
        } else {
            action("Invalid action!");
        }
        theGameUI.setUpPanels(false);
        theGameUI.setVisible(true);
    }

    private void tossAction(Item item, Room room, ArrayList<Room> rooms) {
        for (Room roomtemp : rooms) {
            try {
                if (room.getId() == roomtemp.getId()) {
                    ArrayList<Item> items = theGame.getItems();
                    for (Item itemtemp : items) {
                        if (itemtemp.getId() == item.getId()) {
                            itemtemp.setCurrentRoom(roomtemp);
                            try {
                                roomtemp.addItem(itemtemp);
                            } catch (ImpossiblePositionException e) {
                            } catch (NoSuchItemException e) {
                            }
                        }
                    }
                }
            } catch (NullPointerException e) {
            }
        }
    }

    private void tossMessage(Item item) {
        if ((item.getType()).equals("SmallFood")) {
            SmallFood small = (SmallFood) item;
            action(small.toss());
        } else {
            Potion potion = (Potion) item;
            action(potion.toss());
        }
    }

    private void eatMessage(Item item) {
        if ((item.getType()).equals("Food")) {
            Food food = (Food) item;
            action(food.eat());
        } else if ((item.getType()).equals("SmallFood")) {
            SmallFood small = (SmallFood) item;
            action(small.eat());
        } else {
            Potion potion = (Potion) item;
            action(potion.eat());
        }
    }

    private void wearMessage(Item item) {
        if ((item.getType()).equals("Clothing")) {
            Clothing clothing = (Clothing) item;
            action(clothing.wear());
        } else {
            Ring ring = (Ring) item;
            action(ring.wear());
        }
    }

    private void action(String str) {
        JOptionPane.showMessageDialog(null, str);
    }

    private void setUpInventory(JPanel thePanel) {
        BoxLayout boxlayout = new BoxLayout(thePanel, BoxLayout.Y_AXIS);
        thePanel.setLayout(boxlayout);
        JLabel label = new JLabel("----------Inventory---------");
        thePanel.add(label);
        int num = theGame.getItems().size();
        Inventory inventory = theGame.getInventory();
        ArrayList<Item> items = inventory.getList();
        JLabel itemName;
        String name;
        for (Item item : items) {
            itemName = new JLabel(item.getName());
            thePanel.add(itemName);
            num--;
        }
        contentPane.add(thePanel, BorderLayout.EAST);
    }

    private void setUpMenu(JPanel thePanel) {
        thePanel.setLayout(new GridLayout(2, 1));
        JMenuBar menubar = new JMenuBar();
        thePanel.add(menubar);
        JMenu menu = new JMenu("Menu");
        menubar.add(menu);
        menu(menu);
        try {
            JLabel name = new JLabel("Player: " + theGame.getPlayer().getName());
            thePanel.add(name);
        } catch (NullPointerException e) {
            JLabel name = new JLabel("Player: ");
            thePanel.add(name);
        }
        contentPane.add(thePanel, BorderLayout.NORTH);
    }

    private void menu(JMenu menu) {
        JMenuItem loadJSON = new JMenuItem("New JSON File");
        loadJSON.addActionListener(ev -> newJSON());
        menu.add(loadJSON);
        JMenuItem changeName = new JMenuItem("Change Name");
        menu.add(changeName);
        changeName.addActionListener(ev -> changePlayerName());
        JMenuItem loadGame = new JMenuItem("Load Game");
        menu.add(loadGame);
        loadGame.addActionListener(ev -> chooseFile());
        JMenuItem save = new JMenuItem("Save Game");
        menu.add(save);
        save.addActionListener(ev -> save());
    }

    private void chooseFile() {
        JFileChooser chooser = new JFileChooser();
        if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            File select = chooser.getSelectedFile();
            loadGame(select.getName());
        }
    }

    private void newJSON() {
        boolean isJSON = false;
        JFileChooser chooser = new JFileChooser();
        if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            File select = chooser.getSelectedFile();
            String[] fileParts = select.getName().split("\\.");
            for (String str : fileParts) {
                if (str.equals("json")) {
                    isJSON = true;
                } else {
                    isJSON = false;
                }
            }
            if (isJSON) {
                isJSON(select.getName());
            } else {
                action("Invalid file choice!");
            }
        }
    }

    private void isJSON(String filename) {
        parser.roomParser(filename);
        theGame.removeRoomList();
        theGame.removeItemList();
        String name = theGame.getPlayer().getName();
        theGame.setRogueParser(parser);
        theGame.createRooms();
        theGame.setInventory();
        Player thePlayer = new Player(name);
        theGame.setPlayer(thePlayer);
        message = "new JSON file";
        theGameUI.draw(message, theGame.getNextDisplay());
    }

    private void start() {
        try {
            screen = new TerminalScreen(terminal);
            screen.setCursorPosition(TerminalPosition.TOP_LEFT_CORNER);
            screen.startScreen();
            screen.refresh();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void changePlayerName() {
        String name = JOptionPane.showInputDialog("Enter new name: ");
        try {
            theGame.getPlayer().setName(name);
            theGameUI.setUpPanels(false);
            theGameUI.setVisible(true);
        } catch (NullPointerException e) {
        }
    }

    /**
Prints a string to the screen starting at the indicated column and row.
@param toDisplay the string to be printed
@param column the column in which to start the display
@param row the row in which to start the display
**/
    public void putString(String toDisplay, int column, int row) {
        Terminal t = screen.getTerminal();
        try {
            t.setCursorPosition(column, row);
            for (char ch: toDisplay.toCharArray()) {
                t.putCharacter(ch);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

/**
Changes the message at the top of the screen for the user.
@param msg the message to be displayed
**/
    public void setMessage(String msg) {
        putString("                                                ", 1, 1);
        putString(msg, startCol, msgRow);
    }

/**
Redraws the whole screen including the room and the message.
@param msg the message to be displayed at the top of the room
@param room the room map to be drawn
**/
    public void draw(String msg, String room) {
        try {
            setMessage(msg);
            putString(room, startCol, roomRow);
            screen.refresh();
        } catch (IOException e) {

        }
    }

/**
Obtains input from the user and returns it as a char.  Converts arrow
keys to the equivalent movement keys in rogue.
@return the ascii value of the key pressed by the user
**/
    public char getInput() {
        KeyStroke keyStroke = null;
        while (keyStroke == null) {
        try {
                keyStroke = screen.pollInput();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
         if (keyStroke.getKeyType() == KeyType.ArrowDown) {
            return Rogue.DOWN;  //constant defined in rogue
        } else if (keyStroke.getKeyType() == KeyType.ArrowUp) {
            return Rogue.UP;
        } else if (keyStroke.getKeyType() == KeyType.ArrowLeft) {
            return Rogue.LEFT;
        } else if (keyStroke.getKeyType() == KeyType.ArrowRight) {
            return Rogue.RIGHT;
        }
        return keyStroke.getCharacter();
    }

/**
The controller method for making the game logic work.
@param args command line parameters
**/
    public static void main(String[] args) {
        parser = new RogueParser(configurationFileLocation);
        theGame = new Rogue(parser);
        Player thePlayer = new Player("Ash");
        theGame.setPlayer(thePlayer);
        theGameUI = new WindowUI();
        theGameUI.draw(message, theGame.getNextDisplay());
        theGameUI.setVisible(true);
        while (userInput != 'q') {
            userInput = theGameUI.getInput();
            try {
                message = theGame.makeMove(userInput);
                theGameUI.setUpPanels(false);
                theGameUI.setVisible(true);
                theGameUI.draw(message, theGame.getNextDisplay());
            } catch (InvalidMoveException badMove) {
                theGameUI.setMessage("I didn't understand what you meant, please enter a command");
            }
        }
    }

}
