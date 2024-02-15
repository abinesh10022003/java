package crud;


import java.awt.*;
import java.awt.event.*;
import java.sql.*;

class UserFrame extends Frame implements ActionListener
{	
	Label lblTitle,lblId,lblName,lblCity,lblAge,lblStatus;
	TextField txtName,txtId,txtCity,txtAge;
	Button btnSave,btnClear,btnDelete;
	
	//DataBase
			String qry = "";
			Connection con = null;
			PreparedStatement st = null;
			ResultSet rs = null;
			Statement stmt = null;
			
			//DB Connection
			public void connect() {
				try {
					Class.forName("com.mysql.cj.jdbc.Driver");
					String url ="jdbc:mysql://localhost:3306/dbjoes?characterEncoding=utf8";
					String username ="root";
					String password="root";
					con = DriverManager.getConnection(url,username,password);
				}catch (Exception ex) {
					ex.printStackTrace();
				}
			}
			
			//Clear Form Details
			public void Clear() {
				txtId.setText("");
				txtName.setText("");
				txtAge.setText("");
				txtCity.setText("");
				txtName.requestFocus();
				
			}
	
	public UserFrame() {
		connect();		
		this.setVisible(true);
		this.setSize(1000,600);
		this.setTitle("User Management System");
		this.setLayout(null);
		//Form Color
		Color formColor =new Color(53,59,72);
		setBackground(formColor);
		
		//Fonts
		Font titleFont =new Font("arial",Font.BOLD,25);
		Font labelFont =new Font("arial",Font.PLAIN,18);
		Font textFont =new Font("arial",Font.PLAIN,18);
		
		//Title Label
		lblTitle =new Label("User Management System");
		lblTitle.setBounds(250,40,400,50);
		lblTitle.setFont(titleFont);
		lblTitle.setForeground(Color.YELLOW);
		add(lblTitle);
		//Id
		lblId =new Label("ID");
		lblId.setBounds(250,100,150,30);
		lblId.setFont(labelFont);
		lblId.setForeground(Color.WHITE);
		add(lblId);
		
		txtId =new TextField();
		txtId.setBounds(400,100,400,30);
		txtId.setFont(textFont);
		txtId.addActionListener(this);
		add(txtId);
		
		//Name
		lblName =new Label("Name");
		lblName.setBounds(250,150,150,30);
		lblName.setFont(labelFont);
		lblName.setForeground(Color.WHITE);
		add(lblName);
		
		txtName =new TextField();
		txtName.setBounds(400,150,400,30);
		txtName.setFont(textFont);
		add(txtName);
		//Age
		lblAge =new Label("Age");
		lblAge.setBounds(250,200,150,30);
		lblAge.setFont(labelFont);
		lblAge.setForeground(Color.WHITE);
		add(lblAge);
		
		txtAge =new TextField();
		txtAge.setBounds(400,200,400,30);
		txtAge.setFont(textFont);
		add(txtAge);
		//City
		lblCity =new Label("City");
		lblCity.setBounds(250,250,150,30);
		lblCity.setFont(labelFont);
		lblCity.setForeground(Color.WHITE);
		add(lblCity);
		
		txtCity =new TextField();
		txtCity.setBounds(400,250,400,30);
		txtCity.setFont(textFont);
		add(txtCity);
		
		//All Buttons
		btnSave =new Button("Save");
		btnSave.setBounds(400,300,100,30);
		btnSave.setBackground(Color.BLUE);
		btnSave.setForeground(Color.WHITE);
		btnSave.setFont(labelFont);
		btnSave.addActionListener(this);
		add(btnSave);
		
		btnClear =new Button("Claer");
		btnClear.setBounds(520,300,100,30);
		btnClear.setBackground(Color.ORANGE);
		btnClear.setForeground(Color.WHITE);
		btnClear.setFont(labelFont);
		btnClear.addActionListener(this);
		add(btnClear);
		
		btnDelete =new Button("Delete");
		btnDelete.setBounds(640,300,100,30);
		btnDelete.setBackground(Color.RED);
		btnDelete.setForeground(Color.WHITE);
		btnDelete.setFont(labelFont);
		btnDelete.addActionListener(this);
		add(btnDelete);
		
		//Label Ststus
		lblStatus=new Label("--------");
		lblStatus.setFont(labelFont);
		lblStatus.setForeground(Color.WHITE);
		lblStatus.setBounds(400,350,300,30);
		add(lblStatus);
		
		
		//Close Button
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent we) {
				System.exit(0);
			}
		});
	}
	public void actionPerformed(ActionEvent e)
	{
		try {
			
			String id = txtId.getText();
			String name = txtName.getText();
			String age = txtAge.getText();
			String city = txtCity.getText();
			
			if(e.getSource().equals(txtId)) {
				//Get User By ID
				qry = "SELECT ID,NAME,AGE,CITY from users where ID=" +txtId.getText();
				stmt=con.createStatement();
				rs=stmt.executeQuery(qry);
				if(rs.next()) {
					txtId.setText(rs.getString("ID"));
					txtName.setText(rs.getString("NAME"));
					txtAge.setText(rs.getString("AGE"));
					txtCity.setText(rs.getString("CITY"));
				}else {
					Clear();
					lblStatus.setText("Invalid ID");
				}
			}
			
			if(e.getSource().equals(btnClear)) {
					Clear();
			}
			else if(e.getSource().equals(btnSave)){
				if(id.isEmpty() || id.equals("")) {
					//Save Details
					qry = "insert into users(NAME,AGE,CITY) values(?,?,?)";
					st=con.prepareStatement(qry);
					st.setString(1, name);
					st.setString(2, age);
					st.setString(3, city);
					st.executeUpdate();
					Clear();
					
					lblStatus.setText("Data Insert Success");
				}else {
					//Update Details
					qry = "update users set NAME=?,AGE=?,CITY=? where ID=?";
					st=con.prepareStatement(qry);
					st.setString(1, name);
					st.setString(2, age);
					st.setString(3, city);
					st.setString(4, id);
					st.executeUpdate();
					Clear();
					
					lblStatus.setText("Data Update Success");
				}
					
			   }
			else if(e.getSource().equals(btnDelete)){
				//Delete Details
				if(!id.isEmpty()||!id.equals("")) {
					qry="delete from users where ID=?";
					st =con.prepareStatement(qry);
					st.setString(1,id);
					st.executeUpdate();
					Clear();
					lblStatus.setText("Data Deleted Success");
				}else {
					lblStatus.setText("Please Enter the Correct ID");
				}
				
			   }
		    }
		catch(Exception ex) {
				ex.printStackTrace();
			}
			
	}
	
}

public class student {

	public static void main(String[] args) {
		UserFrame frm = new UserFrame();

	}

}

