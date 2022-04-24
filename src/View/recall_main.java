package View;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextPane;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class recall_main extends JFrame{

		private final String[] labels= {"날짜","점포명","POS번호"};
		private HintTextField tSt = new HintTextField("ex)20210101");
		private HintTextField tEt = new HintTextField("ex)20210101");
		private HintTextField tPt = new HintTextField("ex)1002");
		 
		private JScrollPane scrolledTable;
		private JTable table;
		private JTable table_sum;
		
		private JButton addBtn;
		private JButton delBtn;
		private JButton exlBtn;
		private final String[] str_code = {"백화점","남창원점","금곡점"};
		private JComboBox<String> combo = new JComboBox<String>(str_code);
		String header[]= {"일자","번호","현금","현금합계","상품권","상품권합계","물품","물품합계"};
		String header_sum[]= {"현금","현금합계","상품권","상품권합계","물품","물품합계"};
        String contents[][] = 
        {
        	{"현금","현금합계","상품권","상품권합계","물품","물품합계"},
        	{"","","","","",""}
        };
		DefaultTableModel model=new DefaultTableModel(header,0);
		DefaultTableModel model_sum=new DefaultTableModel(contents,header_sum);
		
		String str_code_num = "11";
		
		public static void main(String[] args) {
			new recall_main();
		}
		public recall_main() {
			
			getContentPane().setLayout(new BorderLayout(10,10));
			JPanel topPanel=new JPanel(new GridLayout(1,1,5,5));
			
			topPanel.add(new JLabel(labels[0]));
			topPanel.add(tSt);	
			topPanel.add(tEt);
			topPanel.add(new JLabel(labels[1]));
			topPanel.add(combo);
			topPanel.add(new JLabel(labels[2]));
			topPanel.add(tPt);	
			topPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
			getContentPane().add("North",topPanel);

			
			
			table=new JTable(model);
			scrolledTable=new JScrollPane(table);
			scrolledTable.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
			getContentPane().add("Center",scrolledTable);
			
			

			JPanel rightPanel=new JPanel(new GridLayout(5,10,10,10));
			
			addBtn=new JButton("검색");
			delBtn=new JButton("초기화");
			exlBtn= new JButton("엑셀");
			
			rightPanel.add(addBtn);
			rightPanel.add(delBtn);
			rightPanel.add(exlBtn);
			rightPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
			
			getContentPane().add("East",rightPanel);	
			
			
			table_sum=new JTable(model_sum);
			table_sum.setEnabled(false);
			table_sum.setBorder(BorderFactory.createEmptyBorder(1,1,5,5));
			getContentPane().add("South",table_sum);
			
			DefaultTableCellRenderer dtcr = new DefaultTableCellRenderer()  {
			    public Component getTableCellRendererComponent(JTable table,
			                 Object value, boolean isSelected, boolean hasFocus, int row, int column) {
			        if (value instanceof String) {          
			            String amount = value.toString();
			            this.setText(amount);
			            this.setHorizontalAlignment(this.CENTER);
			        } 
			        return this;
			    }  
			};
			for(int i = 0; i < 6; i++)
			{
				table_sum.getColumnModel().getColumn(i).setCellRenderer(dtcr);
			}
			
			tSt.addKeyListener(new KeyAdapter() {
				
				@Override
				public void keyPressed(KeyEvent e) {
					if(e.getKeyChar() == '\n')
					{
						addRow(tSt.getText(),tEt.getText(),tPt.getText(),str_code_num);
					}
				}
			});
			tEt.addKeyListener(new KeyAdapter() {
				
				@Override
				public void keyPressed(KeyEvent e) {
					if(e.getKeyChar() == '\n')
					{
						addRow(tSt.getText(),tEt.getText(),tPt.getText(),str_code_num);
					}
				}
			});
			tPt.addKeyListener(new KeyAdapter() {
				
				@Override
				public void keyPressed(KeyEvent e) {
					if(e.getKeyChar() == '\n')
					{
						addRow(tSt.getText(),tEt.getText(),tPt.getText(),str_code_num);
					}
				}
			});
			combo.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					JComboBox<String> cb = (JComboBox)e.getSource(); 
					int index = cb.getSelectedIndex();
					switch (index) {
					case 0:
						str_code_num = "11";
						break;
					case 1:
						str_code_num = "33";
						break;
					case 2:
						str_code_num = "34";
						break;

					default:
						break;
					}
				}
			});
			
			addBtn.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					addRow(tSt.getText(),tEt.getText(),tPt.getText(),str_code_num);
				}
			});
			
			delBtn.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					model.setNumRows(0);
				}
			});
			
			exlBtn.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					String result = null;
					if(table.getRowCount() == 0)
					{
						result = "저장할 값이 없습니다";
					}
					else
					{
						result = excel_export.export(table,table_sum);
					}
					JOptionPane.showMessageDialog(null, result,"Message",JOptionPane.INFORMATION_MESSAGE);
				}
			});
			
			this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			this.setSize(620,400);
			this.setLocationRelativeTo(null);
			this.setVisible(true);
			table.getTableHeader().setReorderingAllowed(false);
			table_sum.getTableHeader().setReorderingAllowed(false);
			setResizable(false);
	}
		
		public void addRow(String s_date,String e_date, String pos, String str_code) {

			if(pos.equals("ex)1002"))
			{
				pos = "";
			}
			createData cd = new createData();
			ArrayList<String[]> pos_list =  cd.checkPos(s_date, e_date, pos,str_code);
			for(int i = 0; i < pos_list.size(); i++)
			{
				model.insertRow(i,pos_list.get(i));
			}
			addSum();

		}
		public void addSum()
		{
			for(int i = 2; i < 8; i++)
			{
				int chk_sum = 0;
				for(int j = 0; j < table.getRowCount(); j++)
				{
					chk_sum += Integer.parseInt((String)table.getValueAt(j, i));
				}
				table_sum.setValueAt(chk_sum+"", 1, i - 2);
			}
		}

		
		
		
}