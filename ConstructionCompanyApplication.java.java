//java packages used in creating program
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

//abstract parent class
abstract class Person 
{
    //attributes 
    protected String name;
    protected String ID;
    
    //parameterized constructor
    public Person(String name, String ID) 
    {
        this.name = name;
	this.ID = ID;
    }

    //getter to get name
    public String getName() 
    {
	return name;
    }

    //getter to get ID
    public String getID() 
    {
	return ID;
    }
}

//child class of person - customer class
class Customer extends Person 
{
    //attributes
    private List<Order> orders;

    //parameterized constructor
    public Customer(String name, String ID) 
    {
	super(name, ID);
	orders = new ArrayList<>();
    }

    //method to place order
    public void placeOrder(Order order) 
    {
	orders.add(order);
    }

    //getter to get order
    public List<Order> getOrders() 
    {
	return orders;
    }
}

//child class of person - employee class 
class Employee extends Person
{
    //attributes
    private List<Order> assignedOrders;

    //parameterized constructor
    public Employee(String name, String ID) 
    {
    super(name, ID);
    assignedOrders = new ArrayList<>();
    }

    //method to assign order
    public void assignOrder(Order order) 
    {
        assignedOrders.add(order);
    }
	
    //method to get list of assigned orders
    public List<Order> getAssignedOrders() 
    {
	return assignedOrders;
    }
}

//part class for customer class
class Order 
{
    //attributes
    private String projectName;
    private String deadline;
    private String budget;

    //parameterized constructor
    public Order(String projectName, String deadline, String budget) 
    {
	this.projectName = projectName;
	this.deadline = deadline;
	this.budget = budget;
    }

    //method to create string
    public String toString() 
    {
	return "Project: " + projectName + ", Deadline: " + deadline + ", Budget: " + budget;
    }

}

//main class of program
public class ConstructionCompanyApplication extends JFrame implements ActionListener 
{
    //attributes
    private Customer customer;
    private Employee employee;
    private List<String> myOrders, completedProjects, tasks;

    //containers and components
    private JTextField projectNameField, deadlineField, budgetField;
    private JTextArea customerDisplay1, customerDisplay2,customerDisplay3, employeeDisplay;
    private JPanel centerPanel;

    private JMenuBar menubar;
    private JMenu customerMenu1, customerMenu2, employeeMenu;
    private JMenuItem placeOrderMenuitem, myOrdersMenuitem, previousWorkMenuitem, tasksMenuitem;

    //previously signed up customers and employees useful for login
    private final String CUSTOMER_USERNAME = "Michael";
    private final String CUSTOMER_PASSWORD = "custMichael";
    private final String EMPLOYEE_USERNAME = "John";
    private final String EMPLOYEE_PASSWORD = "empJohn";

