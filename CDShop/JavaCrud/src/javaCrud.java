import java.awt.EventQueue;
import java.sql.*;
import java.time.Year;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.border.BevelBorder;
import javax.swing.table.DefaultTableModel;

import net.proteanit.sql.DbUtils;
import java.awt.Color;

public class javaCrud {

	private JFrame frmK;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					javaCrud window = new javaCrud();
					window.frmK.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public javaCrud() {
		initialize();
		Connect();
		table_load();
	}
	
	// Establish a connection
	
	Connection con;
	PreparedStatement pst;
	ResultSet rs;
	ResultSetMetaData rd;
	DefaultTableModel model;
	
	private JTextField txtBand;
	private JTextField txtAlbum;
	private JTextField txtYear;
	private JTextField txtPrice;
	private JTextField txtid;
	private JTable table;
	
	public void Connect() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost/javacrud", "root","");
			
		} catch (ClassNotFoundException ex) {
			
		} catch (SQLException ex) {
			
		}
	}
	
	//View Records
	public void table_load() {
		
		try {
			pst = con.prepareStatement("select * from cd");
			rs = pst.executeQuery();
			table.setModel (DbUtils.resultSetToTableModel(rs));
				
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Initialise the contents of the frame.
	 */
	private void initialize() {
		frmK = new JFrame();
		frmK.setTitle("K");
		frmK.setBounds(100, 100, 915, 652);
		frmK.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmK.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("CD Shop");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 30));
		lblNewLabel.setBounds(393, 10, 136, 42);
		frmK.getContentPane().add(lblNewLabel);
		
		JPanel panel = new JPanel();
		panel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panel.setBounds(10, 67, 436, 294);
		frmK.getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel_1_3 = new JLabel("Band ");
		lblNewLabel_1_3.setFont(new Font("Tahoma", Font.BOLD, 27));
		lblNewLabel_1_3.setBounds(10, 21, 196, 49);
		panel.add(lblNewLabel_1_3);
		
		JLabel lblNewLabel_1 = new JLabel("Album Name");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 27));
		lblNewLabel_1.setBounds(10, 89, 196, 49);
		panel.add(lblNewLabel_1);
		
		JLabel lblNewLabel_1_1 = new JLabel("Year");
		lblNewLabel_1_1.setFont(new Font("Tahoma", Font.BOLD, 27));
		lblNewLabel_1_1.setBounds(10, 164, 196, 49);
		panel.add(lblNewLabel_1_1);
		
		JLabel lblNewLabel_1_2 = new JLabel("Price");
		lblNewLabel_1_2.setFont(new Font("Tahoma", Font.BOLD, 27));
		lblNewLabel_1_2.setBounds(10, 234, 196, 50);
		panel.add(lblNewLabel_1_2);
		
		txtAlbum = new JTextField();
		txtAlbum.setFont(new Font("Tahoma", Font.BOLD, 16));
		txtAlbum.setBounds(233, 89, 161, 51);
		panel.add(txtAlbum);
		txtAlbum.setColumns(10);
		
		txtYear = new JTextField();
		txtYear.setFont(new Font("Tahoma", Font.BOLD, 16));
		txtYear.setColumns(10);
		txtYear.setBounds(233, 164, 161, 49);
		panel.add(txtYear);
		
		txtPrice = new JTextField();
		txtPrice.setFont(new Font("Tahoma", Font.BOLD, 16));
		txtPrice.setBounds(233, 234, 161, 50);
		panel.add(txtPrice);
		txtPrice.setColumns(10);
		
		txtBand = new JTextField();
		txtBand.setFont(new Font("Tahoma", Font.BOLD, 16));
		txtBand.setColumns(10);
		txtBand.setBounds(233, 21, 161, 51);
		panel.add(txtBand);
		
		JButton btnSave = new JButton("Save");
		btnSave.setBackground(new Color(120, 180, 146));
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String band,album;
				int year,price;
				
				band = txtBand.getText();
				album = txtAlbum.getText();
				year = Integer.parseInt(txtYear.getText());
				price = Integer.parseInt(txtPrice.getText());
				
				try {
					pst = con.prepareStatement("insert into cd(band,album,year,price) values(?,?,?,?)");
					pst.setString(1, band);
					pst.setString(2, album);
					pst.setInt(3, year);
					pst.setInt(4, price);
					pst.executeUpdate();
					
					JOptionPane.showMessageDialog(null, "Record Added!");
					table_load();
					
					txtBand.setText("");
					txtAlbum.setText("");
					txtYear.setText("");
					txtPrice.setText("");
					txtBand.requestFocus();
					
				} catch (SQLException e1) {
					
					e1.printStackTrace();
				}
			}
			
			
		});
		
		
		btnSave.setFont(new Font("Tahoma", Font.BOLD, 16));
		btnSave.setBounds(36, 371, 105, 49);
		frmK.getContentPane().add(btnSave);
		
		JButton btnExit = new JButton("Exit");
		btnExit.setBackground(new Color(235, 235, 235));
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				System.exit(0);
			}
		});
		btnExit.setFont(new Font("Tahoma", Font.BOLD, 16));
		btnExit.setBounds(706, 367, 105, 53);
		frmK.getContentPane().add(btnExit);
		
		JButton btnClear = new JButton("Clear");
		btnClear.setBackground(new Color(193, 226, 230));
		btnClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				txtBand.setText("");
				txtAlbum.setText("");
				txtYear.setText("");
				txtPrice.setText("");
				txtBand.requestFocus();
				
			}
		});
		btnClear.setFont(new Font("Tahoma", Font.BOLD, 16));
		btnClear.setBounds(510, 367, 105, 53);
		frmK.getContentPane().add(btnClear);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(null, "Search", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_1.setBounds(21, 430, 425, 83);
		frmK.getContentPane().add(panel_1);
		panel_1.setLayout(null);
		
		JLabel lblNewLabel_2 = new JLabel("cd ID");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD, 40));
		lblNewLabel_2.setBounds(10, 22, 187, 43);
		panel_1.add(lblNewLabel_2);
		
		txtid = new JTextField();
		txtid.setFont(new Font("Tahoma", Font.BOLD, 16));
		txtid.addKeyListener(new KeyAdapter() {
			@Override
			// Search Records
			public void keyReleased(KeyEvent e) {
				
				try {
					
					String id = txtid.getText();
					pst = con.prepareStatement("select band,album,year,price from cd where cd_id = ?");
	                pst.setString(1, id);
	                ResultSet rs = pst.executeQuery();
	                
	                if(rs.next()==true) {
	                	
	                	String band = rs.getString(1);
	                	String album = rs.getString(2);
	                    String year = rs.getString(3);
	                    String price = rs.getString(4);
	                    
	                    txtBand.setText(band);
	                    txtAlbum.setText(album);
	                    txtYear.setText(year);
	                    txtPrice.setText(price);
	                    
	                } else {
	                	
	                	txtBand.setText("");
	                	txtAlbum.setText("");
	                    txtYear.setText("");
	                    txtPrice.setText("");
	                }
				} catch (SQLException ex) {
					
				}
				
				
				
			}
		});
		txtid.setBounds(241, 22, 154, 43);
		panel_1.add(txtid);
		txtid.setColumns(10);
		
		JButton btnUpdate = new JButton("Update");
		btnUpdate.setBackground(new Color(124, 162, 218));
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String band,album,id;
				int year,price;
				
				band = txtBand.getText();
				album = txtAlbum.getText();
				year = Integer.parseInt(txtYear.getText());
				price = Integer.parseInt(txtPrice.getText());
				id = txtid.getText();
				
				try {
					pst = con.prepareStatement("update cd set band=?,album=?,year=?,price=? where cd_id=?");
					pst.setString(1, band);
					pst.setString(2, album);
					pst.setInt(3, year);
					pst.setInt(4, price);
					pst.setString(5, id);
					pst.executeUpdate();
					
					JOptionPane.showMessageDialog(null, "Record Updated!");
					table_load();
					
					txtBand.setText("");
					txtAlbum.setText("");
					txtYear.setText("");
					txtPrice.setText("");
					txtBand.requestFocus();
					
				} catch (SQLException e1) {
					
					e1.printStackTrace();
				}
					
				
			}
		});
		btnUpdate.setFont(new Font("Tahoma", Font.BOLD, 16));
		btnUpdate.setBounds(173, 371, 105, 49);
		frmK.getContentPane().add(btnUpdate);
		
		JButton btnDelete = new JButton("Delete");
		btnDelete.setBackground(new Color(208, 147, 145));
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String id;
				
				id = txtid.getText();
				
				try {
					pst = con.prepareStatement("delete from cd where cd_id=?");
					
					pst.setString(1, id);
					pst.executeUpdate();
					
					JOptionPane.showMessageDialog(null, "Record Deleted!");
					table_load();
					
					txtBand.setText("");
					txtAlbum.setText("");
					txtYear.setText("");
					txtPrice.setText("");
					txtBand.requestFocus();
					
				} catch (SQLException e1) {
					
					e1.printStackTrace();
				}
				
				
			}
		});
		btnDelete.setFont(new Font("Tahoma", Font.BOLD, 16));
		btnDelete.setBounds(307, 371, 105, 49);
		frmK.getContentPane().add(btnDelete);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBounds(456, 67, 425, 294);
		frmK.getContentPane().add(panel_2);
		panel_2.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 0, 425, 294);
		panel_2.add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Band", "Album", "Year", "Price"
			}
		) {
			Class[] columnTypes = new Class[] {
				String.class, String.class, Integer.class, Integer.class
			};
			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
		});
	}
}
