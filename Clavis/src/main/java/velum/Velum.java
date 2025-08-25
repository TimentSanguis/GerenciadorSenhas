package velum;

import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultCellEditor;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.SwingConstants;

public class Velum {

	private JFrame frmSenhasFodas;
	private JTable table;
	private JTextField txtDesc;
	private JTextField txtSenha;
	private JLabel lbl_aviso;
	private JLabel lblNewLabel;
	private JLabel lblNewLabel_1;
	private JLabel lblNewLabel_2;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Velum window = new Velum();
					window.frmSenhasFodas.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Velum() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmSenhasFodas = new JFrame();
		frmSenhasFodas.setIconImage(Toolkit.getDefaultToolkit().getImage("E:\\eclipse-workspace\\GerenciadorSenhas\\src\\main\\resources\\chave.png"));
		frmSenhasFodas.setAutoRequestFocus(false);
		frmSenhasFodas.setFont(new Font("Comic Sans MS", Font.PLAIN, 12));
		frmSenhasFodas.setTitle("Gerenciador de senhas");
		frmSenhasFodas.setBounds(0, 0, 720, 480);
		frmSenhasFodas.setLocationRelativeTo(null);
		frmSenhasFodas.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		String[] colunas = {"ID", "Descricao", "Senha","Copiar","Excluir"};
		DefaultTableModel model = new DefaultTableModel(colunas, 0);
				
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 0, 500, 442);
		scrollPane.setViewportBorder(null);
		frmSenhasFodas.getContentPane().add(scrollPane);
		table = new JTable(model);
		scrollPane.setViewportView(table);
		Nexum.carregarDados(table);
		
		
		
		txtDesc = new JTextField();
		txtDesc.setBounds(510, 60, 103, 20);
		frmSenhasFodas.getContentPane().add(txtDesc);
		txtDesc.setColumns(10);
		
		txtSenha = new JTextField();
		txtSenha.setBounds(510, 137, 167, 20);
		txtSenha.setColumns(10);
		frmSenhasFodas.getContentPane().add(txtSenha);
		
		lbl_aviso = new JLabel("");
		lbl_aviso.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_aviso.setBounds(510, 261, 184, 41);
		frmSenhasFodas.getContentPane().add(lbl_aviso);
		
		lblNewLabel = new JLabel("Descrição");
		lblNewLabel.setBounds(510, 35, 71, 14);
		frmSenhasFodas.getContentPane().add(lblNewLabel);
		
		lblNewLabel_1 = new JLabel("Senha");
		lblNewLabel_1.setBounds(510, 112, 46, 14);
		frmSenhasFodas.getContentPane().add(lblNewLabel_1);
		
		lblNewLabel_2 = new JLabel("Tamanho Senha");
		lblNewLabel_2.setBounds(601, 35, 114, 14);
		frmSenhasFodas.getContentPane().add(lblNewLabel_2);
		
		JSpinner spnCaracteres = new JSpinner();
		spnCaracteres.setModel(new SpinnerNumberModel(8, 8, 255, 1));
		spnCaracteres.setBounds(623, 60, 71, 20);
		frmSenhasFodas.getContentPane().add(spnCaracteres);

		JButton btnNewButton = new JButton("");
		btnNewButton.setBounds(673, 137, 21, 20);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int tamanha = (int) spnCaracteres.getValue();
				txtSenha.setText(Munus.senhaAleatoria(tamanha));
			}
		});

		frmSenhasFodas.getContentPane().setLayout(null);
		btnNewButton.setIcon(new ImageIcon("E:\\eclipse-workspace\\GerenciadorSenhas\\src\\main\\resources\\Iconsmind-Outline-Reload-2-2.16.png"));
		frmSenhasFodas.getContentPane().add(btnNewButton);
		
		JButton BtnSalvar = new JButton("Salvar Senha");
		BtnSalvar.setBounds(529, 186, 148, 52);
		BtnSalvar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Munus.inserirSenha(txtDesc.getText(), txtSenha.getText());
				Nexum.carregarDados(table);
			}
		});
		frmSenhasFodas.getContentPane().add(BtnSalvar);
		
		// editores e criadores dos botoes da tabela
		class ButtonRenderer extends JButton implements TableCellRenderer {
		    public ButtonRenderer() {
		        setOpaque(true);
		    }

		    @Override
		    public Component getTableCellRendererComponent(JTable table, Object value,
		            boolean isSelected, boolean hasFocus, int row, int column) {
		        setText((value == null) ? "Botão" : value.toString());
		        return this;
		    }
		}
		
		class ButtonEditor extends DefaultCellEditor {
		    private JButton button;
		    private String label;
		    private boolean clicado;
		    private int row;

		    public ButtonEditor(JCheckBox checkBox, JTable table) {
		        super(checkBox);
		        button = new JButton();
		        button.setOpaque(true);

		        // ação ao clicar no botão
		        button.addActionListener(e -> {
		            fireEditingStopped(); // necessário para parar edição
		            String senha = table.getValueAt(row, 2).toString(); // coluna da senha
		            Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(senha), null);
		            JOptionPane.showMessageDialog(null, "Senha copiada: " + senha);
		        });
		    }
		    
		    @Override
		    public Component getTableCellEditorComponent(JTable table, Object value,boolean isSelected, int row, int column) {
		        label = (value == null) ? "Botão" : value.toString();
		        button.setText(label);
		        this.row = row;
		        clicado = true;
		        return button;
		    }

		    @Override
		    public Object getCellEditorValue() {
		        clicado = false;
		        return label;
		    }
		}
		
		class ButtonExcludeEditor extends DefaultCellEditor{
	    	private JButton button;
	    	private JTable table;
	    	private int row;
	    	
  			public ButtonExcludeEditor(JCheckBox checkBox, JTable table) {
		    	super(checkBox);
		    	this.table = table;
		    	
		    	button = new JButton("excluir");
		    	button.setOpaque(true);
		    	
		    	button.addActionListener(e ->{
		    		fireEditingStopped();
		    		int id = (int) table.getValueAt(row,0);
		    		String desc = table.getValueAt(row,1).toString();
		    		String mensagem = String.format("Tem certeza que deseja excluir a senha de '%s'?",desc);
		    		int result = JOptionPane.showConfirmDialog(null,mensagem,"Teste",
										    				   JOptionPane.YES_NO_OPTION,
										    				   JOptionPane.QUESTION_MESSAGE);
		    		if (result == JOptionPane.YES_OPTION) {
		    			Munus.excluirSenha(id);
		    			Nexum.carregarDados(table);	    			
		    		}
		    	});
		    }	
  			 @Override
  		    public Component getTableCellEditorComponent(JTable table, Object value,
  		            boolean isSelected, int row, int column) {
  		        this.row = row;
  		        return button;
  		    }

  		    @Override
  		    public Object getCellEditorValue() {
  		        return "Excluir";
  		    }
	    }
		
		//botoes da tabela
		table.getColumnModel().getColumn(3).setCellRenderer(new ButtonRenderer());
		table.getColumnModel().getColumn(3).setCellEditor(new ButtonEditor(new JCheckBox(), table));
		table.getColumnModel().getColumn(4).setCellRenderer(new ButtonRenderer());
		table.getColumnModel().getColumn(4).setCellEditor(new ButtonExcludeEditor(new JCheckBox(), table));
		
		txtSenha.getDocument().addDocumentListener(new DocumentListener(){			
			@Override
			public void insertUpdate(DocumentEvent e) {		
				processar();
			}
			
			@Override
			public void removeUpdate(DocumentEvent e) {		
				processar();
			}
			
			@Override
			public void changedUpdate(DocumentEvent e) {
				processar();
			}	
			private void processar() {
				String senha = txtSenha.getText();
				lbl_aviso.setText(Munus.validate(senha));
			}
		}
		);
		
	}
}
