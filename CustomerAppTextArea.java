package lab4;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.TitledBorder;


public class CustomerAppTextArea extends JFrame implements ActionListener {
	
	private JButton openButton = new JButton ("Open File");
	private JButton saveButton = new JButton("Save File");
	private JPanel buttonsPanel = new JPanel();
	private JFileChooser jfc = new JFileChooser();
	
	// Menu structure
	private JMenuBar myBar = new JMenuBar();
	private JMenu fileMenu = new JMenu("File");
	private JMenuItem fileLoad, fileSaveAs;
	
	//form
	private JLabel nameLabel = new JLabel("Customer Name");
    private JTextField nameField = new JTextField(10);
	private JLabel ageLabel = new JLabel("Age");
	private JTextField ageField = new JTextField(10);
	private JLabel emailLabel = new JLabel("Email");
	private JTextField emailField = new JTextField(10);
	private JButton addButton = new JButton("Add");
	private JPanel formPanel = new JPanel();
	
	//array list to store person objects
	private ArrayList<Customer> customerArr = new ArrayList<Customer>();
	private File targetFileLocation;
	private JTextArea customersArea = new JTextArea();
	//private JTable tbl = new JTable();
	///private JScrollPane scrl = new JScrollPane(tbl);
	private JButton deleteButton = new JButton("Delete");
	private JPanel displayPanel = new JPanel();
	private MyTableModel tm = new MyTableModel(customerArr);
	
	public CustomerAppTextArea() {
		
		// Setting up menu
		fileLoad = new JMenuItem("Open");
		fileSaveAs = new JMenuItem("Save As");		
		fileMenu = new JMenu("File");
		fileMenu.add(fileLoad);
		fileMenu.add(fileSaveAs);
		myBar.add(fileMenu);
		


		//create form panel
		createFormPanel();
		
		//default JFileChooser to current location
		jfc.setCurrentDirectory(new File(System.getProperty("user.dir")));

		
		//create file save as action listener
		fileSaveAs.addActionListener(this);
		fileLoad.addActionListener(this);
		
		//create add button action listener
		addButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent evt) {
				int age = -1;
				Customer p = null;
				try {
					if (nameField.getText().isEmpty() || ageField.getText().isEmpty() || emailField.getText().isEmpty() )
						JOptionPane.showMessageDialog(CustomerAppTextArea.this, "All textfields must have a value");

					else {
						String name = nameField.getText();
						age = Integer.parseInt(ageField.getText());
						String email = emailField.getText();
						p = new Customer(name, age, email);
						
						
						customerArr.add(p);
						customersArea.append(p.toString());
						nameField.setText("");
						ageField.setText("");
						emailField.setText("");
					}
						
				}
				catch(NumberFormatException ex) {
					JOptionPane.showMessageDialog(CustomerAppTextArea.this, "Age must be a number");
				}
				
			}
			
		});
		

		////add form panel
		add(formPanel, BorderLayout.NORTH);
		
		//add display panel
		TitledBorder title = BorderFactory.createTitledBorder("Customer Records");
		displayPanel.setBorder(title);

		displayPanel.add(customersArea);
		add(displayPanel, BorderLayout.CENTER);
		
		//add menu bar
	    this.setJMenuBar(myBar);
		
		//set Frame properties
	    this.setTitle("Customer Management Application");
		this.setVisible(true);
		this.setSize(600, 400);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);;
		
	}
	
	public void createFormPanel() {
	    formPanel.setLayout(new GridBagLayout());
		TitledBorder title = BorderFactory.createTitledBorder("Customer Details");
		formPanel.setBorder(title);
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 1;
		c.weighty = 1;
		c.anchor =  GridBagConstraints.WEST; 
	    c.gridx = 0;
	    c.gridy = 0;
	    formPanel.add(nameLabel, c);
	    c.gridx = 1;
	    formPanel.add(nameField, c);
		c.gridx = 0;
	    c.gridy = 1;
	    formPanel.add(ageLabel, c);
	    c.gridx = 1;
	    formPanel.add(ageField,c);
		c.gridx = 0;
	    c.gridy = 2;
	    formPanel.add(emailLabel,c);
	    c.gridx = 1;
	    formPanel.add(emailField,c);;
		c.gridx = 0;
	    c.gridy = 3;
	    c.gridwidth = 2; 
	    c.fill = GridBagConstraints.NONE;
	    c.anchor =  GridBagConstraints.CENTER; 
	    formPanel.add(addButton, c);
   }

	public void readData() {
		FileInputStream fi = null;
		ObjectInputStream in = null;
		ArrayList<Customer>arr = null;
		try {
			fi = new FileInputStream(targetFileLocation);
			in = new ObjectInputStream(fi);
			
			arr = (ArrayList<Customer>)in.readObject();
			
			customerArr.clear();
			
			customerArr.addAll(arr);
			
			// Iterate over entire ArrayList
			for(Customer c : customerArr)
			{
			    // Append each string from ArrayList to the end of text in JTextArea
			    // separated by newline
				customersArea.append(c.toString());
			}



		} catch(EOFException ex) {}
		catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		finally {
			try {
				in.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			for(Customer c:customerArr)
				System.out.println(c.toString());
		}
		


		
		
	}
	
	public void writeData() {
		FileOutputStream fo = null;
		ObjectOutputStream os = null;
		try {
			
			fo = new FileOutputStream(targetFileLocation);
			os = new ObjectOutputStream(fo);
			os.writeObject(customerArr);

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			try {
				os.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
	}

	public static void main(String[] args) throws IOException {
		new CustomerAppTextArea();

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == fileSaveAs) {
			//check that the arraylist has values
			if(customerArr.size()<=0)
				JOptionPane.showMessageDialog(this, "You must add customer records before saving");
			else {
				int retVal = jfc.showSaveDialog(this);
				
				if(retVal == JFileChooser.APPROVE_OPTION) {
					targetFileLocation = jfc.getSelectedFile();
					writeData();
				}
				this.customerArr.clear();
				this.customersArea.setText(null);

			}
		}
		else if(e.getSource() == fileLoad) {
			int openVal = jfc.showOpenDialog(this);
			
			if(openVal == JFileChooser.APPROVE_OPTION) {
				targetFileLocation = jfc.getSelectedFile();
				readData();
			}
			
		}
		
	}

}