    //default constructor
    public ConstructionCompanyApplication() 
    {
	//set data
        customer = new Customer("Michael", "C003");
        employee = new Employee("John", "E001");
        myOrders = Arrays.asList("Project name - " + projectNameField ,"Status - null","Deadline - " + deadlineField); 
        completedProjects = Arrays.asList("Residential Building - Completed on 2023-01-15","Commercial Complex - Completed on 2024-03-20");
        tasks = Arrays.asList("Sports Complex - Deadline on 2025-02-25");
        
        //set the frame
        setTitle("Construction Company System");
        setSize(600, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //set menubar and menu names
        menubar = new JMenuBar();
        customerMenu1 = new JMenu("New");
        customerMenu2 = new JMenu("View");
        employeeMenu = new JMenu("View");

	//set menu item names 
        placeOrderMenuitem = new JMenuItem("Place Order");
        myOrdersMenuitem = new JMenuItem("My Orders");
        previousWorkMenuitem = new JMenuItem("Previous Projects");
        tasksMenuitem = new JMenuItem("Tasks");

	//creating events to menu items 
        placeOrderMenuitem.addActionListener(this);
        myOrdersMenuitem.addActionListener(this);
        previousWorkMenuitem.addActionListener(this);
        tasksMenuitem.addActionListener(this);

	//connecting components
        customerMenu1.add(placeOrderMenuitem);
        customerMenu2.add(myOrdersMenuitem);
        customerMenu2.add(previousWorkMenuitem);
        employeeMenu.add(tasksMenuitem);
        menubar.add(customerMenu1);
        menubar.add(customerMenu2);
        menubar.add(employeeMenu);

        setJMenuBar(menubar);

        //set the center panel
        centerPanel = new JPanel(new BorderLayout());
        add(centerPanel, BorderLayout.CENTER);

        customerDisplay1 = new JTextArea();
        customerDisplay1.setEditable(false);
        customerDisplay1.setLineWrap(true);

        customerDisplay2= new JTextArea();
        customerDisplay2.setEditable(false);
        customerDisplay2.setLineWrap(true);
        
        customerDisplay3= new JTextArea();
        customerDisplay3.setEditable(false);
        customerDisplay3.setLineWrap(true);

        employeeDisplay = new JTextArea();
        employeeDisplay.setEditable(false);
        employeeDisplay.setLineWrap(true);

        //display login dialog 
        showLoginDialog();

        //set frame visible
        setVisible(true);
    }

    //method to display login
    private void showLoginDialog() 
    {
	while (true) 
        {
            JTextField usernameField = new JTextField();
            JPasswordField passwordField = new JPasswordField();

            Object[] message = {"Username:", usernameField, "Password:", passwordField};

            int option = JOptionPane.showConfirmDialog(this, message, "Login", JOptionPane.OK_CANCEL_OPTION);
            
            //define conditions for login
            if (option == JOptionPane.OK_OPTION) 
            {
                String username = usernameField.getText().trim();
                String password = new String(passwordField.getPassword()).trim();

                //condition for customer login 
                if (username.equals(CUSTOMER_USERNAME) && password.equals(CUSTOMER_PASSWORD)) 
                {
                    JOptionPane.showMessageDialog(this, "Welcome " + customer.getName() + "!");
                    //hide employee menu from customer
                    employeeMenu.setVisible(false); 
                    break;
                }
                //condition for employee login
                else if (username.equals(EMPLOYEE_USERNAME) && password.equals(EMPLOYEE_PASSWORD)) 
                {
                    JOptionPane.showMessageDialog(this, "Welcome " + employee.getName() + "!");
                    //hide customer menu's from employee
                    customerMenu1.setVisible(false);
                    customerMenu2.setVisible(false);
                    break;
                } 
                else 
                {
                    //unauthorized login
                    JOptionPane.showMessageDialog(this, "Invalid credentials. Try again.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } 
            else 
            {
                //exit login if user clicks cancel button
                System.exit(0); 
            }
        }
    }

    //method to create order panel 
    private JPanel createPlaceOrderPanel() 
    {
        JPanel panel = new JPanel(new GridLayout(4, 2, 5, 5));
        panel.add(new JLabel("Project Name:"));
        projectNameField = new JTextField();
        panel.add(projectNameField);

        panel.add(new JLabel("Deadline (YYYY-MM-DD):"));
        deadlineField = new JTextField();
        panel.add(deadlineField);

        panel.add(new JLabel("Budget:"));
        budgetField = new JTextField();
        panel.add(budgetField);

        JButton placeOrderBtn = new JButton("Place Order");
        placeOrderBtn.addActionListener(e -> handlePlaceOrder());
        panel.add(placeOrderBtn);

        return panel;
    }
    
    //event handling method to place order
    private void handlePlaceOrder() 
    {
        String projectName = projectNameField.getText().trim();
        String deadline = deadlineField.getText().trim();
        String budget = budgetField.getText().trim();

        if (projectName.isEmpty() || deadline.isEmpty() || budget.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all fields!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Order newOrder = new Order(projectName, deadline, budget);
        customer.placeOrder(newOrder);
        employee.assignOrder(newOrder);

        customerDisplay1.append("Order Placed: " + newOrder + "\n");
        projectNameField.setText("");
        deadlineField.setText("");
        budgetField.setText("");

        JOptionPane.showMessageDialog(this, "Order Placed Successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
    }
    
    //event handling method to view customer orders
    private void handleViewMyOrders() 
    {
        customerDisplay2.setText("My orders:\n " );
        for (String Order : myOrders) 
        {
            customerDisplay2.append(Order + "\n");
        }
    }
    
    //event handling method to view projects
    private void handleViewProjects() 
    {
        customerDisplay3.setText("Completed Projects by Company:\n");
        for (String project : completedProjects) 
        {
            customerDisplay3.append(project + "\n");
        }
    }
    
    //event handling method to view employee tasks
    private void handleViewTasks() 
    {
        employeeDisplay.setText("Upcoming Tasks for Employee " + employee.getName() + ":\n");
        for (Order order : employee.getAssignedOrders()) 
        {
            employeeDisplay.append(order + "\n");
        }
    }

    //override action abstract method 
    @Override
    public void actionPerformed(ActionEvent e) 
    {
        //clear center panel
        centerPanel.removeAll();
        
	//conditions to get source
        if (e.getSource() == placeOrderMenuitem) 
        {
            centerPanel.add(new JScrollPane(createPlaceOrderPanel()), BorderLayout.NORTH);
        } 
        else if (e.getSource() == myOrdersMenuitem) 
        {
            handleViewMyOrders();
            centerPanel.add(new JScrollPane(customerDisplay2), BorderLayout.CENTER);
        } 
        else if (e.getSource() == previousWorkMenuitem) 
        {
            handleViewProjects();
            centerPanel.add(new JScrollPane(customerDisplay3), BorderLayout.CENTER);
        } 
        else if (e.getSource() == tasksMenuitem) 
        {
            handleViewTasks();
            centerPanel.add(new JScrollPane(employeeDisplay), BorderLayout.CENTER);
        }

        centerPanel.revalidate();
        centerPanel.repaint();
    }

    //main method to execute whole program
    public static void main(String[] args) 
    {
        new ConstructionCompanyApplication();
    }
}
