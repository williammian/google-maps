/*
 * Created by JFormDesigner on Thu Feb 24 13:08:58 BRT 2022
 */

package br.com.maps;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.kingaspx.util.BrowserUtil;
import com.kingaspx.version.Version;
import com.teamdev.jxbrowser.chromium.Browser;
import com.teamdev.jxbrowser.chromium.Callback;
import com.teamdev.jxbrowser.chromium.dom.By;
import com.teamdev.jxbrowser.chromium.dom.DOMDocument;
import com.teamdev.jxbrowser.chromium.dom.DOMElement;
import com.teamdev.jxbrowser.chromium.dom.DOMInputElement;
import com.teamdev.jxbrowser.chromium.events.ConsoleEvent;
import com.teamdev.jxbrowser.chromium.events.FinishLoadingEvent;
import com.teamdev.jxbrowser.chromium.events.LoadAdapter;
import com.teamdev.jxbrowser.chromium.swing.BrowserView;


public class Tela extends JFrame {
	
	Browser browser;
    BrowserView view;
    
    private String API_KEY = "your_api_key_google_maps";
	
	public Tela() {
		initComponents();
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	private void initComponents() {
		// JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
		// Generated using JFormDesigner non-commercial license
		panel1 = new JPanel();
		lblMaps = new JLabel();
		txtLocal = new JTextField();
		btnBuscar = new JButton();
		pnlMapa = new JPanel();
		btnRecarregar = new JButton();

		//======== this ========
		var contentPane = getContentPane();
		contentPane.setLayout(null);

		//======== panel1 ========
		{
			panel1.setBackground(Color.white);
			panel1.setLayout(null);

			//---- lblMaps ----
			lblMaps.setText("Maps");
			lblMaps.setFont(new Font("Segoe UI Black", Font.PLAIN, 24));
			panel1.add(lblMaps);
			lblMaps.setBounds(new Rectangle(new Point(10, 6), lblMaps.getPreferredSize()));
			panel1.add(txtLocal);
			txtLocal.setBounds(85, 11, 440, txtLocal.getPreferredSize().height);

			//---- btnBuscar ----
			btnBuscar.setText("Buscar");
			btnBuscar.addActionListener(e -> btnBuscarActionPerformed(e));
			panel1.add(btnBuscar);
			btnBuscar.setBounds(530, 10, 129, 25);

			//======== pnlMapa ========
			{
				pnlMapa.setLayout(new BorderLayout());
			}
			panel1.add(pnlMapa);
			pnlMapa.setBounds(2, 38, 792, 489);

			//---- btnRecarregar ----
			btnRecarregar.setText("Recarregar");
			btnRecarregar.addActionListener(e -> btnRecarregarActionPerformed(e));
			panel1.add(btnRecarregar);
			btnRecarregar.setBounds(665, 10, 129, 25);

			{
				// compute preferred size
				Dimension preferredSize = new Dimension();
				for(int i = 0; i < panel1.getComponentCount(); i++) {
					Rectangle bounds = panel1.getComponent(i).getBounds();
					preferredSize.width = Math.max(bounds.x + bounds.width, preferredSize.width);
					preferredSize.height = Math.max(bounds.y + bounds.height, preferredSize.height);
				}
				Insets insets = panel1.getInsets();
				preferredSize.width += insets.right;
				preferredSize.height += insets.bottom;
				panel1.setMinimumSize(preferredSize);
				panel1.setPreferredSize(preferredSize);
			}
		}
		contentPane.add(panel1);
		panel1.setBounds(1, 2, 796, 530);

		{
			// compute preferred size
			Dimension preferredSize = new Dimension();
			for(int i = 0; i < contentPane.getComponentCount(); i++) {
				Rectangle bounds = contentPane.getComponent(i).getBounds();
				preferredSize.width = Math.max(bounds.x + bounds.width, preferredSize.width);
				preferredSize.height = Math.max(bounds.y + bounds.height, preferredSize.height);
			}
			Insets insets = contentPane.getInsets();
			preferredSize.width += insets.right;
			preferredSize.height += insets.bottom;
			contentPane.setMinimumSize(preferredSize);
			contentPane.setPreferredSize(preferredSize);
		}
		pack();
		setLocationRelativeTo(getOwner());
		// JFormDesigner - End of component initialization  //GEN-END:initComponents
	
		open_site();
	}

	// JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
	// Generated using JFormDesigner non-commercial license
	private JPanel panel1;
	private JLabel lblMaps;
	private JTextField txtLocal;
	private JButton btnBuscar;
	private JPanel pnlMapa;
	private JButton btnRecarregar;
	// JFormDesigner - End of variables declaration  //GEN-END:variables

	public static void main(String[] args) {
		java.awt.EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				Tela tela = new Tela();
				tela.setVisible(true);
			}
		});
	}
	
	private void btnRecarregarActionPerformed(ActionEvent e) {
		Browser.invokeAndWaitFinishLoadingMainFrame(browser, new Callback<Browser>() {
        	@Override
            public void invoke(Browser brw) {
                brw.reload();
            }
        });
		loadMap();
	}
	
	private void btnBuscarActionPerformed(ActionEvent e) {
		DOMDocument doc = browser.getDocument();

        DOMElement address_element = doc.findElement(By.id("address"));
        DOMElement search_element = doc.findElement(By.id("submit"));
        DOMElement button = (DOMElement) search_element;

        DOMInputElement address = (DOMInputElement) address_element;
        address.setValue(txtLocal.getText());

        button.click();
        
        //browser.executeJavaScript("geocodeAddress2('" + txtLocal.getText() + "');");
	}
	
	private void open_site() {
        BrowserUtil.setVersion(Version.V6_22);

        browser = new Browser();
        view = new BrowserView(browser);

        pnlMapa.add(view, BorderLayout.CENTER);

        browser.addConsoleListener((ConsoleEvent evt) -> {
            System.out.println("LOG: " + evt.getMessage());
        });
        
        browser.addLoadListener(new LoadAdapter() {
			@Override
            public void onFinishLoadingFrame(FinishLoadingEvent evt) {
                evt.getBrowser().setZoomLevel(-2);
            }
        });

        String userDir = System.getProperty("user.dir");
        String url = userDir + "\\HTMLGMaps\\simplemap\\simple_map.html";
        
        loadView(url);
    }	
	
	private void loadView(String url) {
		Browser.invokeAndWaitFinishLoadingMainFrame(browser, new Callback<Browser>() {
        	@Override
            public void invoke(Browser brw) {
                brw.loadURL(url);
            }
        });
		loadMap();
	}

	private void loadMap() {
		browser.executeJavaScript("loadMap('" + API_KEY + "');");
	}
}
